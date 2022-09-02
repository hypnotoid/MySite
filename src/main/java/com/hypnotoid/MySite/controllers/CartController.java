package com.hypnotoid.MySite.controllers;

import com.hypnotoid.MySite.models.User;
import com.hypnotoid.MySite.services.CartService;
import com.hypnotoid.MySite.services.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping("/cartOrder")
    public RedirectView order(@AuthenticationPrincipal User user, @RequestHeader(value = "referer", required = false) final String referer, final int id) {
        cartService.addItems(id, 1, user.getCart());
        if (referer == null) return new RedirectView("/");
        return new RedirectView(referer);
    }

    @GetMapping("/cart")
    public String cart(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("cart", user.getCart());
        model.addAttribute("total_price", cartService.getTotalPrice(user.getCart()));
        return "shop/cart";
    }

    @GetMapping("/cartEdit")
    public String bugfix() {
        return "redirect:/cart";
    }

    @PostMapping("/cartEdit")
    public String cartEdit(@AuthenticationPrincipal User user, final int id, final int amount, Model model) {
        if (productService.getAmountForID(id) < amount) {
            model.addAttribute("error", "Слишком много");
        } else {
            cartService.setAmount(id, amount, user.getCart());
        }
        model.addAttribute("cart", user.getCart());
        model.addAttribute("total_price", cartService.getTotalPrice(user.getCart()));
        return "shop/cart";
    }

    @PostMapping("/cartDelete")
    public String cartDelete(@AuthenticationPrincipal User user, final int id) {
        cartService.deleteItem(id, user.getCart());
        return "redirect:/cart";
    }

    @PostMapping("/buy")
    public String buy(@AuthenticationPrincipal User user) {
        cartService.order(user.getCart(), user.getId());
        return "redirect:/";
    }
}
