package com.atouchofjoe.ghprototye4.location.info;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.atouchofjoe.ghprototye4.data.DatabaseDescription;
import com.atouchofjoe.ghprototye4.models.Location;

import static com.atouchofjoe.ghprototye4.LocationInfoActivity.ARG_PARTY_NAME;

public abstract class LocationTabFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    public static final String ARG_PARTY_NAME = "com.atouchofjoe.ghprototye4.location.info.ARG_PARTY_NAME";
    public static final String ARG_LOCATION_NUMBER = "com.atouchofjoe.ghprototye4.location.info.ARG_LOCATION_NUMBER";

    public static final int UNLOCKING_LOCATION_CURSOR_LOADER = 0;
    public static final int ATTEMPTS_CURSOR_LOADER = 1;
    public static final int GLOBAL_ACHIEVEMENTS_GAINED_CURSOR_LOADER = 2;
    public static final int GLOBAL_ACHIEVEMENTS_LOST_CURSOR_LOADER = 3;
    public static final int PARTY_ACHIEVEMENTS_GAINED_CURSOR_LOADER = 4;
    public static final int PARTY_ACHIEVEMENTS_LOST_CURSOR_LOADER = 5;
    public static final int LOCATIONS_UNLOCKED_CURSOR_LOADER = 6;
    public static final int LOCATIONS_BLOCKED_CURSOR_LOADER = 7;
    public static final int ADD_REWARDS_GAINED_CURSOR_LOADER = 8;
    public static final int ADD_PENALTIES_LOST_CURSOR_LOADER = 9;

    public static Location[] locations = new Location[Location.TOTAL_LOCATIONS];

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        switch(i){

            case UNLOCKING_LOCATION_CURSOR_LOADER:
                return new CursorLoader(getActivity(), DatabaseDescription.UnlockingLocations.CONTENT_URI, null,
                        DatabaseDescription.UnlockingLocations.COLUMN_PARTY + " = ? AND " +
                                DatabaseDescription.UnlockingLocations.COLUMN_UNLOCKED_LOCATION + " = ? ",
                        new String []{bundle.getString(ARG_PARTY_NAME), "" + bundle.getString(ARG_LOCATION_NUMBER)}, null);
            case ATTEMPTS_CURSOR_LOADER:
                return new CursorLoader(getActivity(), DatabaseDescription.Attempts.CONTENT_URI, null,
                        DatabaseDescription.Attempts.COLUMN_PARTY + " = ? AND " +
                        DatabaseDescription.Attempts.COLUMN_LOCATION + " = ? ",
                        new String []{bundle.getString(ARG_PARTY_NAME), bundle.getString(ARG_LOCATION_NUMBER)}, null);
            case GLOBAL_ACHIEVEMENTS_GAINED_CURSOR_LOADER:
                return new CursorLoader(getActivity(), DatabaseDescription.GlobalAchievementsGained.CONTENT_URI, null,
                        DatabaseDescription.GlobalAchievementsGained.COLUMN_COMPLETED_LOCATION_NUMBER + " = ? ",
                        new String []{bundle.getString(ARG_LOCATION_NUMBER)}, null);
            case GLOBAL_ACHIEVEMENTS_LOST_CURSOR_LOADER:
                return new CursorLoader(getActivity(), DatabaseDescription.GlobalAchievementsLost.CONTENT_URI, null,
                        DatabaseDescription.GlobalAchievementsLost.COLUMN_COMPLETED_LOCATION_NUMBER + " = ? ",
                        new String []{bundle.getString(ARG_LOCATION_NUMBER)}, null);
            case PARTY_ACHIEVEMENTS_GAINED_CURSOR_LOADER:
                return new CursorLoader(getActivity(), DatabaseDescription.PartyAchievementsGained.CONTENT_URI, null,
                        DatabaseDescription.PartyAchievementsGained.COLUMN_COMPLETED_LOCATION_NUMBER + " = ? ",
                        new String []{bundle.getString(ARG_LOCATION_NUMBER)}, null);
            case PARTY_ACHIEVEMENTS_LOST_CURSOR_LOADER:
                return new CursorLoader(getActivity(), DatabaseDescription.PartyAchievementsLost.CONTENT_URI, null,
                        DatabaseDescription.PartyAchievementsLost.COLUMN_COMPLETED_LOCATION_NUMBER + " = ? ",
                        new String []{bundle.getString(ARG_LOCATION_NUMBER)}, null);
            case LOCATIONS_UNLOCKED_CURSOR_LOADER:
                return new CursorLoader(getActivity(), DatabaseDescription.LocationsUnlocked.CONTENT_URI, null,
                        DatabaseDescription.LocationsUnlocked.COLUMN_COMPLETED_LOCATION_NUMBER + " = ? ",
                        new String []{bundle.getString(ARG_LOCATION_NUMBER)}, null);
            case LOCATIONS_BLOCKED_CURSOR_LOADER:
                return new CursorLoader(getActivity(), DatabaseDescription.LocationsBlocked.CONTENT_URI, null,
                        DatabaseDescription.LocationsBlocked.COLUMN_COMPLETED_LOCATION_NUMBER + " = ? ",
                        new String []{bundle.getString(ARG_LOCATION_NUMBER)}, null);
            case ADD_REWARDS_GAINED_CURSOR_LOADER:
                return new CursorLoader(getActivity(), DatabaseDescription.AddRewardsGained.CONTENT_URI, null,
                        DatabaseDescription.AddRewardsGained.COLUMN_COMPLETED_LOCATION_NUMBER + " = ? ",
                        new String []{bundle.getString(ARG_LOCATION_NUMBER)}, null);
            case ADD_PENALTIES_LOST_CURSOR_LOADER:
                return new CursorLoader(getActivity(), DatabaseDescription.AddPenaltiesLost.CONTENT_URI, null,
                        DatabaseDescription.AddPenaltiesLost.COLUMN_COMPLETED_LOCATION_NUMBER + " = ? ",
                        new String []{bundle.getString(ARG_LOCATION_NUMBER)}, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
