package com.achersoft.mtg.enums.dao;

public enum Color {
    BLUE      ("BLUE","%U%", "Blue"),
    BLACK     ("BLACK","%B%", "Black"),
    GREEN     ("GREEN","%G%", "Green"),
    RED       ("RED","%R%", "Red"),
    WHITE     ("WHITE","%W%", "White"),
    COLORLESS ("COLORLESS","", "Colorless");

    public final String color; 
    public final String abv; 
    public final String description; 
    Color(String color, String abv, String description) {
        this.color = color;
        this.abv = abv;
        this.description = description;
    }
    
    public String color() {
        return color;
    }
    
    public String abv() {
        return abv;
    }

    public String description() {
        return description;
    }

    public static Color fromValue(String value) {
        for (Color type: Color.values()) {
            if (type.color.equals(value)) 
                return type;
        }
        throw new IllegalArgumentException(value);
    }
    
    public static Color fromName(String name) {
        for (Color type: Color.values()) {
            if (type.name().equals(name)) 
                return type;
        }
        throw new IllegalArgumentException(name);
    }
}