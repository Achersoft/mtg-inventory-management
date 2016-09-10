package com.achersoft.order.persistence;

import com.achersoft.order.dao.Order;
import com.achersoft.order.dao.OrderItem;
import com.achersoft.order.dao.OrderItemInventory;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    public void createOrder(Order order);
    public void updateOrder(Order order);
    public void addItemToInventory(@Param("id") String id, @Param("condition") String condition, @Param("qty") int qty);
    public void removeItemFromInventory(@Param("id") String id, @Param("condition") String condition, @Param("qty") int qty);
    public OrderItemInventory getItemInventory(@Param("id") String id, @Param("condition") String condition);
    public void addOrderItem(@Param("orderId") String orderId, @Param("orderItem") OrderItem orderItem);
    public void removeOrderItems(@Param("orderId") String orderId);
    public List<Order> getUnfulfilledOrders();
    public List<Order> getCompletedOrders();
    public Order getOrder(String id);
    public List<OrderItem> getOrderItems(String id);
    public void removeOrder(String id);
}