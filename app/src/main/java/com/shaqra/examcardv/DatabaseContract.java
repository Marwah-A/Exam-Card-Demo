package com.shaqra.examcardv;
import android.provider.BaseColumns;

public final class DatabaseContract {

    // Constructor (to prevent instantiation)
    private DatabaseContract() {}

    // Inner class for the "User" table
    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
    }
}

