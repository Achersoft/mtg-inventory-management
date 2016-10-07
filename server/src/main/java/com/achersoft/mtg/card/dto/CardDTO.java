package com.achersoft.mtg.card.dto;

import com.achersoft.mtg.card.dao.CardDetails;
import com.achersoft.mtg.card.dao.CardStock;
import java.util.List;
import java.util.stream.Collectors;
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
    public String layout;
    public CardDetailsDTO details;
    public List<CardDetailsDTO> subDetails;
    public List<CardStock> stock;
    public List<CardStock> additionalPrintings;
    
    public static CardDTO fromDAO(CardDetails dao){
        return CardDTO.builder()
                .layout(dao.getDetails().layout)
                .details(CardDetailsDTO.fromDAO(dao.getDetails()))
                .subDetails(dao.getSubDetails().stream().map((card) -> {return CardDetailsDTO.fromDAO(card);}).collect(Collectors.toList()))
                .stock(dao.getStock())
                .additionalPrintings(dao.getAdditionalPrintings())
                .build();
    }
}
