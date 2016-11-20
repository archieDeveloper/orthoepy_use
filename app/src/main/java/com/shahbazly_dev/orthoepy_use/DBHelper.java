package com.shahbazly_dev.orthoepy_use;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DBHelper extends SQLiteOpenHelper {

    private static final String WORD = "WORD";
    private static final String STUDY_LEVEL = "STUDY_LEVEL";
    private static final String ERROR_COUNT = "ERROR_COUNT";
    private static final String TABLE_NAME = "words";

    DBHelper(Context context, int dbVersion){
        super(context, "WordsDB.db", null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE `words` (\n" +
                "\t`word_id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`word`\tTEXT,\n" +
                "\t`study_level`\tINTEGER DEFAULT 0,\n" +
                "\t`errors_count`\tINTEGER DEFAULT 0\n" +
                ");");
        Log.d("DATABASE","DB CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS `words`");
        onCreate(db);
    }

}