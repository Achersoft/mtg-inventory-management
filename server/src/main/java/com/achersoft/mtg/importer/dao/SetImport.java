package com.achersoft.mtg.importer.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor 
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SetImport {
    public String id;
    public String name;
    public String code;
    public String gathererCode;
    public String magicCardsInfoCode;
    public String releaseDate;
    public String border;
    public String type;
    public String block;
    public boolean onlineOnly;
    public List<CardImport> cards;
}