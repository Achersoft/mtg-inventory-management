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
    public String inventoryId;
    public String setId;
    public String layout;
    public String type;
    public List<String> types;
    public List<String> colors;
    public String multiverseid;
    public String name;
    public String number;
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
    public String language;
    public List<ForeignImport> foreignNames;
    public List<LegalityImport> legalities;
    public Integer splitSequence;
    public String splitId;
    public boolean hasChildren = false;
    
    public CardImport copy() {
        return CardImport.builder()
                .id(id)
                .setId(setId)
                .layout(layout)
                .type(type)
                .types(types)
                .colors(colors)
                .multiverseid(multiverseid)
                .name(name)
                .number(number)
                .names(names)
                .subtypes(subtypes)
                .supertypes(supertypes)
                .variations(variations)
                .cmc(cmc)
                .rarity(rarity)
                .artist(artist)
                .power(power)
                .toughness(toughness)
                .manaCost(manaCost)
                .originalText(originalText)
                .text(text)
                .flavor(flavor)
                .language(language)
                .foreignNames(foreignNames)
                .legalities(legalities)
                .splitSequence(splitSequence)
                .splitId(splitId)
                .hasChildren(hasChildren)
                .build();
    }
}