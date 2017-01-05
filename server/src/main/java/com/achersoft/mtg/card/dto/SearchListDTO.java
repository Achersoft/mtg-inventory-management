package com.achersoft.mtg.card.dto;

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
public class SearchListDTO {
    public int count;
    public List<CardListItemDTO> cards;
}
