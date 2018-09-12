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

import com.atouchofjoe.ghprototye4.MainActivity;
import com.atouchofjoe.ghprototye4.R;
import com.atouchofjoe.ghprototye4.data.DatabaseDescription;
import com.atouchofjoe.ghprototye4.data.StoryDatabaseHelper;
import com.atouchofjoe.ghprototye4.models.Character;
import com.atouchofjoe.ghprototye4.models.CharacterClass;

import java.util.ArrayList;
import java.util.List;

import static com.atouchofjoe.ghprototye4.location.info.LocationInfoActivity.ARG_PARTY_NAME;
import static com.atouchofjoe.ghprototye4.MainActivity.currentParty;

public class EditPartyActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String RESULT_PARTY_NAME = "com.atouchofjoe.ghprototye4.RESULT_PARTY_NAME";
    private static final int CHARACTER_CURSOR_LOADER = 3;
    public static List<Character> characters;
    private static String originalPartyName;
    private static RecyclerView charRView;

    private static List<Character> deletedChars = new ArrayList<>();
    private static List<Character> unAlteredChars = new ArrayList<>();
    private static List<Character> newChars = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final StoryDatabaseHelper dbHelper = new StoryDatabaseHelper(this);
        dbHelper.getWritableDatabase();

        Bundle args = new Bundle();
        args.putString(ARG_PARTY_NAME, currentParty.getName());
        getLoaderManager().initLoader(CHARACTER_CURSOR_LOADER, args, this);

        characters = new ArrayList<>();

        final EditText partyName = findViewById(R.id.partyNameEditText);
        originalPartyName = currentParty.getName();
        partyName.setText(currentParty.getName());

        charRView = findViewById(R.id.charactersRecyclerView);
        charRView.setAdapter(new CharactersRecyclerViewAdapter(this, characters));
        charRView.setLayoutManager(new LinearLayoutManager(this));

        Button cancelButton = findViewById(R.id.cancelEditButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanUp();
                finish();
            }
        });

        Button  createPartyButton = findViewById(R.id.createPartyButton);
        createPartyButton.setText("Save Changes");
        createPartyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoryDatabaseHelper dbHelper = new StoryDatabaseHelper(view.getContext());
                dbHelper.getWritableDatabase();

                currentParty.setName(partyName.getText().toString());
                ContentValues partyValues = new ContentValues();
                partyValues.put(DatabaseDescription.Parties.COLUMN_NAME, partyName.getText().toString());
                view.getContext().getContentResolver().update(DatabaseDescription.Parties.CONTENT_URI, partyValues,
                        DatabaseDescription.Parties.COLUMN_NAME + " = ? ", new String[]{originalPartyName});

                for(Character nc : newChars) {
                    ContentValues charValues = new ContentValues();
                    charValues.put(DatabaseDescription.Characters.COLUMN_NAME, nc.getName());
                    charValues.put(DatabaseDescription.Characters.COLUMN_CLASS, nc.getCharClass().toString());
                    charValues.put(DatabaseDescription.Characters.COLUMN_PARTY, partyName.getText().toString());
                    partyName.getContext().getContentResolver().insert(DatabaseDescription.Characters.CONTENT_URI, charValues);
                    currentParty.addCharacter(nc);
                }
                for(Character dc: deletedChars) {
                    view.getContext().getContentResolver().delete(DatabaseDescription.Characters.CONTENT_URI,
                        DatabaseDescription.Characters.COLUMN_PARTY + " = ? AND " +
                                DatabaseDescription.Characters.COLUMN_NAME + " =" +
                                " ?",
                        new String[]{originalPartyName, dc.getName()});
                    currentParty.removeCharacter(dc);
                }
                if(!originalPartyName.equals(partyName.getText().toString())) {
                    for(Character c: unAlteredChars) {
                        ContentValues charValues = new ContentValues();
                        partyValues.put(DatabaseDescription.Characters.COLUMN_PARTY, partyName.getText().toString());
                        view.getContext().getContentResolver().update(DatabaseDescription.Characters.CONTENT_URI, charValues,
                                DatabaseDescription.Characters.COLUMN_PARTY + " = ? AND " +
                                DatabaseDescription.Characters.COLUMN_NAME + " = ? ", new String[]{originalPartyName, c.getName()});

                    }
                }

                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit();
                editor.putString(MainActivity.PREF_CURRENT_PARTY, currentParty.getName());
                editor.apply();
                MainActivity.preferencesChanged = true;

                cleanUp();
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
                            Character newChar = new Character(charName.getText().toString(), CharacterClass.values()[position]);
                            newChars.add(newChar);
                            characters.add(newChar);
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

    private void cleanUp(){
        characters = new ArrayList<>();
        newChars = new ArrayList<>();
        unAlteredChars = new ArrayList<>();
        deletedChars = new ArrayList<>();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        if(i == CHARACTER_CURSOR_LOADER) {
            return new CursorLoader(this, DatabaseDescription.Characters.CONTENT_URI, null,
                    DatabaseDescription.Characters.COLUMN_PARTY + " = ? ", new String[]{bundle.getString(ARG_PARTY_NAME)}, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(loader.getId() == CHARACTER_CURSOR_LOADER) {
            int nameIndex = cursor.getColumnIndex(DatabaseDescription.Characters.COLUMN_NAME);
            int classIndex = cursor.getColumnIndex(DatabaseDescription.Characters.COLUMN_CLASS);

            characters.clear();
            while (cursor.moveToNext()) {
                Character character = new Character(cursor.getString(nameIndex), CharacterClass.valueOf(cursor.getString(classIndex)));
                characters.add(character);
                unAlteredChars.add(character);
                currentParty.addCharacter(character);

            }
            charRView.getAdapter().notifyDataSetChanged();
         //   Toast.makeText(this, "Finished loading the Characters for party " + currentParty.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

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
                        Character deletedChar = characters.remove(getAdapterPosition());
                        deletedChars.add(deletedChar);
                        unAlteredChars.remove(deletedChar);
                        notifyDataSetChanged();
                    }
                });
                charClass = view.findViewById(R.id.characterClass);

            }
        }
    }

}
