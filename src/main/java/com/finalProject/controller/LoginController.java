//package com.finalProject.controller;
//
//import com.finalProject.model.Citizen;
//import com.finalProject.service.CitizenService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Controller
//public class LoginController {
//
//    private final CitizenService citizenService;
//
//    @Autowired
//    public LoginController(CitizenService citizenService) {
//        this.citizenService = citizenService;
//    }
//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }
//
//    @GetMapping("/default")
//    public String defaultAfterLogin(Authentication authentication) {
//        Set<String> roles = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toSet());
//
//        if (roles.contains("ROLE_ADMIN")) {
//            return "redirect:/admin/citizens";
//        } else if (roles.contains("ROLE_COLLECTOR")) {
//            return "redirect:/collector/dashboard";}
//        else if (roles.contains("ROLE_CITIZEN")) {
//            return "redirect:/citizen/home";
//        } else {
//            return "redirect:/";
//        }
//    }
//}

package com.finalProject.controller;

import com.finalProject.model.Citizen;
import com.finalProject.service.CitizenService;
import com.finalProject.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class LoginController {

    private final CitizenService citizenService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(CitizenService citizenService, PasswordEncoder passwordEncoder) {
        this.citizenService = citizenService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/default")
    public String defaultAfterLogin(Authentication authentication) {
        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        if (roles.contains("ROLE_ADMIN")) {
            return "redirect:/admin/citizens";
        } else if (roles.contains("ROLE_COLLECTOR")) {
            return "redirect:/collector/dashboard";}
        else if (roles.contains("ROLE_CITIZEN")) {
            return "redirect:/citizen/verify";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("citizen", new Citizen());
        return "signup";
    }

    @PostMapping("/signup")
    public String registerCitizen(@ModelAttribute Citizen citizen,
                                  @RequestParam(value = "profilePicFile", required = false) MultipartFile profilePicFile) {
        try {
            // Set profile picture if provided
            if (profilePicFile != null && !profilePicFile.isEmpty()) {
                citizen.setProfilePic(profilePicFile.getBytes());
            }

            // Set creation date
            citizen.setCreatedAt(LocalDate.now());

            // Encode password
            citizen.setPassword(passwordEncoder.encode(citizen.getPassword()));

            // Add CITIZEN role
            citizen.addRole("CITIZEN");
            // Save the citizen
            citizenService.saveCitizen(citizen);
            // Redirect to login page with success message
            return "redirect:/login?signup=success";
        } catch (IOException e) {
            // Handle file processing error
            e.printStackTrace();
            return "redirect:/signup?error=true";
        }
    }
}
