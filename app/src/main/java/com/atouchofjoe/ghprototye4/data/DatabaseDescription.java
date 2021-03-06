package com.atouchofjoe.ghprototye4.data;


import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/*
 * DatabaseDescription.Java
 * Author: Robert Reed
 * Contains an inner class for each table in the database. Each inner class
 * contains a series of public constants: TABLE_NAME, CONTENT_URI, and
 * one for each non _ID column in the table. There is also a method  in
 * each class to create a URI for a specific element using it's id.
 */
public class DatabaseDescription {
    // ContentProvider's name: typically the package name
    public static final String AUTHORITY =
            "com.atouchofjoe.ghprototye4.location.data";

    // base URI used to interact with the ContentProvider
    private static final Uri BASE_CONTENT_URI =
            Uri.parse("content://" + AUTHORITY);

    // the Attempts table records each Attempt a party makes for each location
    // that a party has attempted. Columns include the timestamp for when
    // the attempt was recorded and whether or not the attempt was successful;
    public static final class Attempts implements BaseColumns {
        public static final String TABLE_NAME = "attempts"; // table's name

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String COLUMN_PARTY = "party";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_SUCCESSFUL = "successful";

        public static Uri buildAttemptUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // the AttemptParticipants table records which characters in a given party participated
    // in a given attempt. Uses the timestamp to distinguish which attempt is being
    // referenced by each entry.
    public static final class AttemptParticipants implements BaseColumns {
        public static final String TABLE_NAME = "attempt_participants"; // table's name

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String COLUMN_ATTEMPT_TIMESTAMP = "attempt_timestamp";
        public static final String COLUMN_CHARACTER = "character";

        public static Uri buildAttemptParticipantUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // the AttemptNonParticipants table records which characters in a given party did not
    // participate in a given attempt. Uses the timestamp to distinguish which
    // attempt is being referenced by each entry.
    public static final class AttemptNonParticipants implements BaseColumns {
        public static final String TABLE_NAME = "attempt_non_participants"; // table's name

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String COLUMN_ATTEMPT_TIMESTAMP = "attempt_timestamp";
        public static final String COLUMN_CHARACTER = "character";

        public static Uri buildAttemptNonParticipantUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // the Parties table records the name of each party that has been created
    public static final class Parties implements BaseColumns {
        public static final String TABLE_NAME = "parties"; // table's name

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String COLUMN_NAME = "name";

        public static Uri buildPartyUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // the Characters table records the characters that are in each party
    public static final class Characters implements BaseColumns {
        public static final String TABLE_NAME = "characters"; // table's name

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String COLUMN_PARTY = "party";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CLASS = "class";

        public static Uri buildCharacterUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // the Locations table records basic information about each location in the
    // game, including the teaser, summary, and conclusion texts.
    public static final class Locations implements BaseColumns {
        public static final String TABLE_NAME = "locations"; // table's name

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String COLUMN_NUMBER = "number";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TEASER = "teaser";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_CONCLUSION = "conclusion";

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // the GlobalAchievements table records information about each Global
    // Achievement in the game
    public static final class GlobalAchievements implements BaseColumns {
        public static final String TABLE_NAME = "global_achievements"; // table's name

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ACHIEVEMENT_REPLACED = "achievement_replaced";
        public static final String COLUMN_ACHIEVEMENT_MAX_COUNT = "achievement_max_count";

        public static Uri buildGlobalAchievementUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // the GlobalAchievementsToBeAwarded table tracks which Global Achievements can be awarded
    // by each Location that awards a global achievement when completed
    public static final class GlobalAchievementsToBeAwarded implements BaseColumns {
        public static final String TABLE_NAME = "global_achievements_to_be_awarded"; // table's name

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String COLUMN_LOCATION_TO_BE_COMPLETED = "location_to_be_completed";
        public static final String COLUMN_GLOBAL_ACHIEVEMENT = "global_achievement";

        public static Uri buildGlobalAchievementToBeAwardedUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // the GlobalAchievementsToBeRevoked table tracks which Global Achievements will be
    // lost by each Location that revokes/replaces a global achievement when completed
    public static final class GlobalAchievementsToBeRevoked implements BaseColumns {
        public static final String TABLE_NAME = "global_achievements_to_be_revoked"; // table's name

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String COLUMN_LOCATION_TO_BE_COMPLETED = "location_to_be_completed";
        public static final String COLUMN_GLOBAL_ACHIEVEMENT = "global_achievement";

        public static Uri buildGlobalAchievementToBeRevokedUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // the PartyAchievements table records all and entry for each Party Achievement in the game
    public static final class PartyAchievements implements BaseColumns {
        public static final String TABLE_NAME = "party_achievements"; // table's name

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ACHIEVEMENT_TO_BE_REPLACED = "achievement_to_be_replaced";

        public static Uri buildPartyAchievementUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // the PartyAchievementsToBeAwarded table tracks which Party Achievements can be awarded
    // by each Location that awards a party achievement when completed
    public static final class PartyAchievementsToBeAwarded implements BaseColumns {
        public static final String TABLE_NAME = "party_achievements_to_be_awarded"; // table's name

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String COLUMN_LOCATION_TO_BE_COMPLETED = "location_to_be_completed";
        public static final String COLUMN_PARTY_ACHIEVEMENT = "party_achievement";

        public static Uri buildPartyAchievementToBeAwardedUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // the PartyAchievementsToBeRevoked table tracks which Party Achievements can be revoked
    // by each Location that revokes a party achievement when completed
    public static final class PartyAchievementsToBeRevoked implements BaseColumns {
        public static final String TABLE_NAME = "party_achievements_to_be_revoked"; // table's name

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String COLUMN_LOCATION_TO_BE_COMPLETED = "location_to_be_completed";
        public static final String COLUMN_PARTY_ACHIEVEMENT = "party_achievement";

        public static Uri buildPartyAchievementToBeRevokedUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // the LocationsToBeUnlocked table records which locations will be unlocked by each
    // location that unlocks one or more locations
    public static final class LocationsToBeUnlocked implements BaseColumns {
        public static final String TABLE_NAME = "locations_to_be_unlocked"; // table's name

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for contacts table's columns
        public static final String COLUMN_LOCATION_TO_BE_COMPLETED = "location_to_be_completed";
        public static final String COLUMN_UNLOCKED_LOCATION_NUMBER = "unlocked_location_number";
        public static final String COLUMN_UNLOCKED_LOCATION_NAME = "unlocked_location_name";

        public static Uri buildLocationToBeUnlockedUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // the LocationsToBeBlocked table records which locations will be blocked by each
    // location that blocks one or more locations
    public static final class LocationsToBeBlocked implements BaseColumns {
        public static final String TABLE_NAME = "locations_to_be_blocked"; // table's name

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String COLUMN_LOCATION_TO_BE_COMPLETED = "location_to_be_completed";
        public static final String COLUMN_BLOCKED_LOCATION_NUMBER = "blocked_location_number";
        public static final String COLUMN_BLOCKED_LOCATION_NAME = "blocked_location_name";

        public static Uri buildLocationToBeBlockedUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // table AddRewardApplicationTypes lists the how rewards can be applied:
    // either individually("each") or collectively
    public static final class AddRewardApplicationTypes implements BaseColumns {
        public static final String TABLE_NAME = "add_reward_application_types";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String COLUMN_APPLICATION_TYPE = "application_type";

        public static Uri buildAddRewardApplicationTypeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // table AddRewardTypes lists the types of additional rewards that can be
    // gained/lost by a party
    public static final class AddRewardTypes implements BaseColumns {
        public static final String TABLE_NAME = "add_reward_types";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names
        public static final String COLUMN_REWARD_TYPE = "reward_type";

        public static Uri buildAddRewardTypeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // the AddRewards table list the additional rewards that can be gained by
    // a party when a given location is completed
    public static final class AddRewards implements BaseColumns {
        public static final String TABLE_NAME = "add_rewards"; // table's name

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String COLUMN_LOCATION_TO_BE_COMPLETED = "location_to_be_completed";
        public static final String COLUMN_REWARD_TYPE = "reward_type";
        public static final String COLUMN_REWARD_VALUE = "reward_value";
        public static final String COLUMN_REWARD_APPLICATION_TYPE = "reward_applicationS_type";

        public static Uri buildAddRewardUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // the AddPenalties table list the additional rewards that will be lost by
    // a party when a given location is completed
    public static final class AddPenalties implements BaseColumns {
        public static final String TABLE_NAME = "add_penalties"; // table's name

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String COLUMN_LOCATION_TO_BE_COMPLETED = "location_to_be_completed";
        public static final String COLUMN_PENALTY_TYPE = "penalty_type";
        public static final String COLUMN_PENALTY_VALUE = "penalty_value";
        public static final String COLUMN_PENALTY_APPLICATION_TYPE = "penalty_application_type";

        public static Uri buildAddPenaltyUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // the UnlockedLocations table tracks which locations have been unlocked by the party
    // along with the completed location that unlocked the given location
    public static final class UnlockedLocations implements BaseColumns {
        public static final String TABLE_NAME = "unlocked_locations"; // table's name

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String COLUMN_PARTY = "party";
        public static final String COLUMN_UNLOCKED_LOCATION_NUMBER = "unlocked_location_number";
        public static final String COLUMN_UNLOCKING_LOCATION_NUMBER = "unlocking_location_number";
        public static final String COLUMN_UNLOCKING_LOCATION_NAME = "unlocking_location_name";

        public static Uri buildUnlockedLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // the BlockedLocations table tracks which locations have been blocked to the party
    // along with the completed location that blocked the given location
    public static final class BlockedLocations implements BaseColumns {
        public static final String TABLE_NAME = "blocked_locations"; // table's name

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String COLUMN_PARTY = "party";
        public static final String COLUMN_BLOCKED_LOCATION_NUMBER = "blocked_location_number";
        public static final String COLUMN_BLOCKING_LOCATION_NUMBER = "blocking_location_number";

        public static Uri buildBlockedLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // the CompletedLocations table records which locations have been completed by each
    // party along with the timestamp of the Successful Attempt SS
    public static final class CompletedLocations implements BaseColumns {
        public static final String TABLE_NAME = "completed_locations"; // table's name

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String COLUMN_PARTY = "party";
        public static final String COLUMN_LOCATION_NUMBER = "number";
        public static final String COLUMN_COMPLETED_TIMESTAMP = "completed_timestamp";

        public static Uri buildCompletedLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // the LockedLocations table records which locations have not been completed by
    // each party
    public static final class LockedLocations implements BaseColumns {
        public static final String TABLE_NAME = "locked_locations"; // table's name

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String COLUMN_PARTY = "party";
        public static final String COLUMN_LOCATION_NUMBER = "number";

        public static Uri buildLockedLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
