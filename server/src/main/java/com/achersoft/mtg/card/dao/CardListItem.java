package com.achersoft.mtg.card.dao;

import java.util.Date;
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
public class CardListItem {
    public String id;
    public String name;
    public String setId;
    public String set;
    public String manaCost;
    public String type;
    public String subType;
    public Boolean isCreature;
    public String rarity;
    public String text;
    public String oracle;
    public String artist;
    public String power;
    public String toughness;
    public String number;
    public String multiverseId;
    public Date releaseDate;
    public String splitId;
    public Boolean isFront;
    public String language;
    public String condition;
    public int qty;
    public double price;
    
    public static CardListItem fromCard(Card dao){
        CardListItem card = CardListItem.builder()
                .id(dao.id)
                .name(dao.name)
                .setId(dao.setId)
                .set(dao.set)    
                .manaCost(dao.manaCost)
                .type(dao.type)
                .subType(dao.subType)
                .isCreature(dao.isCreature)
                .rarity(dao.rarity)
                .text(dao.text)
                .oracle(dao.oracle)
                .artist(dao.artist)
                .power(dao.power)
                .toughness(dao.toughness)
                .number(dao.number)
                .multiverseId(dao.multiverseId)
                .releaseDate(dao.releaseDate)
                .splitId(dao.splitId)
                .isFront(dao.isFront)
                .language(dao.language)
                .build();
        
        if(dao.NM > 0) {
            card.setCondition("NM");
            card.setQty(dao.NM);
            card.setPrice(dao.nmp);
        } else if(dao.SP > 0) {
            card.setCondition("SP");
            card.setQty(dao.SP);
            card.setPrice(dao.spp);
        } else if(dao.MP > 0) {
            card.setCondition("MP");
            card.setQty(dao.MP);
            card.setPrice(dao.mpp);
        } else if(dao.HP > 0) {
            card.setCondition("HP");
            card.setQty(dao.HP);
            card.setPrice(dao.hpp);
        } else {
            card.setCondition("NM");
            card.setQty(0);
            card.setPrice(0);
        }
        
        return card;
    }

}
