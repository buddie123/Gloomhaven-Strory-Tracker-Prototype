package com.atouchofjoe.ghprototye4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.atouchofjoe.ghprototye4.Dummy.DummyContent;
import com.atouchofjoe.ghprototye4.data.DatabaseDescription;
import com.atouchofjoe.ghprototye4.data.StoryDatabaseInitializer;
import com.atouchofjoe.ghprototye4.models.Character;
import com.atouchofjoe.ghprototye4.models.CharacterClass;
import com.atouchofjoe.ghprototye4.models.Location;
import com.atouchofjoe.ghprototye4.models.Party;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.atouchofjoe.ghprototye4.LocationInfoActivity.ARG_PARTY_NAME;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOCATION_CURSOR_LOADER = 1;
    private static final int PARTY_CURSOR_LOADER = 2;
    private static final int CHARACTER_CURSOR_LOADER = 3;
    public static final int PARTY_SELECTED_REQUEST_CODE = 4;

    public static Party currentParty;
    public static Location[] locations = new Location[Location.TOTAL_LOCATIONS];
    public static boolean preferencesChanged = true;
    public static List<Party> partyList;
    public static final String PARTIES = "pref_currentParty";
    public static final String SELECT_A_PARTY = "Select A Party ->";

    public TextView partyValue;
    public Button lastTimeButton;
    public Button locGuideButton;
    public Button historyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        partyList = new ArrayList<>();

        super.onCreate(savedInstanceState);
        new StoryDatabaseInitializer(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                String partyPref = sharedPreferences.getString(s, SELECT_A_PARTY);
                if (!partyPref.equals(SELECT_A_PARTY)) {
                    if (currentParty != null && currentParty.getName().equals(partyPref)) {
                        // TODO
                    } else {
                        currentParty = new Party(partyPref);
                        partyValue.setText(partyPref);
                        Bundle args = new Bundle();
                        args.putString(ARG_PARTY_NAME, partyPref);
                        getLoaderManager().initLoader(CHARACTER_CURSOR_LOADER, args, (MainActivity) partyValue.getContext());
                    }
                }
            }
        });

        partyValue = findViewById(R.id.partyValue);
        partyValue.setText(sharedPreferences.getString(PARTIES, SELECT_A_PARTY));

        ImageButton selectPartyButton = findViewById(R.id.selectPartyButton);
        selectPartyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddSelectPartyDialogFragment().show(getFragmentManager(), "add/select party");
            }
        });


        lastTimeButton = findViewById(R.id.lastTimeButton);
        lastTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LocationInfoActivity.class);
                intent.putExtra(LocationInfoActivity.ARG_LOCATION_NUMBER, 1);
                startActivity(intent);
            }
        });

        locGuideButton = findViewById(R.id.locationGuide);
        locGuideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });

        historyButton = findViewById(R.id.campaignHistory);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        getLoaderManager().initLoader(PARTY_CURSOR_LOADER, null, this);
        getLoaderManager().initLoader(LOCATION_CURSOR_LOADER, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (preferencesChanged) {
            partyValue.setText(PreferenceManager.getDefaultSharedPreferences(this).getString(PARTIES, SELECT_A_PARTY));
            preferencesChanged = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        switch (i) {
            case LOCATION_CURSOR_LOADER:
                return new CursorLoader(this, DatabaseDescription.Locations.CONTENT_URI,
                        null, null, null,
                        DatabaseDescription.Locations.COLUMN_NUMBER);
            case PARTY_CURSOR_LOADER:
                return new CursorLoader(this, DatabaseDescription.Parties.CONTENT_URI,
                        null, null, null, null);
            case CHARACTER_CURSOR_LOADER:
                return new CursorLoader(this, DatabaseDescription.Characters.CONTENT_URI, null,
                        DatabaseDescription.Characters.COLUMN_PARTY + " = ? ", new String[]{bundle.getString(ARG_PARTY_NAME)}, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case PARTY_CURSOR_LOADER:
                if (cursor != null && !cursor.isAfterLast()) {
                    int nameIndex = cursor.getColumnIndex(DatabaseDescription.Parties.COLUMN_NAME);
                    while (cursor.moveToNext()) {
                        Party party = new Party(cursor.getString(nameIndex));
                        partyList.add(party);
                        if(party.getName().contentEquals(partyValue.getText())){
                            currentParty = party;
                        }
                    }
                    lastTimeButton.setEnabled(true);
                    locGuideButton.setEnabled(true);
                    historyButton.setEnabled(true);
                } else {
                    lastTimeButton.setEnabled(false);
                    locGuideButton.setEnabled(false);
                    historyButton.setEnabled(false);
                }
                //Toast.makeText(this, "Finished Loading the Party Cursor", Toast.LENGTH_SHORT).show();
                break;
            case LOCATION_CURSOR_LOADER:
                int locNumberIndex = cursor.getColumnIndex(DatabaseDescription.Locations.COLUMN_NUMBER);
                int locNameIndex = cursor.getColumnIndex(DatabaseDescription.Locations.COLUMN_NAME);
                int locTeaserIndex = cursor.getColumnIndex(DatabaseDescription.Locations.COLUMN_TEASER);
                int locSummaryIndex = cursor.getColumnIndex(DatabaseDescription.Locations.COLUMN_SUMMARY);
                int locConclusionIndex = cursor.getColumnIndex(DatabaseDescription.Locations.COLUMN_CONCLUSION);


                while (cursor.moveToNext()) {
                    locations[cursor.getInt(locNumberIndex)] =
                            new Location(cursor.getInt(locNumberIndex),
                                    cursor.getString(locNameIndex),
                                    cursor.getString(locTeaserIndex),
                                    cursor.getString(locSummaryIndex),
                                    cursor.getString(locConclusionIndex));
                }
                Toast.makeText(this, "Finished loading the Location Cursor Loader", Toast.LENGTH_SHORT).show();
                break;
            case CHARACTER_CURSOR_LOADER:
                int nameIndex = cursor.getColumnIndex(DatabaseDescription.Characters.COLUMN_NAME);
                int classIndex = cursor.getColumnIndex(DatabaseDescription.Characters.COLUMN_CLASS);

                while(cursor.moveToNext()) {
                    currentParty.addCharacter(new Character(cursor.getString(nameIndex), CharacterClass.valueOf(cursor.getString(classIndex))));
                }
                Toast.makeText(this, "Finished loading the Characters for party " + currentParty.getName(), Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public static class AddSelectPartyDialogFragment extends DialogFragment{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());

            builder.setTitle(R.string.dialog_select_party_title);

            builder.setPositiveButton(R.string.dialog_select_party_add_label,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Intent intent = new Intent(getActivity(), CreatePartyActivity.class);
                            startActivityForResult(intent, PARTY_SELECTED_REQUEST_CODE);
                        }
                    });

                final String[] partyNames = new String[partyList.size()];
                int selectedIndex = 0;
                for(int i = 0; i < partyList.size(); i++) {
                    partyNames[i] = partyList.get(i).getName();
                    if(partyList.get(i).getName().contentEquals(currentParty.getName())){
                        selectedIndex = i;
                    }
                }
                builder.setSingleChoiceItems(partyNames, selectedIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                        editor.putString(PARTIES, partyNames[i]);
                        editor.apply();
                        preferencesChanged = true;
                        dialogInterface.dismiss();
                    }
                });



            return builder.create();
        }
    }
}
