package com.achersoft.order;

import com.achersoft.order.dao.Order;

public interface OrderService {
    public void createOrder(Order order) throws Exception;

}