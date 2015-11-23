package com.achersoft.mtg.importer.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Shaun
 */
@Builder
@Data
@NoArgsConstructor 
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardImport {
    public String id;
    public String setId;
    public String layout;
    public String type;
    public List<String> types;
    public List<String> colors;
    public String multiverseid;
    public String name;
    public List<String> names;
    public List<String> subtypes;
    public List<String> supertypes;
    public List<String> variations;
    public String cmc;
    public String rarity;
    public String artist;
    public String power;
    public String toughness;
    public String manaCost;
    public String originalText;
    public String text;
    public String flavor;
    public List<LegalityImport> legalities;
    public Integer splitSequence;
    public String splitId;
}