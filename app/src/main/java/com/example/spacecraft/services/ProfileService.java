package com.example.spacecraft.services;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.spacecraft.database.DatabaseHelper;
import com.example.spacecraft.models.app.Profile;

import java.util.ArrayList;
import java.util.List;
public class ProfileService {
    private final DatabaseHelper dbHelper;
    private final SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "profile_prefs";
    private static final String KEY_PROFILE_ID = "profile_id";

    public ProfileService(Context context) {
        dbHelper = new DatabaseHelper(context);
        sharedPreferences = context.getSharedPreferences("profile", Context.MODE_PRIVATE);
    }

    public int addProfile(Profile profile) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, profile.getUsername());
        values.put(DatabaseHelper.COLUMN_HIGHEST_SCORE, profile.getHighestScore());
        int profileId = (int) db.insert(DatabaseHelper.TABLE_NAME, null, values);
        db.close();
        return profileId;
    }

    public Profile getProfileById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Profile profile = null;
        String[] columns = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_HIGHEST_SCORE};
        String selection = DatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        try (Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, columns, selection, selectionArgs, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                profile = new Profile(
                        cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_HIGHEST_SCORE))
                );
            }
        }
        return profile;
    }

    public List<Profile> getAllProfiles() {
        List<Profile> profileList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Profile profile = new Profile(
                        cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_HIGHEST_SCORE))
                );
                profileList.add(profile);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return profileList;
    }

    public void updateProfileScore(int newScore) {
        int profileId = getProfileIdInPrefs();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {DatabaseHelper.COLUMN_HIGHEST_SCORE};
        String selection = DatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(profileId)};

        try (Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, columns, selection, selectionArgs, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int oldScore = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_HIGHEST_SCORE));
                if (newScore > oldScore) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseHelper.COLUMN_HIGHEST_SCORE, newScore);
                    db.update(DatabaseHelper.TABLE_NAME, values, DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(profileId)});
                }
            }
        }
    }

    public void deleteProfile(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void saveProfileIdToPrefs(int profileId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_PROFILE_ID, profileId);
        editor.apply();
    }

    public int getProfileIdInPrefs() {
        return sharedPreferences.getInt(KEY_PROFILE_ID, -1);
    }

    public void clearProfileIdInPrefs() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_PROFILE_ID);
        editor.apply();
    }


    public Profile getProfileHaveHighestScores() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Profile profile = null;
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " ORDER BY " + DatabaseHelper.COLUMN_HIGHEST_SCORE + " DESC LIMIT 1";

        try (Cursor cursor = db.rawQuery(selectQuery, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                profile = new Profile(
                        cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_HIGHEST_SCORE))
                );
            }
        }
        return profile;
    }

}
