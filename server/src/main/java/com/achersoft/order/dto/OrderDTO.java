package com.achersoft.order.dto;

import com.achersoft.order.dao.Order;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDTO {
    public String id;
    public String customerName;
    public String fulfilledBy;
    public Date createdAt;
    public int discount;
    public double total;
    public int itemCount;
    public List<OrderItemDTO> items;
    
    public Order toDao() {
        return Order.builder()
                .id(id)
                .customerName(customerName)
                .fulfilledBy(fulfilledBy)
                .createdAt(createdAt)
                .discount(discount)
                .total(total)
                .itemCount(itemCount)
                .items(items.stream().map((item) -> {return item.toDao();}).collect(Collectors.toList()))
                .build();     
    }
}
