package com.achersoft.order;

import com.achersoft.mtg.card.dao.Set;
import com.achersoft.mtg.card.persistence.CardMapper;
import com.achersoft.order.dao.Order;
import com.achersoft.order.dao.OrderItem;
import com.achersoft.order.dao.OrderItemInventory;
import com.achersoft.order.persistence.OrderMapper;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.inject.Inject;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    private @Inject OrderMapper mapper;
    private @Resource(name="setList") Map<String, List<Set>> setList;

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
    

}