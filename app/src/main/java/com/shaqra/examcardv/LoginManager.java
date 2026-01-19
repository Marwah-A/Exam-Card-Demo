package com.shaqra.examcardv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginManager {

    private DatabaseHelper dbHelper;

    public LoginManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }


    public boolean login(String username, String password) {

        //register("marwah","12345");
        //register("nouf","12345");
        register("123456","12345");
        register("789012","12345");
        register("112233","12345");
        register("345678","12345");
        register("901234","12345");
        register("567890","12345");
        register("456789","12345");

        register("778899","12345");
        register("445566","12345");

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                "id"
        };
        String selection = "username = ? AND password = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(
                "Users",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean loginSuccess = cursor.moveToFirst();

        cursor.close();
        db.close();

        return loginSuccess;
    }

    public void register(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("remember_me", 0);

        long newRowId = db.insert("Users", null, contentValues);

        db.close();
    }
}
