package com.finalProject.controller;

import com.finalProject.model.Citizen;
import com.finalProject.service.CitizenService;
import com.finalProject.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CitizenService citizenService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(CitizenService citizenService, PasswordEncoder passwordEncoder) {
        this.citizenService = citizenService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = {"/citizens", "/", "/home"})
    public String getAllcitizens(Model model, Authentication authentication,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "6") int size,
                                @RequestParam(defaultValue = "name") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Citizen> citizenPage = citizenService.getAllCitizens(pageable);

        String email = authentication.getName();
        Citizen citizen = citizenService.getCitizenByEmail(email);
        model.addAttribute("fullName", citizen.getName());
        model.addAttribute("role", citizen.getRoles().iterator().next());
        if (citizen.getProfilePic() != null) {
            model.addAttribute("profilePic", ImageUtil.convertToBase64(citizen.getProfilePic()));
        }else{
            model.addAttribute("profilePic", "/media/images.png");
        }

        model.addAttribute("citizens", citizenPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", citizenPage.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        return "admin/home";
    }

    @GetMapping("/citizens/get")
    public String showAddcitizenForm(Model model) {
        model.addAttribute("citizen", new Citizen());
        return "admin/add-citizen";
    }

    @PostMapping("/citizens/add")
    public String addcitizen(@ModelAttribute Citizen citizen, @RequestParam("profilePicFile") MultipartFile profilePicFile, @RequestParam("roles") List<String> roles) {
        try {
            if (!profilePicFile.isEmpty()) {
                citizen.setProfilePic(profilePicFile.getBytes());
            }
            citizen.setPassword(passwordEncoder.encode(citizen.getPassword()));
            for (String role : roles) {
                citizen.addRole(role);
            }
            citizenService.saveCitizen(citizen);
        } catch (IOException e) {
            // Handle file processing error
            e.printStackTrace();
        }
        return "redirect:/admin/citizens";
    }

    @GetMapping("/citizens/edit/{id}")
    public String showEditcitizenForm(@PathVariable Long id, Model model) {
        Citizen citizen = citizenService.getCitizenById(id);
        if (citizen == null) {
            // Handle citizen not found scenario
            return "redirect:/admin/citizens";
        }
        model.addAttribute("citizen", citizen);
        return "admin/edit-citizen";
    }

    @PostMapping("/citizens/edit/{id}")
    public String editcitizen(@PathVariable Long id, @ModelAttribute Citizen citizen, @RequestParam("profilePicFile") MultipartFile profilePicFile, @RequestParam("roles") List<String> roles) {
        try {
            Citizen existingCitizen = citizenService.getCitizenById(id);
            if (existingCitizen != null) {
                if (!profilePicFile.isEmpty()) {
                    citizen.setProfilePic(profilePicFile.getBytes());
                } else {
                    citizen.setProfilePic(existingCitizen.getProfilePic());
                }
                if (!citizen.getPassword().isEmpty()) {
                    citizen.setPassword(passwordEncoder.encode(citizen.getPassword()));
                } else {
                    citizen.setPassword(existingCitizen.getPassword());
                }
                citizen.getRoles().clear();
                for (String role : roles) {
                    citizen.addRole(role);
                }
                citizenService.updateCitizen(id, citizen);
            }
        } catch (IOException e) {
            // Handle file processing error
            e.printStackTrace();
        }
        return "redirect:/admin/citizens";
    }

    @GetMapping("/citizens/delete/{id}")
    public String deletecitizen(@PathVariable Long id) {
        citizenService.deleteCitizen(id);
        return "redirect:/admin/citizens";
    }
}