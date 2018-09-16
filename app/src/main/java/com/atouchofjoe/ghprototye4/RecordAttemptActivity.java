package com.atouchofjoe.ghprototye4;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.atouchofjoe.ghprototye4.data.DatabaseDescription;
import com.atouchofjoe.ghprototye4.location.info.LocationInfoActivity;
import com.atouchofjoe.ghprototye4.models.CharacterClass;
import com.atouchofjoe.ghprototye4.models.Location;
import com.atouchofjoe.ghprototye4.models.Character;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class RecordAttemptActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String ARG_LOCATION_NUMBER = "com.atouchofjoe.ghprototye4.RecordAttemptActivity.ARG_LOCATION_NUMBER";
    private static final int CHARACTER_CURSOR_LOADER = 25;

    private EditText dateET;
    private EditText locNumberET;
    private Button participantsButton;

    private List<String> locStrings = new ArrayList<>();
    private List<Character> participants = new ArrayList<>();
    private List<Character> nonParticipants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_attempt);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Calendar calendar = new GregorianCalendar();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH) + 1;
        final int year = calendar.get(Calendar.YEAR);

        dateET = findViewById(R.id.dateCompleted);
        dateET.setText(month + "/" + day+ "/" + year);
        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog pickerDialog = new DatePickerDialog(view.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                dateET.setText((month + 1) + "/" + day + "/" + year);
                            }
                        }, year, month - 1, day);
                // TODO update these arguments to reflect last selected time
                pickerDialog.show();
            }
        });

        final int defaultLocNumber = getIntent().getIntExtra(ARG_LOCATION_NUMBER, 0);
        final String defaultLocName = LocationInfoActivity.locations[defaultLocNumber].getName();

        final TextView locNameTV = findViewById(R.id.locNameValue);
        locNameTV.setText(defaultLocName);

        final TextView attemptNumTV = findViewById(R.id.attemptNumValue);
        attemptNumTV.setText("" + (MainActivity.currentParty.getLocationAttemptsForLocation(defaultLocNumber).size() + 1));

        final SparseIntArray sparseArray = new SparseIntArray();
        locStrings = new ArrayList<>();

        for(int i = 1; i < Location.TOTAL_LOCATIONS; i ++) {
            if(MainActivity.currentParty.getLocationAvailable(i)) {
                locStrings.add("#" + i + " " + LocationInfoActivity.locations[i].getName());
                sparseArray.put(locStrings.size() - 1, i);
            }
        }

        locNumberET = findViewById(R.id.editLocNumber);
        locNumberET.setText("" + defaultLocNumber);
        locNumberET.setOnClickListener(new View.OnClickListener() {
            int prevLocNumber = defaultLocNumber;
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Choose Location Number");
                builder.setSingleChoiceItems(locStrings.toArray(new String[0]), sparseArray.indexOfValue(prevLocNumber), new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int index) {
                        int newLocNum = sparseArray.get(index);
                        locNumberET.setText("" + newLocNum);
                        locNameTV.setText(LocationInfoActivity.locations[newLocNum].getName());
                        attemptNumTV.setText("" + (MainActivity.currentParty.getLocationAttemptsForLocation(newLocNum).size() + 1));
                        prevLocNumber = newLocNum;
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        final List<Character> characters = MainActivity.currentParty.getCharacters();
        final List<String> characterNames = new ArrayList<>();

        for(int i = 0; i < characters.size(); i++) {
            characterNames.add(characters.get(i).getName());
        }
        final boolean[] charactersSelected = new boolean[characterNames.size()];

        participantsButton = findViewById(R.id.participantsButton);
        participantsButton.setEnabled(false);
        participantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Select 2 to 4 Participants");
                builder.setMultiChoiceItems(characterNames.toArray(new String[0]), charactersSelected, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int index, boolean isSelected) {
                        charactersSelected[index] = isSelected;
                    }
                });
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int index) {
                        // check for valid entries
                        int numSelected = 0;
                        for (Boolean b: charactersSelected) {
                            if (b) {
                                numSelected++;
                            }
                        }

                        if (numSelected < 2 || 4 < numSelected) {
                            Toast.makeText(view.getContext(), "Select 2 to 4 characters", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        participants.clear();
                        nonParticipants.clear();
                        for(int i = 0; i < charactersSelected.length; i++) {
                            if(charactersSelected[i]) {
                                participants.add(characters.get(i));
                            }
                            else {
                                nonParticipants.add(characters.get(i));
                            }
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.create().show();
            }
        });

        getLoaderManager().initLoader(CHARACTER_CURSOR_LOADER, null, this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        if(id == CHARACTER_CURSOR_LOADER) {
            return new CursorLoader(this, DatabaseDescription.Characters.CONTENT_URI, null,
                    DatabaseDescription.Characters.COLUMN_PARTY + " = ? ",
                    new String[]{MainActivity.currentParty.getName()}, null);
        }
        else {
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(loader.getId() == CHARACTER_CURSOR_LOADER) {
            List<Character> chars = MainActivity.currentParty.getCharacters();
            chars.clear();

            int charNameIndex = cursor.getColumnIndex(DatabaseDescription.Characters.COLUMN_NAME);
            int charClassIndex = cursor.getColumnIndex(DatabaseDescription.Characters.COLUMN_CLASS);
            while(cursor.moveToNext()) {
                chars.add(new Character(cursor.getString(charNameIndex),
                        CharacterClass.values()[cursor.getInt(charClassIndex)]));
            }
            participantsButton.setEnabled(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO
    }
}