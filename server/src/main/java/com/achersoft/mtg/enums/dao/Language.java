package com.achersoft.mtg.enums.dao;

public enum Language {
    English    ("English","%English%"),
    Russian    ("Russian","%Russian%"),
    Japanese   ("Japanese","%Japanese%"),
    Korean     ("Korean","%Korean%"),
    Spanish    ("Spanish","%Spanish%"),
    German     ("German","%German%"),
    Chinese    ("Chinese","%Chinese%"),
    Portuguese ("Portuguese","%Portuguese%"),
    French     ("French","%French%"),
    Italian    ("Italian","%Italian%");

    private final String language; 
    private final String description; 
    Language(String color, String description) {
        this.language = color;
        this.description = description;
    }
    
    public String color() {
        return language;
    }

    public String description() {
        return description;
    }

    public static Language fromValue(String value) {
        for (Language type: Language.values()) {
            if (type.language.equals(value)) 
                return type;
        }
        throw new IllegalArgumentException(value);
    }
    
    public static Language fromName(String name) {
        for (Language type: Language.values()) {
            if (type.name().equals(name)) 
                return type;
        }
        throw new IllegalArgumentException(name);
    }
}