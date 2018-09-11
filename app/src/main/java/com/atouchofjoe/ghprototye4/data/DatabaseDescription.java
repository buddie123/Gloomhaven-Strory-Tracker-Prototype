package com.atouchofjoe.ghprototye4.data;


import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseDescription {
    // ContentProvider's name: typically the package name
    public static final String AUTHORITY =
            "com.atouchofjoe.ghprototye4.location.data";

    // base URI used to interact with the ContentProvider
    private static final Uri BASE_CONTENT_URI =
            Uri.parse("content://" + AUTHORITY);



    public static final class UnlockingLocations implements BaseColumns {
        public static final String TABLE_NAME = "unlocking_locations";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
        public static final String COLUMN_PARTY = "party";
        public static final String COLUMN_UNLOCKED_LOCATION = "unlocked_location";
        public static final String COLUMN_UNLOCKING_LOCATION = "unlocking_location";

        public static Uri buildUnlockingLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // nested class defines contents table
    public static final class Attempts implements BaseColumns {
        public static final String TABLE_NAME = "attempts"; // table's name

        // Uri for the cotacts table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for contacts table's columns
        public static final String COLUMN_PARTY = "party";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_SUCCESSFUL = "successful";

        // creates a URI for a specific contact
        public static Uri buildAttemptUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // nested class defines contents table
    public static final class Participants implements BaseColumns {
        public static final String TABLE_NAME = "participants"; // table's name

        // Uri for the cotacts table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for contacts table's columns
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_CHARACTER = "character";

        // creates a URI for a specific contact
        public static Uri buildParticipantUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // nested class defines contents table
    public static final class NonParticipants implements BaseColumns {
        public static final String TABLE_NAME = "non_participants"; // table's name

        // Uri for the cotacts table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for contacts table's columns
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_CHARACTER = "character";

        // creates a URI for a specific contact
        public static Uri buildNonParticipantUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // nested class defines contents table
    public static final class Parties implements BaseColumns {
        public static final String TABLE_NAME = "parties"; // table's name

        // Uri for the cotacts table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for contacts table's columns
        public static final String COLUMN_NAME = "name";

        // creates a URI for a specific contact
        public static Uri buildPartyUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // nested class defines contents table
    public static final class Characters implements BaseColumns {
        public static final String TABLE_NAME = "characters"; // table's name

        // Uri for the cotacts table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for contacts table's columns
        public static final String COLUMN_PARTY = "party";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CLASS = "class";

        // creates a URI for a specific contact
        public static Uri buildCharacterUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // nested class defines contents table
    public static final class Locations implements BaseColumns {
        public static final String TABLE_NAME = "locations"; // table's name

        // Uri for the cotacts table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for contacts table's columns
        public static final String COLUMN_NUMBER = "number";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TEASER = "teaser";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_CONCLUSION = "conclusion";

        // creates a URI for a specific contact
        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // nested class defines contents table
    public static final class GlobalAchievements implements BaseColumns {
        public static final String TABLE_NAME = "global_achievements"; // table's name

        // Uri for the contacts table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for contacts table's columns
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ACHIEVEMENT_REPLACED = "achievement_replaced";
        public static final String COLUMN_ACHIEVEMENT_MAX_COUNT = "achievement_max_count";

        // creates a URI for a specific contact
        public static Uri buildGlobalAchievementUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // nested class defines contents table
    public static final class GlobalAchievementsGained implements BaseColumns {
        public static final String TABLE_NAME = "global_achievements_gained"; // table's name

        // Uri for the contacts table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for contacts table's columns
        public static final String COLUMN_COMPLETED_LOCATION_NUMBER = "completed_location_number";
        public static final String COLUMN_GLOBAL_ACHIEVEMENT = "global_achievement";

        // creates a URI for a specific contact
        public static Uri buildGlobalAchievementGainedUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // nested class defines contents table
    public static final class GlobalAchievementsLost implements BaseColumns {
        public static final String TABLE_NAME = "global_achievements_lost"; // table's name

        // Uri for the contacts table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for contacts table's columns
        public static final String COLUMN_COMPLETED_LOCATION_NUMBER = "completed_location_number";
        public static final String COLUMN_GLOBAL_ACHIEVEMENT = "global_achievement";

        // creates a URI for a specific contact
        public static Uri buildGlobalAchievementLostUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // nested class defines contents table
    public static final class PartyAchievements implements BaseColumns {
        public static final String TABLE_NAME = "party_achievements"; // table's name

        // Uri for the contacts table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for contacts table's columns
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ACHIEVEMENT_REPLACED = "achievement_replaced";

        // creates a URI for a specific contact
        public static Uri buildPartyAchievementUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // nested class defines contents table
    public static final class PartyAchievementsGained implements BaseColumns {
        public static final String TABLE_NAME = "party_achievements_gained"; // table's name

        // Uri for the contacts table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for contacts table's columns

        public static final String COLUMN_COMPLETED_LOCATION_NUMBER = "completed_location_number";
        public static final String COLUMN_PARTY_ACHIEVEMENT = "party_achievement";

        // creates a URI for a specific contact
        public static Uri buildPartyAchievementGainedUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // nested class defines contents table
    public static final class PartyAchievementsLost implements BaseColumns {
        public static final String TABLE_NAME = "party_achievements_lost"; // table's name

        // Uri for the contacts table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for contacts table's columns
        public static final String COLUMN_COMPLETED_LOCATION_NUMBER = "completed_location_number";
        public static final String COLUMN_PARTY_ACHIEVEMENT = "party_achievement";

        // creates a URI for a specific contact
        public static Uri buildPartyAchievementLostUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // nested class defines contents table
    public static final class LocationsUnlocked implements BaseColumns {
        public static final String TABLE_NAME = "locations_unlocked"; // table's name

        // Uri for the contacts table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for contacts table's columns
        public static final String COLUMN_COMPLETED_LOCATION_NUMBER = "completed_location_number";
        public static final String COLUMN_UNLOCKED_LOCATION_NUMBER = "unlocked_location_number";

        // creates a URI for a specific contact
        public static Uri buildUnlockedLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // nested class defines contents table
    public static final class LocationsBlocked implements BaseColumns {
        public static final String TABLE_NAME = "locations_blocked"; // table's name

        // Uri for the contacts table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for contacts table's columns
        public static final String COLUMN_COMPLETED_LOCATION_NUMBER = "completed_location_number";
        public static final String COLUMN_BLOCKED_LOCATION_NUMBER = "blocked_location_number";

        // creates a URI for a specific contact
        public static Uri buildBlockedLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class AppliedTypes implements BaseColumns {
        public static final String TABLE_NAME = "applied_types";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names
        public static final String COLUMN_TYPE = "type";

        public static Uri buildAppliedTypeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }


    public static final class AddRewardTypes implements BaseColumns {
        public static final String TABLE_NAME = "add_reward_types";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names
        public static final String COLUMN_TYPE = "type";

        public static Uri buildAddRewardTypeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // nested class defines contents table
    public static final class AddRewardsGained implements BaseColumns {
        public static final String TABLE_NAME = "add_rewards_gained"; // table's name

        // Uri for the contacts table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for contacts table's columns
        public static final String COLUMN_COMPLETED_LOCATION_NUMBER = "completed_location_number";
        public static final String COLUMN_REWARD_TYPE = "reward_type";
        public static final String COLUMN_REWARD_VALUE = "reward_value";
        public static final String COLUMN_REWARD_APPLIED_TYPE = "reward_applied_type";

        // creates a URI for a specific contact
        public static Uri buildAddRewardUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // nested class defines contents table
    public static final class AddPenaltiesLost implements BaseColumns {
        public static final String TABLE_NAME = "add_penalties_lost"; // table's name

        // Uri for the contacts table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for contacts table's columns
        public static final String COLUMN_COMPLETED_LOCATION_NUMBER = "completed_location_number";
        public static final String COLUMN_PENALTY_TYPE = "penalty_type";
        public static final String COLUMN_PENALTY_VALUE = "penalty_value";
        public static final String COLUMN_PENALTY_APPLIED_TYPE = "penalty_applied_type";

        // creates a URI for a specific contact
        public static Uri buildAddPenaltyUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);

        }
    }

    // nested class defines contents table
    public static final class UnlockedLocations implements BaseColumns {
        public static final String TABLE_NAME = "unlocked_locations"; // table's name

        // Uri for the contacts table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for contacts table's columns
        public static final String COLUMN_PARTY = "party";
        public static final String COLUMN_UNLOCKED_LOCATION_NUMBER = "unlocked_location_number";
        public static final String COlUMN_UNLOCKING_LOCATION_NUMBER = "unlocking_location_number";

        // creates a URI for a specific contact
        public static Uri buildUnlockedLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // nested class defines contents table
    public static final class BlockedLocations implements BaseColumns {
        public static final String TABLE_NAME = "blocked_locations"; // table's name

        // Uri for the contacts table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for contacts table's columns
        public static final String COLUMN_PARTY = "party";
        public static final String COLUMN_BLOCKED_LOCATION_NUMBER = "blocked_location_number";
        public static final String COlUMN_BLOCKING_LOCATION_NAME = "blocking_location_number";

        // creates a URI for a specific contact
        public static Uri buildBlockedLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // nested class defines contents table
    public static final class CompletedLocations implements BaseColumns {
        public static final String TABLE_NAME = "completed_locations"; // table's name

        // Uri for the contacts table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for contacts table's columns
        public static final String COLUMN_PARTY = "party";
        public static final String COLUMN_LOCATION_NUMBER = "number";
        public static final String COLUMN_COMPLETED_TIMESTAMP = "completed_timestamp";

        // creates a URI for a specific contact
        public static Uri buildCompletedLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // nested class defines contents table
    public static final class LockedLocations implements BaseColumns {
        public static final String TABLE_NAME = "locked_locations"; // table's name

        // Uri for the contacts table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for contacts table's columns
        public static final String COLUMN_PARTY = "party";
        public static final String COLUMN_LOCATION_NUMBER = "number";

        // creates a URI for a specific contact
        public static Uri buildLockedLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
