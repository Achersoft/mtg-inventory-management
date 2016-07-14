package com.achersoft.order;

import com.achersoft.mtg.card.dao.Set;
import com.achersoft.mtg.card.persistence.CardMapper;
import com.achersoft.order.dao.Order;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.inject.Inject;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class OrderServiceImpl implements OrderService {

    private @Inject CardMapper mapper;
    private @Resource(name="setList") Map<String, List<Set>> setList;

    @Override
    public void createOrder(Order order) {
        
    }
    

}