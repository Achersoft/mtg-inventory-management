package com.achersoft.mtg.card.dto;

import com.achersoft.mtg.card.dao.CardSearch;
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
public class CardSearchDTO {
    public String id;
    public String name;
    public String like;
    public boolean limit = false;
    
    public CardSearch toDao() {
        if(like != null)
            like += "%";
        return CardSearch.builder()
                .id(id)
                .name(name)
                .like(like)
                .limit(limit)
                .build();
    }
}
