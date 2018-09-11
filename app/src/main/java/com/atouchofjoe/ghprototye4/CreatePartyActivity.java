package com.atouchofjoe.ghprototye4;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.atouchofjoe.ghprototye4.data.DatabaseDescription;
import com.atouchofjoe.ghprototye4.data.StoryDatabaseHelper;
import com.atouchofjoe.ghprototye4.models.CharacterClass;
import com.atouchofjoe.ghprototye4.models.Character;
import com.atouchofjoe.ghprototye4.models.Party;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import static com.atouchofjoe.ghprototye4.location.info.LocationTabFragment.locations;

public class CreatePartyActivity extends AppCompatActivity {

    public static final String RESULT_PARTY_NAME = "com.atouchofjoe.ghprototye4.RESULT_PARTY_NAME";
    public static List<Character> characters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText partyName = findViewById(R.id.partyNameEditText);

        final RecyclerView charRView = findViewById(R.id.charactersRecyclerView);
        charRView.setAdapter(new CharactersRecyclerViewAdapter(this, characters));
        charRView.setLayoutManager(new LinearLayoutManager(this));

        Button  createPartyButton = findViewById(R.id.createPartyButton);
        createPartyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Party party = new Party(partyName.getText().toString());

                StoryDatabaseHelper dbHelper = new StoryDatabaseHelper(view.getContext());
                dbHelper.getWritableDatabase();

                ContentValues partyValues = new ContentValues();
                partyValues.put(DatabaseDescription.Parties.COLUMN_NAME, party.getName());
                view.getContext().getContentResolver().insert(DatabaseDescription.Parties.CONTENT_URI, partyValues);

                for(Character c : characters) {
                    party.addCharacter(c);

                    ContentValues charValues = new ContentValues();
                    charValues.put(DatabaseDescription.Characters.COLUMN_NAME, c.getName());
                    charValues.put(DatabaseDescription.Characters.COLUMN_CLASS, c.getCharClass().toString());
                    charValues.put(DatabaseDescription.Characters.COLUMN_PARTY, party.getName());
                    view.getContext().getContentResolver().insert(DatabaseDescription.Characters.CONTENT_URI, charValues);
                }

                ContentValues completedLocValues = new ContentValues();
                completedLocValues.put(DatabaseDescription.CompletedLocations.COLUMN_PARTY, party.getName());
                completedLocValues.put(DatabaseDescription.CompletedLocations.COLUMN_LOCATION_NUMBER, 0);
                completedLocValues.put(DatabaseDescription.CompletedLocations.COLUMN_COMPLETED_TIMESTAMP, System.currentTimeMillis());
                view.getContext().getContentResolver().insert(DatabaseDescription.CompletedLocations.CONTENT_URI, completedLocValues);

                ContentValues unlockedLocValues = new ContentValues();
                unlockedLocValues.put(DatabaseDescription.UnlockedLocations.COLUMN_PARTY, party.getName());
                unlockedLocValues.put(DatabaseDescription.UnlockedLocations.COLUMN_UNLOCKED_LOCATION_NUMBER, 1);
                unlockedLocValues.put(DatabaseDescription.UnlockedLocations.COlUMN_UNLOCKING_LOCATION_NUMBER, 0);
                view.getContext().getContentResolver().insert(DatabaseDescription.UnlockedLocations.CONTENT_URI, unlockedLocValues);


                for(int i = 2; i < locations.length; i++ ){
                    if(locations[i] != null){
                        ContentValues lockedLocValues = new ContentValues();
                        lockedLocValues.put(DatabaseDescription.LockedLocations.COLUMN_PARTY, party.getName());
                        lockedLocValues.put(DatabaseDescription.LockedLocations.COLUMN_LOCATION_NUMBER, i);
                        view.getContext().getContentResolver().insert(DatabaseDescription.LockedLocations.CONTENT_URI, lockedLocValues);
                    }
                }
                MainActivity.partyList.add(party);
                MainActivity.currentParty = party;

                characters.clear();

                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit();
                editor.putString(MainActivity.PARTIES, party.getName() );
                editor.apply();


                charRView.getAdapter().notifyDataSetChanged();

                MainActivity.preferencesChanged = true;
                finish();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(partyName.getWindowToken(), 0);


                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Create Character");

                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                LinearLayout createCharacterLayout = (LinearLayout) inflater.inflate(R.layout.dialog_create_character,null);

                final EditText charName = createCharacterLayout.findViewById(R.id.charNameEditText);

                final Spinner charClass = createCharacterLayout.findViewById(R.id.classSpinner);


                builder.setView(createCharacterLayout);
                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int position = charClass.getSelectedItemPosition() - 1;
                        if(position >= 0) {
                            characters.add(new Character(charName.getText().toString(), CharacterClass.values()[position]));
                            charRView.getAdapter().notifyDataSetChanged();

                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Make a Class selection!", Toast.LENGTH_SHORT).show();
                        }
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(charName.getWindowToken(), 0);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(charName.getWindowToken(), 0);
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static class CharactersRecyclerViewAdapter
            extends RecyclerView.Adapter<CharactersRecyclerViewAdapter.ViewHolder> {

        private final List<Character> mValues;
        private LayoutInflater mInflater;

        private CharactersRecyclerViewAdapter(Context context, List<Character> data){
            mInflater = LayoutInflater.from(context);
            mValues = data;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.content_character_detail, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            Character character = mValues.get(position);
            holder.charName.setText(character.getName());
            holder.charClass.setText(character.getCharClass().toString());
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView charName;
            final ImageButton deleteCharacterButton;
            final TextView charClass;


            ViewHolder(View view) {
                super(view);
                charName = view.findViewById(R.id.charName);
                deleteCharacterButton = view.findViewById(R.id.deleteCharacterButton);
                deleteCharacterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        characters.remove(getAdapterPosition());
                        notifyDataSetChanged();
                    }
                });
                charClass = view.findViewById(R.id.characterClass);

            }
        }
    }

}
