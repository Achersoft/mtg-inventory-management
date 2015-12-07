/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.achersoft.mtg.card.dto;

import com.achersoft.mtg.card.dao.Card;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author Shaun
 */
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {
    public String id;
    public String name;
    public String set;
    public List<String> manaCost;
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
    public List<CardStockDTO> stock;
    
    public static CardDTO fromDAO(Card dao){
        return CardDTO.builder()
                .id(dao.id)
                .name(dao.name)
                .set(dao.set)    
                .manaCost((dao.manaCost!=null)?Arrays.asList(dao.manaCost.replace("{", "").replace("}", ",").replace("/", "").toLowerCase().split(",")):null)
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
                .stock(CardStockDTO.fromDAO(dao))
                .build();
    }
}
