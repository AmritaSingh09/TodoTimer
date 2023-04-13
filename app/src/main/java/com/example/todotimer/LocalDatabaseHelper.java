package com.example.todotimer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabaseHelper extends SQLiteOpenHelper {

    Context context;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LocalDatabase.db";
    public static final String SQL_CREATE_ENTITIES = "CREATE TABLE "+DATABASE_NAME+" ("+TodoModal.class.getName();//TODO UPDATE HERE
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DATABASE_NAME;


    public LocalDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTITIES);//SQL_CREATE_ENTITIES = create table database_name(id type,title type,desc type);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
