package com.shahbazly_dev.orthoepy_use.Database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class DBHelper extends SQLiteOpenHelper {

    private AssetManager manager;

    public DBHelper(Context context, int dbVersion){
        super(context, "WordsDB.db", null, dbVersion);
        manager = context.getAssets();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE `words` (\n" +
                "\t`word_id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`word`\tTEXT,\n" +
                "\t`study_level`\tINTEGER DEFAULT 0,\n" +
                "\t`mistakes_count`\tINTEGER DEFAULT 0,\n" +
                "\t`is_studied`\tbit NULL DEFAULT 0\n" +
                ");");
        Log.d("DATABASE","DB CREATED");

        InputStream inputStream;
        String text = "words.txt";
        byte[] buffer;

        try {
            //перевод содержания assets/words.txt в String
            inputStream = manager.open(text);
            int size = inputStream.available();
            buffer = new byte[size];

            inputStream.read(buffer);
            inputStream.close();

            //Строка со словами из words.txt
            String str_data = new String(buffer);

            //Перенос слов в базу данных
            Scanner scan = new Scanner(str_data);
            while (scan.hasNext()) {
                String word = scan.next();
                database.execSQL("INSERT INTO words (word) VALUES ('"+word+"');");
                Log.d("DATABASE","Word: "+ word);
            }
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        database.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS `words`");
        onCreate(db);
    }
}