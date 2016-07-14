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
    public String condition;
    public int qty;
}
