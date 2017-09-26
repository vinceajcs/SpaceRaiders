package com.example.hvincentstephen.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hvincentstephen on 5/3/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "game";
    // Table name
    private static final String TABLE_SCORE = "score";
    // Score Table Columns names
    private static final String KEY_ID_SCORE = "_id";
    private static final String KEY_SCORE = "score_value";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SCORE_TABLE = "CREATE TABLE " + TABLE_SCORE + "("
                + KEY_ID_SCORE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_SCORE + " TEXT" + ")";

        db.execSQL(CREATE_SCORE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Get rid of older table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);

        // Create table(s) again
        onCreate(db);
    }

    // Adding new score
    public void addScore(int score) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_SCORE, score); // score value

        // Inserting balues
        db.insert(TABLE_SCORE, null, values);

        db.close();

    }
    // Getting All Scores
    public String[] getSortedScores() {

        // query arranges the scores from descending order
        String selectQuery = "SELECT  * FROM " + TABLE_SCORE +  " ORDER BY "+ "CAST (" + KEY_SCORE + " AS INTEGER)" + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        int i = 0;

        String[] data = new String[cursor.getCount()];

        while (cursor.moveToNext()) {

            data[i] = cursor.getString(1);
            i++;

        }
        cursor.close();
        db.close();
        // return score array (of type String)
        return data;
    }


}
