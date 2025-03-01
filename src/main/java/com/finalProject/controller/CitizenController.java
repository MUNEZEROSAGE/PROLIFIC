package com.finalProject.controller;

import com.finalProject.model.Citizen;
import com.finalProject.model.VerificationCode;
import com.finalProject.model.VerificationRequest;
import com.finalProject.service.CitizenService;
import com.finalProject.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/citizen")
public class CitizenController {

//    private final VerificationCodeService verificationCodeService;
//    private final CitizenService citizenService;
//
//    @Autowired
//    public CitizenController(VerificationCodeService verificationCodeService,
//                             CitizenService citizenService) {
//        this.verificationCodeService = verificationCodeService;
//        this.citizenService = citizenService;
//    }
//
//    @GetMapping("/redeem")
//    public String showRedeemForm(Model model) {
//        model.addAttribute("code", new VerificationCode());
//        return "citizen/redeem-code";
//    }
//
//    @PostMapping("/redeem")
//    public String redeemCode(@ModelAttribute("code") VerificationCode codeInput,
//                             Authentication authentication, Model model) {
//        try {
//            Citizen citizen = citizenService.getCitizenByEmail(authentication.getName());
//            verificationCodeService.redeemCode(codeInput.getCode(), citizen);
//            model.addAttribute("success", "Tokens added successfully!");
//        } catch (RuntimeException e) {
//            model.addAttribute("error", e.getMessage());
//        }
//        return "citizen/redeem-code";
//    }


    private final CitizenService citizenService;
    private final VerificationCodeService verificationCodeService;

    @Autowired
    public CitizenController(CitizenService citizenService,
                             VerificationCodeService verificationCodeService) {
        this.citizenService = citizenService;
        this.verificationCodeService = verificationCodeService;
    }

    @GetMapping("/verify")
    public String showVerificationForm(Model model) {
        model.addAttribute("verificationRequest", new VerificationRequest()); // Ensure this line exists
        return "citizen/verify";
    }

    @PostMapping("/verify")
    public String processVerification(@ModelAttribute VerificationRequest request,
                                      Authentication authentication,
                                      RedirectAttributes redirectAttributes) {
        // Get logged-in citizen
        String email = authentication.getName();
        Citizen citizen = citizenService.getCitizenByEmail(email);

        if (verificationCodeService.isValidCode(request.getCode())) {
            // Award tokens
            citizenService.addEcoPoints(citizen.getId(), 10); // 10 points per bottle
            verificationCodeService.invalidateCode(request.getCode());
            redirectAttributes.addFlashAttribute("success", "Verified! 10 eco-points added!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid verification code");
        }

        return "redirect:/citizen/verify";
    }
}