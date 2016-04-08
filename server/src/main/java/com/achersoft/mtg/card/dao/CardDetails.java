package com.achersoft.mtg.card.dao;

import java.util.ArrayList;
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
public class CardDetails {
    private String id;
    private String name;
    private String setId;
    private String set;
    private String language;
    private String manaCost;
    private String type;
    private String subType;
    private Boolean isCreature;
    private String rarity;
    private String text;
    private String oracle;
    private String artist;
    private String power;
    private String toughness;
    private String number;
    private String multiverseId;
    private Date releaseDate;
    private String splitId;
    private Boolean isFront;
    private List<CardStock> stock;
    private List<CardStock> additionalPrintings;
    
    public static CardDetails fromCard(Card dao){
        return CardDetails.builder()
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
                .stock(CardStock.fromDAO(dao))
                .additionalPrintings(new ArrayList())
                .build();
    }
}
