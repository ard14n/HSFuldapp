package com.abmedia.hsfuldapp.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dini on 17.06.2017.
 */

public class ConnectionSQL extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 2;
    private static final String DICTIONARY_TABLE_NAME = "";
    private static final String DICTIONARY_TABLE_CREATE =
            "CREATE TABLE " + DICTIONARY_TABLE_NAME + " (" +
                    "KEYWORD" + " TEXT, " +
                    "KEY_DEFINITION" + " TEXT);";

    ConnectionSQL(Context context) {
        super(context, "Datenbankname", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DICTIONARY_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void createDBMensa(){

    }

    public void addMensa(){

    }

    public void createDBNoten(){

    }

    public void addNoten(){

    }

    public void createDBSys2(){

    }

    public void addNews(){

    }

}
