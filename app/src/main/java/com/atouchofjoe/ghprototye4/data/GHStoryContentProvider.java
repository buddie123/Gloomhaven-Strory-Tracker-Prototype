package com.atouchofjoe.ghprototye4.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Selection;
import android.util.Log;

import com.atouchofjoe.ghprototye4.R;

public class GHStoryContentProvider extends ContentProvider {
    // used to access the database
    private StoryDatabaseHelper dbHelper;

    // UriMatcher helps ContentProvider determine operation to perform
    private static final UriMatcher uriMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    // contents used with UriMatcher to determine operation to perform
    private static final int ONE_LOCATION = 1;
    private static final int ONE_PARTY = 2;
    private static final int ONE_CHARACTER = 3;
    private static final int ONE_ATTEMPT = 4;
    private static final int ONE_GLOBAL_ACHIEVEMENT = 5;
    private static final int ONE_PARTY_ACHIEVEMENT = 6;
    private static final int ONE_GLOBAL_ACHIEVEMENT_GAINED = 7;
    private static final int ONE_GLOBAL_ACHIEVEMENT_LOST = 8;
    private static final int ONE_PARTY_ACHIEVEMENT_GAINED = 9;
    private static final int ONE_PARTY_ACHIEVEMENT_LOST = 10;
    private static final int ONE_LOCATION_UNLOCKED = 11;
    private static final int ONE_LOCATION_BLOCKED = 12;
    private static final int ONE_APPLIED_TYPE = 13;
    private static final int ONE_ADD_REWARD_TYPE = 14;
    private static final int ONE_ADD_REWARD = 15;
    private static final int ONE_ADD_PENALTY = 16;
    private static final int ONE_PARTICIPANT = 17;
    private static final int ONE_NON_PARTICIPANT = 18;
    private static final int ONE_UNLOCKED_LOCATION = 19;
    private static final int ONE_BLOCKED_LOCATION = 20;
    private static final int ONE_COMPLETED_LOCATION = 21;
    private static final int ONE_LOCKED_LOCATION = 22;
    private static final int ONE_UNLOCKING_LOCATION = 23;

    private static final int LOCATIONS = 31;
    private static final int PARTIES = 32;
    private static final int CHARACTERS = 33;
    private static final int ATTEMPTS = 34;
    private static final int GLOBAL_ACHIEVEMENTS = 35;
    private static final int PARTY_ACHIEVEMENTS = 36;
    private static final int GLOBAL_ACHIEVEMENTS_GAINED = 37;
    private static final int GLOBAL_ACHIEVEMENTS_LOST = 38;
    private static final int PARTY_ACHIEVEMENTS_GAINED = 39;
    private static final int PARTY_ACHIEVEMENTS_LOST = 40;
    private static final int LOCATIONS_UNLOCKED = 41;
    private static final int LOCATIONS_BLOCKED = 42;
    private static final int APPLIED_TYPES = 43;
    private static final int ADD_REWARD_TYPES = 44;
    private static final int ADD_REWARDS = 45;
    private static final int ADD_PENALTIES = 46;
    private static final int PARTICIPANTS = 47;
    private static final int NON_PARTICIPANTS = 48;
    private static final int UNLOCKED_LOCATIONS = 49;
    private static final int BLOCKED_LOCATIONS = 50;
    private static final int COMPLETED_LOCATIONS = 51;
    private static final int LOCKED_LOCATIONS = 52;
    private static final int UNLOCKING_LOCATIONS = 53;


    static {
        // Locations
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.Locations.TABLE_NAME + "/#", ONE_LOCATION);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.Locations.TABLE_NAME, LOCATIONS);


        // Parties
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.Parties.TABLE_NAME + "/#", ONE_PARTY);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.Parties.TABLE_NAME, PARTIES);

        // Characters
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.Characters.TABLE_NAME + "/#", ONE_CHARACTER);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.Characters.TABLE_NAME, CHARACTERS);

        // Attempts
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.Attempts.TABLE_NAME + "/#", ONE_ATTEMPT);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.Attempts.TABLE_NAME, ATTEMPTS);

        // Global Achievements
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.GlobalAchievements.TABLE_NAME + "/#", ONE_GLOBAL_ACHIEVEMENT);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.GlobalAchievements.TABLE_NAME, GLOBAL_ACHIEVEMENTS);

        // Party Achievements
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.PartyAchievements.TABLE_NAME + "/#", ONE_PARTY_ACHIEVEMENT);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.PartyAchievements.TABLE_NAME, PARTY_ACHIEVEMENTS);

        // Global Achievements Gained
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.GlobalAchievementsGained.TABLE_NAME + "/#", ONE_GLOBAL_ACHIEVEMENT_GAINED);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.GlobalAchievementsGained.TABLE_NAME, GLOBAL_ACHIEVEMENTS_GAINED);

        // Global Achievements Lost
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.GlobalAchievementsLost.TABLE_NAME + "/#", ONE_GLOBAL_ACHIEVEMENT_LOST);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.GlobalAchievementsLost.TABLE_NAME, GLOBAL_ACHIEVEMENTS_LOST);

        // Party Achievements Gained
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.PartyAchievementsGained.TABLE_NAME + "/#", ONE_PARTY_ACHIEVEMENT_GAINED);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.PartyAchievementsGained.TABLE_NAME, PARTY_ACHIEVEMENTS_GAINED);

        // Party Achievements Lost
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.PartyAchievementsLost.TABLE_NAME + "/#", ONE_PARTY_ACHIEVEMENT_LOST);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.PartyAchievementsLost.TABLE_NAME, PARTY_ACHIEVEMENTS_LOST);

        // Locations Unlocked
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.LocationsUnlocked.TABLE_NAME + "/#", ONE_LOCATION_UNLOCKED);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.LocationsUnlocked.TABLE_NAME, LOCATIONS_UNLOCKED);

        // Locations Blocked
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.LocationsBlocked.TABLE_NAME + "/#", ONE_LOCATION_BLOCKED);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.LocationsBlocked.TABLE_NAME, LOCATIONS_BLOCKED);

        // Applied Types
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.AppliedTypes.TABLE_NAME + "/#", ONE_APPLIED_TYPE);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.AppliedTypes.TABLE_NAME, APPLIED_TYPES);

        // Add Reward Types
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.AddRewardTypes.TABLE_NAME + "/#", ONE_ADD_REWARD_TYPE);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.AddRewardTypes.TABLE_NAME, ADD_REWARD_TYPES);

        // Add Rewards Gained
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.AddRewardsGained.TABLE_NAME + "/#", ONE_ADD_REWARD);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.AddRewardsGained.TABLE_NAME, ADD_REWARDS);
        // Add Penalties Lost
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.AddPenaltiesLost.TABLE_NAME + "/#", ONE_ADD_PENALTY);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.AddPenaltiesLost.TABLE_NAME, ADD_PENALTIES);

        // Participants
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.Participants.TABLE_NAME + "/#", ONE_PARTICIPANT);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.Participants.TABLE_NAME, PARTICIPANTS);

        // Non-Participants
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.NonParticipants.TABLE_NAME + "/#", ONE_NON_PARTICIPANT);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.NonParticipants.TABLE_NAME, NON_PARTICIPANTS);

        // Unlocked Locations
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.UnlockedLocations.TABLE_NAME + "/#", ONE_UNLOCKED_LOCATION);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.UnlockedLocations.TABLE_NAME, UNLOCKED_LOCATIONS);

        // Blocked Locations
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.BlockedLocations.TABLE_NAME + "/#", ONE_BLOCKED_LOCATION);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.BlockedLocations.TABLE_NAME, BLOCKED_LOCATIONS);

        // Completed Locations
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.CompletedLocations.TABLE_NAME + "/#", ONE_COMPLETED_LOCATION);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.CompletedLocations.TABLE_NAME, COMPLETED_LOCATIONS);

        // Locked Locations
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.LockedLocations.TABLE_NAME + "/#", ONE_LOCKED_LOCATION);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.LockedLocations.TABLE_NAME, LOCKED_LOCATIONS);

        // Unlocking Locations
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.UnlockingLocations.TABLE_NAME + "/#", ONE_UNLOCKING_LOCATION);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.UnlockingLocations.TABLE_NAME, UNLOCKING_LOCATIONS);


    }

    @Override
    public boolean onCreate() {
        dbHelper = new StoryDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch(uriMatcher.match(uri)) {
            case ONE_LOCATION:
                queryBuilder.setTables(DatabaseDescription.Locations.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.Locations._ID + "=" +
                                uri.getLastPathSegment());
                break;
            case ONE_PARTY:
                queryBuilder.setTables(DatabaseDescription.Parties.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.Parties._ID + "=" +
                                uri.getLastPathSegment());
                break;
            case ONE_CHARACTER:
                queryBuilder.setTables(DatabaseDescription.Characters.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.Characters._ID + "=" +
                                uri.getLastPathSegment());
                break;
            case ONE_ATTEMPT:
                queryBuilder.setTables(DatabaseDescription.Attempts.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.Attempts._ID + "=" +
                                uri.getLastPathSegment());
                break;
            case ONE_GLOBAL_ACHIEVEMENT:
                queryBuilder.setTables(DatabaseDescription.GlobalAchievements.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.GlobalAchievements._ID + "=" +
                                uri.getLastPathSegment());
                break;
            case ONE_PARTY_ACHIEVEMENT:
                queryBuilder.setTables(DatabaseDescription.PartyAchievements.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.PartyAchievements._ID + "=" +
                                uri.getLastPathSegment());
                break;
            case ONE_GLOBAL_ACHIEVEMENT_GAINED:
                queryBuilder.setTables(DatabaseDescription.GlobalAchievementsGained.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.GlobalAchievementsGained._ID + "=" +
                                uri.getLastPathSegment());
                break;
            case ONE_GLOBAL_ACHIEVEMENT_LOST:
                queryBuilder.setTables(DatabaseDescription.GlobalAchievementsLost.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.GlobalAchievementsLost._ID + "=" +
                                uri.getLastPathSegment());
                break;
            case ONE_PARTY_ACHIEVEMENT_GAINED:
                queryBuilder.setTables(DatabaseDescription.PartyAchievementsGained.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.PartyAchievementsGained._ID + "=" +
                                uri.getLastPathSegment());
                break;
            case ONE_PARTY_ACHIEVEMENT_LOST:
                queryBuilder.setTables(DatabaseDescription.PartyAchievementsLost.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.PartyAchievementsLost._ID + "=" +
                                uri.getLastPathSegment());
                break;
            case ONE_LOCATION_UNLOCKED:
                queryBuilder.setTables(DatabaseDescription.LocationsUnlocked.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.LocationsUnlocked._ID + "=" +
                                uri.getLastPathSegment());
                break;
            case ONE_LOCATION_BLOCKED:
                queryBuilder.setTables(DatabaseDescription.LocationsBlocked.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.LocationsBlocked._ID + "=" +
                                uri.getLastPathSegment());
                break;
            case ONE_APPLIED_TYPE:
                queryBuilder.setTables(DatabaseDescription.AppliedTypes.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.AppliedTypes._ID + "=" +
                                uri.getLastPathSegment());
                break;
            case ONE_ADD_REWARD_TYPE:
                queryBuilder.setTables(DatabaseDescription.AddRewardTypes.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.AddRewardTypes._ID + "=" +
                                uri.getLastPathSegment());
                break;
            case ONE_ADD_REWARD:
                queryBuilder.setTables(DatabaseDescription.AddRewardsGained.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.AddRewardsGained._ID + "=" +
                                uri.getLastPathSegment());
                break;
            case ONE_ADD_PENALTY:
                queryBuilder.setTables(DatabaseDescription.AddPenaltiesLost.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.AddPenaltiesLost._ID + "=" +
                                uri.getLastPathSegment());
                break;
            case ONE_PARTICIPANT:
                queryBuilder.setTables(DatabaseDescription.Participants.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.Participants._ID + "=" +
                                uri.getLastPathSegment());
                break;
            case ONE_NON_PARTICIPANT:
                queryBuilder.setTables(DatabaseDescription.NonParticipants.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.NonParticipants._ID + "=" +
                                uri.getLastPathSegment());
                break;
            case ONE_UNLOCKED_LOCATION:
                queryBuilder.setTables(DatabaseDescription.UnlockedLocations.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.UnlockedLocations._ID + "=" +
                                uri.getLastPathSegment());
                break;
            case ONE_BLOCKED_LOCATION:
                queryBuilder.setTables(DatabaseDescription.BlockedLocations.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.BlockedLocations._ID + "=" +
                                uri.getLastPathSegment());
                break;
            case ONE_COMPLETED_LOCATION:
                queryBuilder.setTables(DatabaseDescription.CompletedLocations.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.CompletedLocations._ID + "=" +
                                uri.getLastPathSegment());
                break;
            case ONE_LOCKED_LOCATION:
                queryBuilder.setTables(DatabaseDescription.LockedLocations.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.LockedLocations._ID + "=" +
                                uri.getLastPathSegment());
                break;
            case ONE_UNLOCKING_LOCATION:
                queryBuilder.setTables(DatabaseDescription.UnlockingLocations.TABLE_NAME);
                queryBuilder.appendWhere(
                        DatabaseDescription.UnlockingLocations._ID + "=" +
                                uri.getLastPathSegment());
                break;

            // Multiple entry queries
            case LOCATIONS:
                queryBuilder.setTables(DatabaseDescription.Locations.TABLE_NAME);
                break;
            case PARTIES:
                queryBuilder.setTables(DatabaseDescription.Parties.TABLE_NAME);
                break;
            case CHARACTERS:
                queryBuilder.setTables(DatabaseDescription.Characters.TABLE_NAME);
                break;
            case ATTEMPTS:
                queryBuilder.setTables(DatabaseDescription.Attempts.TABLE_NAME);
                break;
            case GLOBAL_ACHIEVEMENTS:
                queryBuilder.setTables(DatabaseDescription.GlobalAchievements.TABLE_NAME);
                break;
            case PARTY_ACHIEVEMENTS:
                queryBuilder.setTables(DatabaseDescription.PartyAchievements.TABLE_NAME);
                break;
            case GLOBAL_ACHIEVEMENTS_GAINED:
                queryBuilder.setTables(DatabaseDescription.GlobalAchievementsGained.TABLE_NAME);
                break;
            case GLOBAL_ACHIEVEMENTS_LOST:
                queryBuilder.setTables(DatabaseDescription.GlobalAchievementsLost.TABLE_NAME);
                break;
            case PARTY_ACHIEVEMENTS_GAINED:
                queryBuilder.setTables(DatabaseDescription.PartyAchievementsGained.TABLE_NAME);
                break;
            case PARTY_ACHIEVEMENTS_LOST:
                queryBuilder.setTables(DatabaseDescription.PartyAchievementsLost.TABLE_NAME);
                break;
            case LOCATIONS_UNLOCKED:
                queryBuilder.setTables(DatabaseDescription.LocationsUnlocked.TABLE_NAME);
                break;
            case LOCATIONS_BLOCKED:
                queryBuilder.setTables(DatabaseDescription.LocationsBlocked.TABLE_NAME);
                break;
            case APPLIED_TYPES:
                queryBuilder.setTables(DatabaseDescription.AppliedTypes.TABLE_NAME);
                break;
            case ADD_REWARD_TYPES:
                queryBuilder.setTables(DatabaseDescription.AddRewardTypes.TABLE_NAME);
                break;
            case ADD_REWARDS:
                queryBuilder.setTables(DatabaseDescription.AddRewardsGained.TABLE_NAME);
                break;
            case ADD_PENALTIES:
                queryBuilder.setTables(DatabaseDescription.AddPenaltiesLost.TABLE_NAME);
                break;
            case PARTICIPANTS:
                queryBuilder.setTables(DatabaseDescription.Participants.TABLE_NAME);
                break;
            case NON_PARTICIPANTS:
                queryBuilder.setTables(DatabaseDescription.NonParticipants.TABLE_NAME);
                break;
            case UNLOCKED_LOCATIONS:
                queryBuilder.setTables(DatabaseDescription.UnlockedLocations.TABLE_NAME);
                break;
            case BLOCKED_LOCATIONS:
                queryBuilder.setTables(DatabaseDescription.BlockedLocations.TABLE_NAME);
                break;
            case COMPLETED_LOCATIONS:
                queryBuilder.setTables(DatabaseDescription.CompletedLocations.TABLE_NAME);
                break;
            case LOCKED_LOCATIONS:
                queryBuilder.setTables(DatabaseDescription.LockedLocations.TABLE_NAME);
                break;
            case UNLOCKING_LOCATIONS:
                queryBuilder.setTables(DatabaseDescription.UnlockingLocations.TABLE_NAME);
                break;
            default:
                throw new UnsupportedOperationException(
                        getContext().getString(R.string.error_invalid_query) + uri);
        }

        Cursor cursor = queryBuilder.query(dbHelper.getReadableDatabase(),
            projection, selection, selectionArgs, null, null, sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Uri newElementUri = null;
        long rowID;

        switch (uriMatcher.match(uri)) {
            case LOCATIONS:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.Locations.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowID > 0) {
                    newElementUri = DatabaseDescription.Locations.buildLocationUri(rowID);
                }
                break;
            case PARTIES:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.Parties.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowID > 0) {
                    newElementUri = DatabaseDescription.Parties.buildPartyUri(rowID);
                }
                break;
            case CHARACTERS:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.Characters.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowID > 0) {
                    newElementUri = DatabaseDescription.Characters.buildCharacterUri(rowID);
                }
                break;
            case ATTEMPTS:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.Attempts.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowID > 0) {
                    newElementUri = DatabaseDescription.Attempts.buildAttemptUri(rowID);
                }
                break;
            case GLOBAL_ACHIEVEMENTS:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.GlobalAchievements.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowID > 0) {
                    newElementUri = DatabaseDescription.GlobalAchievements.buildGlobalAchievementUri(rowID);
                }
                break;
            case PARTY_ACHIEVEMENTS:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.PartyAchievements.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowID > 0) {
                    newElementUri = DatabaseDescription.PartyAchievements.buildPartyAchievementUri(rowID);
                }
                break;
            case GLOBAL_ACHIEVEMENTS_GAINED:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.GlobalAchievementsGained.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowID > 0) {
                    newElementUri = DatabaseDescription.GlobalAchievementsGained.buildGlobalAchievementGainedUri(rowID);
                }
                break;
            case GLOBAL_ACHIEVEMENTS_LOST:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.GlobalAchievementsLost.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowID > 0) {
                    newElementUri = DatabaseDescription.GlobalAchievementsLost.buildGlobalAchievementLostUri(rowID);
                }
                break;
            case PARTY_ACHIEVEMENTS_GAINED:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.PartyAchievementsGained.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowID > 0) {
                    newElementUri = DatabaseDescription.PartyAchievementsGained.buildPartyAchievementGainedUri(rowID);
                }
                break;
            case PARTY_ACHIEVEMENTS_LOST:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.PartyAchievementsLost.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowID > 0) {
                    newElementUri = DatabaseDescription.PartyAchievementsLost.buildPartyAchievementLostUri(rowID);
                }
                break;
            case LOCATIONS_UNLOCKED:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.LocationsUnlocked.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowID > 0) {
                    newElementUri = DatabaseDescription.LocationsUnlocked.buildUnlockedLocationUri(rowID);
                }
                break;
            case LOCATIONS_BLOCKED:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.LocationsBlocked.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowID > 0) {
                    newElementUri = DatabaseDescription.LocationsBlocked.buildBlockedLocationUri(rowID);
                }
                break;
            case APPLIED_TYPES:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.AppliedTypes.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowID > 0) {
                    newElementUri = DatabaseDescription.AppliedTypes.buildAppliedTypeUri(rowID);
                }
                break;
            case ADD_REWARD_TYPES:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.AddRewardTypes.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowID > 0) {
                    newElementUri = DatabaseDescription.AddRewardTypes.buildAddRewardTypeUri(rowID);
                }
                break;
            case ADD_REWARDS:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.AddRewardsGained.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowID > 0) {
                    newElementUri = DatabaseDescription.AddRewardsGained.buildAddRewardUri(rowID);
                }
                break;
            case ADD_PENALTIES:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.AddPenaltiesLost.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowID > 0) {
                    newElementUri = DatabaseDescription.AddPenaltiesLost.buildAddPenaltyUri(rowID);
                }
                break;
            case PARTICIPANTS:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.Participants.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowID > 0) {
                    newElementUri = DatabaseDescription.Participants.buildParticipantUri(rowID);
                }
                break;
            case NON_PARTICIPANTS:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.NonParticipants.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowID > 0) {
                    newElementUri = DatabaseDescription.NonParticipants.buildNonParticipantUri(rowID);
                }
                break;
            case UNLOCKED_LOCATIONS:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.UnlockedLocations.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowID > 0) {
                    newElementUri = DatabaseDescription.UnlockedLocations.buildUnlockedLocationUri(rowID);
                }
                break;
            case BLOCKED_LOCATIONS:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.BlockedLocations.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowID > 0) {
                    newElementUri = DatabaseDescription.BlockedLocations.buildBlockedLocationUri(rowID);
                }
                break;
            case COMPLETED_LOCATIONS:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.CompletedLocations.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowID > 0) {
                    newElementUri = DatabaseDescription.CompletedLocations.buildCompletedLocationUri(rowID);
                }
                break;
            case LOCKED_LOCATIONS:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.LockedLocations.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowID > 0) {
                    newElementUri = DatabaseDescription.LockedLocations.buildLockedLocationUri(rowID);
                }
                break;
            case UNLOCKING_LOCATIONS:
                rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                        DatabaseDescription.UnlockingLocations.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                if(rowID > 0) {
                    newElementUri = DatabaseDescription.UnlockingLocations.buildUnlockingLocationUri(rowID);
                }
                break;
            default:
                throw new SQLException(
                        getContext().getString(R.string.error_invalid_insert_uri) + uri);
        }

        if (rowID > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
        //        throw new SQLException(
            Log.i("ContentProvider" , "rowID <= 0" );      //getContext().getString(R.string.error_invalid_insert_uri) + uri);
        }
        return newElementUri;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numberOfRowsUpdated; // 1 if update successful; 0 otherwise

        String id = uri.getLastPathSegment();

        switch (uriMatcher.match(uri)) {
            case ONE_LOCATION:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                    DatabaseDescription.Locations.TABLE_NAME , contentValues,
                    DatabaseDescription.Locations._ID + "=" + id,
                    selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case PARTIES:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                    DatabaseDescription.Parties.TABLE_NAME, contentValues, selection,
                    selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case CHARACTERS:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                    DatabaseDescription.Characters.TABLE_NAME, contentValues, selection,
                        selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case ONE_ATTEMPT:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                    DatabaseDescription.Attempts.TABLE_NAME, contentValues,
                        DatabaseDescription.Attempts._ID+ "=" + id,
                        selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case ONE_GLOBAL_ACHIEVEMENT:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                    DatabaseDescription.GlobalAchievements.TABLE_NAME, contentValues,
                        DatabaseDescription.GlobalAchievements._ID + "=" + id,
                        selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case ONE_PARTY_ACHIEVEMENT:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                    DatabaseDescription.PartyAchievements.TABLE_NAME, contentValues,
                        DatabaseDescription.PartyAchievements._ID + "=" + id,
                        selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case ONE_GLOBAL_ACHIEVEMENT_GAINED:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                    DatabaseDescription.GlobalAchievementsGained.TABLE_NAME, contentValues,
                        DatabaseDescription.GlobalAchievementsGained._ID + "=" + id,
                        selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case ONE_GLOBAL_ACHIEVEMENT_LOST:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                    DatabaseDescription.GlobalAchievementsLost.TABLE_NAME, contentValues,
                        DatabaseDescription.GlobalAchievementsLost._ID + "=" + id,
                         selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case ONE_PARTY_ACHIEVEMENT_GAINED:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                    DatabaseDescription.PartyAchievementsGained.TABLE_NAME, contentValues,
                        DatabaseDescription.PartyAchievementsGained._ID + "=" + id,
                         selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case ONE_PARTY_ACHIEVEMENT_LOST:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                    DatabaseDescription.PartyAchievementsLost.TABLE_NAME, contentValues,
                        DatabaseDescription.PartyAchievementsLost._ID + "=" + id,
                        selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case ONE_LOCATION_UNLOCKED:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                    DatabaseDescription.LocationsUnlocked.TABLE_NAME, contentValues,
                        DatabaseDescription.LocationsUnlocked._ID + "=" + id,
                        selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case ONE_LOCATION_BLOCKED:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                    DatabaseDescription.LocationsBlocked.TABLE_NAME, contentValues,
                        DatabaseDescription.LocationsBlocked._ID + "=" + id,
                        selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case ONE_APPLIED_TYPE:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                    DatabaseDescription.AppliedTypes.TABLE_NAME, contentValues,
                        DatabaseDescription.AppliedTypes._ID + "=" + id,
                        selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case ONE_ADD_REWARD_TYPE:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                    DatabaseDescription.AddRewardTypes.TABLE_NAME, contentValues,
                        DatabaseDescription.AddRewardTypes._ID + "=" + id,
                                selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case ONE_ADD_REWARD:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                    DatabaseDescription.AddRewardsGained.TABLE_NAME, contentValues,
                        DatabaseDescription.AddRewardsGained._ID + "="  + id,
                                selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case ONE_ADD_PENALTY:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                    DatabaseDescription.AddPenaltiesLost.TABLE_NAME, contentValues,
                        DatabaseDescription.AddPenaltiesLost._ID + "=" + id,
                                selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case ONE_PARTICIPANT:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                    DatabaseDescription.Participants.TABLE_NAME, contentValues,
                        DatabaseDescription.Participants._ID + "=" + id,
                        selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case ONE_NON_PARTICIPANT:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                    DatabaseDescription.NonParticipants.TABLE_NAME, contentValues,
                        DatabaseDescription.NonParticipants._ID + "=" + id,
                        selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case UNLOCKED_LOCATIONS:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                    DatabaseDescription.UnlockedLocations.TABLE_NAME, contentValues,
                       selection, selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case BLOCKED_LOCATIONS:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                    DatabaseDescription.BlockedLocations.TABLE_NAME, contentValues,
                        selection, selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case COMPLETED_LOCATIONS:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                    DatabaseDescription.CompletedLocations.TABLE_NAME, contentValues,
                        selection, selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case LOCKED_LOCATIONS:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                    DatabaseDescription.LockedLocations.TABLE_NAME, contentValues,
                        selection, selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case ONE_UNLOCKING_LOCATION:
                numberOfRowsUpdated = dbHelper.getWritableDatabase().updateWithOnConflict(
                        DatabaseDescription.UnlockingLocations.TABLE_NAME, contentValues,
                        DatabaseDescription.UnlockingLocations._ID + "=" + id,
                        selectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            default:
                throw new UnsupportedOperationException(
                        getContext().getString(R.string.error_invalid_update_uri) + uri);
        }

        if (numberOfRowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numberOfRowsUpdated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int numberOfRowsDeleted; // 1 if delete successful; 0 otherwise

        String id = uri.getLastPathSegment();

        switch (uriMatcher.match(uri)) {
            case ONE_LOCATION:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.Locations.TABLE_NAME ,
                        DatabaseDescription.Locations._ID + "=" + id,
                        selectionArgs);
                break;
            case PARTIES:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.Parties.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            case CHARACTERS:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.Characters.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            case ONE_ATTEMPT:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.Attempts.TABLE_NAME,
                        DatabaseDescription.Attempts._ID + "=" + id,
                        selectionArgs);
                break;
            case ONE_GLOBAL_ACHIEVEMENT:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.GlobalAchievements.TABLE_NAME,
                        DatabaseDescription.GlobalAchievements._ID + "=" + id,
                        selectionArgs);
                break;
            case ONE_PARTY_ACHIEVEMENT:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.PartyAchievements.TABLE_NAME,
                        DatabaseDescription.PartyAchievements._ID + "=" + id,
                        selectionArgs);
                break;
            case ONE_GLOBAL_ACHIEVEMENT_GAINED:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.GlobalAchievementsGained.TABLE_NAME,
                        DatabaseDescription.GlobalAchievementsGained._ID + "=" + id,
                        selectionArgs);
                break;
            case ONE_GLOBAL_ACHIEVEMENT_LOST:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.GlobalAchievementsLost.TABLE_NAME,
                        DatabaseDescription.GlobalAchievementsLost._ID + "=" + id,
                        selectionArgs);
                break;
            case ONE_PARTY_ACHIEVEMENT_GAINED:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.PartyAchievementsGained.TABLE_NAME,
                        DatabaseDescription.PartyAchievementsGained._ID + "=" + id,
                        selectionArgs);
                break;
            case ONE_PARTY_ACHIEVEMENT_LOST:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.PartyAchievementsLost.TABLE_NAME,
                        DatabaseDescription.PartyAchievementsLost._ID + "=" + id,
                        selectionArgs);
                break;
            case ONE_LOCATION_UNLOCKED:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.LocationsUnlocked.TABLE_NAME,
                        DatabaseDescription.LocationsUnlocked._ID + "=" + id,
                        selectionArgs);
                break;
            case ONE_LOCATION_BLOCKED:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.LocationsBlocked.TABLE_NAME,
                        DatabaseDescription.LocationsBlocked._ID + "=" + id,
                        selectionArgs);
                break;
            case ONE_APPLIED_TYPE:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.AppliedTypes.TABLE_NAME,
                        DatabaseDescription.AppliedTypes._ID + "=" + id,
                        selectionArgs);
                break;
            case ONE_ADD_REWARD_TYPE:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.AddRewardTypes.TABLE_NAME,
                        DatabaseDescription.AddRewardTypes._ID + "=" + id,
                        selectionArgs);
                break;
            case ONE_ADD_REWARD:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.AddRewardsGained.TABLE_NAME,
                        DatabaseDescription.AddRewardsGained._ID + "="  + id,
                        selectionArgs);
                break;
            case ONE_ADD_PENALTY:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.AddPenaltiesLost.TABLE_NAME,
                        DatabaseDescription.AddPenaltiesLost._ID + "=" + id,
                        selectionArgs);
                break;
            case ONE_PARTICIPANT:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.Participants.TABLE_NAME,
                        DatabaseDescription.Participants._ID + "=" + id,
                        selectionArgs);
                break;
            case ONE_NON_PARTICIPANT:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.NonParticipants.TABLE_NAME,
                        DatabaseDescription.NonParticipants._ID + "=" + id,
                        selectionArgs);
                break;
            case ONE_UNLOCKED_LOCATION:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.UnlockedLocations.TABLE_NAME,
                        DatabaseDescription.UnlockedLocations._ID + "=" + id,
                        selectionArgs);
                break;
            case ONE_BLOCKED_LOCATION:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.BlockedLocations.TABLE_NAME,
                        DatabaseDescription.BlockedLocations._ID + "=" + id,
                        selectionArgs);
                break;
            case ONE_COMPLETED_LOCATION:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.CompletedLocations.TABLE_NAME,
                        DatabaseDescription.CompletedLocations._ID + "=" + id,
                        selectionArgs);
                break;
            case ONE_LOCKED_LOCATION:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.LockedLocations.TABLE_NAME,
                        DatabaseDescription.LockedLocations._ID + "=" + id,
                        selectionArgs);
                break;
            case ONE_UNLOCKING_LOCATION:
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        DatabaseDescription.UnlockingLocations.TABLE_NAME,
                        DatabaseDescription.UnlockingLocations._ID + "=" + id,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException(
                        getContext().getString(R.string.error_invalid_delete_uri) + uri);
        }

        if (numberOfRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numberOfRowsDeleted;
    }
}
