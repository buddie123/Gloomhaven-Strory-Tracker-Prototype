package com.atouchofjoe.ghprototye4.location.data;

import android.content.ContentValues;
import android.content.Context;

public class StoryDatabaseInitializer {

    public static StoryDatabaseHelper databaseHelper;

    public StoryDatabaseInitializer(Context context){
        databaseHelper = new StoryDatabaseHelper(context);
        databaseHelper.getWritableDatabase();
        initializeGlobalAchievements(context);
        initializePartyAchievements(context);
        initializeAppliedTypes(context);
        initializeAddRewardTypes(context);

        initializeLocations(context);

        initializeGlobalAchievementsGained(context);
        initializeGlobalAchievementsLost(context);
        initializePartyAchievementsGained(context);
        initializePartyAchievementsLost(context);
        initializeLocationsUnlocked(context);
        initializeLocationsBlocked(context);
        initializeAddRewardsGained(context);
        initializeAddPenaltiesLost(context);
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

    private void initializePartyAchievements(Context context) {
        initializePartyAchievement(context, "First Steps", null);
        initializePartyAchievement(context, "Jekserah's Plans", null);
        initializePartyAchievement(context, "Dark Bounty", null);
        initializePartyAchievement(context, "A Demon's Errand", null);
    }

    private void initializePartyAchievement(Context context, String name, String achievementReplaced) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseDescription.PartyAchievements.COLUMN_NAME, name);
        if(achievementReplaced != null) {
            contentValues.put(DatabaseDescription.PartyAchievements.COLUMN_ACHIEVEMENT_REPLACED, achievementReplaced);
        }
        context.getContentResolver().insert(DatabaseDescription.PartyAchievements.CONTENT_URI, contentValues);
    }

    private void initializeGlobalAchievementsGained(Context context) {
        initializeGlobalAchievementGained(context, 0, "City Rule: Militaristic");
        initializeGlobalAchievementGained(context, 8, "The Merchant Flees");
        initializeGlobalAchievementGained(context, 9, "The Dead Invade");
        initializeGlobalAchievementGained(context, 11, "City Rule: Economic");
        initializeGlobalAchievementGained(context, 11, "End of the Invasion");
        initializeGlobalAchievementGained(context, 12, "End of the Invasion");
        initializeGlobalAchievementGained(context, 14, "The Power of Enhancement");
    }

    private void initializeGlobalAchievementsLost(Context context) {
        initializeGlobalAchievementLost(context, 11, "City Rule: Militaristic");
    }

    private void initializePartyAchievementsGained(Context context) {
        initializePartyAchievementGained(context, 1, "First Steps");
        initializePartyAchievementGained(context, 3, "Jekserah's Plans");
        initializePartyAchievementGained(context, 6, "Jekserah's Plans");
        initializePartyAchievementGained(context, 6, "Dark Bounty");
        initializePartyAchievementGained(context, 10, "A Demon's Errand");
    }

    private void initializePartyAchievementsLost(Context context) {
        // TODO
    }

    private void initializeLocationsUnlocked(Context context) {
        initializeLocationUnlocked(context, 0, 1);
        initializeLocationUnlocked(context, 1, 2);
        initializeLocationUnlocked(context, 2, 3);
        initializeLocationUnlocked(context, 2, 4);
        initializeLocationUnlocked(context, 3, 8);
        initializeLocationUnlocked(context, 3, 9);
        initializeLocationUnlocked(context, 4, 5);
        initializeLocationUnlocked(context, 4, 6);
        initializeLocationUnlocked(context, 5, 10);
        initializeLocationUnlocked(context, 5, 14);
        initializeLocationUnlocked(context, 5, 19);
        initializeLocationUnlocked(context, 6, 8);
        initializeLocationUnlocked(context, 7, 20);
        initializeLocationUnlocked(context, 8, 7);
        initializeLocationUnlocked(context, 8, 13);
        initializeLocationUnlocked(context, 8, 14);
        initializeLocationUnlocked(context, 9, 11);
        initializeLocationUnlocked(context, 9, 12);
        initializeLocationUnlocked(context, 10,21);
        initializeLocationUnlocked(context, 10,22);
        initializeLocationUnlocked(context, 11,16);
        initializeLocationUnlocked(context, 11,18);
        initializeLocationUnlocked(context, 12,16);
        initializeLocationUnlocked(context, 12,18);
        initializeLocationUnlocked(context, 12,28);
        initializeLocationUnlocked(context, 13, 15);
        initializeLocationUnlocked(context, 13, 17);
        initializeLocationUnlocked(context, 13, 20);
    }

    private void initializeLocationsBlocked(Context context) {
        initializeLocationBlocked(context, 8, 3);
        initializeLocationBlocked(context, 9, 8);
        initializeLocationBlocked(context, 8, 9);
        initializeLocationBlocked(context, 21, 11);
    }

    private void initializeGlobalAchievementGained(Context context, int locNumber, String globalAchievement) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseDescription.GlobalAchievementsGained.COLUMN_COMPLETED_LOCATION_NUMBER, locNumber);
        contentValues.put(DatabaseDescription.GlobalAchievementsGained.COLUMN_GLOBAL_ACHIEVEMENT, globalAchievement);
        context.getContentResolver().insert(DatabaseDescription.GlobalAchievementsGained.CONTENT_URI, contentValues);
    }

    private void initializeGlobalAchievementLost(Context context, int locNumber, String globalAchievement) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseDescription.GlobalAchievementsLost.COLUMN_COMPLETED_LOCATION_NUMBER, locNumber);
        contentValues.put(DatabaseDescription.GlobalAchievementsLost.COLUMN_GLOBAL_ACHIEVEMENT, globalAchievement);
        context.getContentResolver().insert(DatabaseDescription.GlobalAchievementsGained.CONTENT_URI, contentValues);
    }

    private void initializePartyAchievementGained(Context context, int locNumber, String globalAchievement) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseDescription.PartyAchievementsGained.COLUMN_COMPLETED_LOCATION_NUMBER, locNumber);
        contentValues.put(DatabaseDescription.PartyAchievementsGained.COLUMN_PARTY_ACHIEVEMENT, globalAchievement);
        context.getContentResolver().insert(DatabaseDescription.PartyAchievementsGained.CONTENT_URI, contentValues);
    }


    // for use in the initializePartyAchievementsLost() method
    private void initializePartyAchievementLost(Context context, int locNumber, String globalAchievement) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseDescription.PartyAchievementsLost.COLUMN_COMPLETED_LOCATION_NUMBER, locNumber);
        contentValues.put(DatabaseDescription.PartyAchievementsLost.COLUMN_PARTY_ACHIEVEMENT, globalAchievement);
        context.getContentResolver().insert(DatabaseDescription.PartyAchievementsLost.CONTENT_URI, contentValues);
    }

    private void initializeLocationUnlocked(Context context, int completedLoc, int unlockedLoc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseDescription.LocationsUnlocked.COLUMN_COMPLETED_LOCATION_NUMBER, completedLoc);
        contentValues.put(DatabaseDescription.LocationsUnlocked.COLUMN_UNLOCKED_LOCATION_NUMBER, unlockedLoc);
        context.getContentResolver().insert(DatabaseDescription.LocationsUnlocked.CONTENT_URI, contentValues);
    }

    private void initializeLocationBlocked(Context context, int completedLoc, int blockedLoc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseDescription.LocationsBlocked.COLUMN_COMPLETED_LOCATION_NUMBER, completedLoc);
        contentValues.put(DatabaseDescription.LocationsBlocked.COLUMN_BLOCKED_LOCATION_NUMBER, blockedLoc);
        context.getContentResolver().insert(DatabaseDescription.LocationsBlocked.CONTENT_URI, contentValues);
    }


    private void initializeAppliedTypes(Context context) {
        initializeAppliedType(context, "each");
        initializeAppliedType(context, "collectively");
        initializeAppliedType(context, "design");
    }

    private void initializeAppliedType(Context context, String type) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseDescription.AppliedTypes.COLUMN_TYPE, type);
        context.getContentResolver().insert(DatabaseDescription.AppliedTypes.CONTENT_URI, contentValues);
    }

    private void initializeAddRewardTypes(Context context) {
        initializeAddRewardType(context, "gold");
        initializeAddRewardType(context, "prosperity");
        initializeAddRewardType(context, "reputation");
        initializeAddRewardType(context, "\"Skullbane Axe\" design ( Item 113 )");
        initializeAddRewardType(context, "experience");
    }

    private void initializeAddRewardType(Context context, String type) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseDescription.AddRewardTypes.COLUMN_TYPE, type);
        context.getContentResolver().insert(DatabaseDescription.AddRewardTypes.CONTENT_URI, contentValues);
    }

    private void initializeAddRewardsGained(Context context) {
        initializeAddRewardGained(context, 2, "gold", 10, "each");
        initializeAddRewardGained(context, 2, "prosperity", 1, "collectively");
        initializeAddRewardGained(context, 3, "gold", 15, "each");
        initializeAddRewardGained(context, 3, "prosperity", 1, "collectively");
        initializeAddRewardGained(context, 6, "gold", 5, "each");
        initializeAddRewardGained(context, 8, "reputation", 2, "collectively");
        initializeAddRewardGained(context, 9, "gold", 20, "each");
        initializeAddRewardGained(context, 11,"gold", 15, "each");
        initializeAddRewardGained(context, 11,"prosperity", 2, "collectively");
        initializeAddRewardGained(context, 11, "\"Skullbane Axe\" design (Item 113)", 0,"design");
        initializeAddRewardGained(context, 12, "reputation", 4, "collective");
        initializeAddRewardGained(context, 12, "\"Skullbane Axe\" design (Item 113)", 0,"design");
        initializeAddRewardGained(context, 15, "experience", 20, "each");
    }

    private void initializeAddRewardGained(Context context,  int locNumber, String rewardType, int rewardValue, String appliedType) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseDescription.AddRewardsGained.COLUMN_COMPLETED_LOCATION_NUMBER, locNumber);
        contentValues.put(DatabaseDescription.AddRewardsGained.COLUMN_REWARD_TYPE, rewardType);
        contentValues.put(DatabaseDescription.AddRewardsGained.COLUMN_REWARD_VALUE, rewardValue);
        contentValues.put(DatabaseDescription.AddRewardsGained.COLUMN_REWARD_APPLIED_TYPE, appliedType);
        context.getContentResolver().insert(DatabaseDescription.AddRewardsGained.CONTENT_URI, contentValues);
    }

    private void initializeAddPenaltiesLost(Context context) {
        initializeAddPenaltyLost(context, 11, "reputation", 2, "collectively");
    }

    private void initializeAddPenaltyLost(Context context, int locNumber, String rewardType, int rewardValue, String appliedType) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseDescription.AddPenaltiesLost.COLUMN_COMPLETED_LOCATION_NUMBER, locNumber);
        contentValues.put(DatabaseDescription.AddPenaltiesLost.COLUMN_PENALTY_TYPE, rewardType);
        contentValues.put(DatabaseDescription.AddPenaltiesLost.COLUMN_PENALTY_VALUE, rewardValue);
        contentValues.put(DatabaseDescription.AddPenaltiesLost.COLUMN_PENALTY_APPLIED_TYPE, appliedType);
        context.getContentResolver().insert(DatabaseDescription.AddPenaltiesLost.CONTENT_URI, contentValues);
    }

    private void initializeLocations(Context context) {
        initializeLocation(context,
            0,
            "Gloomhaven",
            null,
            null,
            "You were approached in the Sleeping Lion by a Valrath woman named Jekserah. She has a job for you: track down a thief and return important documents. After some investigation, it looks like you\'ll be beginning your journey at the Black Barrow.");

        initializeLocation(context,
                1,
                "Black Barrow",
                "You were tasked by Jekserah to track down a thief " +
                        "and retrieve some important documents. After roughing up " +
                        "some low-life's, it looks like the Black Barrow is the place" +
                        "to be...",
                "The Black Barrow is easy to find - just east of of the city gates." +
                        "As you approach the hill and descend the dark stairs, you find your quarry " +
                        "- along with a few armed friends. Of course, your quarry " +
                        "would disappear through a doorway and leave you to dispatch the remainder.",
                "After dispatching some bandits and some restless undead," +
                        "you can take a breather and push aside the horrors of the battle. Of course, " +
                        "with the job for Jekserah incomplete, you have no choice but to trespass deeper into the Barrow Lair [2]!");

        initializeLocation(context,
                2,
                "Barrow Lair",
                "Having cleared out the main rooms of the Black Barrow, you are still left " +
                        "with a thief to catch. It looks like you'll have to head down into the " +
                        "Barrow Lair to finish the job for Jekserah...",
                "The smell is almost more than you can handle, but you still manage to " +
                        "make your way through the crypts and into a small room full of bandits " +
                        "ready to strike! (You must put 3 CURSE cards into each character's attack " +
                        "modifier deck for this scenario.)",
                "After finding your way through the barrow lair and killing the Bandit Commander, whose eerie eyes " +
                        "and talk of the \"Work of Gloom\" still have you wondering, " +
                        "you finally have the scrolls for Jekserah in your possesion. You can't help " +
                        "but take a look and find among the markings a map of a location" +
                        "near the Still River clearly marked. Perhaps you can find out some more about this \"Gloom\" there. [4] " +
                        "In the mean time, there is a fee to collect. After finding Jekserah at the Sleeping Lion and " +
                        "receive your payment, she tells your about another job clearing out an Inox encampment in the " +
                        "Dagger Forest [3]." );
        initializeLocation(context,
                3,
                "Inox Encampment",
                "After clearing out the Barrow Lair and retrieving some documents for Jekserah, " +
                        "the Valrath mentions that she has another job for you. Apparently, there is " +
                        "an encampment of Inox that have been ransacking caravans on their way to the " +
                        "Capital. She wants an example made of them. Of course, there is nothing like " +
                        "repeat business from a paying customer...",
                "",
                "");
        initializeLocation(context,
                4,
                "Crypt of the Damned",
                "You've completed the job for Jekserah, but you couldn't help taking a peak at the " +
                        "goods you returned. The writings on the scrolls were in some mysterious unknown " +
                        "language. However, the map you saw was clear as day. Also, you can't help but be " +
                        "more than a little curious about what the \"Gloom\" is that the Bandit Commander spoke of..." ,
                "",
                "");
    }

    private void initializeLocation(Context context, int number, String name,
                                    String teaser, String summary, String conclusion) {
        // insert location
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseDescription.Locations.COLUMN_NUMBER, number);
        contentValues.put(DatabaseDescription.Locations.COLUMN_NAME, name);
        contentValues.put(DatabaseDescription.Locations.COLUMN_TEASER, teaser);
        contentValues.put(DatabaseDescription.Locations.COLUMN_SUMMARY, summary);
        contentValues.put(DatabaseDescription.Locations.COLUMN_CONCLUSION, conclusion);
        context.getContentResolver().insert(
                DatabaseDescription.Locations.CONTENT_URI, contentValues);
    }
}
