package com.shahbazly_dev.orthoepy_use;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class TrainActivity extends AppCompatActivity {
    ArrayList<String> words = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        loadWords();
    }

    protected void loadWords() {
        InputStream inputStream;
        String text = "words.txt";
        byte[] buffer;

        try {
            //Asset в String
            inputStream = getAssets().open(text);
            int size = inputStream.available();
            buffer = new byte[size];

            inputStream.read(buffer);
            inputStream.close();

            String str_data = new String(buffer);

            //String в ArrayList
            Scanner scan = new Scanner(str_data);
            while (scan.hasNext()) {
                words.add(scan.next());
            }
            scan.close();
            String str = Integer.toString(words.size());
            Log.e("Количество слов в базе",str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
