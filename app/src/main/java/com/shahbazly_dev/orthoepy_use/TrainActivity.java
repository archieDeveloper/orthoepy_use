package com.shahbazly_dev.orthoepy_use;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import info.hoang8f.widget.FButton;


public class TrainActivity extends AppCompatActivity {
    ArrayList<String> words = new ArrayList<String>();
    String true_letter;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        loadWords();
        startTrain();
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

            //String в ArrayList
            Scanner scan = new Scanner(str_data);
            while (scan.hasNext()) {
                words.add(scan.next());
            }
            scan.close();

            String str = Integer.toString(words.size());
            Log.d("Количество слов в базе",str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void startTrain(){
        // Инициализируем генератор случайного числа
        Random rnd = new Random(System.currentTimeMillis());
        // Получаем случайное число в диапазоне от min до max (включительно)
        int max = words.size();
        int min = 0;
        int random = min + rnd.nextInt(max - min + 1);

        String word = words.get(random); // рандомное число

        char[] word_by_letter = word.toCharArray(); // разбиваем слово на буквы
        for(char x:word_by_letter) {
            if (Character.isUpperCase(x)) { // x - правильный ответ(буква)
                true_letter = Character.toString(x);
                Log.e("WORD", word);
                Log.e("CHAR", String.valueOf(x));
                break;
            }
        }

        int word_length = word.length();
        for(int i = 0;i< word_length;++i){
            //Создаем кнопки и помещаем в них буквы
            final FButton myButton = new FButton(this);
            myButton.setLayoutParams(new LinearLayout.LayoutParams(120, 120));
            myButton.setText(Character.toString(word_by_letter[i]));
            myButton.setTextSize(17);
            myButton.setCornerRadius(100);
            myButton.setShadowEnabled(false);
            myButton.setButtonColor(getResources().getColor(R.color.color_sun_flower));
            myButton.setShadowColor(getResources().getColor(R.color.color_orange));
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text  = (String) myButton.getText();
                    if(text.equals(true_letter)){
                        myButton.setButtonColor(getResources().getColor(R.color.color_emerald));
                        myButton.setShadowColor(getResources().getColor(R.color.color_nephritis));
                        linearLayout.removeAllViews();
                        startTrain();
                    }
                    else{
                        myButton.setButtonColor(getResources().getColor(R.color.color_alizarin));
                        myButton.setShadowColor(getResources().getColor(R.color.color_pomegranate));
                    }
                }
            });
            linearLayout = (LinearLayout)findViewById(R.id.liner);
            linearLayout.addView(myButton);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) myButton.getLayoutParams();
            params.setMargins(0, 0, 10, 0); //слева, сверху, справа, снизу
            myButton.setLayoutParams(params);
        }
    }
}
