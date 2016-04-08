package com.achersoft.mtg.card.dao;

import java.util.ArrayList;
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
public class CardStock {
    public String cardId;
    public String setId;
    public String set;
    public String language;
    public String condition;
    public int qty;
    public double price;
    
    public static List<CardStock> fromDAO(Card dao){
        List<CardStock> stocks = new ArrayList();
        if(dao.NM > 0) {
            stocks.add(CardStock.builder()
                    .cardId(dao.getId())
                    .setId(dao.setId)
                    .set(dao.set)
                    .language(dao.language)
                    .condition("NM")
                    .qty(dao.NM)
                    .price(dao.nmp)
                    .build());
        } if(dao.SP > 0) {
            stocks.add(CardStock.builder()
                    .cardId(dao.getId())
                    .setId(dao.setId)
                    .set(dao.set)
                    .language(dao.language)
                    .condition("SP")
                    .qty(dao.SP)
                    .price(dao.spp)
                    .build());
        } if(dao.MP > 0) {
            stocks.add(CardStock.builder()
                    .cardId(dao.getId())
                    .setId(dao.setId)
                    .set(dao.set)
                    .language(dao.language)
                    .condition("MP")
                    .qty(dao.MP)
                    .price(dao.mpp)
                    .build());
        } if(dao.HP > 0) {
            stocks.add(CardStock.builder()
                    .cardId(dao.getId())
                    .setId(dao.setId)
                    .set(dao.set)
                    .language(dao.language)
                    .condition("HP")
                    .qty(dao.HP)
                    .price(dao.hpp)
                    .build());
        } if(dao.FNM > 0) {
            stocks.add(CardStock.builder()
                    .cardId(dao.getId())
                    .setId(dao.setId)
                    .set(dao.set + " (foil)")
                    .language(dao.language)
                    .condition("NM")
                    .qty(dao.FNM)
                    .price(dao.fnmp)
                    .build());
        } if(dao.FSP > 0) {
            stocks.add(CardStock.builder()
                    .cardId(dao.getId())
                    .setId(dao.setId)
                    .set(dao.set + " (foil)")
                    .language(dao.language)
                    .condition("SP")
                    .qty(dao.FSP)
                    .price(dao.fspp)
                    .build());
        } if(dao.FMP > 0) {
            stocks.add(CardStock.builder()
                    .cardId(dao.getId())
                    .setId(dao.setId)
                    .set(dao.set + " (foil)")
                    .language(dao.language)
                    .condition("MP")
                    .qty(dao.FMP)
                    .price(dao.fmpp)
                    .build());
        } if(dao.FHP > 0) {
            stocks.add(CardStock.builder()
                    .cardId(dao.getId())
                    .setId(dao.setId)
                    .set(dao.set + " (foil)")
                    .language(dao.language)
                    .condition("HP")
                    .qty(dao.FHP)
                    .price(dao.fhpp)
                    .build());
        }
        return stocks;
    }
  
       
}
