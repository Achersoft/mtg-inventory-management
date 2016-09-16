package com.achersoft.order.dao;

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
public class OrderItem {
    public String id;
    public String name;
    public String set;
    public String language;
    public String condition;
    public int qty;
    public int maxQty;
    public double price;
    public boolean tcg;
}
