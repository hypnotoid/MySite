package com.hypnotoid.MySite.controllers;

import com.hypnotoid.MySite.dto.UserDTO;
import com.hypnotoid.MySite.services.UserDTOService;
import com.hypnotoid.MySite.validators.UserDTOValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserDTOService userDTOService;
    private final BCryptPasswordEncoder encoder;
    private final UserDTOValidator validator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @GetMapping("/register")
    public String showRegistrationForm(ModelMap model) {
        model.addAttribute("user", userDTOService.create());
        return "register/register";
    }


    @PostMapping("/registerProcess")
    public String processRegister(@Valid @ModelAttribute("user") UserDTO user, BindingResult br, Model model) {
        if (userDTOService.existByUsername(user.getUsername()))
            br.rejectValue("username","","Этот логин уже занят");
        if (br.hasErrors()) {
            return "register/register";
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userDTOService.add(user);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("firstname", user.getFirstname());
        model.addAttribute("lastname", user.getLastname());
        return "register/registerSuccess";
    }


    public RegistrationController(UserDTOService userDTOService, BCryptPasswordEncoder encoder, UserDTOValidator validator) {
        this.userDTOService = userDTOService;
        this.encoder = encoder;
        this.validator = validator;
    }
}
