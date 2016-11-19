package com.shahbazly_dev.orthoepy_use;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String WORD = "WORD";
    private static final String STUDY_LEVEL = "STUDY_LEVEL";
    private static final String ERROR_COUNT = "ERROR_COUNT";
    private static final String TABLE_NAME = "words";

    public DBHelper(Context context, int dbVersion){
        super(context, "WordsDB.db", null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE words (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " WORD TEXT," +
                " STUDY_LEVEL INTEGER," +
                " ERROR_COUNT INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS words");
        onCreate(db);
    }
}