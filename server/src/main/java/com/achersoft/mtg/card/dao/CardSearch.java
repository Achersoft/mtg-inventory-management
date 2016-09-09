package com.achersoft.mtg.card.dao;

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
public class CardSearch {
    public String id;
    public String name;
    public String like;
    public boolean limit;
}
