package com.atouchofjoe.ghprototye4.party.info;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import com.atouchofjoe.ghprototye4.models.Character;
import com.atouchofjoe.ghprototye4.models.CharacterClass;
import com.atouchofjoe.ghprototye4.models.Party;

import java.util.ArrayList;
import java.util.List;

import static com.atouchofjoe.ghprototye4.location.info.LocationInfoActivity.ARG_PARTY_NAME;
import static com.atouchofjoe.ghprototye4.MainActivity.currentParty;

public class EditPartyActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    // loader call backs constants
    private static final int CHARACTER_CURSOR_LOADER = 3;

    // A copy of the list of characters in the current party
    // used by the recycler view to preview the list of characters
    // before the party is edited. When the "save changes" button
    // is pressed, the most recent version of this list will be
    // committed to the database.
    public static List<Character> characters;
    public static List<String> characterNames;

    // holds the characterRecyclerView layout for easy access
    private RecyclerView charRView;

    // Character Lists used to save temporary changes until the
    // changes are saved are canceled
    private static List<Character> deletedChars = new ArrayList<>();
    private static List<Character> unAlteredChars = new ArrayList<>();
    private static List<Character> newChars = new ArrayList<>();

    // save the original party name for updating entries in the database
    private static String originalPartyName;

    // lifecycle method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // inflate the view and and setup the menu bar
        setContentView(R.layout.activity_create_edit_party);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize the characters array (
        characters = new ArrayList<>();
        characterNames = new ArrayList<>();

        // start the CHARACTER_CURSOR_LOADER
        Bundle args = new Bundle();
        args.putString(ARG_PARTY_NAME, currentParty.getName());
        getLoaderManager().initLoader(CHARACTER_CURSOR_LOADER, args, this);

        // set the partyName EditText's text and the originalPartyName
        // field to to the currentParty's name
        final EditText partyName = findViewById(R.id.partyNameEditText);
        originalPartyName = currentParty.getName();
        partyName.setText(currentParty.getName());

        final FrameLayout noCharsFrame = findViewById(R.id.noCharsFrame);

        // initialize the charRView recyclerView
        charRView = findViewById(R.id.charactersRecyclerView);
        charRView.setAdapter(new CharactersRecyclerViewAdapter(this, characters));
        charRView.setLayoutManager(new LinearLayoutManager(this));

        // the cancel button resets all the ArrayLists used to track temporary
        // changes and calls finish().
        Button cancelButton = findViewById(R.id.cancelEditButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanUp();
                finish();
            }
        });

        // set the createPartyButton to save any changes to the party
        // and update all the relevant database tables
        Button createPartyButton = findViewById(R.id.createPartyButton);
        createPartyButton.setText(R.string.label_save_changes);
        createPartyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the new party name
                String partyNameString = partyName.getText().toString();

                if (!partyNameString.equals(currentParty.getName())) {
                    // check if the name has already been used
                    boolean nameExists = false;
                    for (Party p : MainActivity.partyList) {
                        if (p != currentParty &&
                                p.getName().equals(partyNameString)) {
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

                    // if name is a legal value, update the sharedPeferences object and
                    // the currentParty object's name value
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit();
                    editor.putString(MainActivity.PREF_CURRENT_PARTY, partyNameString);
                    editor.apply();
                    MainActivity.preferencesChanged = true;
                    currentParty.setName(partyNameString);

                    // update the party entry in the database
                    ContentValues partyValues = new ContentValues();
                    partyValues.put(DatabaseDescription.Parties.COLUMN_NAME, partyNameString);
                    view.getContext().getContentResolver().update(DatabaseDescription.Parties.CONTENT_URI, partyValues,
                            DatabaseDescription.Parties.COLUMN_NAME + " = ? ", new String[]{originalPartyName});

                    // update the party name for each un-updated character in the database
                    for (Character c : unAlteredChars) {
                        ContentValues charValues = new ContentValues();
                        partyValues.put(DatabaseDescription.Characters.COLUMN_PARTY, partyNameString);
                        view.getContext().getContentResolver().update(DatabaseDescription.Characters.CONTENT_URI, charValues,
                                DatabaseDescription.Characters.COLUMN_PARTY + " = ? AND " +
                                        DatabaseDescription.Characters.COLUMN_NAME + " = ? ", new String[]{originalPartyName, c.getName()});
                    }

                    // update the party name for each of the location tables in the database

                    // UnlockedLocations
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DatabaseDescription.UnlockedLocations.COLUMN_PARTY, partyNameString);
                    view.getContext().getContentResolver().update(DatabaseDescription.UnlockedLocations.CONTENT_URI, contentValues,
                            DatabaseDescription.UnlockedLocations.COLUMN_PARTY + " = ? ",
                            new String[]{originalPartyName});

                    // BlockedLocations
                    contentValues = new ContentValues();
                    contentValues.put(DatabaseDescription.BlockedLocations.COLUMN_PARTY, partyNameString);
                    view.getContext().getContentResolver().update(DatabaseDescription.BlockedLocations.CONTENT_URI, contentValues,
                            DatabaseDescription.BlockedLocations.COLUMN_PARTY + " = ? ",
                            new String[]{originalPartyName});

                    // CompletedLocations
                    contentValues = new ContentValues();
                    contentValues.put(DatabaseDescription.CompletedLocations.COLUMN_PARTY, partyNameString);
                    view.getContext().getContentResolver().update(DatabaseDescription.CompletedLocations.CONTENT_URI, contentValues,
                            DatabaseDescription.CompletedLocations.COLUMN_PARTY + " = ? ",
                            new String[]{originalPartyName});

                    // LockedLocations
                    contentValues = new ContentValues();
                    contentValues.put(DatabaseDescription.LockedLocations.COLUMN_PARTY, partyNameString);
                    view.getContext().getContentResolver().update(DatabaseDescription.LockedLocations.CONTENT_URI, contentValues,
                            DatabaseDescription.LockedLocations.COLUMN_PARTY + " = ? ",
                            new String[]{originalPartyName});

                    // TODO add additional tables that need updating when the party name is changed
                }

                // add the new characters to both the current party and the database
                for (Character nc : newChars) {
                    ContentValues charValues = new ContentValues();
                    charValues.put(DatabaseDescription.Characters.COLUMN_NAME, nc.getName());
                    charValues.put(DatabaseDescription.Characters.COLUMN_CLASS, nc.getCharClass().toString());
                    charValues.put(DatabaseDescription.Characters.COLUMN_PARTY, partyNameString);
                    partyName.getContext().getContentResolver().insert(DatabaseDescription.Characters.CONTENT_URI, charValues);
                    currentParty.addCharacter(nc);
                }

                // remove the deleted characters from both the current party and the database
                for (Character dc : deletedChars) {
                    view.getContext().getContentResolver().delete(DatabaseDescription.Characters.CONTENT_URI,
                            DatabaseDescription.Characters.COLUMN_PARTY + " = ? AND " +
                                    DatabaseDescription.Characters.COLUMN_NAME + " = ?",
                            new String[]{originalPartyName, dc.getName()});
                    currentParty.removeCharacter(dc);
                }

                // clear all the character lists for the next time the screen is loaded
                cleanUp();

                // return to the main screen
                finish();
            }
        });

        // the add characters button. Opens an alertDialog showing the available
        // classes unlocked by the party and the name to be picked
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // hide the keyboard from the
                final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(partyName.getWindowToken(), 0);
                }

                // Initialize an alert dialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                // set the title
                builder.setTitle("Create Character");

                // add a custom view containing and EditText view and a Spinner widget
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                LinearLayout createCharacterLayout = (LinearLayout) inflater.inflate(R.layout.dialog_create_character, null);
                builder.setView(createCharacterLayout);

                // save the layout components for easy access
                final EditText charName = createCharacterLayout.findViewById(R.id.charNameEditText);
                final Spinner charClass = createCharacterLayout.findViewById(R.id.classSpinner);

                // set the positive button to create the defined character and add it
                // to the list of potential changes
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
                            if (((LinearLayout.LayoutParams) charRView.getLayoutParams()).weight == 0) {
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        0, 10);
                                charRView.setLayoutParams(params);

                                params = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        0, 0);
                                noCharsFrame.setLayoutParams(params);
                            }

                            // update the character lists and notify the RecyclerViewAdapter
                            Character newChar = new Character(charName.getText().toString(), CharacterClass.values()[position]);
                            newChars.add(newChar);
                            characters.add(newChar);
                            characterNames.add(newChar.getName());
                            charRView.getAdapter().notifyDataSetChanged();
                        }

                        // hide the keyboard, if applicable
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(charName.getWindowToken(), 0);
                        }
                    }
                });

                // set the negative button to cancel the creation of the character
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
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

        // display the up button
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
    }

    // reinitialize the character arrays
    private void cleanUp(){
        characters = new ArrayList<>();
        characterNames = new ArrayList<>();
        newChars = new ArrayList<>();
        unAlteredChars = new ArrayList<>();
        deletedChars = new ArrayList<>();
    }

    // creates the CHARACTER_CURSOR_LOADER to get all characters belonging to the
    // current party from the database
    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle bundle) {
        if(loaderID == CHARACTER_CURSOR_LOADER) {
            return new CursorLoader(this, DatabaseDescription.Characters.CONTENT_URI, null,
                    DatabaseDescription.Characters.COLUMN_PARTY + " = ? ", new String[]{bundle.getString(ARG_PARTY_NAME)}, null);
        }
        // return null if loaderID invalid
        return null;
    }

    // when the CHARACTER_CURSOR_LOADER finishes, populate the characters list.
    // if the list is empty, switch out the RecyclerView for the empty chars list
    // FrameLayout. Then, notify the RecyclerView about the character list update.
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(loader.getId() == CHARACTER_CURSOR_LOADER) {
            // get the indices of the columns in question
            int nameIndex = cursor.getColumnIndex(DatabaseDescription.Characters.COLUMN_NAME);
            int classIndex = cursor.getColumnIndex(DatabaseDescription.Characters.COLUMN_CLASS);

            // reset the characters list as a precaution
            characters.clear();
            characterNames.clear();

            // populate the characters list, the unaltered characters list,
            // and the currentParty character list with each entry returned by
            // the query
            while (cursor.moveToNext()) {
                Character character = new Character(cursor.getString(nameIndex), CharacterClass.valueOf(cursor.getString(classIndex)));
                characters.add(character);
                characterNames.add(character.getName());
                unAlteredChars.add(character);
                currentParty.addCharacter(character);
            }

            // switch out the RecyclerView for the noChars FrameLayout if characters
            // list is empty
            if (characters.size() == 0) {
                FrameLayout noCharsFrame = findViewById(R.id.noCharsFrame);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 0, 10);
                noCharsFrame.setLayoutParams(params);

                RecyclerView charRView = findViewById(R.id.charactersRecyclerView);
                params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 0 , 0);
                charRView.setLayoutParams(params);
            }

            // notify the RecyclerView adapter that the character list has changed
            charRView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO
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
        // characterList at the given position
        @Override
        public void onBindViewHolder(@NonNull CharactersRecyclerViewAdapter.ViewHolder holder, int position) {
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
         * class ViewHolder
         */
        class ViewHolder extends RecyclerView.ViewHolder {
            // declaration of the fields used to hold the objects in the view
            final TextView charName;
            final ImageButton deleteCharacterButton;
            final TextView charClass;

            // default constructor
            ViewHolder(View view) {
                super(view);
                // the character name Textview
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

                        // remove the character from the temp list, add it to the
                        // list of characters to be deleted, and removed it from the list
                        // of characters that haven't been altered. Then, notify the
                        // adapter that the data set has changed.
                        Character deletedChar = characters.remove(getAdapterPosition());
                        characterNames.remove(getAdapterPosition());
                        deletedChars.add(deletedChar);
                        unAlteredChars.remove(deletedChar);
                        notifyDataSetChanged();
                    }
                });

                // the character class TextView
                charClass = view.findViewById(R.id.characterClass);
            }
        }
    }
}