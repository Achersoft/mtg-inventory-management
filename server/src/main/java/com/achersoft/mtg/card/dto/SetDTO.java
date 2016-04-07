package com.achersoft.mtg.card.dto;

import com.achersoft.mtg.card.dao.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor 
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SetDTO {
    public String id;
    public String name;
    public String language;
    
    public static SetDTO fromDAO(Set dao){
        return SetDTO.builder()
                .id(dao.getId())
                .name(dao.getName())
                .language(dao.getLanguage())
                .build();
    }
}