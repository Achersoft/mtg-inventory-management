package com.achersoft.mtg.card.dto;

import com.achersoft.mtg.card.dao.CardDetails;
import com.achersoft.mtg.card.dao.CardStock;
import java.util.Arrays;
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
public class CardDTO {
    public String id;
    public String name;
    public String setId;
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
    public List<CardStock> stock;
    public List<CardStock> additionalPrintings;
    
    public static CardDTO fromDAO(CardDetails dao){
        return CardDTO.builder()
                .id(dao.getId())
                .name(dao.getName())
                .setId(dao.getSetId())
                .set(dao.getSet())    
                .manaCost((dao.getManaCost()!=null)?Arrays.asList(dao.getManaCost().replace("{", "").replace("}", ",").replace("/", "").toLowerCase().split(",")):null)
                .type(dao.getType())
                .subType(dao.getSubType())
                .isCreature(dao.getIsCreature())
                .rarity(dao.getRarity())
                .text(dao.getText())
                .oracle(dao.getOracle())
                .artist(dao.getArtist())
                .power(dao.getPower())
                .toughness(dao.getToughness())
                .number(dao.getNumber())
                .multiverseId(dao.getMultiverseId())
                .releaseDate(dao.getReleaseDate())
                .splitId(dao.getSplitId())
                .isFront(dao.getIsFront())
                .stock(dao.getStock())
                .additionalPrintings(dao.getAdditionalPrintings())
                .build();
    }
}
