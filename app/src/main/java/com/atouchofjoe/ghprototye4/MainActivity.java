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
import android.net.Uri;
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
import android.widget.Toast;

import com.atouchofjoe.ghprototye4.Dummy.DummyContent;
import com.atouchofjoe.ghprototye4.data.DatabaseDescription;
import com.atouchofjoe.ghprototye4.data.StoryDatabaseInitializer;
import com.atouchofjoe.ghprototye4.models.Location;
import com.atouchofjoe.ghprototye4.models.Party;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOCATION_CURSOR_LOADER = 1;
    private static final int PARTY_CURSOR_LOADER = 2;

    public static Party currentParty;
    public static Location[] locations = new Location[Location.TOTAL_LOCATIONS];
    private boolean preferencesChanged = true;
    public static List<Party> partyList;
    public static final String PARTIES = "pref_currentParty";
    public static final String SELECT_A_PARTY = "Select A Party ->";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String partyName = getString(R.string.sample_party_name);
        partyList = new ArrayList<>();

        super.onCreate(savedInstanceState);
        StoryDatabaseInitializer initializer = new StoryDatabaseInitializer(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getLoaderManager().initLoader(PARTY_CURSOR_LOADER, null, this);
        getLoaderManager().initLoader(LOCATION_CURSOR_LOADER, null, this);

//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        //      PreferenceManager.getDefaultSharedPreferences(this).
        //              registerOnSharedPreferenceChangeListener(preferencesOnChangeListener);

        ImageButton selectPartyButton = findViewById(R.id.selectPartyButton);
        selectPartyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddSelectPartyDialogFragment().show(getFragmentManager(), "add/select party");
            }
        });


        Button lastTimeButton = findViewById(R.id.lastTimeButton);
        lastTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LocationInfoActivity.class);
                intent.putExtra(LocationInfoActivity.ARG_LOCATION_NUMBER, 1);
                intent.putExtra(LocationInfoActivity.ARG_PARTY_NAME, partyName);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (preferencesChanged) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String party = preferences.getString(PARTIES, null);
            if (party != null) {
                setCurrentParty(party);
            } else {
                setCurrentParty(SELECT_A_PARTY);
            }
        }
    }

    private void setCurrentParty(String party) {
        ((TextView) findViewById(R.id.partyValue)).setText(party);
        if (!party.equals(SELECT_A_PARTY)) {
            new Party(party);
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

    SharedPreferences.OnSharedPreferenceChangeListener preferencesOnChangeListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                    if (sharedPreferences.getString(PARTIES, "No Parties Created").equals("No Parties Created")) {
                        findViewById(R.id.lastTimeButton).setEnabled(false);
                        findViewById(R.id.locationGuide).setEnabled(false);
                        findViewById(R.id.campaignHistory).setEnabled(false);
                        setCurrentParty("Create a Party ->");
                    } else {
                        findViewById(R.id.lastTimeButton).setEnabled(true);
                        findViewById(R.id.locationGuide).setEnabled(true);
                        findViewById(R.id.campaignHistory).setEnabled(true);
                        setCurrentParty(sharedPreferences.getString(PARTIES, null));
                    }
                }
            };

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
                        partyList.add(new Party(cursor.getString(nameIndex)));
                    }
                    ((TextView) findViewById(R.id.partyValue)).setText(partyList.get(0).getName());
                } else {
                    ((TextView) findViewById(R.id.partyValue)).setText(SELECT_A_PARTY);
                }
                Toast.makeText(this, "Finished Loading the Party Cursor", Toast.LENGTH_SHORT).show();
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
            builder.setMessage(R.string.dialog_select_party_message);

            builder.setPositiveButton(R.string.dialog_select_party_add_label,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Intent intent = new Intent(getActivity(), CreatePartyActivity.class);
                            startActivity(intent);
                        }
                    });

            if(partyList.size() > 0 ) {
                final String[] partyNames = new String[partyList.size()];

                for(int i = 0; i < partyList.size(); i++) {
                    partyNames[i] = partyList.get(i).getName();
                }
                builder.setItems(partyNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Button selection = getActivity().findViewById(i);
                        int index = 0;
                        for(; !selection.getText().equals(partyNames[i]); i ++) {
                            continue;
                        }
                        currentParty = partyList.get(index);
                        dialogInterface.dismiss();
                    }
                });
            }
            return builder.create();
        }
    }
}
