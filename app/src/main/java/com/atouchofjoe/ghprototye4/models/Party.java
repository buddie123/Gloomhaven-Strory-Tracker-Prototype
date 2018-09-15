package com.atouchofjoe.ghprototye4.models;

import java.util.ArrayList;
import java.util.List;

import static com.atouchofjoe.ghprototye4.models.Location.TOTAL_LOCATIONS;

public class Party {
    private List<com.atouchofjoe.ghprototye4.models.Character> characters;
    private String name;
    private boolean[] locationsCompleted;
    private boolean[] locationsAvailable;
    private Object[] locationAttempts; // will be used as a Array of ArrayList<Attempt> objects

    public Party(String name) {
        this.name = name;
        characters = new ArrayList<>();
        locationsCompleted = new boolean[TOTAL_LOCATIONS];
        locationsCompleted[0] = true;
        locationsAvailable = new boolean[TOTAL_LOCATIONS];
        locationsAvailable[1] = true;
        locationAttempts = new Object[TOTAL_LOCATIONS];
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCharacter(com.atouchofjoe.ghprototye4.models.Character character){
        characters.add(character);
    }

    public void removeCharacter(com.atouchofjoe.ghprototye4.models.Character character) {
        characters.remove(character);
    }

    public String getName(){
        return name;
    }

    public List<com.atouchofjoe.ghprototye4.models.Character> getCharacters() {
        return characters;
    }

    public void setLocationCompleted(Location loc) {
        locationsCompleted[loc.getNumber()] = true;
        locationsAvailable[loc.getNumber()] = false;
    }

    public boolean getLocationCompleted(Location loc) {
        return locationsCompleted[loc.getNumber()];
    }

    public void setLocationAvailable(int locNum) {
        locationsAvailable[locNum] = true;
    }

    public boolean getLocationAvailable(int locNum) {
        return locationsAvailable[locNum];
    }

    public boolean getLocationAttempted(Location loc) { return locationAttempts[loc.getNumber()] != null; }

    public ArrayList<Attempt> getLocationAttemptsForLocation(Location loc){
        return (ArrayList<Attempt>) locationAttempts[loc.getNumber()];
    }

    public void addLocationAttempt(Location loc, Attempt attempt) {
        if(locationAttempts[loc.getNumber()] == null){
            locationAttempts[loc.getNumber()] = new ArrayList<Attempt>();
        }

        ((ArrayList<Attempt>) locationAttempts[loc.getNumber()]).add(0,attempt);

        if(attempt.getAttemptSuccessful()) {
            locationsCompleted[loc.getNumber()] = true;
        }

    }
}
