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
                        DatabaseDescription.PartyAchievements.COLUMN_ACHIEVEMENT_TO_BE_REPLACED + " TEXT);";
        db.execSQL(CREATE_PARTY_ACHIEVEMENTS_TABLE);

        // create the GlobalAchievementsToBeAwarded table
        final String CREATE_GLOBAL_ACHIEVEMENTS_GAINED_TABLE =
                "CREATE TABLE " + DatabaseDescription.GlobalAchievementsToBeAwarded.TABLE_NAME + "(" +
                        DatabaseDescription.GlobalAchievementsToBeAwarded._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.GlobalAchievementsToBeAwarded.COLUMN_LOCATION_TO_BE_COMPLETED + " INTEGER, " +
                        DatabaseDescription.GlobalAchievementsToBeAwarded.COLUMN_GLOBAL_ACHIEVEMENT + " TEXT, " +
                        "UNIQUE (" + DatabaseDescription.GlobalAchievementsToBeAwarded.COLUMN_LOCATION_TO_BE_COMPLETED +
                        ", " + DatabaseDescription.GlobalAchievementsToBeAwarded.COLUMN_GLOBAL_ACHIEVEMENT +"));";
        db.execSQL(CREATE_GLOBAL_ACHIEVEMENTS_GAINED_TABLE);

        // create the GlobalAchievementsToBeRevoked table
        final String CREATE_GLOBAL_ACHIEVEMENTS_LOST_TABLE =
                "CREATE TABLE " + DatabaseDescription.GlobalAchievementsToBeRevoked.TABLE_NAME + "(" +
                        DatabaseDescription.GlobalAchievementsToBeRevoked._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.GlobalAchievementsToBeRevoked.COLUMN_LOCATION_TO_BE_COMPLETED + " INTEGER, " +
                        DatabaseDescription.GlobalAchievementsToBeRevoked.COLUMN_GLOBAL_ACHIEVEMENT + " TEXT, " +
                        "UNIQUE (" + DatabaseDescription.GlobalAchievementsToBeRevoked.COLUMN_LOCATION_TO_BE_COMPLETED +
                        ", " + DatabaseDescription.GlobalAchievementsToBeRevoked.COLUMN_GLOBAL_ACHIEVEMENT + "));";
        db.execSQL(CREATE_GLOBAL_ACHIEVEMENTS_LOST_TABLE);

        // create the PartyAchievementsToBeAwarded table
        final String CREATE_PARTY_ACHIEVEMENTS_GAINED_TABLE =
                "CREATE TABLE " + DatabaseDescription.PartyAchievementsToBeAwarded.TABLE_NAME + "(" +
                        DatabaseDescription.PartyAchievementsToBeAwarded._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.PartyAchievementsToBeAwarded.COLUMN_LOCATION_TO_BE_COMPLETED + " INTEGER, " +
                        DatabaseDescription.PartyAchievementsToBeAwarded.COLUMN_PARTY_ACHIEVEMENT + " TEXT, " +
                        "UNIQUE (" + DatabaseDescription.PartyAchievementsToBeAwarded.COLUMN_LOCATION_TO_BE_COMPLETED +
                        ", " + DatabaseDescription.PartyAchievementsToBeAwarded.COLUMN_PARTY_ACHIEVEMENT + "));";
        db.execSQL(CREATE_PARTY_ACHIEVEMENTS_GAINED_TABLE);

        // create the PartyAchievementsToBeRevoked table
        final String CREATE_PARTY_ACHIEVEMENTS_LOST_TABLE =
                "CREATE TABLE " + DatabaseDescription.PartyAchievementsToBeRevoked.TABLE_NAME + "(" +
                        DatabaseDescription.PartyAchievementsToBeRevoked._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.PartyAchievementsToBeRevoked.COLUMN_LOCATION_TO_BE_COMPLETED + " INTEGER, " +
                        DatabaseDescription.PartyAchievementsToBeRevoked.COLUMN_PARTY_ACHIEVEMENT + " TEXT, " +
                        "UNIQUE (" + DatabaseDescription.PartyAchievementsToBeRevoked.COLUMN_LOCATION_TO_BE_COMPLETED +
                        ", " + DatabaseDescription.PartyAchievementsToBeRevoked.COLUMN_PARTY_ACHIEVEMENT +"));";
        db.execSQL(CREATE_PARTY_ACHIEVEMENTS_LOST_TABLE);

        // create the LocationsToBeUnlocked table
        final String CREATE_LOCATIONS_UNLOCKED_TABLE =
                "CREATE TABLE " + DatabaseDescription.LocationsToBeUnlocked.TABLE_NAME + "(" +
                        DatabaseDescription.LocationsToBeUnlocked._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.LocationsToBeUnlocked.COLUMN_LOCATION_TO_BE_COMPLETED + " INTEGER, " +
                        DatabaseDescription.LocationsToBeUnlocked.COLUMN_UNLOCKED_LOCATION + " INTEGER, " +
                        "UNIQUE (" + DatabaseDescription.LocationsToBeUnlocked.COLUMN_LOCATION_TO_BE_COMPLETED +
                        ", " + DatabaseDescription.LocationsToBeUnlocked.COLUMN_UNLOCKED_LOCATION +"));";
        db.execSQL(CREATE_LOCATIONS_UNLOCKED_TABLE);

        // create the LocationsToBeBlocked table
        final String CREATE_LOCATIONS_BLOCKED_TABLE =
                "CREATE TABLE " + DatabaseDescription.LocationsToBeBlocked.TABLE_NAME + "(" +
                        DatabaseDescription.LocationsToBeBlocked._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.LocationsToBeBlocked.COLUMN_LOCATION_TO_BE_COMPLETED + " INTEGER, " +
                        DatabaseDescription.LocationsToBeBlocked.COLUMN_BLOCKED_LOCATION + " INTEGER, " +
                        "UNIQUE (" + DatabaseDescription.LocationsToBeBlocked.COLUMN_LOCATION_TO_BE_COMPLETED +
                        ", " + DatabaseDescription.LocationsToBeBlocked.COLUMN_BLOCKED_LOCATION +"));";
        db.execSQL(CREATE_LOCATIONS_BLOCKED_TABLE);

        // create AddRewardApplicationTypes table
        final String CREATE_APPLIED_TYPES_TABLE =
                "CREATE TABLE " + DatabaseDescription.AddRewardApplicationTypes.TABLE_NAME + "(" +
                        DatabaseDescription.AddRewardApplicationTypes._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.AddRewardApplicationTypes.COLUMN_APPLICATION_TYPE + " TEXT UNIQUE);";
        db.execSQL(CREATE_APPLIED_TYPES_TABLE);

        // create AddRewardApplicationTypes table
        final String CREATE_ADD_REWARD_TYPES_TABLE =
                "CREATE TABLE " + DatabaseDescription.AddRewardTypes.TABLE_NAME + "(" +
                        DatabaseDescription.AddRewardTypes._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.AddRewardTypes.COLUMN_REWARD_TYPE + " TEXT UNIQUE);";
        db.execSQL(CREATE_ADD_REWARD_TYPES_TABLE);

        // create the AddRewards table
        final String CREATE_ADD_REWARDS_TABLE =
                "CREATE TABLE " + DatabaseDescription.AddRewards.TABLE_NAME + "(" +
                        DatabaseDescription.AddRewards._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.AddRewards.COLUMN_LOCATION_TO_BE_COMPLETED + " INTEGER, " +
                        DatabaseDescription.AddRewards.COLUMN_REWARD_TYPE + " TEXT, " +
                        DatabaseDescription.AddRewards.COLUMN_REWARD_VALUE + " INTEGER, " +
                        DatabaseDescription.AddRewards.COLUMN_REWARD_APPLICATION_TYPE + " TEXT, " +
                        "UNIQUE (" + DatabaseDescription.AddRewards.COLUMN_LOCATION_TO_BE_COMPLETED +
                        ", " + DatabaseDescription.AddRewards.COLUMN_REWARD_TYPE +"));";
        db.execSQL(CREATE_ADD_REWARDS_TABLE);

        // create the AddPenalties table
        final String CREATE_ADD_PENALTIES_TABLE =
                "CREATE TABLE " + DatabaseDescription.AddPenalties.TABLE_NAME + "(" +
                        DatabaseDescription.AddPenalties._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.AddPenalties.COLUMN_LOCATION_TO_BE_COMPLETED + " INTEGER, " +
                        DatabaseDescription.AddPenalties.COLUMN_PENALTY_TYPE + " TEXT, " +
                        DatabaseDescription.AddPenalties.COLUMN_PENALTY_VALUE + " INTEGER, " +
                        DatabaseDescription.AddPenalties.COLUMN_PENALTY_APPLICATION_TYPE + " TEXT, " +
                        "UNIQUE (" + DatabaseDescription.AddPenalties.COLUMN_LOCATION_TO_BE_COMPLETED +
                        ", " + DatabaseDescription.AddPenalties.COLUMN_PENALTY_TYPE +"));";
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

        // create AttemptParticipants table
        final String CREATE_PARTICIPANTS_TABLE =
                "CREATE TABLE " + DatabaseDescription.AttemptParticipants.TABLE_NAME + "(" +
                        DatabaseDescription.GlobalAchievementsToBeAwarded._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.AttemptParticipants.COLUMN_ATTEMPT_TIMESTAMP + " INTEGER, " +
                        DatabaseDescription.AttemptParticipants.COLUMN_CHARACTER + " INTEGER, " +
                        "UNIQUE (" + DatabaseDescription.AttemptParticipants.COLUMN_ATTEMPT_TIMESTAMP +
                        ", " + DatabaseDescription.AttemptParticipants.COLUMN_CHARACTER +"));";
        db.execSQL(CREATE_PARTICIPANTS_TABLE);


        // create AttemptParticipants table
        final String CREATE_NON_PARTICIPANTS_TABLE =
                "CREATE TABLE " + DatabaseDescription.AttemptNonParticipants.TABLE_NAME + "(" +
                        DatabaseDescription.AttemptNonParticipants._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.AttemptNonParticipants.COLUMN_ATTEMPT_TIMESTAMP + " INTEGER, " +
                        DatabaseDescription.AttemptNonParticipants.COLUMN_CHARACTER + " INTEGER, " +
                        "UNIQUE (" + DatabaseDescription.AttemptNonParticipants.COLUMN_ATTEMPT_TIMESTAMP +
                        ", " + DatabaseDescription.AttemptNonParticipants.COLUMN_CHARACTER +"));";
        db.execSQL(CREATE_NON_PARTICIPANTS_TABLE);

        final String CREATE_UNLOCKED_LOCATIONS_TABLE =
                "CREATE TABLE " + DatabaseDescription.UnlockedLocations.TABLE_NAME + "(" +
                        DatabaseDescription.UnlockedLocations._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.UnlockedLocations.COLUMN_PARTY + " TEXT, " +
                        DatabaseDescription.UnlockedLocations.COLUMN_UNLOCKED_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.UnlockedLocations.COLUMN_UNLOCKING_LOCATION_NUMBER + " INTEGER, " +
                        "UNIQUE (" + DatabaseDescription.UnlockedLocations.COLUMN_UNLOCKED_LOCATION_NUMBER +
                        ", " + DatabaseDescription.UnlockedLocations.COLUMN_UNLOCKING_LOCATION_NUMBER +"));";
        db.execSQL(CREATE_UNLOCKED_LOCATIONS_TABLE);

        final String CREATE_BLOCKED_LOCATIONS_TABLE =
                "CREATE TABLE " + DatabaseDescription.BlockedLocations.TABLE_NAME + "(" +
                        DatabaseDescription.BlockedLocations._ID + " INTEGER primary key AUTOINCREMENT, " +
                        DatabaseDescription.BlockedLocations.COLUMN_PARTY + " TEXT, " +
                        DatabaseDescription.BlockedLocations.COLUMN_BLOCKED_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.BlockedLocations.COLUMN_BLOCKING_LOCATION_NUMBER + " INTEGER, " +
                        "UNIQUE (" + DatabaseDescription.BlockedLocations.COLUMN_BLOCKED_LOCATION_NUMBER +
                        ", " + DatabaseDescription.BlockedLocations.COLUMN_BLOCKING_LOCATION_NUMBER +"));";
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
