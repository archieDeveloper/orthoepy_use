package com.shahbazly_dev.orthoepy_use;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.io.IOException;

public class TrainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TrainManager trainManager;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        linearLayout = (LinearLayout) findViewById(R.id.liner);

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
                    createButton(word.charAt(i), trueLetter, i);
                }
            } else {
                Log.e("ERROE", "Не нашел правильный символ в слове: \"" + word + "\"");
            }
        } else {
            Log.e("GAMEOVER", "Закончились слова, начни занова :) " + Integer.toString(trainManager.getCountErrors()) + " ошибок");
        }
    }

    public void createButton(char charInButton, String true_letter, final int item) {
        boolean isVowelsChar = trainManager.getWordsModel().isVowelsChar(charInButton);
        final ButtonChar buttonChar = new ButtonChar(this);
        buttonChar.createButton(charInButton, item);
        if (isVowelsChar) {
            View.OnClickListener onClickListener = onClickListenerButton(buttonChar, true_letter);
            buttonChar.createVowelsButton(onClickListener);
        } else {
            buttonChar.createConsonantButton();
        }
        linearLayout.addView(buttonChar);
    }

    public View.OnClickListener onClickListenerButton(final ButtonChar buttonChar, final String true_letter) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = (String) buttonChar.getText();
                if (text.equals(true_letter)) {
                    progressBar = (ProgressBar) findViewById(R.id.progressTrain);
                    progressBar.setProgress(trainManager.getProgress());
                    buttonChar.correctAnswer();
                    final int childСount = linearLayout.getChildCount();
                    for (int i = 0; i < childСount; i++) {
                        ButtonChar curBtn = (ButtonChar) linearLayout.getChildAt(i);
                        curBtn.setOnClickListener(null);
                        if (i == childСount-1) {
                            TranslateAnimation.AnimationListener animationListener = animationListenerButton();
                            curBtn.outAnim(i, animationListener);
                        } else {
                            curBtn.outAnim(i);
                        }
                    }
                } else {
                    buttonChar.inCorrectAnswer();
                    trainManager.addCountError();
                }
            }
        };
    }

    public TranslateAnimation.AnimationListener animationListenerButton() {
        return new TranslateAnimation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linearLayout.removeAllViews();
                startTrain();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }

}
