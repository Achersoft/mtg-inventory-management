package com.achersoft.mtg.enums.dao;

public enum CardType {
    CREATURE     ("CREATURE", "%Creature%", "Creature"),
    ARTIFACT     ("ARTIFACT", "%Artifact%", "Artifact"),
    SORCERY      ("SORCERY", "%Sorcery%", "Sorcery"),
    INSTANT      ("INSTANT", "%Instant%", "Instant"),
    ENCHANTMENT  ("ENCHANTMENT", "%Enchantment%", "Enchantment"),
    PLANESWALKER ("PLANESWALKER", "%Planeswalker%", "Planeswalker"),
    LAND         ("LAND", "%Land%", "Land");

    public final String type; 
    public final String abv; 
    public final String description; 
    CardType(String color, String abv, String description) {
        this.type = color;
        this.abv = abv;
        this.description = description;
    }
    
    public String color() {
        return type;
    }

    public String description() {
        return description;
    }

    public static CardType fromValue(String value) {
        for (CardType type: CardType.values()) {
            if (type.type.equals(value)) 
                return type;
        }
        throw new IllegalArgumentException(value);
    }
    
    public static CardType fromName(String name) {
        for (CardType type: CardType.values()) {
            if (type.name().equals(name)) 
                return type;
        }
        throw new IllegalArgumentException(name);
    }
}