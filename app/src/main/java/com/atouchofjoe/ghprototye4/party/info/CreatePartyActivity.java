package com.atouchofjoe.ghprototye4.party.info;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.atouchofjoe.ghprototye4.MainActivity;
import com.atouchofjoe.ghprototye4.R;
import com.atouchofjoe.ghprototye4.data.DatabaseDescription;
import com.atouchofjoe.ghprototye4.models.CharacterClass;
import com.atouchofjoe.ghprototye4.models.Character;
import com.atouchofjoe.ghprototye4.models.Location;
import com.atouchofjoe.ghprototye4.models.Party;

import java.util.ArrayList;
import java.util.List;

/*
 * CreatePartyActivity.java
 * Author: Robert Reed
 * A page from which to create a new party and add characters through an alert dialog
 * via a FAB button. If created, the party is set as the currentParty in the MainActivity
 * and the sharedPreferences object is updated.
 */
public class CreatePartyActivity extends AppCompatActivity {

    // A copy of the list of characters in the current party used
    // in the recycler view to preview the list of characters
    // before the party is created. When the party is created,
    // the most recent version of this list will be committed to
    // the database.
    public static List<Character> characters = new ArrayList<>();
    public static List<String> characterNames = new ArrayList<>();

    // lifecycle method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // inflate the view and menu
        setContentView(R.layout.activity_create_edit_party);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // allows the user to enter the name of the party to be created
        final EditText partyName = findViewById(R.id.partyNameEditText);

        // a placeholder layout that displays a message instead of having a
        // blank screen when there are no characters
        final FrameLayout noCharsFrame = findViewById(R.id.noCharsFrame);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,  0, 10);
        noCharsFrame.setLayoutParams(params);

        // setup the recycler view with an empty list of characters
        final RecyclerView charRView = findViewById(R.id.charactersRecyclerView);
        charRView.setAdapter(new CharactersRecyclerViewAdapter(this, characters));
        charRView.setLayoutManager(new LinearLayoutManager(this));

        // make the recycler view disappear until there is a character added
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 0);
        charRView.setLayoutParams(params);


        // Hide the cancel edit button when creating a new party.
        // This button should only be visible when editing an existing party
        // in the EditPartyActivity
        Button cancelButton = findViewById(R.id.cancelEditButton);
        cancelButton.setVisibility(View.GONE);

        // Initialize the createPartyButton. Clicking this button creates the party
        // and creates and adds to the party all of the character objects. It also
        // adds the party and characters created to the database. Finally, it
        // sets the created party object as the currentParty in the MainActivity,
        // and updates the sharedPreferences object to point to the new party.
        Button  createPartyButton = findViewById(R.id.createPartyButton);
        createPartyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get party name
                String partyNameString = partyName.getText().toString();

                // check if the name has already been used
                boolean nameExists = false;
                for (Party p : MainActivity.partyList) {
                    if (p.getName().equals(partyNameString)) {
                        nameExists = true;
                        break;
                    }
                }

                // Display an alert Toast if the party name has already been used or if
                // the name is the empty string and do nothing
                if (nameExists) {
                    Toast.makeText(getApplicationContext(), "Party names must be unique!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (partyNameString.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter a party name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // create the new party object, add it to the partyList and
                // set it as the currentParty
                Party party = new Party(partyNameString);
                MainActivity.partyList.add(party);
                MainActivity.currentParty = party;

                // set shared preferences object to point to the new party
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit();
                editor.putString(MainActivity.PREF_CURRENT_PARTY, party.getName() );
                editor.apply();
                MainActivity.preferencesChanged = true;

                // put the party entry into the database
                ContentValues partyValues = new ContentValues();
                partyValues.put(DatabaseDescription.Parties.COLUMN_NAME, party.getName());
                view.getContext().getContentResolver().insert(DatabaseDescription.Parties.CONTENT_URI, partyValues);

                // for each character created, add the character object to the party object
                // and add a corresponding entry to the characters table in the database
                for(Character c : characters) {
                    // add the character object to the party
                    party.addCharacter(c);

                    // add the character entry to the database
                    ContentValues charValues = new ContentValues();
                    charValues.put(DatabaseDescription.Characters.COLUMN_NAME, c.getName());
                    charValues.put(DatabaseDescription.Characters.COLUMN_CLASS, c.getCharClass().toString());
                    charValues.put(DatabaseDescription.Characters.COLUMN_PARTY, party.getName());
                    view.getContext().getContentResolver().insert(DatabaseDescription.Characters.CONTENT_URI, charValues);
                }

                // set the 0th location as having been completed by the party in the database
                ContentValues completedLocValues = new ContentValues();
                completedLocValues.put(DatabaseDescription.CompletedLocations.COLUMN_PARTY, party.getName());
                completedLocValues.put(DatabaseDescription.CompletedLocations.COLUMN_LOCATION_NUMBER, 0);
                completedLocValues.put(DatabaseDescription.CompletedLocations.COLUMN_COMPLETED_TIMESTAMP, System.currentTimeMillis());
                view.getContext().getContentResolver().insert(DatabaseDescription.CompletedLocations.CONTENT_URI, completedLocValues);

                // set the 1st location as being unlocked by the party in the database
                ContentValues unlockedLocValues = new ContentValues();
                unlockedLocValues.put(DatabaseDescription.UnlockedLocations.COLUMN_PARTY, party.getName());
                unlockedLocValues.put(DatabaseDescription.UnlockedLocations.COLUMN_UNLOCKED_LOCATION_NUMBER, 1);
                unlockedLocValues.put(DatabaseDescription.UnlockedLocations.COLUMN_UNLOCKING_LOCATION_NUMBER, 0);
                view.getContext().getContentResolver().insert(DatabaseDescription.UnlockedLocations.CONTENT_URI, unlockedLocValues);

                // set each other location as being locked for the party in the database
                for(int i = 2; i < Location.TOTAL_LOCATIONS; i++ ){
                    ContentValues lockedLocValues = new ContentValues();
                    lockedLocValues.put(DatabaseDescription.LockedLocations.COLUMN_PARTY, party.getName());
                    lockedLocValues.put(DatabaseDescription.LockedLocations.COLUMN_LOCATION_NUMBER, i);
                    view.getContext().getContentResolver().insert(DatabaseDescription.LockedLocations.CONTENT_URI, lockedLocValues);
                }

                // clear the character list for the next time the screen is loaded
                characters.clear();
                charRView.getAdapter().notifyDataSetChanged();

                // return to the main screen
                finish();
            }
        });

        // the add characters button. Opens an alertDialog showing the available classes
        // unlocked by the party and the name to be picked
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // hide the keyboard from the CreatePartyActivity page, if applicable
                final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm != null) {
                    imm.hideSoftInputFromWindow(partyName.getWindowToken(), 0);
                }

                // Initialize an alert dialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                // set the title
                builder.setTitle("Create Character");

                // add a custom view containing an EditText view and a Spinner widget
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                LinearLayout createCharacterLayout = (LinearLayout) inflater.inflate (R.layout.dialog_create_character, null );
                builder.setView(createCharacterLayout);

                // save the layout components for easy access
                final EditText charName = createCharacterLayout.findViewById(R.id.charNameEditText);
                final Spinner charClass = createCharacterLayout.findViewById(R.id.classSpinner);

                // set the positive button to create the defined character and add it the
                // potential changes list
                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // get the character name
                        String charNameString = charName.getText().toString();

                        // check if the character name has already been used in this party
                        boolean nameExists = false;
                        for (String name : characterNames) {
                            if (name.equals(charNameString)) {
                                nameExists = true;
                                break;
                            }
                        }

                        // get the position of the selected character class within the list
                        // (Subtract by one to exclude the spinner default value)
                        int position = charClass.getSelectedItemPosition() - 1;

                        // show a notification and close the alert dialog on invalid entry
                        if (nameExists) {
                            Toast.makeText(getApplicationContext(), "Character names must be unique!", Toast.LENGTH_SHORT).show();
                        } else if (charNameString.equals("")) {
                            Toast.makeText(getApplicationContext(), "Please enter a character name!", Toast.LENGTH_SHORT).show();
                        } else if (position < 0) {
                            Toast.makeText(getApplicationContext(), "Make a Class selection!", Toast.LENGTH_SHORT).show();
                        } else { // if the entries are valid

                            // switch out the FrameLayout for the character RecyclerView
                            // if there are no characters already
                            if(((LinearLayout.LayoutParams)charRView.getLayoutParams()).weight == 0) {
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        0, 10);
                                charRView.setLayoutParams(params);

                                params = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        0, 0);
                                noCharsFrame.setLayoutParams(params);
                            }

                            // update the characters list and notify the RecyclerViewAdapter
                            characters.add(new Character(charNameString, CharacterClass.values()[position]));
                            characterNames.add(charNameString);
                            charRView.getAdapter().notifyDataSetChanged();
                        }

                        // hide the keyboard, if applicable
                        if(imm != null) {
                            imm.hideSoftInputFromWindow(charName.getWindowToken(), 0);
                        }
                    }
                });

                // set the negative button to cancel the creation of the character
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // hide the keyboard, if applicable
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if(imm != null) {
                            imm.hideSoftInputFromWindow(charName.getWindowToken(), 0);
                        }

                        // close the dialog
                        dialogInterface.cancel();
                    }
                });

                // display the dialog
                builder.create().show();
            }
        });

        //display the up button
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /*
     * CharactersRecyclerViewAdapter
     */
    public static class CharactersRecyclerViewAdapter
            extends RecyclerView.Adapter<CharactersRecyclerViewAdapter.ViewHolder> {

        // private fields
        private final List<Character> characterList;
        private LayoutInflater mInflater;


        // private constructor
        private CharactersRecyclerViewAdapter(Context context, List<Character> charList){
            mInflater = LayoutInflater.from(context);
            characterList = charList;
        }

        // inflate the view for each ViewHolder
        @NonNull
        @Override
        public CharactersRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.content_character_detail, parent, false);
            return new ViewHolder(view);
        }

        // update the view's values to reflect the character object within the
        // characterLIst at the given position
        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            Character character = characterList.get(position);
            holder.charName.setText(character.getName());
            holder.charClass.setText(character.getCharClass().toString());
        }

        // the count is the number of characters in characterList
        @Override
        public int getItemCount() {
            return characterList.size();
        }

        /*
         *  class ViewHolder
         */
        class ViewHolder extends RecyclerView.ViewHolder {
            // declaration of the fields used to hold the objects in the view
            final TextView charName;
            final ImageButton deleteCharacterButton;
            final TextView charClass;


            // default constructor
            ViewHolder(View view) {
                super(view);

                // the character name TextView
                charName = view.findViewById(R.id.charName);

                // the button used to delete a character
                // deletes can be undone by hitting the up or back buttons
                deleteCharacterButton = view.findViewById(R.id.deleteCharacterButton);
                deleteCharacterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // if the last character in the list is being removed,
                        // switch out the RecyclerView for the noChars FrameLayout
                        if(characters.size() == 1) {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    0, 0);
                            view.getRootView().findViewById(R.id.charactersRecyclerView).setLayoutParams(params);

                            params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    0, 10);
                            view.getRootView().findViewById(R.id.noCharsFrame).setLayoutParams(params);
                        }
                        // remove the character from the temp list and
                        // notify the adapter that the data set has been altered
                        characters.remove(getAdapterPosition());
                        notifyDataSetChanged();
                    }
                });

                // the character class TextView
                charClass = view.findViewById(R.id.characterClass);
            }
        }
    }
}
