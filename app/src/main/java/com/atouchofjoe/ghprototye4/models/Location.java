package com.atouchofjoe.ghprototye4.models;

import java.util.ArrayList;
import java.util.List;

public class Location {
    public static final int TOTAL_LOCATIONS = 95;

    private int number;
    private String name;
    private String teaser;
    private String summary;
    private String conclusion;
    private List<String> globalAchievementsGained = new ArrayList<>();
    private List<String> globalAchievementsLost = new ArrayList<>();
    private List<String> partyAchievementsGained = new ArrayList<>();
    private List<String> partyAchievementsLost = new ArrayList<>();
    private List<String> locationsUnlocked = new ArrayList<>();
    private List<String> locationsBlocked = new ArrayList<>();
    private List<String> addRewards = new ArrayList<>();
    private List<String> addPenalties = new ArrayList<>();

    public Location(int sNumber, String sName,
                    String sTeaser, String sSummary, String sConclusion) {
        this.number = sNumber;
        this.name = sName;
        this.teaser = sTeaser;
        this.summary = sSummary;
        this.conclusion = sConclusion;
    }


//        this.globalAchievementsGained = sGlobalAchievementsGained;
//        this.globalAchievementsLost = sGlobalAchievementsLost;
//        this.partyAchievementsGained = sPartyAchievementsGained;
//        this.partyAchievementsLost = sPartyAchievementsLost;
//        this.locationsUnlocked = sDummyScenariosUnlocked;
//        this.locationsBlocked = sDummyScenariosBlocked;
//        this.addRewards = sExtrasGained;
//        this.addPenalties = sExtrasLost;

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getTeaser() {
        return teaser;
    }

    public String getSummary() {
        return summary;
    }

    public String getConclusion() {
        return conclusion;
    }

    public List<String> getGlobalAchievementsGained() {
        return globalAchievementsGained;
    }

    public List<String> getGlobalAchievementsLost() {
        return globalAchievementsLost;
    }

    public List<String> getPartyAchievementsGained() {
        return partyAchievementsGained;
    }

    public List<String> getPartyAchievementsLost() {
        return partyAchievementsLost;
    }

    public List<String> getLocationsUnlocked() {
        return locationsUnlocked;
    }

    public List<String> getLocationsBlocked() {
        return locationsBlocked;
    }

    public List<String> getAddRewards() {
        return addRewards;
    }

    public List<String> getAddPenalties() {
        return addPenalties;
    }

    @Override
    public String toString() { return "#" + number + " " + name; }
    }

