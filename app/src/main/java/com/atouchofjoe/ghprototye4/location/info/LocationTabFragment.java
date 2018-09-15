package com.atouchofjoe.ghprototye4.location.info;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.atouchofjoe.ghprototye4.data.DatabaseDescription;

public abstract class LocationTabFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final int LOCATIONS_CURSOR_LOADER = 10;
    public static final int ATTEMPTS_CURSOR_LOADER = 1;
    public static final int GLOBAL_ACHIEVEMENTS_GAINED_CURSOR_LOADER = 2;
    public static final int GLOBAL_ACHIEVEMENTS_LOST_CURSOR_LOADER = 3;
    public static final int PARTY_ACHIEVEMENTS_GAINED_CURSOR_LOADER = 4;
    public static final int PARTY_ACHIEVEMENTS_LOST_CURSOR_LOADER = 5;
    public static final int LOCATIONS_UNLOCKED_CURSOR_LOADER = 6;
    public static final int LOCATIONS_BLOCKED_CURSOR_LOADER = 7;
    public static final int ADD_REWARDS_GAINED_CURSOR_LOADER = 8;
    public static final int ADD_PENALTIES_LOST_CURSOR_LOADER = 9;

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        switch(i){
            case LOCATIONS_CURSOR_LOADER:
                return new CursorLoader(getActivity(), DatabaseDescription.Locations.CONTENT_URI, null,
                        DatabaseDescription.Locations.COLUMN_NUMBER + " + ? ",
                        new String[]{bundle.getString(LocationInfoActivity.ARG_LOCATION_NUMBER)}, null);
            case ATTEMPTS_CURSOR_LOADER:
                return new CursorLoader(getActivity(), DatabaseDescription.Attempts.CONTENT_URI, null,
                        DatabaseDescription.Attempts.COLUMN_PARTY + " = ? AND " +
                        DatabaseDescription.Attempts.COLUMN_LOCATION + " = ? ",
                        new String []{bundle.getString(LocationInfoActivity.ARG_PARTY_NAME),
                            bundle.getString(LocationInfoActivity.ARG_LOCATION_NUMBER)}, null);
            case GLOBAL_ACHIEVEMENTS_GAINED_CURSOR_LOADER:
                return new CursorLoader(getActivity(), DatabaseDescription.GlobalAchievementsToBeAwarded.CONTENT_URI, null,
                        DatabaseDescription.GlobalAchievementsToBeAwarded.COLUMN_LOCATION_TO_BE_COMPLETED + " = ? ",
                        new String []{bundle.getString(LocationInfoActivity.ARG_LOCATION_NUMBER)}, null);
            case GLOBAL_ACHIEVEMENTS_LOST_CURSOR_LOADER:
                return new CursorLoader(getActivity(), DatabaseDescription.GlobalAchievementsToBeRevoked.CONTENT_URI, null,
                        DatabaseDescription.GlobalAchievementsToBeRevoked.COLUMN_LOCATION_TO_BE_COMPLETED + " = ? ",
                        new String []{bundle.getString(LocationInfoActivity.ARG_LOCATION_NUMBER)}, null);
            case PARTY_ACHIEVEMENTS_GAINED_CURSOR_LOADER:
                return new CursorLoader(getActivity(), DatabaseDescription.PartyAchievementsToBeAwarded.CONTENT_URI, null,
                        DatabaseDescription.PartyAchievementsToBeAwarded.COLUMN_LOCATION_TO_BE_COMPLETED + " = ? ",
                        new String []{bundle.getString(LocationInfoActivity.ARG_LOCATION_NUMBER)}, null);
            case PARTY_ACHIEVEMENTS_LOST_CURSOR_LOADER:
                return new CursorLoader(getActivity(), DatabaseDescription.PartyAchievementsToBeRevoked.CONTENT_URI, null,
                        DatabaseDescription.PartyAchievementsToBeRevoked.COLUMN_LOCATION_TO_BE_COMPLETED + " = ? ",
                        new String []{bundle.getString(LocationInfoActivity.ARG_LOCATION_NUMBER)}, null);
            case LOCATIONS_UNLOCKED_CURSOR_LOADER:
                return new CursorLoader(getActivity(), DatabaseDescription.LocationsToBeUnlocked.CONTENT_URI, null,
                        DatabaseDescription.LocationsToBeUnlocked.COLUMN_LOCATION_TO_BE_COMPLETED + " = ? ",
                        new String []{bundle.getString(LocationInfoActivity.ARG_LOCATION_NUMBER)}, null);
            case LOCATIONS_BLOCKED_CURSOR_LOADER:
                return new CursorLoader(getActivity(), DatabaseDescription.LocationsToBeBlocked.CONTENT_URI, null,
                        DatabaseDescription.LocationsToBeBlocked.COLUMN_LOCATION_TO_BE_COMPLETED + " = ? ",
                        new String []{bundle.getString(LocationInfoActivity.ARG_LOCATION_NUMBER)}, null);
            case ADD_REWARDS_GAINED_CURSOR_LOADER:
                return new CursorLoader(getActivity(), DatabaseDescription.AddRewards.CONTENT_URI, null,
                        DatabaseDescription.AddRewards.COLUMN_LOCATION_TO_BE_COMPLETED + " = ? ",
                        new String []{bundle.getString(LocationInfoActivity.ARG_LOCATION_NUMBER)}, null);
            case ADD_PENALTIES_LOST_CURSOR_LOADER:
                return new CursorLoader(getActivity(), DatabaseDescription.AddPenalties.CONTENT_URI, null,
                        DatabaseDescription.AddPenalties.COLUMN_LOCATION_TO_BE_COMPLETED + " = ? ",
                        new String []{bundle.getString(LocationInfoActivity.ARG_LOCATION_NUMBER)}, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
