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
    WordsModel wordsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        try {
            wordsModel = new WordsModel(getAssets().open("words.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        startTrain();
    }

    protected void startTrain() {
        String word = wordsModel.getRandomWord();
        String trueLetter = wordsModel.getTrueLatter(word);

        if (trueLetter != null) {
            int wordLength = word.length();
            for (int i = 0; i < wordLength; i++) {
                createButtons(word.charAt(i), trueLetter);
            }
        } else {
            Log.e("ERROE", "Не нашел правильный символ в слове: \"" + word + "\"");
        }
    }

    public void createButtons(char charInButton, final String true_letter) {
        //Создаем кнопки и помещаем в них буквы
        final FButton myButton = new FButton(this);
        myButton.setLayoutParams(new LinearLayout.LayoutParams(120, 120));
        myButton.setText(Character.toString(charInButton));
        myButton.setTextSize(17);
        myButton.setCornerRadius(100);
        myButton.setShadowEnabled(false);
        myButton.setButtonColor(getResources().getColor(R.color.color_sun_flower));
        myButton.setShadowColor(getResources().getColor(R.color.color_orange));
        final LinearLayout finalLinearLayout = linearLayout;
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = (String) myButton.getText();
                if (text.equals(true_letter)) {
                    myButton.setButtonColor(getResources().getColor(R.color.color_emerald));
                    myButton.setShadowColor(getResources().getColor(R.color.color_nephritis));
                    finalLinearLayout.removeAllViews();
                    startTrain();
                } else {
                    myButton.setButtonColor(getResources().getColor(R.color.color_alizarin));
                    myButton.setShadowColor(getResources().getColor(R.color.color_pomegranate));
                }
            }
        });
        linearLayout = (LinearLayout) findViewById(R.id.liner);
        linearLayout.addView(myButton);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) myButton.getLayoutParams();
        params.setMargins(0, 0, 10, 0); //слева, сверху, справа, снизу
        myButton.setLayoutParams(params);
    }

}
