package com.achersoft.order;

import com.achersoft.order.dao.Order;
import com.achersoft.order.dao.OrderList;

public interface OrderService {
    public void createOrder(Order order) throws Exception;
    public OrderList getOrders(int page, int size);
    public OrderList getCompletedOrders(int page, int size);
    public Order getOrder(String id);
    public void fulfillOrder(Order order) throws Exception;
    public void cancelOrder(String id);
}