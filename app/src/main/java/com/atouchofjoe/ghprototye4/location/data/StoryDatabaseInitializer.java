package com.atouchofjoe.ghprototye4.location.data;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import java.util.Map;

public class StoryDatabaseInitializer {

    public static StoryDatabaseHelper databaseHelper;

    public StoryDatabaseInitializer(Context context){
        databaseHelper = new StoryDatabaseHelper(context);
        databaseHelper.getWritableDatabase();
        initializeGlobalAchievements(context);
        //initializePartyAchievements(context);
        initializeLocations(context);
    }

    private void initializeGlobalAchievements(Context context){
        initializeGlobalAchievement(context, "City Rule: Militaristic", null, 1);
        initializeGlobalAchievement(context, "The Merchant Flees", null, 1);
        initializeGlobalAchievement(context, "The Dead Invade", null, 1);
        initializeGlobalAchievement(context, "City Rule: Economic", "City Rule: Militaristic", 1);
        initializeGlobalAchievement(context, "End of the Invasion", null, 1);
        initializeGlobalAchievement(context, "The Power of Enhancement", null, 1);
    }

    private void initializeGlobalAchievement(Context context, String name, String achievementReplaced, int max_count) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseDescription.GlobalAchievements.COLUMN_NAME, name);
        if(achievementReplaced != null) {
            contentValues.put(DatabaseDescription.GlobalAchievements.COLUMN_ACHIEVEMENT_REPLACED, achievementReplaced);
        }
        if(max_count > 1) {
            contentValues.put(DatabaseDescription.GlobalAchievements.COLUMN_ACHIEVEMENT_MAX_COUNT, max_count);
        }
        context.getContentResolver().insert(DatabaseDescription.GlobalAchievements.CONTENT_URI, contentValues);
    }


    private void initializeLocations(Context context) {
        initializeLocation(context,
            0,
            "Gloomhaven",
            null,
            null,
            "You were approached in the Sleeping Lion by a Valrath woman named Jekserah. She has a job for you: track down a thief and return important documents. After some investigation, it looks like you\'ll be beginning your journey at the Black Barrow....\n",
            "City Rule: Militaristic",
            null,
            null,
            null,
            new int[]{1},
            null,
            null,
            null);
    }

    private void initializeLocation(Context context, int number, String name,
                                    String teaser, String summary, String conclusion,
                                    String globalAchievementsGained, String GlobalAchievementsLost,
                                    String partyAchievementsGained, String partyAchievmentsLost,
                                    int[] locationsUnlocked, int []locationsBlocked,
                                    Map<String, Integer> addRewards, Map<String, Integer> addPenalties) {
        // insert location
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseDescription.Locations.COLUMN_NUMBER, number);
        contentValues.put(DatabaseDescription.Locations.COLUMN_NAME, name);
        contentValues.put(DatabaseDescription.Locations.COLUMN_TEASER, teaser);
        contentValues.put(DatabaseDescription.Locations.COLUMN_SUMMARY, summary);
        contentValues.put(DatabaseDescription.Locations.COLUMN_CONCLUSION, conclusion);
        Uri newLocationUri = context.getContentResolver().insert(
                DatabaseDescription.Locations.CONTENT_URI, contentValues);
    }
}
