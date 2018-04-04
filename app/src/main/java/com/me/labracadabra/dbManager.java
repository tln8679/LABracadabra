package com.me.labracadabra;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
/**
 * Created by tln86 on 2/15/2018.
 */

public class dbManager extends SQLiteOpenHelper implements BaseColumns {
    private static String DB_NAME = "ScoreKeeper.db";
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


    public dbManager(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public void insertNewScore (String user, int score, String activity ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbManager.COLUMN_NAME_MAGICIAN, user);
        values.put(dbManager.COLUMN_NAME_SCORE, score);
        values.put(dbManager.COLUMN_NAME_ACTIVITY, activity);
        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public void deleteScore (String magician, String activity){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"magician = ? AND activity = ?",
                new String[] {magician, activity});
        db.close();
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public ArrayList<String> getTaskList(String savedState){
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
