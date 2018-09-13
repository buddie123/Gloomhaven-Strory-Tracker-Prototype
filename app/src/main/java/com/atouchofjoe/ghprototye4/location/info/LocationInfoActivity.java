package com.atouchofjoe.ghprototye4.location.info;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
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

import com.atouchofjoe.ghprototye4.LoadingFragment;
import com.atouchofjoe.ghprototye4.MainActivity;
import com.atouchofjoe.ghprototye4.R;
import com.atouchofjoe.ghprototye4.data.DatabaseDescription;
import com.atouchofjoe.ghprototye4.models.Location;
import com.atouchofjoe.ghprototye4.models.Party;

public class LocationInfoActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static Location[] locations = new Location[Location.TOTAL_LOCATIONS];

    private static final int LOCATION_CURSOR_LOADER = 1;
    public static final String ARG_LOCATION_NUMBER = "com.atouchofjoe.ghprototye4.location.info.LocationInfoActivity.ARG_LOCATION_NUMBER";
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
    private boolean locationsLoaded = false;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_info);

        currentLocNumber = getIntent().getIntExtra(ARG_LOCATION_NUMBER, 0);
        currentParty = MainActivity.currentParty;

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


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getLoaderManager().initLoader(LOCATION_CURSOR_LOADER, null, this);
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
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle bundle) {
        // get all locations in the database, sorted by location number
        switch (loaderID) {
            case LOCATION_CURSOR_LOADER: // TODO move this to the location guide activity
                return new CursorLoader(this, DatabaseDescription.Locations.CONTENT_URI,
                        null, null, null,
                        DatabaseDescription.Locations.COLUMN_NUMBER);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch(loader.getId()) {
            case LOCATION_CURSOR_LOADER: // TODO move this to the Location Guide Activity
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


                Location currentLoc = locations[currentLocNumber];

                TextView locationName = findViewById(R.id.locationName);
                locationName.setText(currentLoc.toString());

                TextView locationStatus = findViewById(R.id.statusValue);
                locationStatus.setText(currentParty.getLocationCompleted(currentLoc) ? R.string.status_completed :
                        currentParty.getLocationAttempted(currentLoc) ? R.string.status_attempted :
                                R.string.status_unplayed);
                locationsLoaded = true;
                mSectionsPagerAdapter.notifyDataSetChanged();
                break;
            default:
                return;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

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
            if(locationsLoaded) {
                Bundle args = new Bundle();
                args.putInt(LocationInfoActivity.ARG_LOCATION_NUMBER, currentLocNumber);
                args.putString(ARG_PARTY_NAME, currentParty.getName());
                scenarioTabFragment.setArguments(args);
                return scenarioTabFragment;
            }
            else{
                return new LoadingFragment();
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return locationsLoaded ? 3 : 0;
        }
    }
}
