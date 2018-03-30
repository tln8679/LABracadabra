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
    public static final String TABLE_NAME = "progress";
    public static final String COLUMN_NAME_MAGICIAN = "magician";
    public static final String COLUMN_NAME_SCORE = "score";
    public static final String COLUMN_NAME_ACTIVITY = "activity";
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

}
