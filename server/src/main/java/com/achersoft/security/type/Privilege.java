package com.achersoft.security.type;

public enum Privilege {
    //ADMIN operations
    ADMIN("Admin", "Allows access to system administrative functions"),
    EDIT_INVENTORY("Admin", "Allows access to system administrative functions"),
    VIEW_INVENTORY("Admin", "Allows access to system administrative functions");
    
    private final String text;
    private final String description;

    Privilege(String text, String description) {
        this.text = text;
        this.description = description;
    }
    
    public String text() {
        return text;
    }

    public String description() {
        return description;
    }
}

