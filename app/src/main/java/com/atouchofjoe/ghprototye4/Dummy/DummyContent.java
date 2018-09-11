package com.atouchofjoe.ghprototye4.Dummy;


import android.app.Activity;
import android.content.Context;

import com.atouchofjoe.ghprototye4.MainActivity;
import com.atouchofjoe.ghprototye4.R;
import com.atouchofjoe.ghprototye4.models.Attempt;
import com.atouchofjoe.ghprototye4.models.Character;
import com.atouchofjoe.ghprototye4.models.CharacterClass;
import com.atouchofjoe.ghprototye4.models.Location;
import com.atouchofjoe.ghprototye4.models.Party;

import java.util.ArrayList;
import java.util.List;

import static com.atouchofjoe.ghprototye4.location.info.LocationTabFragment.locations;
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
        party.addCharacter(new Character("Francis", CharacterClass.Brute));
        party.addCharacter(new Character("Ping-Pong", CharacterClass.Cragheart));
        party.addCharacter(new Character("I Pitty The Fool", CharacterClass.Mindthief));
        party.addCharacter(new Character("Me", CharacterClass.Scoundrel));
        party.addCharacter(new Character("Flim-Flam", CharacterClass.Spellweaver));
        party.addCharacter(new Character("Mr. Meek", CharacterClass.Tinkerer));
//
//        Attempt attemptOne = Attempt.getNewAttempt(party, locations[1], false);
//        attemptOne.setAsParticipant(party.getCharacters().get(0));
//        attemptOne.setAsParticipant(party.getCharacters().get(1));
//        attemptOne.setAsParticipant(party.getCharacters().get(2));
//        attemptOne.setAsNonParticipant(party.getCharacters().get(3));
//        attemptOne.setAsNonParticipant(party.getCharacters().get(4));
//        attemptOne.setAsNonParticipant(party.getCharacters().get(5));
//
//        Attempt attemptTwo = Attempt.getNewAttempt(party, locations[1], true);
//        attemptTwo.setAsParticipant(party.getCharacters().get(4));
//        attemptTwo.setAsParticipant(party.getCharacters().get(2));
//        attemptTwo.setAsParticipant(party.getCharacters().get(1));
//        attemptTwo.setAsParticipant(party.getCharacters().get(5));
//        attemptTwo.setAsNonParticipant(party.getCharacters().get(3));
//        attemptTwo.setAsNonParticipant(party.getCharacters().get(0));
    }

    private void addScenario(Location loc) {
        locations[loc.getNumber()] = loc;
    }

    public Party getParty() {
        return party;
    }
}
