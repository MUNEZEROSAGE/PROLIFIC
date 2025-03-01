package com.finalProject.controller;

import com.finalProject.model.Citizen;
import com.finalProject.model.CollectionRequest;
import com.finalProject.model.SmartBin;
import com.finalProject.service.CitizenService;
import com.finalProject.service.SmartBinService;
import com.finalProject.service.CollectionRequestService;
import com.finalProject.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/collector")
public class CollectorController {

    private final SmartBinService smartBinService;
    private final CollectionRequestService collectionRequestService;
    private final CitizenService citizenService; // Add this

    @Autowired
    public CollectorController(SmartBinService smartBinService,
                               CollectionRequestService collectionRequestService,
                               CitizenService citizenService) { // Add this parameter
        this.smartBinService = smartBinService;
        this.collectionRequestService = collectionRequestService;
        this.citizenService = citizenService; // Initialize
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Authentication authentication) {
        // Add collector profile info
        Citizen collector = citizenService.getCitizenByEmail(authentication.getName());
        model.addAttribute("collectorName", collector.getName());
        model.addAttribute("role", collector.getRoles().iterator().next());
        if (collector.getProfilePic() != null) {
            model.addAttribute("profilePic", ImageUtil.convertToBase64(collector.getProfilePic()));
        }

        // Add dashboard metrics
        long totalBins = smartBinService.getAllSmartBins(Pageable.unpaged()).getTotalElements();
        long halfFullBins = smartBinService.findByStatus("HALF_FULL", Pageable.unpaged()).getTotalElements();

        model.addAttribute("totalBins", totalBins);
        model.addAttribute("halfFullBins", halfFullBins);

        return "collector/dashboard";
    }

    @GetMapping("/bins")
    public String viewBins(Model model, Authentication authentication,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size) {
        // Add collector profile info
        Citizen collector = citizenService.getCitizenByEmail(authentication.getName());
        model.addAttribute("collectorName", collector.getName());
        model.addAttribute("role", collector.getRoles().iterator().next());
        if (collector.getProfilePic() != null) {
            model.addAttribute("profilePic", ImageUtil.convertToBase64(collector.getProfilePic()));
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("lastCollectedAt").descending());
        Page<SmartBin> binsPage = smartBinService.getAllSmartBins(pageable);

        model.addAttribute("bins", binsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", binsPage.getTotalPages());
        return "collector/bins";
    }

    // Add a new bin
    @GetMapping("/bins/add")
    public String showAddBinForm(Model model) {
        model.addAttribute("bin", new SmartBin());
        return "collector/add-bin";
    }

    @PostMapping("/bins/add")
    public String addBin(@ModelAttribute SmartBin bin) {
        bin.setStatus("EMPTY"); // Default status for new bins
        bin.setFillLevel(0); // Default fill level
        bin.setLastCollectedAt(LocalDateTime.now()); // Set current timestamp
        smartBinService.saveSmartBin(bin);
        return "redirect:/collector/bins";
    }

    // Update an existing bin
    @GetMapping("/bins/edit/{id}")
    public String showEditBinForm(@PathVariable Long id, Model model) {
        SmartBin bin = smartBinService.getSmartBinById(id);
        if (bin == null) {
            return "redirect:/collector/bins"; // Redirect if bin not found
        }
        model.addAttribute("bin", bin);
        return "collector/edit-bin";
    }

    @PostMapping("/bins/edit/{id}")
    public String updateBin(@PathVariable Long id, @ModelAttribute SmartBin bin) {
        if (smartBinService.getSmartBinById(id) != null) {
            bin.setId(id); // Ensure the ID is set
            smartBinService.updateSmartBin(id, bin);
        }
        return "redirect:/collector/bins";
    }

    // Delete a bin
    @GetMapping("/bins/delete/{id}")
    public String deleteBin(@PathVariable Long id) {
        smartBinService.deleteSmartBin(id);
        return "redirect:/collector/bins";
    }

    // 1. Flag bin as BROKEN (POST)
    @PostMapping("/flag-broken/{id}")
    public String flagBrokenBin(@PathVariable Long id) {
        SmartBin bin = smartBinService.getSmartBinById(id);
        bin.setStatus("BROKEN");
        smartBinService.updateSmartBin(id, bin);
        return "redirect:/collector/bins";
    }

    // 2. Mark bin as EMPTY (POST)
    @PostMapping("/mark-empty/{id}")
    public String markBinEmpty(@PathVariable Long id) {
        SmartBin bin = smartBinService.getSmartBinById(id);
        bin.setStatus("EMPTY");
        bin.setFillLevel(0);
        bin.setLastCollectedAt(LocalDateTime.now());
        smartBinService.updateSmartBin(id, bin);

        // Auto-close related collection requests
        collectionRequestService.findBySmartBinId(id, Pageable.unpaged())
                .forEach(request -> {
                    request.setStatus("COMPLETED");
                    request.setCompletedAt(LocalDateTime.now());
                    collectionRequestService.updateCollectionRequest(request.getId(), request);
                });

        return "redirect:/collector/bins";
    }

    // 3. Auto-create CollectionRequest when fill level >85%
    // Add this to wherever you update the bin's fill level (e.g., in a service)
    private void checkAndCreateCollectionRequest(SmartBin bin) {
        if (bin.getFillLevel() > 85) {
            CollectionRequest request = new CollectionRequest();
            request.setSmartBin(bin);
            request.setStatus("PENDING");
            request.setRequestedAt(LocalDateTime.now());
            collectionRequestService.saveCollectionRequest(request);
        }
    }

    // 4. View Collection Requests
    @GetMapping("/collection-requests")
    public String viewCollectionRequests(Model model,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("requestedAt").descending());
        Page<CollectionRequest> requests = collectionRequestService.findByStatus("PENDING", pageable);

        model.addAttribute("requests", requests);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", requests.getTotalPages());
        return "collector/collection-requests";
    }

    // Scan Waste Item for a Bin
    @GetMapping("/bins/scan/{id}")
    public String showScanPage(@PathVariable Long id, Model model) {
        model.addAttribute("bin", smartBinService.getSmartBinById(id));
        return "collector/scan";
    }

    // API to increment fill level
    @PostMapping("/api/bins/{id}/increment-fill")
    @ResponseBody
    public ResponseEntity<String> incrementFillLevel(
            @PathVariable Long id,
            @RequestParam int amount
    ) {
        SmartBin bin = smartBinService.getSmartBinById(id);
        bin.setFillLevel(bin.getFillLevel() + amount);
        smartBinService.updateSmartBin(id, bin);

        // Auto-create collection request if fill >85%
        if (bin.getFillLevel() > 85) {
            CollectionRequest request = new CollectionRequest();
            request.setSmartBin(bin);
            request.setStatus("PENDING");
            request.setRequestedAt(LocalDateTime.now());
            collectionRequestService.saveCollectionRequest(request);
        }

        return ResponseEntity.ok("Fill level updated");
    }
}