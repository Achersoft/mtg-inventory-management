package com.achersoft.order.dao;

import java.util.Date;
import java.util.List;
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
public class Order {
    public String id;
    public String customerName;
    public String fulfilledBy;
    public Date createdAt;
    public int discount;
    public double total;
    public int itemCount;
    public List<OrderItem> items;
}
