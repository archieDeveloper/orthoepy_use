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

import co.mobiwise.library.ProgressLayout;

public class TrainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    ProgressLayout progressLayout;
    TrainManager trainManager;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        linearLayout = (LinearLayout) findViewById(R.id.liner);
        progressBar = (ProgressBar) findViewById(R.id.progressTrain);
        progressLayout = (ProgressLayout) findViewById(R.id.progressLayout);

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
            progressBar.setProgress(trainManager.getProgress());
            progressLayout.setCurrentProgress(trainManager.getProgress());
            String word = trainManager.getNextWord();
            String trueLetter = trainManager.getTrueLetter();
            trainWord(word, trueLetter);
        } else {
            this.recreate();
            Log.e("GAMEOVER", "Закончились слова, начни занова :) " + Integer.toString(trainManager.getCountErrors()) + " ошибок");
        }
    }

    public void trainWord(String word, String trueLetter) {
        if (trueLetter != null) {
            int wordLength = word.length();
            for (int i = 0; i < wordLength; i++) {
                boolean isLast = i == wordLength - 1;
                createButton(word.charAt(i), trueLetter, i, isLast);
            }
        } else {
            Log.e("ERROR", "Не нашел правильный символ в слове: \"" + word + "\"");
        }
    }

    public void createButton(char charInButton, String true_letter, int item, boolean isLast) {
        ButtonChar buttonChar = new ButtonChar(this , charInButton);
        if (isLast) {
            buttonChar.inAnim(item, animationListenerInButton());
        } else {
            buttonChar.inAnim(item);
        }
        boolean isVowelsChar = trainManager.getWordsModel().isVowelsChar(charInButton);
        if (isVowelsChar) {
            boolean isCorrectButton = true_letter.equals(Character.toString(charInButton));
            View.OnClickListener onClickListener = isCorrectButton
                    ? onClickListenerButtonCorrect()
                    : onClickListenerButtonInCorrect();
            buttonChar.createVowelsButton(onClickListener);
        } else {
            buttonChar.createConsonantButton();
        }
        linearLayout.addView(buttonChar);
    }

    public View.OnClickListener onClickListenerButtonCorrect() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonChar buttonChar = (ButtonChar) v;
                buttonChar.correctAnswer();
                int childCount = linearLayout.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    ButtonChar currentButtonChar = (ButtonChar) linearLayout.getChildAt(i);
                    currentButtonChar.disableClickLister();
                    if (i == childCount-1) {
                        currentButtonChar.outAnim(i, animationListenerOutButton());
                    } else {
                        currentButtonChar.outAnim(i);
                    }
                }
            }
        };
    }

    public View.OnClickListener onClickListenerButtonInCorrect() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonChar buttonChar = (ButtonChar) v;
                buttonChar.inCorrectAnswer();
                trainManager.addCountError();
            }
        };
    }

    public TranslateAnimation.AnimationListener animationListenerInButton() {
        return new TranslateAnimation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                int childCount = linearLayout.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    ButtonChar currentButtonChar = (ButtonChar) linearLayout.getChildAt(i);
                    currentButtonChar.enableClickLister();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }

    public TranslateAnimation.AnimationListener animationListenerOutButton() {
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
