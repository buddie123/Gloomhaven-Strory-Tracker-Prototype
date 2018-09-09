package com.atouchofjoe.ghprototye4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.atouchofjoe.ghprototye4.Dummy.DummyContent;
import com.atouchofjoe.ghprototye4.location.data.StoryDatabaseInitializer;
import com.atouchofjoe.ghprototye4.models.Party;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static Map<String, Party> partyMap = new HashMap<>();
    public static DummyContent content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        content = new DummyContent(this);
        final String partyName = getString(R.string.sample_party_name);
        partyMap.put(partyName, content.getParty());
        super.onCreate(savedInstanceState);
        StoryDatabaseInitializer initializer = new StoryDatabaseInitializer(this);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
}
