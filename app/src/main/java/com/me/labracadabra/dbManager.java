package com.me.labracadabra;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
/**
 * Created by tln86 on 2/15/2018.
 */

public class dbManager extends SQLiteOpenHelper {
    private static String DB_NAME = "TAYLOR'SDB";
    private static final int DB_VER = 1;
    private static final String DB_TABLE = "Questions";
    public static final String DB_COLUMN = "Answer";
    public dbManager(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
