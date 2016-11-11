package com.shahbazly_dev.orthoepy_use;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class TrainActivity extends AppCompatActivity {
    ArrayList<String> words;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        textView = (TextView)findViewById(R.id.textView);

        //а это кажется попытка поймать исключение, но я не уверен, короче тоже не знаю, что это
        try {
            loadWords();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // throws FileNotFoundException - не знаю, что это, но без этого не компилтся XD
    protected void loadWords() throws FileNotFoundException {
        // загрузка слов из words.txt в ArrayList
        Scanner scan = new Scanner(new File("/src/main/res/raw/words.txt"));
        words = new ArrayList<String>();
        while (scan.hasNextLine()){
            words.add(scan.next());
        }
        scan.close();

        //Попытка проверить, загрузились ли слова в аррей, посредством отображения их в textView
        String string = " ";
        for (int i = 0; i < words.size(); i++) {
            string = string + words.get(i) + " ";
        }
        textView.setText(string);

    }
}
