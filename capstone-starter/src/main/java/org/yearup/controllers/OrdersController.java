package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.data.*;
import org.yearup.models.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDateTime;

@RestController
@CrossOrigin
@RequestMapping("/orders")
@PreAuthorize("permitAll()")

public class OrdersController {
    ProfileDao profileDao;
    ShoppingCartDao shoppingCartDao;
    UserDao userDao;
    OrderDao orderDao;

    @Autowired
    public OrdersController(ProfileDao profileDao, ShoppingCartDao shoppingCartDao, UserDao userDao, OrderDao orderDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
        this.shoppingCartDao = shoppingCartDao;
        this.orderDao = orderDao;
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    public void checkout(Principal principal) {
        //get user
        String userName = principal.getName();
        User user = userDao.getByUserName(userName);
        int userId = user.getId();
        //get car
        ShoppingCart cart = shoppingCartDao.getByUserId(userId);
        //if cart is empty, throw error
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Shopping cart is empty.");
        }
        //get profile
        Profile profile = profileDao.getByUserId(userId);
        Order order = new Order();

        order.setUserId(userId);
        order.setDate(LocalDateTime.now());
        order.setAddress(profile.getAddress());
        order.setCity(profile.getCity());
        order.setState(profile.getState());
        order.setZip(profile.getZip());
        order.setShipping_amount(10.0);

        int orderId = orderDao.createOrder(order);
        //int userId, LocalDateTime date, String address, String city, String state, String zip, Double shipping_amount

        //int orderId, int productId, Double sales_price, int quantity, Double discount
        for (ShoppingCartItem item : cart.getItems().values()) {
            OrderLineItem line = new OrderLineItem();

            line.setOrderId(orderId);
            line.setProductId(item.getProductId());
            line.setSalesPrice(item.getLineTotal());
            line.setQuantity(item.getQuantity());
            line.setDiscount(item.getDiscountPercent());
            //TODO

            orderDao.addOrderLineItem(line);
        }

        shoppingCartDao.clearCart(userId);
    }

}
