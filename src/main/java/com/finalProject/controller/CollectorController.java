package com.finalProject.controller;

import com.finalProject.model.SmartBin;
import com.finalProject.service.SmartBinService;
import com.finalProject.util.QRCodeGenerator;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/collector")
public class CollectorController {

    private final SmartBinService smartBinService;
    private final QRCodeGenerator qrCodeGenerator;

    @Autowired
    public CollectorController(SmartBinService smartBinService,
                               QRCodeGenerator qrCodeGenerator) {
        this.smartBinService = smartBinService;
        this.qrCodeGenerator = qrCodeGenerator;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size) {
        // Get pending collection requests
        Page<SmartBin> bins = smartBinService.findByStatus("FULL", PageRequest.of(page, size));
        model.addAttribute("bins", bins);
        return "collector/dashboard";
    }


    @GetMapping("/bins/new")
    public String showCreateBinForm(Model model) {
        model.addAttribute("smartBin", new SmartBin());
        return "collector/create-bin";
    }

    @PostMapping("/bins/create")
    public ResponseEntity<ByteArrayResource> createBin(@ModelAttribute SmartBin smartBin)
            throws IOException, WriterException {

        smartBin.setStatus("EMPTY");
        SmartBin savedBin = smartBinService.saveSmartBin(smartBin);

        // Generate QR Code with bin ID
        byte[] qrCode = qrCodeGenerator.generateQRCode(
                "BIN_ID:" + savedBin.getId(),
                300, 300
        );
        savedBin.setQrCode(qrCode);
        smartBinService.updateSmartBin(savedBin.getId(), savedBin);

        // Generate PDF with QR Code
        ByteArrayResource pdf = qrCodeGenerator.generateQRCodePDF(qrCode, savedBin.getId());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=BinQR_" + savedBin.getId() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/bins/edit/{id}")
    public String showEditBinForm(@PathVariable Long id, Model model) {
        SmartBin bin = smartBinService.getSmartBinById(id);
        model.addAttribute("smartBin", bin);
        return "collector/edit-bin";
    }

    @PostMapping("/bins/edit/{id}")
    public String updateBin(@PathVariable Long id, @ModelAttribute SmartBin smartBin) {
        smartBin.setId(id);
        smartBinService.updateSmartBin(id, smartBin);
        return "redirect:/collector/dashboard";
    }

    @GetMapping("/bins/delete/{id}")
    public String deleteBin(@PathVariable Long id) {
        smartBinService.deleteSmartBin(id);
        return "redirect:/collector/dashboard";
    }

    @PostMapping("/bins/flag/{id}")
    public String flagBin(@PathVariable Long id) {
        SmartBin bin = smartBinService.getSmartBinById(id);
        bin.setStatus("BROKEN");
        smartBinService.updateSmartBin(id, bin);
        return "redirect:/collector/dashboard";
    }

    @PostMapping("/bins/collect/{id}")
    public String markAsCollected(@PathVariable Long id) {
        SmartBin bin = smartBinService.getSmartBinById(id);
        bin.setStatus("EMPTY");
        bin.setLastCollectedAt(java.time.LocalDateTime.now());
        smartBinService.updateSmartBin(id, bin);
        return "redirect:/collector/dashboard";
    }

    @GetMapping("/bins/qr/{binId}")
    public ResponseEntity<ByteArrayResource> downloadQR(@PathVariable Long binId) {
        SmartBin bin = smartBinService.getSmartBinById(binId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=BinQR_" + binId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new ByteArrayResource(bin.getQrCode()));
    }
}