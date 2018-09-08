package com.atouchofjoe.ghprototye4.location.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

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
                        DatabaseDescription.Locations.COLUMN_NUMBER + " INTEGER primary key, " +
                        DatabaseDescription.Locations.COLUMN_NAME + " TEXT, " +
                        DatabaseDescription.Locations.COLUMN_TEASER + " TEXT, " +
                        DatabaseDescription.Locations.COLUMN_SUMMARY + " TEXT, " +
                        DatabaseDescription.Locations.COLUMN_CONCLUSION + " TEXT);";
        db.execSQL(CREATE_LOCATIONS_TABLE);

        // create the GlobalAchievements table
        final String CREATE_GLOBAL_ACHIEVEMENTS_TABLE =
                "CREATE TABLE " + DatabaseDescription.GlobalAchievements.TABLE_NAME + "(" +
                        DatabaseDescription.GlobalAchievements.COLUMN_NAME + " TEXT primary key, " +
                        DatabaseDescription.GlobalAchievements.COLUMN_ACHIEVEMENT_REPLACED + " TEXT, " +
                        DatabaseDescription.GlobalAchievements.COLUMN_ACHIEVEMENT_MAX_COUNT + " INTEGER DEFAULT 1);";
        db.execSQL(CREATE_GLOBAL_ACHIEVEMENTS_TABLE);

        // create the PartyAchievements table
        final String CREATE_PARTY_ACHIEVEMENTS_TABLE =
                "CREATE TABLE " + DatabaseDescription.PartyAchievements.TABLE_NAME + "(" +
                        DatabaseDescription.PartyAchievements.COLUMN_NAME + " TEXT primary key, " +
                        DatabaseDescription.PartyAchievements.COLUMN_ACHIEVEMENT_REPLACED + " TEXT);";
        db.execSQL(CREATE_PARTY_ACHIEVEMENTS_TABLE);

        // create the GlobalAchievementsGained table
        final String CREATE_GLOBAL_ACHIEVEMENTS_GAINED_TABLE =
                "CREATE TABLE " + DatabaseDescription.GlobalAchievementsGained.TABLE_NAME + "(" +
                        DatabaseDescription.GlobalAchievementsGained.COLUMN_COMPLETED_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.GlobalAchievementsGained.COLUMN_GLOBAL_ACHIEVEMENT + " TEXT, " +
                        DatabaseDescription.GlobalAchievementsGained.COLUMN_TIMES_GAINED + " INTEGER DEFAULT 1, " +
                        "PRIMARY KEY (" + DatabaseDescription.GlobalAchievementsGained.COLUMN_COMPLETED_LOCATION_NUMBER + ", " +
                        DatabaseDescription.GlobalAchievementsGained.COLUMN_GLOBAL_ACHIEVEMENT + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.GlobalAchievementsGained.COLUMN_COMPLETED_LOCATION_NUMBER +
                        ") REFERENCES " + DatabaseDescription.Locations.TABLE_NAME +
                        " (" + DatabaseDescription.Locations.COLUMN_NUMBER + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.GlobalAchievementsGained.COLUMN_GLOBAL_ACHIEVEMENT +
                        ") REFERENCES " + DatabaseDescription.GlobalAchievements.TABLE_NAME +
                        " (" + DatabaseDescription.GlobalAchievements.COLUMN_NAME + "));";
        db.execSQL(CREATE_GLOBAL_ACHIEVEMENTS_GAINED_TABLE);

        // create the GlobalAchievementsLost table
        final String CREATE_GLOBAL_ACHIEVEMENTS_LOST_TABLE =
                "CREATE TABLE " + DatabaseDescription.GlobalAchievementsLost.TABLE_NAME + "(" +
                        DatabaseDescription.GlobalAchievementsLost.COLUMN_COMPLETED_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.GlobalAchievementsLost.COLUMN_GLOBAL_ACHIEVEMENT + " TEXT, " +
                        "PRIMARY KEY (" + DatabaseDescription.GlobalAchievementsLost.COLUMN_COMPLETED_LOCATION_NUMBER + ", " +
                        DatabaseDescription.GlobalAchievementsLost.COLUMN_GLOBAL_ACHIEVEMENT + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.GlobalAchievementsLost.COLUMN_COMPLETED_LOCATION_NUMBER +
                        ") REFERENCES " + DatabaseDescription.Locations.TABLE_NAME +
                        " (" + DatabaseDescription.Locations.COLUMN_NUMBER + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.GlobalAchievementsLost.COLUMN_GLOBAL_ACHIEVEMENT +
                        ") REFERENCES " + DatabaseDescription.GlobalAchievements.TABLE_NAME +
                        " (" + DatabaseDescription.GlobalAchievements.COLUMN_NAME + "));";
        db.execSQL(CREATE_GLOBAL_ACHIEVEMENTS_LOST_TABLE);

        // create the PartyAchievementsGained table
        final String CREATE_PARTY_ACHIEVEMENTS_GAINED_TABLE =
                "CREATE TABLE " + DatabaseDescription.PartyAchievementsGained.TABLE_NAME + "(" +
                        DatabaseDescription.PartyAchievementsGained.COLUMN_COMPLETED_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.PartyAchievementsGained.COLUMN_PARTY_ACHIEVEMENT + " TEXT, " +
                        "PRIMARY KEY (" + DatabaseDescription.PartyAchievementsGained.COLUMN_COMPLETED_LOCATION_NUMBER + ", " +
                        DatabaseDescription.PartyAchievementsGained.COLUMN_PARTY_ACHIEVEMENT + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.PartyAchievementsGained.COLUMN_COMPLETED_LOCATION_NUMBER +
                        ") REFERENCES " + DatabaseDescription.Locations.TABLE_NAME +
                        " (" + DatabaseDescription.Locations.COLUMN_NUMBER + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.PartyAchievementsGained.COLUMN_PARTY_ACHIEVEMENT +
                        ") REFERENCES " + DatabaseDescription.PartyAchievements.TABLE_NAME +
                        " (" + DatabaseDescription.PartyAchievements.COLUMN_NAME + "));";
        db.execSQL(CREATE_PARTY_ACHIEVEMENTS_GAINED_TABLE);

        // create the PartyAchievementsLost table
        final String CREATE_PARTY_ACHIEVEMENTS_LOST_TABLE =
                "CREATE TABLE " + DatabaseDescription.PartyAchievementsLost.TABLE_NAME + "(" +
                        DatabaseDescription.PartyAchievementsLost.COLUMN_COMPLETED_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.PartyAchievementsLost.COLUMN_PARTY_ACHIEVEMENT + " TEXT, " +
                        "PRIMARY KEY (" + DatabaseDescription.PartyAchievementsLost.COLUMN_COMPLETED_LOCATION_NUMBER + ", " +
                        DatabaseDescription.PartyAchievementsLost.COLUMN_PARTY_ACHIEVEMENT + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.PartyAchievementsLost.COLUMN_COMPLETED_LOCATION_NUMBER +
                        ") REFERENCES " + DatabaseDescription.Locations.TABLE_NAME +
                        " (" + DatabaseDescription.Locations.COLUMN_NUMBER + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.PartyAchievementsLost.COLUMN_PARTY_ACHIEVEMENT +
                        ") REFERENCES " + DatabaseDescription.PartyAchievements.TABLE_NAME +
                        " (" + DatabaseDescription.PartyAchievements.COLUMN_NAME + "));";
        db.execSQL(CREATE_PARTY_ACHIEVEMENTS_LOST_TABLE);

        // create the LocationsUnlocked table
        final String CREATE_LOCATIONS_UNLOCKED_TABLE =
                "CREATE TABLE " + DatabaseDescription.LocationsUnlocked.TABLE_NAME + "(" +
                        DatabaseDescription.LocationsUnlocked.COLUMN_COMPLETED_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.LocationsUnlocked.COLUMN_UNLOCKED_LOCATION_NUMBER + " INTEGER, " +
                        "PRIMARY KEY (" + DatabaseDescription.LocationsUnlocked.COLUMN_COMPLETED_LOCATION_NUMBER + ", " +
                        DatabaseDescription.LocationsUnlocked.COLUMN_UNLOCKED_LOCATION_NUMBER + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.LocationsUnlocked.COLUMN_COMPLETED_LOCATION_NUMBER +
                        ") REFERENCES " + DatabaseDescription.Locations.TABLE_NAME +
                        " (" + DatabaseDescription.Locations.COLUMN_NUMBER + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.LocationsUnlocked.COLUMN_UNLOCKED_LOCATION_NUMBER +
                        ") REFERENCES " + DatabaseDescription.Locations.TABLE_NAME +
                        " (" + DatabaseDescription.Locations.COLUMN_NUMBER + "));";
        db.execSQL(CREATE_LOCATIONS_UNLOCKED_TABLE);

        // create the LocationsBlocked table
        final String CREATE_LOCATIONS_BLOCKED_TABLE =
                "CREATE TABLE " + DatabaseDescription.LocationsBlocked.TABLE_NAME + "(" +
                        DatabaseDescription.LocationsBlocked.COLUMN_COMPLETED_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.LocationsBlocked.COLUMN_BLOCKED_LOCATION_NUMBER + " INTEGER, " +
                        "PRIMARY KEY (" + DatabaseDescription.LocationsBlocked.COLUMN_COMPLETED_LOCATION_NUMBER + ", " +
                        DatabaseDescription.LocationsBlocked.COLUMN_BLOCKED_LOCATION_NUMBER + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.LocationsBlocked.COLUMN_COMPLETED_LOCATION_NUMBER +
                        ") REFERENCES " + DatabaseDescription.Locations.TABLE_NAME +
                        " (" + DatabaseDescription.Locations.COLUMN_NUMBER + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.LocationsBlocked.COLUMN_BLOCKED_LOCATION_NUMBER +
                        ") REFERENCES " + DatabaseDescription.Locations.TABLE_NAME +
                        " (" + DatabaseDescription.Locations.COLUMN_NUMBER + "));";
        db.execSQL(CREATE_LOCATIONS_BLOCKED_TABLE);

        // create AppliedTypes table
        final String CREATE_APPLIED_TYPES_TABLE =
                "CREATE TABLE " + DatabaseDescription.AppliedTypes.TABLE_NAME + "(" +
                        DatabaseDescription.AppliedTypes.COLUMN_TYPE + " TEXT primary key);";
        db.execSQL(CREATE_APPLIED_TYPES_TABLE);

        // create AppliedTypes table
        final String CREATE_ADD_REWARD_TYPES_TABLE =
                "CREATE TABLE " + DatabaseDescription.AddRewardTypes.TABLE_NAME + "(" +
                        DatabaseDescription.AddRewardTypes.COLUMN_TYPE + " TEXT primary key);";
        db.execSQL(CREATE_ADD_REWARD_TYPES_TABLE);

        // create the AddRewards table
        final String CREATE_ADD_REWARDS_TABLE =
                "CREATE TABLE " + DatabaseDescription.AddRewardsGained.TABLE_NAME + "(" +
                        DatabaseDescription.AddRewardsGained.COLUMN_COMPLETED_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.AddRewardsGained.COLUMN_REWARD_TYPE + " TEXT, " +
                        DatabaseDescription.AddRewardsGained.COLUMN_REWARD_VALUE + " INTEGER, " +
                        DatabaseDescription.AddRewardsGained.COLUMN_REWARD_APPLIED_TYPE + " TEXT, " +
                        "PRIMARY KEY (" + DatabaseDescription.AddRewardsGained.COLUMN_COMPLETED_LOCATION_NUMBER + ", " +
                        DatabaseDescription.AddRewardsGained.COLUMN_REWARD_TYPE + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.AddRewardsGained.COLUMN_COMPLETED_LOCATION_NUMBER +
                        ") REFERENCES " + DatabaseDescription.Locations.TABLE_NAME +
                        " (" + DatabaseDescription.Locations.COLUMN_NUMBER + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.AddRewardsGained.COLUMN_REWARD_TYPE +
                        ") REFERENCES " + DatabaseDescription.AddRewardTypes.TABLE_NAME +
                        " (" + DatabaseDescription.AddRewardTypes.COLUMN_TYPE + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.AddRewardsGained.COLUMN_REWARD_APPLIED_TYPE +
                        ") REFERENCES " + DatabaseDescription.AppliedTypes.TABLE_NAME +
                        " (" + DatabaseDescription.AppliedTypes.COLUMN_TYPE + "));";
        db.execSQL(CREATE_ADD_REWARDS_TABLE);

        // create the AddPenalties table
        final String CREATE_ADD_PENALTIES_TABLE =
                "CREATE TABLE " + DatabaseDescription.AddPenaltiesLost.TABLE_NAME + "(" +
                        DatabaseDescription.AddPenaltiesLost.COLUMN_COMPLETED_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.AddPenaltiesLost.COLUMN_PENALTY_TYPE + " TEXT, " +
                        DatabaseDescription.AddPenaltiesLost.COLUMN_PENALTY_VALUE + " INTEGER, " +
                        DatabaseDescription.AddPenaltiesLost.COLUMN_PENALTY_APPLIED_TYPE + " TEXT, " +
                        "PRIMARY KEY (" + DatabaseDescription.AddPenaltiesLost.COLUMN_COMPLETED_LOCATION_NUMBER + ", " +
                        DatabaseDescription.AddPenaltiesLost.COLUMN_PENALTY_TYPE + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.AddPenaltiesLost.COLUMN_COMPLETED_LOCATION_NUMBER +
                        ") REFERENCES " + DatabaseDescription.Locations.TABLE_NAME +
                        " (" + DatabaseDescription.Locations.COLUMN_NUMBER + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.AddPenaltiesLost.COLUMN_PENALTY_TYPE +
                        ") REFERENCES " + DatabaseDescription.AddRewardTypes.TABLE_NAME +
                        " (" + DatabaseDescription.AddRewardTypes.COLUMN_TYPE + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.AddPenaltiesLost.COLUMN_PENALTY_APPLIED_TYPE +
                        ") REFERENCES " + DatabaseDescription.AppliedTypes.TABLE_NAME +
                        " (" + DatabaseDescription.AppliedTypes.COLUMN_TYPE + "));";
        db.execSQL(CREATE_ADD_PENALTIES_TABLE);

        // create PARTIES table
        final String CREATE_PARTIES_TABLE =
                "CREATE TABLE " + DatabaseDescription.Parties.TABLE_NAME + "(" +
                        DatabaseDescription.Parties.COLUMN_NAME + " TEXT primary key);";
        db.execSQL(CREATE_PARTIES_TABLE);

        // create Characters table
        final String CREATE_CHARACTERS_TABLE =
                "CREATE TABLE " + DatabaseDescription.Characters.TABLE_NAME + "(" +
                        DatabaseDescription.Characters.COLUMN_NAME + " TEXT, " +
                        DatabaseDescription.Characters.COLUMN_PARTY + " TEXT, " +
                        "PRIMARY KEY (" + DatabaseDescription.Characters.COLUMN_NAME + ", " +
                        DatabaseDescription.Characters.COLUMN_PARTY + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.Characters.COLUMN_PARTY +
                        ") REFERENCES " + DatabaseDescription.Parties.TABLE_NAME +
                        " (" + DatabaseDescription.Parties.COLUMN_NAME + "));";
        db.execSQL(CREATE_CHARACTERS_TABLE);

        // create Attempt Table
        final String CREATE_ATTEMPTS_TABLE =
                "CREATE TABLE " + DatabaseDescription.Attempts.TABLE_NAME + "(" +
                        DatabaseDescription.Attempts.COLUMN_TIMESTAMP + " INTEGER primary key, " +
                        DatabaseDescription.Attempts.COLUMN_PARTY + " TEXT, " +
                        DatabaseDescription.Attempts.COLUMN_LOCATION + " INTEGER, " +
                        DatabaseDescription.Attempts.COLUMN_SUCCESSFUL + " TEXT, " +
                        "FOREIGN KEY (" + DatabaseDescription.Attempts.COLUMN_PARTY +
                        ") REFERENCES " + DatabaseDescription.Parties.TABLE_NAME + " (" +
                        DatabaseDescription.Parties.COLUMN_NAME + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.Attempts.COLUMN_LOCATION +
                        ") REFERENCES " + DatabaseDescription.Locations.TABLE_NAME + " (" +
                        DatabaseDescription.Locations.COLUMN_NUMBER + "));";
        db.execSQL(CREATE_ATTEMPTS_TABLE);

        // create Participants table
        final String CREATE_PARTICIPANTS_TABLE =
                "CREATE TABLE " + DatabaseDescription.Participants.TABLE_NAME + "(" +
                        DatabaseDescription.Participants.COLUMN_TIMESTAMP + " INTEGER, " +
                        DatabaseDescription.Participants.COLUMN_CHARACTER + " TEXT, " +
                        "PRIMARY KEY (" + DatabaseDescription.Participants.COLUMN_TIMESTAMP + ", " +
                        DatabaseDescription.Participants.COLUMN_CHARACTER + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.Participants.COLUMN_TIMESTAMP +
                        ") REFERENCES " + DatabaseDescription.Attempts.TABLE_NAME + " (" +
                        DatabaseDescription.Attempts.COLUMN_TIMESTAMP + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.Participants.COLUMN_CHARACTER +
                        ") REFERENCES " + DatabaseDescription.Characters.TABLE_NAME + " (" +
                        DatabaseDescription.Characters.COLUMN_NAME + "));";
        db.execSQL(CREATE_PARTICIPANTS_TABLE);


        // create Participants table
        final String CREATE_NON_PARTICIPANTS_TABLE =
                "CREATE TABLE " + DatabaseDescription.NonParticipants.TABLE_NAME + "(" +
                        DatabaseDescription.NonParticipants.COLUMN_TIMESTAMP + " INTEGER, " +
                        DatabaseDescription.NonParticipants.COLUMN_CHARACTER + " TEXT, " +
                        "PRIMARY KEY (" + DatabaseDescription.NonParticipants.COLUMN_TIMESTAMP + ", " +
                        DatabaseDescription.NonParticipants.COLUMN_CHARACTER + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.NonParticipants.COLUMN_TIMESTAMP +
                        ") REFERENCES " + DatabaseDescription.Attempts.TABLE_NAME + " (" +
                        DatabaseDescription.Attempts.COLUMN_TIMESTAMP + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.NonParticipants.COLUMN_CHARACTER +
                        ") REFERENCES " + DatabaseDescription.Characters.TABLE_NAME + " (" +
                        DatabaseDescription.Characters.COLUMN_NAME + "));";
        db.execSQL(CREATE_NON_PARTICIPANTS_TABLE);

        final String CREATE_UNLOCKED_LOCATIONS_TABLE =
                "CREATE TABLE " + DatabaseDescription.UnlockedLocations.TABLE_NAME + "(" +
                        DatabaseDescription.UnlockedLocations.COLUMN_PARTY + " TEXT, " +
                        DatabaseDescription.UnlockedLocations.COLUMN_UNLOCKED_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.UnlockedLocations.COlUMN_UNLOCKING_LOCATION_NUMBER + " INTEGER, " +
                        "PRIMARY KEY (" + DatabaseDescription.UnlockedLocations.COLUMN_PARTY + ", " +
                        DatabaseDescription.UnlockedLocations.COLUMN_UNLOCKED_LOCATION_NUMBER + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.UnlockedLocations.COLUMN_PARTY +
                        ") REFERENCES " + DatabaseDescription.Parties.TABLE_NAME + " (" +
                        DatabaseDescription.Parties.COLUMN_NAME + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.UnlockedLocations.COLUMN_UNLOCKED_LOCATION_NUMBER +
                        ") REFERENCES " + DatabaseDescription.Locations.TABLE_NAME + " (" +
                        DatabaseDescription.Locations.COLUMN_NUMBER + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.UnlockedLocations.COlUMN_UNLOCKING_LOCATION_NUMBER +
                        ") REFERENCES " + DatabaseDescription.Locations.TABLE_NAME + " (" +
                        DatabaseDescription.Locations.COLUMN_NUMBER + "));";
        db.execSQL(CREATE_UNLOCKED_LOCATIONS_TABLE);

        final String CREATE_BLOCKED_LOCATIONS_TABLE =
                "CREATE TABLE " + DatabaseDescription.BlockedLocations.TABLE_NAME + "(" +
                        DatabaseDescription.BlockedLocations.COLUMN_PARTY + " TEXT, " +
                        DatabaseDescription.BlockedLocations.COLUMN_BLOCKED_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.BlockedLocations.COlUMN_BLOCKING_LOCATION_NAME + " INTEGER, " +
                        "PRIMARY KEY (" + DatabaseDescription.BlockedLocations.COLUMN_PARTY + ", " +
                        DatabaseDescription.BlockedLocations.COLUMN_BLOCKED_LOCATION_NUMBER + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.BlockedLocations.COLUMN_PARTY +
                        ") REFERENCES " + DatabaseDescription.Parties.TABLE_NAME + " (" +
                        DatabaseDescription.Parties.COLUMN_NAME + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.BlockedLocations.COLUMN_BLOCKED_LOCATION_NUMBER +
                        ") REFERENCES " + DatabaseDescription.Locations.TABLE_NAME + " (" +
                        DatabaseDescription.Locations.COLUMN_NUMBER + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.BlockedLocations.COlUMN_BLOCKING_LOCATION_NAME +
                        ") REFERENCES " + DatabaseDescription.Locations.TABLE_NAME + " (" +
                        DatabaseDescription.Locations.COLUMN_NUMBER + "));";
        db.execSQL(CREATE_BLOCKED_LOCATIONS_TABLE);

        final String CREATE_COMPLETED_LOCATIONS_TABLE =
                "CREATE TABLE " + DatabaseDescription.CompletedLocations.TABLE_NAME + "(" +
                        DatabaseDescription.CompletedLocations.COLUMN_PARTY + " TEXT, " +
                        DatabaseDescription.CompletedLocations.COLUMN_LOCATION_NUMBER + " INTEGER, " +
                        DatabaseDescription.CompletedLocations.COLUMN_COMPLETED_TIMESTAMP + " INTEGER, " +
                        "PRIMARY KEY (" + DatabaseDescription.CompletedLocations.COLUMN_PARTY + ", " +
                        DatabaseDescription.CompletedLocations.COLUMN_LOCATION_NUMBER + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.CompletedLocations.COLUMN_PARTY +
                        ") REFERENCES " + DatabaseDescription.Parties.TABLE_NAME + " (" +
                        DatabaseDescription.Parties.COLUMN_NAME + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.CompletedLocations.COLUMN_LOCATION_NUMBER +
                        ") REFERENCES " + DatabaseDescription.Locations.TABLE_NAME + " (" +
                        DatabaseDescription.Locations.COLUMN_NUMBER + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.CompletedLocations.COLUMN_COMPLETED_TIMESTAMP +
                        ") REFERENCES " + DatabaseDescription.Attempts.TABLE_NAME + " (" +
                        DatabaseDescription.Attempts.COLUMN_TIMESTAMP + "));";
        db.execSQL(CREATE_COMPLETED_LOCATIONS_TABLE);

        final String CREATE_LOCKED_LOCATIONS_TABLE =
                "CREATE TABLE " + DatabaseDescription.LockedLocations.TABLE_NAME + "(" +
                        DatabaseDescription.LockedLocations.COLUMN_PARTY + " TEXT, " +
                        DatabaseDescription.LockedLocations.COLUMN_LOCATION_NUMBER + " INTEGER, " +
                        "PRIMARY KEY (" + DatabaseDescription.LockedLocations.COLUMN_PARTY + ", " +
                        DatabaseDescription.LockedLocations.COLUMN_LOCATION_NUMBER + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.LockedLocations.COLUMN_PARTY +
                        ") REFERENCES " + DatabaseDescription.Parties.TABLE_NAME + " (" +
                        DatabaseDescription.Parties.COLUMN_NAME + "), " +
                        "FOREIGN KEY (" + DatabaseDescription.LockedLocations.COLUMN_LOCATION_NUMBER +
                        ") REFERENCES " + DatabaseDescription.Locations.TABLE_NAME + " (" +
                        DatabaseDescription.Locations.COLUMN_NUMBER + "));";
        db.execSQL(CREATE_LOCKED_LOCATIONS_TABLE);
    }




    // normally defines how to upgrade the database when the schema changes
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // TODO
    }
}
