package com.me.labracadabra;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import java.util.ArrayList;

/**
 * @author tln86
 * Created by Taylor Noble on 2/15/2018.
 * Filename: dbManager.java
 * Purpose: This program file creates a SQLite Database table and methods used to save the states
 *          the three different magicians throughout the applications lifecycle (i.e. onDestroy,
 *          onUpdate, onPause, etc.)
 * Revised: 4/03/2018
 * Data Structures: Uses strings for column names and an array list to return a list of the scores
 * Reason for existence: Best scores must persist after the application is closed
 * Input: A magician id, score, and activity is put into the insert score method to update the
 *          database. A row is deleted when a new best score is entered.
 * Extensions/Revisions: There no further data that would need to persist, unless for tracking
 *          purposes. We could make a new method and log those results to Amazon Web Services.
 */
public class dbManager extends SQLiteOpenHelper implements BaseColumns {
    private static String DB_NAME = "ScoreKeeper.db";
    // Version must be changed every time updates are made to the table (add a column, etc.)
    private static final int DB_VER = 2;
    private static final String TABLE_NAME = "progress";
    private static final String COLUMN_NAME_MAGICIAN = "magician";
    private static final String COLUMN_NAME_SCORE = "score";
    private static final String COLUMN_NAME_ACTIVITY = "activity";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_MAGICIAN + " TEXT," +
                    COLUMN_NAME_SCORE + " TEXT," +
                    COLUMN_NAME_ACTIVITY + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * Created by Taylor Noble on 2/15/2018.
     * @param context: context of the applications current state (this).
     */
    public dbManager(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    /**
     * Created by Taylor Noble on 2/15/2018.
     * @param user: The magician selected (1, 2, or 3)
     * @param score: The users score (translates to stars. Higher score = less stars).
     * @param activity: The activity within a Location (i.e. Produce aisle if in the grocery store)
     *
     *  Called after user completes a game/module and does better than previously.
     */
    public void insertNewScore (String user, int score, String activity ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbManager.COLUMN_NAME_MAGICIAN, user);
        values.put(dbManager.COLUMN_NAME_SCORE, score);
        values.put(dbManager.COLUMN_NAME_ACTIVITY, activity);
        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    /**
     * Created by Taylor Noble on 2/15/2018.
     * @param magician: The current player (1, 2, or 3)
     * @param activity: The activity within a Location (i.e. Produce aisle if in the grocery store)
     *
     * Called after user completes a game/module and does better than previously
     */
    public void deleteScore (String magician, String activity){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"magician = ? AND activity = ?",
                new String[] {magician, activity}); //  We delete an old best score when beat
        db.close();
    }

    /**
     * Created by Taylor Noble on 2/15/2018.
     * @param db: The writable database
     */
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * Created by Taylor Noble on 2/15/2018.
     * @param db: The writable database
     * @param oldVersion: Previous version of the database
     * @param newVersion: Version after update (current)
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * Created by Taylor Noble on 2/15/2018.
     * @param db: The writable database
     * @param oldVersion: Previous version of the database
     * @param newVersion: Version after update (current)
     */
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * Created by Taylor Noble on 2/15/2018.
     * @param savedState: The magician string (1, 2, or 3)
     * @return a list of the best scores
     *
     * Called when a locations screen is launched (grocery store, hardware store, etc.)
     * Updates the best score for each game/learning module
     */
    public ArrayList<String> getBestScore(String savedState){
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        ArrayList<String> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                dbManager._ID,
                dbManager.COLUMN_NAME_MAGICIAN,
                dbManager.COLUMN_NAME_SCORE,
                dbManager.COLUMN_NAME_ACTIVITY
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = dbManager.COLUMN_NAME_MAGICIAN + " = ?";
        String[] selectionArgs = { savedState };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                dbManager.COLUMN_NAME_SCORE + " DESC";

        Cursor cursor = db.query(
                TABLE_NAME,     // The table to query
                projection,     // The array of columns to return (pass null to get all)
                selection,      // The columns for the WHERE clause
                selectionArgs,      // The values for the WHERE clause
                null,       // don't group the rows
                null,       // don't filter by row groups
                sortOrder       // The sort order
        );

        int index = cursor.getColumnIndex(COLUMN_NAME_SCORE);
        while(cursor.moveToNext()){
            items.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return items;
    }

}
