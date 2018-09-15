package com.atouchofjoe.ghprototye4.models;

import android.util.SparseArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.atouchofjoe.ghprototye4.models.Location.TOTAL_LOCATIONS;

public class Party {
    private List<com.atouchofjoe.ghprototye4.models.Character> characters;
    private String name;
    private boolean[] locationsCompleted;
    private boolean[] locationsAvailable;
    private SparseArray<List<Attempt>> locationAttempts; // will be used as a Array of ArrayList<Attempt> objects
    private static final int INDEX_OF_BEG_OF_LIST = 0;

    public Party(String name) {
        this.name = name;
        characters = new ArrayList<>();
        locationsCompleted = new boolean[TOTAL_LOCATIONS];
        locationsAvailable = new boolean[TOTAL_LOCATIONS];
        locationAttempts = new SparseArray<>(TOTAL_LOCATIONS);
        setLocationCompleted(0);
        setLocationAvailable(1);
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

    public void setLocationCompleted(int locNum) {
        locationsCompleted[locNum] = true;
        locationsAvailable[locNum] = false;
    }

    public boolean getLocationCompleted(int locNum) {
        return locationsCompleted[locNum];
    }

    public void setLocationAvailable(int locNum) {
        locationsAvailable[locNum] = true;
        if(locationAttempts.get(locNum) == null) {
            locationAttempts.put(locNum, new ArrayList<Attempt>());
        }
    }

    public boolean getLocationAvailable(int locNum) {
        return locationsAvailable[locNum];
    }

    public boolean getLocationAttempted(int locNum) {

        return locationAttempts.get(locNum) != null && locationAttempts.get(locNum).size() > 0;

    }

    public ArrayList<Attempt> getLocationAttemptsForLocation(int locNum){
        return (ArrayList<Attempt>) locationAttempts.get(locNum);
    }

    public void addLocationAttempt(int locNum, Attempt attempt) {
        if(locationAttempts.get(locNum) == null){
            locationAttempts.put(locNum, new ArrayList<Attempt>());
        }

        locationAttempts.get(locNum).add(INDEX_OF_BEG_OF_LIST, attempt);

        if(attempt.getAttemptSuccessful()) {
            setLocationCompleted(locNum);
        }
    }
}