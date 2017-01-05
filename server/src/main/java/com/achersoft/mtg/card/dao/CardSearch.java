package com.achersoft.mtg.card.dao;

import com.achersoft.mtg.enums.dao.CardType;
import com.achersoft.mtg.enums.dao.Color;
import com.achersoft.mtg.enums.dao.Language;
import com.achersoft.mtg.enums.dao.Rarity;
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
public class CardSearch {
    public String id;
    public String name;
    public String like;
    public List<Color> colors;
    public List<CardType> types;
    public List<Rarity> rarities;
    public List<Language> languages;
    public Integer cmc;
    public Double priceMin;
    public Double priceMax;
    public Integer page;
    public Integer limit;
}
