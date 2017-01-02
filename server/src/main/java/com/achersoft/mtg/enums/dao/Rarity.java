package com.achersoft.mtg.enums.dao;

public enum Rarity { 
    COMMON   ("COMMON","Common"),
    UNCOMMON ("UNCOMMON","Uncommon"),
    RARE     ("RARE","Rare"),
    MYTHIC   ("MYTHIC","Mythic Rare"),
    SPECIAL  ("SPECIAL","Special"),
    BASIC    ("BASIC","Basic Land");

    private final String rarity; 
    private final String description; 
    Rarity(String color, String description) {
        this.rarity = color;
        this.description = description;
    }
    
    public String rarity() {
        return rarity;
    }

    public String description() {
        return description;
    }

    public static Rarity fromValue(String value) {
        for (Rarity type: Rarity.values()) {
            if (type.rarity.equals(value)) 
                return type;
        }
        throw new IllegalArgumentException(value);
    }
    
    public static Rarity fromName(String name) {
        for (Rarity type: Rarity.values()) {
            if (type.name().equals(name)) 
                return type;
        }
        throw new IllegalArgumentException(name);
    }
}