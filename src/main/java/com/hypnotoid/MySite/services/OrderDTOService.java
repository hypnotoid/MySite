package com.hypnotoid.MySite.services;

import com.hypnotoid.MySite.converters.OrderDTOConverter;
import com.hypnotoid.MySite.dto.OrderDTO;
import com.hypnotoid.MySite.models.Order;
import com.hypnotoid.MySite.models.User;
import com.hypnotoid.MySite.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class OrderDTOService {
   private final OrderRepository orderRepo;
   private final UserService userService;

   private final OrderDTOConverter converter;

   private final ProductService productService;

    public List<OrderDTO> listAllOrdersForUser(int userid){
        List<OrderDTO> out = new ArrayList<>();
        for (Order order: orderRepo.findAll()){
            if (order.getUser().getId()==userid) out.add(converter.fromOrderToDTO(order));
        }
        return out;
    }
    public OrderDTO create(){
        return new OrderDTO();
    }

    public void add(final int productId, final int userId, int amount){
        OrderDTO order = new OrderDTO();
        order.setUserId(userId);
        order.setUsername(userService.getById(userId).getUsername());
        order.setAmount(amount);
        order.setOrderDate(new Date());
        order.setProductId(productId);
        order.setProductName(productService.getById(productId).getName());
        order.setProductPrice(productService.getById(productId).getPrice());
        order.setTotal_price();
        orderRepo.save(converter.fromDTOToOrder(order));
    }

    public void deleteById(int id){
        Optional<Order> get = orderRepo.findById(id);
        if(get.isPresent()) {
           Order order = get.get();
            order.setUser(null);
            order.setProduct(null);
            orderRepo.save(order);
            orderRepo.deleteById(id);
        }
    }
    public void deleteByUser(User user) {
        orderRepo.deleteAllByUser(user);
    }


    public OrderDTO getById(final int id) {
        Optional<Order> order = orderRepo.findById(id);
        if(order.isPresent()){
            return converter.fromOrderToDTO(order.get());
        } else return null;
    }

    public OrderDTOService(OrderRepository orderRepo, UserService userService, OrderDTOConverter converter, ProductService productService) {
        this.orderRepo = orderRepo;
        this.userService = userService;
        this.converter = converter;
        this.productService = productService;
    }
}
