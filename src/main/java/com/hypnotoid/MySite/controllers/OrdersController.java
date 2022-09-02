package com.hypnotoid.MySite.controllers;

import com.hypnotoid.MySite.models.User;
import com.hypnotoid.MySite.services.OrderDTOService;
import com.hypnotoid.MySite.services.UserDTOService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrdersController {

    private final OrderDTOService orderService;
    private final UserDTOService userDTOService;

    @PostMapping("/orders")
    public String orders(final int id, Model model) {
        if (!userDTOService.existById(id))  return "redirect:/usersList";
        model.addAttribute("orders", orderService.listAllOrdersForUser(id));
        model.addAttribute("userId", id);
        return "shop/orders";
    }

    @PostMapping("/orderDelete")
    public String orderDelete(final int userId ,final int orderId, Model model) {
        if (!userDTOService.existById(userId))  return "redirect:/usersList";
        orderService.deleteById(orderId);
        model.addAttribute("orders", orderService.listAllOrdersForUser(userId));
        model.addAttribute("userId", userId);
        return "shop/orders";
    }

    @GetMapping("/myOrders")
    public String userOrders(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("orders", orderService.listAllOrdersForUser(user.getId()));
        return "shop/userOrders";
    }


    public OrdersController(OrderDTOService orderService, UserDTOService userDTOService) {
        this.orderService = orderService;
        this.userDTOService = userDTOService;
    }
}
