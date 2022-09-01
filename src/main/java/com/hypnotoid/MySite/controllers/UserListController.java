package com.hypnotoid.MySite.controllers;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
public class UserListController {
    private final BCryptPasswordEncoder encoder;
    private final UserDTOService userDTOService;

    private final UserDTOValidator validator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @GetMapping("/usersList")
    public String listUsers(Model model,@AuthenticationPrincipal User userAuth ) {
        model.addAttribute("loggedUserId",userAuth.getId());
        model.addAttribute("listUsers", userDTOService.getAll());
        if (model.getAttribute("org.springframework.validation.BindingResult.user") == null
                && model.asMap().get("user") == null) {
            model.addAttribute("user", userDTOService.create());
        }
        return "shop/usersList";
    }

    @PostMapping("/usersListDelete")
    public String deleteUser(int id) {
        userDTOService.deleteById(id);
        return "redirect:/usersList";
    }

    @PostMapping("/usersListSaveProcess") //change gui on page
    public RedirectView editUserProcess(@Valid @ModelAttribute("user") UserDTO user, BindingResult br, RedirectAttributes attributes) {
        if (br.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.user", br);
            attributes.addFlashAttribute("user", user);
        }
        else if (user.getId() == 0) { //if user is not exist
            user.setPassword(encoder.encode(user.getPassword()));
            userDTOService.add(user);
        }
        else {
            user.setPassword(encoder.encode(user.getPassword()));
            userDTOService.edit(user);
        }
        return new RedirectView("/usersList");
    }

    @PostMapping("/usersListSave")
    public RedirectView editUser(RedirectAttributes attributes, int id) {
        //UserDTO userDTO = userDTOService.getById(id);
       // if (userDTO != null)  {
            attributes.addFlashAttribute("user", userDTOService.getById(id)); //can be null
       // }
        return new RedirectView("/usersList");
    }
    @GetMapping("/usersListFind")
    public String bugFix(){
        return ("redirect:/usersList");
    }
    @PostMapping(value = "/usersListFind")
    public String productsListFind(Model model, String find,@AuthenticationPrincipal User userAuth ) {
        model.addAttribute("loggedUserId",userAuth.getId());
        model.addAttribute("listUsers", userDTOService.find(find));
        return ("shop/usersListFind");
    }

    //@Autowired


    public UserListController(BCryptPasswordEncoder encoder, UserDTOService userDTOService, UserDTOValidator validator) {
        this.encoder = encoder;
        this.userDTOService = userDTOService;
        this.validator = validator;
    }
}
