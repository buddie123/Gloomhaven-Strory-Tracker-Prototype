package com.atouchofjoe.ghprototye4;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.atouchofjoe.ghprototye4.location.info.LocationInfoActivity;
import com.atouchofjoe.ghprototye4.models.Location;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class RecordAttemptActivity extends AppCompatActivity {

    public static final String ARG_LOCATION_NUMBER = "com.atouchofjoe.ghprototye4.RecordAttemptActivity.ARG_LOCATION_NUMBER";

    private EditText dateET;
    private EditText locNumberET;
    private List<String> locStrings = new ArrayList<>();

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
                            public void onDateSet(DatePicker datePicker,
                                                  int year, int month, int day) {
                                dateET.setText((month + 1) + "/" + day + "/" + year);
                            }
                        }, year, month - 1, day);
                // TODO  update these arguments to reflect last selected time
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
                        int newLocNum = (Integer)sparseArray.get(index);
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


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
