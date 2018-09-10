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
    private Party party;

    public DummyContent(Context context) {
        party = new Party(context.getString(R.string.sample_party_name));

        // add a party
        party.addCharacter("Francis");
        party.addCharacter("Ping-Pong");
        party.addCharacter("I Pitty The Fool");
        party.addCharacter("Me");
        party.addCharacter("Flim-Flam");
        party.addCharacter("Mr. Meek");

        Attempt attemptOne = Attempt.getNewAttempt(party, MainActivity.locations[1], false);
        attemptOne.setAsParticipant(party.getCharacters().get(0));
        attemptOne.setAsParticipant(party.getCharacters().get(1));
        attemptOne.setAsParticipant(party.getCharacters().get(2));
        attemptOne.setAsNonParticipant(party.getCharacters().get(3));
        attemptOne.setAsNonParticipant(party.getCharacters().get(4));
        attemptOne.setAsNonParticipant(party.getCharacters().get(5));

        Attempt attemptTwo = Attempt.getNewAttempt(party, MainActivity.locations[1], true);
        attemptTwo.setAsParticipant(party.getCharacters().get(4));
        attemptTwo.setAsParticipant(party.getCharacters().get(2));
        attemptTwo.setAsParticipant(party.getCharacters().get(1));
        attemptTwo.setAsParticipant(party.getCharacters().get(5));
        attemptTwo.setAsNonParticipant(party.getCharacters().get(3));
        attemptTwo.setAsNonParticipant(party.getCharacters().get(0));
    }

    private void addScenario(Location loc) {
        MainActivity.locations[loc.getNumber()] = loc;
    }

    public Party getParty() {
        return party;
    }
}
