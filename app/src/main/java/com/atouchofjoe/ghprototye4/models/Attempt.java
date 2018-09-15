package com.atouchofjoe.ghprototye4.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Attempt {
    public static final int MAX_PARTICIPANTS = 4;

    private Location location;
    private Party party;
    private List<String> participants;
    private List<String> nonParticipants;
    private Date timestamp;
    private boolean successful;

    public static Attempt getNewAttempt(Party party, Location location, boolean successful) {
        if(party.getLocationCompleted(location.getNumber()) == true) {
            return null;
        }
        else {
            return new Attempt(party, location, successful);
        }
    }

    private Attempt(Party party, Location location, boolean successful) {
        timestamp = new Date(System.currentTimeMillis());
        this.party = party;
        this.location = location;
        participants = new ArrayList<>();
        participants.add("Participating Characters");
        nonParticipants = new ArrayList<>();
        nonParticipants.add("Non-Participating Characters");
        this.successful = successful;
    }

    public boolean setAsParticipant(Character participant){
        if(participants.size() != MAX_PARTICIPANTS + 1 && !participants.contains(participant)){
            if(nonParticipants.contains(participant.getName())) {
                nonParticipants.remove(participant.getName());
            }
            participants.add(participant.getName());
            return true;
        }
        else{
            return false;
        }
    }

    public boolean setAsNonParticipant(Character nonParticipant){
        if(!nonParticipants.contains(nonParticipant.getName())) {
            if(participants.contains(nonParticipant.getName())) {
                participants.remove(nonParticipant.getName());
            }
            nonParticipants.add(nonParticipant.getName());
            return true;
        }
        else {
            return false;
        }
    }

    public List<String> getParticipants() {
        return participants;
    }

    public List<String> getNonParticipants() {
        return nonParticipants;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public boolean getAttemptSuccessful() {
        return successful;
    }
}
