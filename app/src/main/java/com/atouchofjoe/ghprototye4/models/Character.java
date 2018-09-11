package com.atouchofjoe.ghprototye4.models;

public class Character {

    private String name;
    private CharacterClass charClass;

    public Character(String name, CharacterClass charClass) {
        this.name = name;
        this.charClass = charClass;
    }

    public String getName() {
        return name;
    }

    public CharacterClass getCharClass() {
        return charClass;
    }
}
