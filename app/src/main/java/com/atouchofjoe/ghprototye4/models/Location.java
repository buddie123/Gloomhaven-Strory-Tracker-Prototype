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
    private int unlockingLoc;

    public Location(int sNumber, String sName,
                    String sTeaser, String sSummary, String sConclusion) {
        this.number = sNumber;
        this.name = sName;
        this.teaser = sTeaser;
        this.summary = sSummary;
        this.conclusion = sConclusion;
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

    @Override
    public String toString() { return "#" + number + " " + name; }
    }

