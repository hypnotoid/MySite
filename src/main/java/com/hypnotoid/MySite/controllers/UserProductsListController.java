package com.hypnotoid.MySite.controllers;

import com.hypnotoid.MySite.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class UserProductsListController {
    private final ProductService productService;
    @GetMapping("/userProductsList")
    public String listProductsUser(Model model) {
        model.addAttribute("products", productService.removeAmount0(productService.getAll()));
        return "shop/userProductsList";
    }
    @GetMapping("/userProductsListFind")
    public String bugFix(){
        return ("redirect:/userProductsList");
    }

    @PostMapping("/userProductsListFind")
    public String productsListFind(Model model, String find) {
        model.addAttribute("products", productService.removeAmount0(productService.find(find)));
        return ("shop/userProductsListFind");
    }

    public UserProductsListController(ProductService productService) {
        this.productService = productService;
    }
}
