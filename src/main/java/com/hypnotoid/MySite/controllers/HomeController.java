package com.hypnotoid.MySite.controllers;

import com.hypnotoid.MySite.models.User;
import com.hypnotoid.MySite.services.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class HomeController {
    private final ProductService productService;

    //@Autowired
    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homepage(Model model, @AuthenticationPrincipal User user) {
        if (user != null) model.addAttribute("username", user.getUsername());
        model.addAttribute("products", productService.removeAmount0(productService.getTop10()));
        return "shop/home";
    }
}
