package com.achersoft.order;

import com.achersoft.order.dao.Order;
import com.achersoft.order.dao.OrderItem;
import com.achersoft.order.dao.OrderItemInventory;
import com.achersoft.order.dao.OrderList;
import com.achersoft.order.persistence.OrderMapper;
import com.achersoft.security.providers.UserPrincipalProvider;
import com.achersoft.user.persistence.UserMapper;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public OrderList getOrders(int page, int size) {
        OrderList orderList = OrderList.builder().count(mapper.getUnfulfilledOrdersCount()).orders(mapper.getUnfulfilledOrders((page-1)*size, size)).build();
        orderList.getOrders().stream().forEach((order) -> {
            order.setItems(mapper.getOrderItems(order.getId()));
            order.setItemCount(0);
            order.setTotal(0);
            order.getItems().stream().forEach((item) -> {
                order.setItemCount(order.getItemCount() + item.getQty());
                order.setTotal(order.getTotal() + (item.getQty()*item.getPrice()));
            });
        });
        return orderList;
    }
    
    @Override
    public OrderList getCompletedOrders(int page, int size) {
        OrderList orderList = OrderList.builder().count(mapper.getCompletedOrdersCount()).orders(mapper.getCompletedOrders((page-1)*size, size)).build();
        orderList.getOrders().stream().forEach((order) -> {
            order.setFulfilledBy(userMapper.getUser(Integer.parseInt(order.getFulfilledBy())).getName());
            order.setItems(mapper.getOrderItems(order.getId()));
            order.setItemCount(0);
            order.getItems().stream().forEach((item) -> {
                order.setItemCount(order.getItemCount() + item.getQty());
            });
        });
        return orderList;
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
        Map<String, OrderItem> origionalItems = new HashMap();
        order.setTotal(0);
        order.setFulfilledBy(userPrincipalProvider.getUserPrincipal().getSub());
        mapper.getOrderItems(order.getId()).stream().forEach((item) -> {
            origionalItems.put(item.getId(), item);
        });
        mapper.removeOrderItems(order.getId());
        order.getItems().stream().forEach((item) -> {
            OrderItem orig = origionalItems.remove(item.getId());
            if(orig.getQty() > item.getQty())
                mapper.addItemToInventory(item.getId(), item.getCondition(), orig.getQty() - item.getQty());
            mapper.addOrderItem(order.getId(), item);
            order.setTotal(order.getTotal() + (item.getQty()*item.getPrice()));
        });
        origionalItems.values().stream().forEach((item) -> {
            mapper.addItemToInventory(item.getId(), item.getCondition(), item.getQty());
        });
        if(order.getDiscount() > 0)
            order.setTotal(order.getTotal() - order.getTotal()*(order.getDiscount()/100.0));
        order.setTotal(new BigDecimal(order.getTotal()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        mapper.updateOrder(order);
    }

    @Override
    public void cancelOrder(String id) {
        mapper.removeOrder(id);
    }
}