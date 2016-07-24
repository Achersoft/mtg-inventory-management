package com.achersoft.order;

import com.achersoft.order.dao.Order;
import java.util.List;

public interface OrderService {
    public void createOrder(Order order) throws Exception;
    public List<Order> getOrders();
    public List<Order> getCompletedOrders();
    public Order getOrder(String id);
    public void fulfillOrder(Order order) throws Exception;
}