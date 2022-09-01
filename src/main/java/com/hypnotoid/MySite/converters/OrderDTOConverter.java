package com.hypnotoid.MySite.converters;

import com.hypnotoid.MySite.dto.OrderDTO;
import com.hypnotoid.MySite.models.Order;
import com.hypnotoid.MySite.models.Product;
import com.hypnotoid.MySite.models.User;
import com.hypnotoid.MySite.services.ProductService;
import com.hypnotoid.MySite.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class OrderDTOConverter {

   private final UserService userService;
    private final ProductService productService;

    public OrderDTO fromOrderToDTO(Order order) {
        if (order == null) return null;
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setAmount(order.getAmount());
        dto.setProductId(order.getProduct().getId());
        dto.setProductName(order.getProduct().getName());
        dto.setProductPrice(order.getProduct().getPrice());
        dto.setUserId(order.getUser().getId());
        dto.setUsername(order.getUser().getUsername());
        dto.setTotal_price();
        dto.setOrderDate(order.getOrderDate());
        return dto;
    }

    public Order fromDTOToOrder(OrderDTO orderDTO) {
        if (orderDTO == null) return null;
        Order order = new Order();
        User user = userService.getById(orderDTO.getUserId());
        Product product = productService.getById(orderDTO.getProductId());
        order.setId(orderDTO.getId());
        order.setProduct(product);
        order.setUser(user);
        order.setAmount(orderDTO.getAmount());
        order.setOrderDate(orderDTO.getOrderDate());
        return order;
    }

    public OrderDTOConverter(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }
}
