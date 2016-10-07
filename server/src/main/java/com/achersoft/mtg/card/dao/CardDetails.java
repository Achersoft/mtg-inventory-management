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
    private Card details;
    private List<Card> subDetails;
    private List<CardStock> stock;
    private List<CardStock> additionalPrintings;
    
    public static CardDetails fromCard(Card dao){
        return CardDetails.builder()
                .details(dao)
                .subDetails(new ArrayList())
                .stock(CardStock.fromDAO(dao))
                .additionalPrintings(new ArrayList())
                .build();
    }
}
