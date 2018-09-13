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

import com.atouchofjoe.ghprototye4.R;

public class GHStoryContentProvider extends ContentProvider {
    // used to access the database
    private StoryDatabaseHelper dbHelper;

    // UriMatcher helps ContentProvider determine operation to perform
    private static final UriMatcher uriMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    private static final int LOCATIONS = 31;
    private static final int PARTIES = 32;
    private static final int CHARACTERS = 33;
    private static final int ATTEMPTS = 34;
    private static final int GLOBAL_ACHIEVEMENTS = 35;
    private static final int PARTY_ACHIEVEMENTS = 36;
    private static final int GLOBAL_ACHIEVEMENTS_TO_BE_AWARDED = 37;
    private static final int GLOBAL_ACHIEVEMENTS_TO_BE_REVOKED = 38;
    private static final int PARTY_ACHIEVEMENTS_TO_BE_AWARDED = 39;
    private static final int PARTY_ACHIEVEMENTS_TO_BE_REVOKED = 40;
    private static final int LOCATIONS_TO_BE_UNLOCKED = 41;
    private static final int LOCATIONS_TO_BE_BLOCKED = 42;
    private static final int REWARD_APPLICATION_TYPES = 43;
    private static final int ADD_REWARD_TYPES = 44;
    private static final int ADD_REWARDS = 45;
    private static final int ADD_PENALTIES = 46;
    private static final int ATTEMPT_PARTICIPANTS = 47;
    private static final int ATTEMPT_NON_PARTICIPANTS = 48;
    private static final int UNLOCKED_LOCATIONS = 49;
    private static final int BLOCKED_LOCATIONS = 50;
    private static final int COMPLETED_LOCATIONS = 51;
    private static final int LOCKED_LOCATIONS = 52;


    static {
        // Locations
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.Locations.TABLE_NAME, LOCATIONS);

        // Parties
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.Parties.TABLE_NAME, PARTIES);

        // Characters
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.Characters.TABLE_NAME, CHARACTERS);

        // Attempts
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.Attempts.TABLE_NAME, ATTEMPTS);

        // Global Achievements
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.GlobalAchievements.TABLE_NAME, GLOBAL_ACHIEVEMENTS);

        // Party Achievements
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.PartyAchievements.TABLE_NAME, PARTY_ACHIEVEMENTS);

        // Global Achievements To Be Awarded
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.GlobalAchievementsToBeAwarded.TABLE_NAME, GLOBAL_ACHIEVEMENTS_TO_BE_AWARDED);

        // Global Achievements To Be Revoked
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.GlobalAchievementsToBeRevoked.TABLE_NAME, GLOBAL_ACHIEVEMENTS_TO_BE_REVOKED);

        // Party Achievements To Be Awarded
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.PartyAchievementsToBeAwarded.TABLE_NAME, PARTY_ACHIEVEMENTS_TO_BE_AWARDED);

        // Party Achievements To Be Revoked
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.PartyAchievementsToBeRevoked.TABLE_NAME, PARTY_ACHIEVEMENTS_TO_BE_REVOKED);

        // Locations To Be Unlocked
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.LocationsToBeUnlocked.TABLE_NAME, LOCATIONS_TO_BE_UNLOCKED);

        // Locations To Be Blocked
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.LocationsToBeBlocked.TABLE_NAME, LOCATIONS_TO_BE_BLOCKED);

        // Add Reward Application Types
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.AddRewardApplicationTypes.TABLE_NAME, REWARD_APPLICATION_TYPES);

        // Add Reward Types
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.AddRewardTypes.TABLE_NAME, ADD_REWARD_TYPES);

        // Add Rewards
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.AddRewards.TABLE_NAME, ADD_REWARDS);
        // Add Penalties
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.AddPenalties.TABLE_NAME, ADD_PENALTIES);

        // AttemptParticipants
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.AttemptParticipants.TABLE_NAME, ATTEMPT_PARTICIPANTS);

        // Attempt Non-Participants
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.AttemptNonParticipants.TABLE_NAME, ATTEMPT_NON_PARTICIPANTS);

        // Unlocked Locations
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.UnlockedLocations.TABLE_NAME, UNLOCKED_LOCATIONS);

        // Blocked Locations
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.BlockedLocations.TABLE_NAME, BLOCKED_LOCATIONS);

        // Completed Locations
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.CompletedLocations.TABLE_NAME, COMPLETED_LOCATIONS);

        // Locked Locations
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                DatabaseDescription.LockedLocations.TABLE_NAME, LOCKED_LOCATIONS);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new StoryDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        // TODO Research how this method is used
        return null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // initialize the queryBuilder object used to get the cursor with the query results
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // set the appropriate table to query based on the given uri
        queryBuilder.setTables(getTableName(uri));
        //  get the cursor object
        Cursor cursor = queryBuilder.query(dbHelper.getReadableDatabase(),
            projection, selection, selectionArgs, null, null, sortOrder);

        if(getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Uri newElementUri = null;

        // insert into database. ignore insert if the constraints aren't met
        long rowID = dbHelper.getWritableDatabase().insertWithOnConflict(
                getTableName(uri), null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);

        // get the uri for inserted element
        if(rowID > 0) {
            switch (uriMatcher.match(uri)) {
                case LOCATIONS:
                    newElementUri = DatabaseDescription.Locations.buildLocationUri(rowID);
                    break;
                case PARTIES:
                    newElementUri = DatabaseDescription.Parties.buildPartyUri(rowID);
                    break;
                case CHARACTERS:
                    newElementUri = DatabaseDescription.Characters.buildCharacterUri(rowID);
                    break;
                case ATTEMPTS:
                    newElementUri = DatabaseDescription.Attempts.buildAttemptUri(rowID);
                    break;
                case GLOBAL_ACHIEVEMENTS:
                    newElementUri = DatabaseDescription.GlobalAchievements.buildGlobalAchievementUri(rowID);
                    break;
                case PARTY_ACHIEVEMENTS:
                    newElementUri = DatabaseDescription.PartyAchievements.buildPartyAchievementUri(rowID);
                    break;
                case GLOBAL_ACHIEVEMENTS_TO_BE_AWARDED:
                    newElementUri = DatabaseDescription.GlobalAchievementsToBeAwarded.buildGlobalAchievementToBeAwardedUri(rowID);
                    break;
                case GLOBAL_ACHIEVEMENTS_TO_BE_REVOKED:
                    newElementUri = DatabaseDescription.GlobalAchievementsToBeRevoked.buildGlobalAchievementToBeRevokedUri(rowID);
                    break;
                case PARTY_ACHIEVEMENTS_TO_BE_AWARDED:
                    newElementUri = DatabaseDescription.PartyAchievementsToBeAwarded.buildPartyAchievementToBeAwardedUri(rowID);
                    break;
                case PARTY_ACHIEVEMENTS_TO_BE_REVOKED:
                    newElementUri = DatabaseDescription.PartyAchievementsToBeRevoked.buildPartyAchievementToBeRevokedUri(rowID);
                    break;
                case LOCATIONS_TO_BE_UNLOCKED:
                    newElementUri = DatabaseDescription.LocationsToBeUnlocked.buildLocationToBeUnlockedUri(rowID);
                    break;
                case LOCATIONS_TO_BE_BLOCKED:
                    newElementUri = DatabaseDescription.LocationsToBeBlocked.buildLocationToBeBlockedUri(rowID);
                    break;
                case REWARD_APPLICATION_TYPES:
                    newElementUri = DatabaseDescription.AddRewardApplicationTypes.buildAddRewardApplicationTypeUri(rowID);
                    break;
                case ADD_REWARD_TYPES:
                    newElementUri = DatabaseDescription.AddRewardTypes.buildAddRewardTypeUri(rowID);
                    break;
                case ADD_REWARDS:
                    newElementUri = DatabaseDescription.AddRewards.buildAddRewardUri(rowID);
                    break;
                case ADD_PENALTIES:
                    newElementUri = DatabaseDescription.AddPenalties.buildAddPenaltyUri(rowID);
                    break;
                case ATTEMPT_PARTICIPANTS:
                    newElementUri = DatabaseDescription.AttemptParticipants.buildAttemptParticipantUri(rowID);
                    break;
                case ATTEMPT_NON_PARTICIPANTS:
                    newElementUri = DatabaseDescription.AttemptNonParticipants.buildAttemptNonParticipantUri(rowID);
                    break;
                case UNLOCKED_LOCATIONS:
                    newElementUri = DatabaseDescription.UnlockedLocations.buildUnlockedLocationUri(rowID);
                    break;
                case BLOCKED_LOCATIONS:
                    newElementUri = DatabaseDescription.BlockedLocations.buildBlockedLocationUri(rowID);
                    break;
                case COMPLETED_LOCATIONS:
                    newElementUri = DatabaseDescription.CompletedLocations.buildCompletedLocationUri(rowID);
                    break;
                case LOCKED_LOCATIONS:
                    newElementUri = DatabaseDescription.LockedLocations.buildLockedLocationUri(rowID);
                    break;
            }

            // update the content resolver that the specific table has been changed
            if(getContext() != null) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
        }
        return newElementUri;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        // update the rows in the appropriate table
        int numberOfRowsUpdated = dbHelper.getWritableDatabase().update(
                getTableName(uri) , contentValues, selection, selectionArgs);

        // notify the contentResolver if a table has been changed
        if (numberOfRowsUpdated != 0 && getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // return how many rows were updated
        return numberOfRowsUpdated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        // delete the rows in the appropriate table, if able
        int numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                getTableName(uri), selection, selectionArgs);

        // notify the contentResolver if a table has been changed
        if (numberOfRowsDeleted != 0) {
            if(getContext() != null) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
        }

        // return how many rows were deleted
        return numberOfRowsDeleted;
    }

    // return a table name based on a given Uri, or throw an error if invalid
    private String getTableName(Uri uri) {
        String tableName;
        switch (uriMatcher.match(uri)) {
            case LOCATIONS:
                tableName = DatabaseDescription.Locations.TABLE_NAME;
                break;
            case PARTIES:
                tableName = DatabaseDescription.Parties.TABLE_NAME;
                break;
            case CHARACTERS:
                tableName = DatabaseDescription.Characters.TABLE_NAME;
                break;
            case ATTEMPTS:
                tableName = DatabaseDescription.Attempts.TABLE_NAME;
                break;
            case GLOBAL_ACHIEVEMENTS:
                tableName = DatabaseDescription.GlobalAchievements.TABLE_NAME;
                break;
            case PARTY_ACHIEVEMENTS:
                tableName = DatabaseDescription.PartyAchievements.TABLE_NAME;
                break;
            case GLOBAL_ACHIEVEMENTS_TO_BE_AWARDED:
                tableName = DatabaseDescription.GlobalAchievementsToBeAwarded.TABLE_NAME;
                break;
            case GLOBAL_ACHIEVEMENTS_TO_BE_REVOKED:
                tableName = DatabaseDescription.GlobalAchievementsToBeRevoked.TABLE_NAME;
                break;
            case PARTY_ACHIEVEMENTS_TO_BE_AWARDED:
                tableName = DatabaseDescription.PartyAchievementsToBeAwarded.TABLE_NAME;
                break;
            case PARTY_ACHIEVEMENTS_TO_BE_REVOKED:
                tableName = DatabaseDescription.PartyAchievementsToBeRevoked.TABLE_NAME;
                break;
            case LOCATIONS_TO_BE_UNLOCKED:
                tableName = DatabaseDescription.LocationsToBeUnlocked.TABLE_NAME;
                break;
            case LOCATIONS_TO_BE_BLOCKED:
                tableName = DatabaseDescription.LocationsToBeBlocked.TABLE_NAME;
                break;
            case REWARD_APPLICATION_TYPES:
                tableName = DatabaseDescription.AddRewardApplicationTypes.TABLE_NAME;
                break;
            case ADD_REWARD_TYPES:
                tableName = DatabaseDescription.AddRewardTypes.TABLE_NAME;
                break;
            case ADD_REWARDS:
                tableName = DatabaseDescription.AddRewards.TABLE_NAME;
                break;
            case ADD_PENALTIES:
                tableName = DatabaseDescription.AddPenalties.TABLE_NAME;
                break;
            case ATTEMPT_PARTICIPANTS:
                tableName = DatabaseDescription.AttemptParticipants.TABLE_NAME;
                break;
            case ATTEMPT_NON_PARTICIPANTS:
                tableName = DatabaseDescription.AttemptNonParticipants.TABLE_NAME;
                break;
            case UNLOCKED_LOCATIONS:
                tableName = DatabaseDescription.UnlockedLocations.TABLE_NAME;
                break;
            case BLOCKED_LOCATIONS:
                tableName = DatabaseDescription.BlockedLocations.TABLE_NAME;
                break;
            case COMPLETED_LOCATIONS:
                tableName = DatabaseDescription.CompletedLocations.TABLE_NAME;
                break;
            case LOCKED_LOCATIONS:
                tableName = DatabaseDescription.LockedLocations.TABLE_NAME;
                break;
            default:
                throw new SQLException(getContext() != null ?
                        getContext().getString(R.string.error_invalid_insert_uri) + uri: null);
        }
        return tableName;
    }
}