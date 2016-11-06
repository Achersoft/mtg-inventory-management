package com.achersoft.order.dto;

import com.achersoft.order.dao.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class OrderItemDTO {
    public String id;
    public String name;
    public String set;
    public String language;
    public String condition;
    public String color;
    public int qty;
    public int maxQty;
    public double price;
    
    public OrderItem toDao() {
        return OrderItem.builder()
                .id(id)
                .name(name)
                .set(set)
                .language(language)
                .condition(condition)
                .color(color)
                .qty(qty)
                .price(price)
                .build();
    }
}
