package com.atouchofjoe.ghprototye4;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.atouchofjoe.ghprototye4.location.info.LocationAttemptsTabFragment;
import com.atouchofjoe.ghprototye4.location.info.LocationRewardsTabFragment;
import com.atouchofjoe.ghprototye4.location.info.LocationStoryTabFragment;
import com.atouchofjoe.ghprototye4.models.Location;
import com.atouchofjoe.ghprototye4.models.Party;


public class LocationInfoActivity extends AppCompatActivity {

    public static final String ARG_LOCATION_NUMBER = "com.atouchofjoe.ghprototye4.LocationInfoActivity.ARG_LOCATION_NUMBER";
    public static final String ARG_PARTY_NAME = "com.atouchofjoe.ghprototype.LocationInfoActivity.ARG_PARTY_NAME";


    private int currentLocNumber;
    private Party currentParty;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_info);

        currentLocNumber = getIntent().getIntExtra(ARG_LOCATION_NUMBER, 0);
        currentParty = MainActivity.partyMap.get(getIntent().getStringExtra(ARG_PARTY_NAME));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        Location currentLoc= MainActivity.content.getLocations()[currentLocNumber];

        TextView locationName = findViewById(R.id.locationName);
        locationName.setText(currentLoc.toString());

        TextView locationStatus = findViewById(R.id.statusValue);
        locationStatus.setText(currentParty.getLocationCompleted(currentLoc) ? R.string.status_completed :
                               currentParty.getLocationAttempted(currentLoc) ? R.string.status_attempted :
                                                            R.string.status_unplayed);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.

            Fragment scenarioTabFragment;
            if(position == 0) {
                scenarioTabFragment = new LocationStoryTabFragment();
            }
            else if(position == 1) {
                scenarioTabFragment = new LocationAttemptsTabFragment();
            }
            else if(position == 2) {
                scenarioTabFragment = new LocationRewardsTabFragment();
            }
            else {
                throw new IndexOutOfBoundsException();
            }
            Bundle args = new Bundle();
            args.putInt(LocationInfoActivity.ARG_LOCATION_NUMBER, currentLocNumber);
            args.putString(ARG_PARTY_NAME, currentParty.getName());
            scenarioTabFragment.setArguments(args);
            return scenarioTabFragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
