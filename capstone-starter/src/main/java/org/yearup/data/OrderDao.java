package org.yearup.data;

import org.yearup.models.Order;
import org.yearup.models.OrderLineItem;
import org.yearup.models.ShoppingCartItem;

import java.time.LocalDateTime;

public interface OrderDao {
    int createOrder(Order order);

    void addOrderLineItem(OrderLineItem orderLineItem);
}
