package com.hypnotoid.MySite.controllers;

import com.hypnotoid.MySite.converters.UserDTOConverter;
import com.hypnotoid.MySite.dto.UserDTO;
import com.hypnotoid.MySite.models.User;
import com.hypnotoid.MySite.services.UserDTOService;
import com.hypnotoid.MySite.validators.UserDTOValidator;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

@Controller
public class UserPageController {
    private final UserDTOService userDTOService;
    private final UserDTOConverter converter;
    private final BCryptPasswordEncoder encoder;
    private final UserDTOValidator validator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @GetMapping("/user")
    public String userPage(@AuthenticationPrincipal User user, Model model){
        if (model.getAttribute("org.springframework.validation.BindingResult.user") == null) {
            model.addAttribute("user", converter.fromUserToDTO(user));
        }
        return "shop/userPage";
    }

    @PostMapping("/userDelete")
    public String deleteUser(@AuthenticationPrincipal User user){
        userDTOService.deleteById(user.getId());
        return "redirect:/logout";
    }


    @PostMapping("/userEdit") //ugly page
    public String editUserProcess(@Valid @ModelAttribute("user") UserDTO user, BindingResult br, @AuthenticationPrincipal User userAuth) {
        if (user.getId()==userAuth.getId()) {
            if (br.hasErrors()) {
                return "shop/userPage";
            }
            else {
                user.setPassword(encoder.encode(user.getPassword()));
                userDTOService.edit(user);
            }
        }
        return "redirect:/logout";
    }

    public UserPageController(UserDTOService userDTOService, UserDTOConverter converter, BCryptPasswordEncoder encoder, UserDTOValidator validator) {
        this.userDTOService = userDTOService;
        this.converter = converter;
        this.encoder = encoder;
        this.validator = validator;
    }
}
