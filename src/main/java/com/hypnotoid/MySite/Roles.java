package com.hypnotoid.MySite;

public enum Roles {
    ADMIN("ROLE_ADMIN", 1), //ADMIN permissions:
    // has access to any page on server,
    // can add, remove, edit editors;
    // has user_id = 1
    // created by default
    // IS EDITOR
    // IS NOT USER
    USER("ROLE_USER", 2), //USER permissions:
    //Just a regular user
    EDITOR("ROLE_EDITOR", 3),
    //EDITOR permissions:
    //has access to restricted amount pages on server
    //can add, remove, edit products;
    //can add, remove, edit users;
    //IS NOT USER

    ANONYMOUS("ROLE_ANONYMOUS", -1);
    //ANONYMOUS permissions:
    //has access to root page, login page and register related pages ONLY!

    private final String name;
    private final int id;

    Roles(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
