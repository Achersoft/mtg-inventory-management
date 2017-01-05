package com.achersoft.mtg.card.dto;

import com.achersoft.mtg.card.dao.CardSearch;
import com.achersoft.mtg.enums.dao.CardType;
import com.achersoft.mtg.enums.dao.Rarity;
import com.achersoft.mtg.enums.dao.Color;
import com.achersoft.mtg.enums.dao.Language;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardSearchDTO {
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
    public Integer page = 1;
    public Integer limit = 20;
    
    public CardSearch toDao() {
        if(like != null)
            like += "%";
        if(priceMin != null && priceMax == null)
            priceMax = 100000.0;
        if(priceMax != null && priceMin == null)
            priceMin = 0.0;
        return CardSearch.builder()
                .id(id)
                .name(name)
                .like(like)
                .colors(colors)
                .types(types)
                .rarities(rarities)
                .languages((languages == null || languages.isEmpty())?Lists.newArrayList(Language.English):languages)
                .cmc(cmc)
                .priceMin(priceMin)
                .priceMax(priceMax)
                .page((page-1)*limit)
                .limit(limit)
                .build();
    }
}
