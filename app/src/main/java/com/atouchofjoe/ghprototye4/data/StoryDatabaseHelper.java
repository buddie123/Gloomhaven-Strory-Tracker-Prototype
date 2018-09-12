package com.atouchofjoe.ghprototye4.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StoryDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "GloomhavenStory.db";
    private static final int DATABASE_VERSION= 1;

    //constructor
    public StoryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // creates all the tables when the database is created
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create the Locations Table
        final String CREATE_LOCATIONS_TABLE =
                "CREATE TABLE " + DatabaseDescription.Locations.TABLE_NAME + "(" +
                        DatabaseDescription.Locations._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.Locations.COLUMN_NUMBER + " INTEGER UNIQUE, " +
                        DatabaseDescription.Locations.COLUMN_NAME + " TEXT, " +
                        DatabaseDescription.Locations.COLUMN_TEASER + " TEXT, " +
                        DatabaseDescription.Locations.COLUMN_SUMMARY + " TEXT, " +
                        DatabaseDescription.Locations.COLUMN_CONCLUSION + " TEXT);";
        db.execSQL(CREATE_LOCATIONS_TABLE);

        // create the GlobalAchievements table
        final String CREATE_GLOBAL_ACHIEVEMENTS_TABLE =
                "CREATE TABLE " + DatabaseDescription.GlobalAchievements.TABLE_NAME + "(" +
                        DatabaseDescription.GlobalAchievements._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.GlobalAchievements.COLUMN_NAME + " TEXT UNIQUE, " +
                        DatabaseDescription.GlobalAchievements.COLUMN_ACHIEVEMENT_REPLACED + " TEXT, " +
                        DatabaseDescription.GlobalAchievements.COLUMN_ACHIEVEMENT_MAX_COUNT + " INTEGER DEFAULT 1);";
        db.execSQL(CREATE_GLOBAL_ACHIEVEMENTS_TABLE);

        // create the PartyAchievements table
        final String CREATE_PARTY_ACHIEVEMENTS_TABLE =
                "CREATE TABLE " + DatabaseDescription.PartyAchievements.TABLE_NAME + "(" +
                        DatabaseDescription.PartyAchievements._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.PartyAchievements.COLUMN_NAME + " TEXT UNIQUE, " +
                        DatabaseDescription.PartyAchievements.COLUMN_ACHIEVEMENT_REPLACED + " TEXT);";
        db.execSQL(CREATE_PARTY_ACHIEVEMENTS_TABLE);

        // create the GlobalAchievementsGained table
        final String CREATE_GLOBAL_ACHIEVEMENTS_GAINED_TABLE =
                "CREATE TABLE " + DatabaseDescription.GlobalAchievementsGained.TABLE_NAME + "(" +
                        DatabaseDescription.GlobalAchievementsGained._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.GlobalAchievementsGained.COLUMN_COMPLETED_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.GlobalAchievementsGained.COLUMN_GLOBAL_ACHIEVEMENT + " TEXT, " +
                        "UNIQUE (" + DatabaseDescription.GlobalAchievementsGained.COLUMN_COMPLETED_LOCATION_NUMBER +
                        ", " + DatabaseDescription.GlobalAchievementsGained.COLUMN_GLOBAL_ACHIEVEMENT +"));";
        db.execSQL(CREATE_GLOBAL_ACHIEVEMENTS_GAINED_TABLE);

        // create the GlobalAchievementsLost table
        final String CREATE_GLOBAL_ACHIEVEMENTS_LOST_TABLE =
                "CREATE TABLE " + DatabaseDescription.GlobalAchievementsLost.TABLE_NAME + "(" +
                        DatabaseDescription.GlobalAchievementsLost._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.GlobalAchievementsLost.COLUMN_COMPLETED_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.GlobalAchievementsLost.COLUMN_GLOBAL_ACHIEVEMENT + " TEXT, " +
                        "UNIQUE (" + DatabaseDescription.GlobalAchievementsLost.COLUMN_COMPLETED_LOCATION_NUMBER +
                        ", " + DatabaseDescription.GlobalAchievementsLost.COLUMN_GLOBAL_ACHIEVEMENT + "));";
        db.execSQL(CREATE_GLOBAL_ACHIEVEMENTS_LOST_TABLE);

        // create the PartyAchievementsGained table
        final String CREATE_PARTY_ACHIEVEMENTS_GAINED_TABLE =
                "CREATE TABLE " + DatabaseDescription.PartyAchievementsGained.TABLE_NAME + "(" +
                        DatabaseDescription.PartyAchievementsGained._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.PartyAchievementsGained.COLUMN_COMPLETED_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.PartyAchievementsGained.COLUMN_PARTY_ACHIEVEMENT + " TEXT, " +
                        "UNIQUE (" + DatabaseDescription.PartyAchievementsGained.COLUMN_COMPLETED_LOCATION_NUMBER +
                        ", " + DatabaseDescription.PartyAchievementsGained.COLUMN_PARTY_ACHIEVEMENT + "));";
        db.execSQL(CREATE_PARTY_ACHIEVEMENTS_GAINED_TABLE);

        // create the PartyAchievementsLost table
        final String CREATE_PARTY_ACHIEVEMENTS_LOST_TABLE =
                "CREATE TABLE " + DatabaseDescription.PartyAchievementsLost.TABLE_NAME + "(" +
                        DatabaseDescription.PartyAchievementsLost._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.PartyAchievementsLost.COLUMN_COMPLETED_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.PartyAchievementsLost.COLUMN_PARTY_ACHIEVEMENT + " TEXT, " +
                        "UNIQUE (" + DatabaseDescription.PartyAchievementsLost.COLUMN_COMPLETED_LOCATION_NUMBER +
                        ", " + DatabaseDescription.PartyAchievementsLost.COLUMN_PARTY_ACHIEVEMENT +"));";
        db.execSQL(CREATE_PARTY_ACHIEVEMENTS_LOST_TABLE);

        // create the LocationsUnlocked table
        final String CREATE_LOCATIONS_UNLOCKED_TABLE =
                "CREATE TABLE " + DatabaseDescription.LocationsUnlocked.TABLE_NAME + "(" +
                        DatabaseDescription.LocationsUnlocked._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.LocationsUnlocked.COLUMN_COMPLETED_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.LocationsUnlocked.COLUMN_UNLOCKED_LOCATION_NUMBER + " INTEGER, " +
                        "UNIQUE (" + DatabaseDescription.LocationsUnlocked.COLUMN_COMPLETED_LOCATION_NUMBER +
                        ", " + DatabaseDescription.LocationsUnlocked.COLUMN_UNLOCKED_LOCATION_NUMBER +"));";
        db.execSQL(CREATE_LOCATIONS_UNLOCKED_TABLE);

        // create the LocationsBlocked table
        final String CREATE_LOCATIONS_BLOCKED_TABLE =
                "CREATE TABLE " + DatabaseDescription.LocationsBlocked.TABLE_NAME + "(" +
                        DatabaseDescription.LocationsBlocked._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.LocationsBlocked.COLUMN_COMPLETED_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.LocationsBlocked.COLUMN_BLOCKED_LOCATION_NUMBER + " INTEGER, " +
                        "UNIQUE (" + DatabaseDescription.LocationsBlocked.COLUMN_COMPLETED_LOCATION_NUMBER +
                        ", " + DatabaseDescription.LocationsBlocked.COLUMN_BLOCKED_LOCATION_NUMBER +"));";
        db.execSQL(CREATE_LOCATIONS_BLOCKED_TABLE);

        // create AppliedTypes table
        final String CREATE_APPLIED_TYPES_TABLE =
                "CREATE TABLE " + DatabaseDescription.AppliedTypes.TABLE_NAME + "(" +
                        DatabaseDescription.AppliedTypes._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.AppliedTypes.COLUMN_TYPE + " TEXT UNIQUE);";
        db.execSQL(CREATE_APPLIED_TYPES_TABLE);

        // create AppliedTypes table
        final String CREATE_ADD_REWARD_TYPES_TABLE =
                "CREATE TABLE " + DatabaseDescription.AddRewardTypes.TABLE_NAME + "(" +
                        DatabaseDescription.AddRewardTypes._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.AddRewardTypes.COLUMN_TYPE + " TEXT UNIQUE);";
        db.execSQL(CREATE_ADD_REWARD_TYPES_TABLE);

        // create the AddRewards table
        final String CREATE_ADD_REWARDS_TABLE =
                "CREATE TABLE " + DatabaseDescription.AddRewardsGained.TABLE_NAME + "(" +
                        DatabaseDescription.AddRewardsGained._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.AddRewardsGained.COLUMN_COMPLETED_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.AddRewardsGained.COLUMN_REWARD_TYPE + " TEXT, " +
                        DatabaseDescription.AddRewardsGained.COLUMN_REWARD_VALUE + " INTEGER, " +
                        DatabaseDescription.AddRewardsGained.COLUMN_REWARD_APPLIED_TYPE + " TEXT, " +
                        "UNIQUE (" + DatabaseDescription.AddRewardsGained.COLUMN_COMPLETED_LOCATION_NUMBER +
                        ", " + DatabaseDescription.AddRewardsGained.COLUMN_REWARD_TYPE +"));";
        db.execSQL(CREATE_ADD_REWARDS_TABLE);

        // create the AddPenalties table
        final String CREATE_ADD_PENALTIES_TABLE =
                "CREATE TABLE " + DatabaseDescription.AddPenaltiesLost.TABLE_NAME + "(" +
                        DatabaseDescription.AddPenaltiesLost._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.AddPenaltiesLost.COLUMN_COMPLETED_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.AddPenaltiesLost.COLUMN_PENALTY_TYPE + " TEXT, " +
                        DatabaseDescription.AddPenaltiesLost.COLUMN_PENALTY_VALUE + " INTEGER, " +
                        DatabaseDescription.AddPenaltiesLost.COLUMN_PENALTY_APPLIED_TYPE + " TEXT, " +
                        "UNIQUE (" + DatabaseDescription.AddPenaltiesLost.COLUMN_COMPLETED_LOCATION_NUMBER +
                        ", " + DatabaseDescription.AddPenaltiesLost.COLUMN_PENALTY_TYPE +"));";
        db.execSQL(CREATE_ADD_PENALTIES_TABLE);

        // create PARTIES table
        final String CREATE_PARTIES_TABLE =
                "CREATE TABLE " + DatabaseDescription.Parties.TABLE_NAME + "(" +
                        DatabaseDescription.Parties._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.Parties.COLUMN_NAME + " TEXT UNIQUE);";
        db.execSQL(CREATE_PARTIES_TABLE);

        // create Characters table
        final String CREATE_CHARACTERS_TABLE =
                "CREATE TABLE " + DatabaseDescription.Characters.TABLE_NAME + "(" +
                        DatabaseDescription.Characters._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.Characters.COLUMN_NAME + " TEXT, " +
                        DatabaseDescription.Characters.COLUMN_CLASS + " TEXT, " +
                        DatabaseDescription.Characters.COLUMN_PARTY + " TEXT, " +
                        "UNIQUE (" + DatabaseDescription.Characters.COLUMN_NAME +
                        ", " + DatabaseDescription.Characters.COLUMN_PARTY +"));";
        db.execSQL(CREATE_CHARACTERS_TABLE);

        // create UnlockingLocations table
        final String CREATE_UNLOCKING_LOCATIONS_TABLE =
                "CREATE TABLE " + DatabaseDescription.UnlockingLocations.TABLE_NAME + "(" +
                        DatabaseDescription.UnlockingLocations._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.UnlockingLocations.COLUMN_PARTY + " TEXT, " +
                        DatabaseDescription.UnlockingLocations.COLUMN_UNLOCKED_LOCATION + " INTEGER, " +
                        DatabaseDescription.UnlockingLocations.COLUMN_UNLOCKING_LOCATION + " INTEGER, " +
                        "UNIQUE (" + DatabaseDescription.UnlockingLocations.COLUMN_UNLOCKED_LOCATION +
                        ", " + DatabaseDescription.UnlockingLocations.COLUMN_PARTY +"));";
        db.execSQL(CREATE_UNLOCKING_LOCATIONS_TABLE);

        // create Attempt Table
        final String CREATE_ATTEMPTS_TABLE =
                "CREATE TABLE " + DatabaseDescription.Attempts.TABLE_NAME + "(" +
                        DatabaseDescription.Attempts._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.Attempts.COLUMN_TIMESTAMP + " INTEGER, " +
                        DatabaseDescription.Attempts.COLUMN_PARTY + " TEXT, " +
                        DatabaseDescription.Attempts.COLUMN_LOCATION + " INTEGER, " +
                        DatabaseDescription.Attempts.COLUMN_SUCCESSFUL + " TEXT, " +
                        "UNIQUE (" + DatabaseDescription.Attempts.COLUMN_TIMESTAMP +
                        ", " + DatabaseDescription.Attempts.COLUMN_PARTY +
                        ", " + DatabaseDescription.Attempts.COLUMN_LOCATION + "));";
        db.execSQL(CREATE_ATTEMPTS_TABLE);

        // create Participants table
        final String CREATE_PARTICIPANTS_TABLE =
                "CREATE TABLE " + DatabaseDescription.Participants.TABLE_NAME + "(" +
                        DatabaseDescription.GlobalAchievementsGained._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.Participants.COLUMN_TIMESTAMP + " INTEGER, " +
                        DatabaseDescription.Participants.COLUMN_CHARACTER + " INTEGER, " +
                        "UNIQUE (" + DatabaseDescription.Participants.COLUMN_TIMESTAMP +
                        ", " + DatabaseDescription.Participants.COLUMN_CHARACTER +"));";
        db.execSQL(CREATE_PARTICIPANTS_TABLE);


        // create Participants table
        final String CREATE_NON_PARTICIPANTS_TABLE =
                "CREATE TABLE " + DatabaseDescription.NonParticipants.TABLE_NAME + "(" +
                        DatabaseDescription.NonParticipants._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.NonParticipants.COLUMN_TIMESTAMP + " INTEGER, " +
                        DatabaseDescription.NonParticipants.COLUMN_CHARACTER + " INTEGER, " +
                        "UNIQUE (" + DatabaseDescription.NonParticipants.COLUMN_TIMESTAMP +
                        ", " + DatabaseDescription.NonParticipants.COLUMN_CHARACTER +"));";
        db.execSQL(CREATE_NON_PARTICIPANTS_TABLE);

        final String CREATE_UNLOCKED_LOCATIONS_TABLE =
                "CREATE TABLE " + DatabaseDescription.UnlockedLocations.TABLE_NAME + "(" +
                        DatabaseDescription.UnlockedLocations._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.UnlockedLocations.COLUMN_PARTY + " TEXT, " +
                        DatabaseDescription.UnlockedLocations.COLUMN_UNLOCKED_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.UnlockedLocations.COlUMN_UNLOCKING_LOCATION_NUMBER + " INTEGER, " +
                        "UNIQUE (" + DatabaseDescription.UnlockedLocations.COLUMN_UNLOCKED_LOCATION_NUMBER +
                        ", " + DatabaseDescription.UnlockedLocations.COlUMN_UNLOCKING_LOCATION_NUMBER +"));";
        db.execSQL(CREATE_UNLOCKED_LOCATIONS_TABLE);

        final String CREATE_BLOCKED_LOCATIONS_TABLE =
                "CREATE TABLE " + DatabaseDescription.BlockedLocations.TABLE_NAME + "(" +
                        DatabaseDescription.BlockedLocations._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.BlockedLocations.COLUMN_PARTY + " TEXT, " +
                        DatabaseDescription.BlockedLocations.COLUMN_BLOCKED_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.BlockedLocations.COlUMN_BLOCKING_LOCATION_NAME + " INTEGER, " +
                        "UNIQUE (" + DatabaseDescription.BlockedLocations.COLUMN_BLOCKED_LOCATION_NUMBER +
                        ", " + DatabaseDescription.BlockedLocations.COlUMN_BLOCKING_LOCATION_NAME +"));";
        db.execSQL(CREATE_BLOCKED_LOCATIONS_TABLE);

        final String CREATE_COMPLETED_LOCATIONS_TABLE =
                "CREATE TABLE " + DatabaseDescription.CompletedLocations.TABLE_NAME + "(" +
                        DatabaseDescription.CompletedLocations._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.CompletedLocations.COLUMN_PARTY + " TEXT, " +
                        DatabaseDescription.CompletedLocations.COLUMN_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.CompletedLocations.COLUMN_COMPLETED_TIMESTAMP + " INTEGER, " +
                        "UNIQUE (" + DatabaseDescription.CompletedLocations.COLUMN_PARTY +
                        ", " + DatabaseDescription.CompletedLocations.COLUMN_LOCATION_NUMBER +"));";
        db.execSQL(CREATE_COMPLETED_LOCATIONS_TABLE);

        final String CREATE_LOCKED_LOCATIONS_TABLE =
                "CREATE TABLE " + DatabaseDescription.LockedLocations.TABLE_NAME + "(" +
                        DatabaseDescription.LockedLocations._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.LockedLocations.COLUMN_PARTY + " TEXT, " +
                        DatabaseDescription.LockedLocations.COLUMN_LOCATION_NUMBER + " INTEGER, " +
                        "UNIQUE (" + DatabaseDescription.LockedLocations.COLUMN_PARTY +
                        ", " + DatabaseDescription.LockedLocations.COLUMN_LOCATION_NUMBER +"));";
        db.execSQL(CREATE_LOCKED_LOCATIONS_TABLE);
    }

    // normally defines how to upgrade the database when the schema changes
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // TODO
    }
}
