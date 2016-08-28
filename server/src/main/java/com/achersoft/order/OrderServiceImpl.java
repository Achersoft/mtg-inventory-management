package com.achersoft.order;

import com.achersoft.order.dao.Order;
import com.achersoft.order.dao.OrderItem;
import com.achersoft.order.dao.OrderItemInventory;
import com.achersoft.order.persistence.OrderMapper;
import com.achersoft.security.providers.UserPrincipalProvider;
import com.achersoft.user.persistence.UserMapper;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    private @Inject OrderMapper mapper;
    private @Inject UserMapper userMapper;
    private @Inject UserPrincipalProvider userPrincipalProvider;

    @Override
    public void createOrder(Order order) throws Exception {
        order.setCreatedAt(new Date());
        mapper.createOrder(order);
        for(OrderItem item : order.getItems()) {
            mapper.removeItemFromInventory(item.getId(), item.getCondition(), item.getQty());
            OrderItemInventory itemInventory = mapper.getItemInventory(item.getId(), item.getCondition());
            if(itemInventory.getQty() < 0)
                throw new Exception();
            item.setPrice(itemInventory.getPrice());
            mapper.addOrderItem(order.getId(), item);
        }
    }

    @Override
    public List<Order> getOrders() {
        List<Order> unfulfilledOrders = mapper.getUnfulfilledOrders();
        unfulfilledOrders.stream().forEach((order) -> {
            order.setItems(mapper.getOrderItems(order.getId()));
            order.setItemCount(0);
            order.setTotal(0);
            order.getItems().stream().forEach((item) -> {
                order.setItemCount(order.getItemCount() + item.getQty());
                order.setTotal(order.getTotal() + (item.getQty()*item.getPrice()));
            });
        });
        return unfulfilledOrders;
    }
    
    @Override
    public List<Order> getCompletedOrders() {
        List<Order> completedOrders = mapper.getCompletedOrders();
        completedOrders.stream().forEach((order) -> {
            order.setFulfilledBy(userMapper.getUser(Integer.parseInt(order.getFulfilledBy())).getName());
            order.setItems(mapper.getOrderItems(order.getId()));
            order.setItemCount(0);
            order.getItems().stream().forEach((item) -> {
                order.setItemCount(order.getItemCount() + item.getQty());
            });
        });
        return completedOrders;
    }

    @Override
    public Order getOrder(String id) {
        Order order = mapper.getOrder(id);
        order.setItems(mapper.getOrderItems(id));
        order.getItems().stream().map((item) -> {
            item.setMaxQty(item.getQty() + mapper.getItemInventory(item.getId(), item.getCondition()).getQty());
            return item;                
        });
        return order;
    }

    @Override
    public void fulfillOrder(Order order) throws Exception {
        order.setTotal(0);
        order.setFulfilledBy(userPrincipalProvider.getUserPrincipal().getSub());
        mapper.getOrderItems(order.getId()).stream().forEach((item) -> {
            mapper.addItemToInventory(item.getId(), item.getCondition(), item.getQty());
        });
        mapper.removeOrderItems(order.getId());
        for(OrderItem item : order.getItems()) {
            mapper.removeItemFromInventory(item.getId(), item.getCondition(), item.getQty());
            OrderItemInventory itemInventory = mapper.getItemInventory(item.getId(), item.getCondition());
            if(itemInventory.getQty() < 0)
                throw new Exception();
            mapper.addOrderItem(order.getId(), item);
            order.setTotal(order.getTotal() + (item.getQty()*item.getPrice()));
        }
        if(order.getDiscount() > 0)
            order.setTotal(order.getTotal() - order.getTotal()*(order.getDiscount()/100.0));
        order.setTotal(new BigDecimal(order.getTotal()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        mapper.updateOrder(order);
    }
}