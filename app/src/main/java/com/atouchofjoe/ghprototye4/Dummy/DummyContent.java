package com.atouchofjoe.ghprototye4.Dummy;


import android.app.Activity;
import android.content.Context;

import com.atouchofjoe.ghprototye4.MainActivity;
import com.atouchofjoe.ghprototye4.R;
import com.atouchofjoe.ghprototye4.models.Attempt;
import com.atouchofjoe.ghprototye4.models.Location;
import com.atouchofjoe.ghprototye4.models.Party;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    private Location[] locations;
    private Party party;

    public DummyContent(Context context) {
        locations = new Location[Location.TOTAL_LOCATIONS];
        party = new Party(context.getString(R.string.sample_party_name));

        // Add some sample items.
            addScenario(new Location(
                    0,
                    "Gloomhaven",
                    null,
                    null,
                    "You were approached in the Sleeping Lion by a Valrath woman named Jekserah. She has a job for you: track down a thief and return important documents. After some investigation, it looks like you\'ll be beginning your journey at the Black Barrow....\n",
                    new String[] {"City Rule: Militaristic"},
                    new String[] {},
                    new String[] {},
                    new String[] {},
                    new String[]{"1"},
                    new String[]{"0"},
                    new String[] {},
                    new String[] {}));

        addScenario(new Location(
                1,
                "Black Barrow",
                "You were tasked by Jekserah to track down a thief " +
                        "and retrieve some important documents. After roughing up " +
                        "some low-life's, it looks like the Black Barrow is the place" +
                        "to go...\n",
                "The Black Barrow was easy to find - just east of of the city gates." +
                        "You approached the hill and descended the dark stairs, at the bottom of which" +
                        "was your quarry - along with a few friends. Of course, your quarry " +
                        "disappeared through a doorway and you were left to dispatch his friend.",
                "After dispatching some bandits and some restless undead," +
                "you took a breather and pushed aside the horrors of the battle. Of course, " +
                "with the job incomplete, it seemed you would have to trespass deeper. " +
                "You have no choice but to head straight into the Barrow Lair [2]!",
                new String[] {},
                new String[] {},
                new String[]{"First Steps"},
                new String[] {},
                new String[] {},
                new String[]{"2"},
                new String[] {},
                new String[] {}));

        // add a party
        party.addCharacter("Francis");
        party.addCharacter("Ping-Pong");
        party.addCharacter("I Pitty The Fool");
        party.addCharacter("Me");
        party.addCharacter("Flim-Flam");
        party.addCharacter("Mr. Meek");

        Attempt attemptOne = Attempt.getNewAttempt(party, locations[1], false);
        attemptOne.setAsParticipant(party.getCharacters().get(0));
        attemptOne.setAsParticipant(party.getCharacters().get(1));
        attemptOne.setAsParticipant(party.getCharacters().get(2));
        attemptOne.setAsNonParticipant(party.getCharacters().get(3));
        attemptOne.setAsNonParticipant(party.getCharacters().get(4));
        attemptOne.setAsNonParticipant(party.getCharacters().get(5));

        Attempt attemptTwo = Attempt.getNewAttempt(party, locations[1], true);
        attemptTwo.setAsParticipant(party.getCharacters().get(4));
        attemptTwo.setAsParticipant(party.getCharacters().get(2));
        attemptTwo.setAsParticipant(party.getCharacters().get(1));
        attemptTwo.setAsParticipant(party.getCharacters().get(5));
        attemptTwo.setAsNonParticipant(party.getCharacters().get(3));
        attemptTwo.setAsNonParticipant(party.getCharacters().get(0));
    }

    private void addScenario(Location loc) {
        locations[loc.getNumber()] = loc;
    }

    public Location[] getLocations() {
        return locations;
    }

    public Party getParty() {
        return party;
    }
}
