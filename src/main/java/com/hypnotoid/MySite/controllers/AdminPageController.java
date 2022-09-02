package com.hypnotoid.MySite.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AdminPageController {
    private final BCryptPasswordEncoder encoder;

    public AdminPageController(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "adminPage";
    }

    @PostMapping("/adminEncrypt")
    public RedirectView encryptPassword(RedirectAttributes attributes, String password) {
        attributes.addFlashAttribute("encoded", encoder.encode(password));
        return new RedirectView("/admin");
    }
}
