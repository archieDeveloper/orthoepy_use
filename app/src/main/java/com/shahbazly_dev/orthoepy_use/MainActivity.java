package com.shahbazly_dev.orthoepy_use;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.stephentuso.welcome.WelcomeHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    WelcomeHelper welcomeScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeScreen = new WelcomeHelper(this, WelcomeActivity.class);
        welcomeScreen.show(savedInstanceState);

        Button button = (Button)findViewById(R.id.btn_start_train);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrainActivity.class);
                startActivity(intent);
            }
        });
        loadWords();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        welcomeScreen.onSaveInstanceState(outState);
    }
    protected void loadWords() {
        InputStream inputStream;
        String text = "words.txt";
        byte[] buffer;

        try {
            //перевод содержания assets/words.txt в String
            inputStream = getAssets().open(text);
            int size = inputStream.available();
            buffer = new byte[size];

            inputStream.read(buffer);
            inputStream.close();

            //Строка со словами из words.txt
            String str_data = new String(buffer);

            //Перенос слов в базу данных
            DBHelper dbHelper = new DBHelper(getApplicationContext(),1);
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            Scanner scan = new Scanner(str_data);
            while (scan.hasNext()) {
                String word = scan.next();
                cv.put("word", word);
                database.insert("words", null, cv);
            }
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
