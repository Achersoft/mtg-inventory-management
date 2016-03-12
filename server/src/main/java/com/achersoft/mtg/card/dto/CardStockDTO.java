/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.achersoft.mtg.card.dto;

import com.achersoft.mtg.card.dao.Card;
import java.util.ArrayList;
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
public class CardStockDTO {
    public String language;
    public String condition;
    public int qty;
    public double price;
    
    public static List<CardStockDTO> fromDAO(Card dao){
        List<CardStockDTO> stocks = new ArrayList();
        if(dao.NM > 0)
            stocks.add(CardStockDTO.builder().condition("NM/M").qty(dao.NM).price(dao.nmp).build());
        if(dao.SP > 0)
            stocks.add(CardStockDTO.builder().condition("SP").qty(dao.SP).price(dao.spp).build());
        if(dao.MP > 0)
            stocks.add(CardStockDTO.builder().condition("MP").qty(dao.MP).price(dao.mpp).build());
        if(dao.HP > 0)
            stocks.add(CardStockDTO.builder().condition("HP").qty(dao.HP).price(dao.hpp).build());
        if(dao.FNM > 0)
            stocks.add(CardStockDTO.builder().condition("FNM").qty(dao.FNM).price(dao.fnmp).build());
        if(dao.FSP > 0)
            stocks.add(CardStockDTO.builder().condition("FSP").qty(dao.FSP).price(dao.fspp).build());
        if(dao.FMP > 0)
            stocks.add(CardStockDTO.builder().condition("FMP").qty(dao.FMP).price(dao.fmpp).build());
        if(dao.FHP > 0)
            stocks.add(CardStockDTO.builder().condition("FHP").qty(dao.FHP).price(dao.fhpp).build());
        if(stocks.isEmpty())
            stocks.add(CardStockDTO.builder().condition("Out of Stock").build());
        return stocks;
    }
}
