package com.shahbazly_dev.orthoepy_use;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.io.IOException;

import info.hoang8f.widget.FButton;


public class TrainActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    TrainManager trainManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        try {
            WordsModel wordsModel = new WordsModel(getAssets().open("words.txt"));
            trainManager = new TrainManager(wordsModel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        startTrain();
    }

    protected void startTrain() {
        if (trainManager.hasNextWord()) {
            String word = trainManager.getNextWord();
            String trueLetter = trainManager.getTrueLetter();

            if (trueLetter != null) {
                int wordLength = word.length();
                for (int i = 0; i < wordLength; i++) {
                    createButtons(word.charAt(i), trueLetter);
                }
            } else {
                Log.e("ERROE", "Не нашел правильный символ в слове: \"" + word + "\"");
            }
        } else {
            Log.e("GAMEOVER", "Закончились слова, начни занова :) " + Integer.toString(trainManager.getCountErrors()) + " ошибок");
        }
    }

    public void createButtons(char charInButton, final String true_letter) {
        boolean isVowelsChar = trainManager.getWordsModel().isVowelsChar(charInButton);
        //Создаем кнопки и помещаем в них буквы
        final FButton myButton = new FButton(this);
        myButton.setLayoutParams(new LinearLayout.LayoutParams(120, 120));
        myButton.setText(Character.toString(charInButton));
        myButton.setTextSize(17);
        myButton.setCornerRadius(100);
        myButton.setShadowEnabled(false);
        if (isVowelsChar) {
            myButton.setButtonColor(getResources().getColor(R.color.color_sun_flower));
            myButton.setShadowColor(getResources().getColor(R.color.color_orange));
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = (String) myButton.getText();
                    if (text.equals(true_letter)) {
                        myButton.setButtonColor(getResources().getColor(R.color.color_emerald));
                        myButton.setShadowColor(getResources().getColor(R.color.color_nephritis));
                        linearLayout.removeAllViews();
                        startTrain();
                    } else {
                        trainManager.addCountError();
                        myButton.setButtonColor(getResources().getColor(R.color.color_alizarin));
                        myButton.setShadowColor(getResources().getColor(R.color.color_pomegranate));
                    }
                }
            });
        } else {
            myButton.setEnabled(false);
            myButton.setButtonColor(getResources().getColor(R.color.color_silver));
            myButton.setShadowColor(getResources().getColor(R.color.color_concrete));
        }
        linearLayout = (LinearLayout) findViewById(R.id.liner);
        linearLayout.addView(myButton);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) myButton.getLayoutParams();
        params.setMargins(0, 0, 10, 0); //слева, сверху, справа, снизу
        myButton.setLayoutParams(params);
    }

}
