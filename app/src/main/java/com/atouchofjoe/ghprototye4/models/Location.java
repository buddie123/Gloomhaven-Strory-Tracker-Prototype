package com.atouchofjoe.ghprototye4.models;

public class Location {
    public static final int TOTAL_LOCATIONS = 95;

    private int number;
    private String name;
    private String teaser;
    private String summary;
    private String conclusion;
    private String[] globalAchievementsGained;
    private String[] globalAchievementsLost;
    private String[] partyAchievementsGained;
    private String[] partyAchievementsLost;
    private String[] locationsUnlocked;
    private String[] locationsBlocked;
    private String[] addRewards;
    private String[] addPenalties;

    public Location(int sNumber, String sName,
                    String sTeaser, String sSummary, String sConclusion,
                    String[] sGlobalAchievementsGained, String[] sGlobalAchievementsLost,
                    String[] sPartyAchievementsGained, String[] sPartyAchievementsLost,
                    String[] sDummyScenariosUnlocked, String[] sDummyScenariosBlocked,
                    String[] sExtrasGained, String[] sExtrasLost) {
        this.number = sNumber;
        this.name = sName;
        this.teaser = sTeaser;
        this.summary = sSummary;
        this.conclusion = sConclusion;
        this.globalAchievementsGained = sGlobalAchievementsGained;
        this.globalAchievementsLost = sGlobalAchievementsLost;
        this.partyAchievementsGained = sPartyAchievementsGained;
        this.partyAchievementsLost = sPartyAchievementsLost;
        this.locationsUnlocked = sDummyScenariosUnlocked;
        this.locationsBlocked = sDummyScenariosBlocked;
        this.addRewards = sExtrasGained;
        this.addPenalties = sExtrasLost;
    }

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

    public String[] getGlobalAchievementsGained() {
        return globalAchievementsGained;
    }

    public String[] getGlobalAchievementsLost() {
        return globalAchievementsLost;
    }

    public String[] getPartyAchievementsGained() {
        return partyAchievementsGained;
    }

    public String[] getPartyAchievementsLost() {
        return partyAchievementsLost;
    }

    public String[] getLocationsUnlocked() {
        return locationsUnlocked;
    }

    public String[] getLocationsBlocked() {
        return locationsBlocked;
    }

    public String[] getAddRewards() {
        return addRewards;
    }

    public String[] getAddPenalties() {
        return addPenalties;
    }

    @Override
    public String toString() { return "#" + number + " " + name; }
    }

