package com.atouchofjoe.ghprototye4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.atouchofjoe.ghprototye4.data.DatabaseDescription;
import com.atouchofjoe.ghprototye4.data.StoryDatabaseInitializer;
import com.atouchofjoe.ghprototye4.location.info.LocationInfoActivity;
import com.atouchofjoe.ghprototye4.models.Character;
import com.atouchofjoe.ghprototye4.models.CharacterClass;
import com.atouchofjoe.ghprototye4.models.Party;
import com.atouchofjoe.ghprototye4.party.info.CreatePartyActivity;
import com.atouchofjoe.ghprototye4.party.info.EditPartyActivity;

import java.util.ArrayList;
import java.util.List;

/*
 * MainActivity.java
 * Author: Robert Reed
 * The landing page for the Gloomhaven story tracking app. Displays a welcome message
 * along with the current/last party selected, if any, with a few choice buttons to
 * navigate to the main parts of the app. These include an add/edit party button next
 * to the party name.
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    // Loader constants for LoaderCallbacks
    private static final int PARTY_CURSOR_LOADER = 2;
    private static final int CHARACTER_CURSOR_LOADER = 17;
    private static final String ARG_PARTY_NAME  = "com.atouchofjoe.ghprototye4.MainActivity.ARG_PARTY_NAME";

    // other constants
    public static final String PREF_CURRENT_PARTY = "pref_currentParty";
    public static final String CREATE_A_PARTY = "Create a Party -->";

    // public variables for tracking the party options across activities
    public static Party currentParty;
    public static boolean preferencesChanged = false;
    public static List<Party> partyList = new ArrayList<>();
    public static int newPartySelection = 0;

    // shared preference object
    private static SharedPreferences sharedPreferences;

    // important objects in the activity_main layout that need to be accessed asynchronously
    private TextView partyNameTV;
    private ImageButton selectPartyButton;
    private Button lastTimeButton;
    private Button locGuideButton;
    private Button historyButton;

    // lifecycle method onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize database if needed
        new StoryDatabaseInitializer(this);

        // inflate view and add menu
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get shared preferences object and set listener
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String prefType) {
                // get the updated preference
                String sharedPreferencesString = sharedPreferences.getString(prefType, null);

                // if the current party preference was changed, assign a new Party object to
                // currentParty with the new setting and change the partyNameTV text to reflect
                // the new currentParty name
                if (prefType.equals(PREF_CURRENT_PARTY) && sharedPreferencesString != null) {
                    if (currentParty == null || !currentParty.getName().equals(sharedPreferencesString)) {
                        currentParty = new Party(sharedPreferencesString);
                        partyList.add(currentParty);
                    }
                    partyNameTV.setText(sharedPreferencesString);
                }
            }
        });

        // display the current party name or, if no parties created, a message to create a party
        partyNameTV = findViewById(R.id.partyValue);
        partyNameTV.setText(sharedPreferences.getString(PREF_CURRENT_PARTY, CREATE_A_PARTY));

        // set the selectPartyButton to display an Alert Dialog with options to add, edit
        // or switch out the current party
        // NOTE: should be disabled until the PARTY_CURSOR_LOADER is finished
        selectPartyButton = findViewById(R.id.selectPartyButton);
        selectPartyButton.setEnabled(false);
        selectPartyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create and show dialog
                new AddSelectPartyDialogFragment().show(getFragmentManager(), "add/select party");
            }
        });

        // set the lastTimeButton to open the LocationInfoActivity with the info for the last
        // location attempted by the current party loaded.
        lastTimeButton = findViewById(R.id.lastTimeButton);
        lastTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LocationInfoActivity.class);
                intent.putExtra(LocationInfoActivity.ARG_PARTY_NAME, currentParty.getName());
                intent.putExtra(LocationInfoActivity.ARG_LOCATION_NUMBER, 0); // TODO edit this to use a new shared preference value
                startActivity(intent);
            }
        });

        // open the location guide helper screen
        locGuideButton = findViewById(R.id.locationGuide);
        locGuideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO create a LocationGuideActivity class and start it here
            }
        });

        // open the campaign history screen
        historyButton = findViewById(R.id.campaignHistory);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO create a CampaignHistoryActivity class and start it here
            }
        });

        // initialize loaders for retrieving the data for the created parties
        getLoaderManager().initLoader(PARTY_CURSOR_LOADER, null, this);
    }

    // lifecycle method onStart used to reset the current party if necessary when the activity
    // is restarted
    @Override
    protected void onStart() {
        super.onStart();

        // update app to reflect preferences
        if (preferencesChanged) {

            // set the partyNameTV field to the name of the currentParty
            partyNameTV.setText(currentParty.getName());

            if(!partyList.contains(currentParty))
                partyList.add(currentParty);
            // reset the preferencesChanged variable
            preferencesChanged = false;
        }
}


    // set up the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // TODO add menu options
        return true;
    }

    // set the actions for each menu option
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // TODO add menu option actions
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Inner class defining the logic for the selectPartyButton
    // Displays the list of created parties, which have been pre-loaded
    // from the database, along with options to open the edit or add party
    // screens or simply select a new current party from the list
    public static class AddSelectPartyDialogFragment extends DialogFragment{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // get the builder object
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());

            // set the title
            builder.setTitle(R.string.dialog_select_party_title);

            // display an "add party" message instead of a party list if the partyList is empty
            if(partyList.size() == 0) {
                builder.setMessage("Create a new party");
            }
            // display a list of party names, along with "Select Party" and "Edit Party" buttons
            // if the partyList is not empty
            else {

                // initialize the value that will be used to set the default of the
                // alert dialog list to match the current party
                int defaultSelectionIndex = 0;

                // get a list of party names in the same order as the corresponding partyList
                final String[] partyNames = new String[partyList.size()];
                for (int i = 0; i < partyList.size(); i++) {
                    partyNames[i] = partyList.get(i).getName();

                    // if this is the index of the current party, update defaultSelectionIndex
                    // to this index
                    if (partyList.get(i) == currentParty) {
                        defaultSelectionIndex = i;
                        newPartySelection = i;
                    }
                }

                // set a radio button list with the currentParty name selected as a default
                builder.setSingleChoiceItems(partyNames, defaultSelectionIndex, new DialogInterface.OnClickListener() {
                    // clicking on a selection does nothing unless you also press either the"Select Party" or
                    // "Edit Party" button
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        newPartySelection = i;
                    }
                });

                // the top/right button updates the currentParty on the main screen along with the
                // value in the shared preferences object
                builder.setPositiveButton("Select Party", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // update shared preferences object
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(PREF_CURRENT_PARTY, partyNames[newPartySelection]);
                        editor.apply();
                        preferencesChanged = true;

                        // update currentParty
                        currentParty = partyList.get(newPartySelection);
                        ((TextView)getActivity().findViewById(R.id.partyValue)).setText(currentParty.getName());
                    }
                });

                // the middle button updates the current party value along with the sharedPreferences
                // object and opens pre-populated "Edit Party" activity
                builder.setNegativeButton("Edit Party", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                        // update shared preferences object
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(PREF_CURRENT_PARTY, partyNames[newPartySelection]);
                        editor.apply();
                        preferencesChanged = true;

                        // update currentParty
                        currentParty = partyList.get(newPartySelection);

                        // open the editParty screen ( will populate with the currentParty info)
                        Intent intent = new Intent(getActivity(), EditPartyActivity.class);
                        startActivity(intent);
                    }
                });
            }

            // the bottom/left button is always displayed and opens a "Create Party" activity
            builder.setNeutralButton(R.string.dialog_select_party_add_label,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Intent intent = new Intent(getActivity(), CreatePartyActivity.class);
                            startActivity(intent);
                        }
                    });

            // return the created dialog
            return builder.create();
        }
    }

    // implementation of the LoaderCallbacks methods
    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle bundle) {
        // get all parties in the database
        if (loaderID == PARTY_CURSOR_LOADER) {
            return new CursorLoader(this, DatabaseDescription.Parties.CONTENT_URI,
                    null, null, null, null);
        } else if (loaderID >= CHARACTER_CURSOR_LOADER  &&
                    loaderID < CHARACTER_CURSOR_LOADER + partyList.size()) {
            return new CursorLoader(this, DatabaseDescription.Characters.CONTENT_URI, null,
                    DatabaseDescription.Characters.COLUMN_PARTY + " = ? ",
                    new String[]{bundle.getString(ARG_PARTY_NAME)}, null);
        }
        else {
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(loader.getId() == PARTY_CURSOR_LOADER) {

            // make a new partyList containing all the parties in the database
            partyList = new ArrayList<>();

            // get the index for the "name" column
            int nameIndex = cursor.getColumnIndex(DatabaseDescription.Parties.COLUMN_NAME);


            String partyPrefName = sharedPreferences.getString(PREF_CURRENT_PARTY, null);

            // create a new party object for each party entry in the database and add
            // it to partyList
            while (cursor.moveToNext()) {
                Party party = new Party(cursor.getString(nameIndex));
                partyList.add(party);

                // set currentParty if party matches sharedPreferences value
                if (partyPrefName != null && party.getName().contentEquals(partyPrefName)) {
                    currentParty = party;
                }
            }

            // enable buttons if there is a current party selected
            if (currentParty != null) {
                lastTimeButton.setEnabled(true);
                locGuideButton.setEnabled(true);
                historyButton.setEnabled(true);
                for (int i = 0; i < partyList.size(); i++) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ARG_PARTY_NAME, partyList.get(i).getName());
                    getLoaderManager().initLoader(CHARACTER_CURSOR_LOADER + i, bundle, this);
                }
            } else { // disable buttons if there is no current party selected
                lastTimeButton.setEnabled(false);
                locGuideButton.setEnabled(false);
                historyButton.setEnabled(false);
            }
            selectPartyButton.setEnabled(true);
        }
        else if (loader.getId() >= CHARACTER_CURSOR_LOADER  &&
                loader.getId() < CHARACTER_CURSOR_LOADER + partyList.size()) {

            Party party = partyList.get(loader.getId() - CHARACTER_CURSOR_LOADER);

            List<Character> chars = party.getCharacters();
            chars.clear();

            int charNameIndex = cursor.getColumnIndex(DatabaseDescription.Characters.COLUMN_NAME);
            int charClassIndex = cursor.getColumnIndex(DatabaseDescription.Characters.COLUMN_CLASS);
            while (cursor.moveToNext()) {
                chars.add(new Character(cursor.getString(charNameIndex),
                        CharacterClass.values()[cursor.getInt(charClassIndex)]));
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Research how to use this method
    }
}
