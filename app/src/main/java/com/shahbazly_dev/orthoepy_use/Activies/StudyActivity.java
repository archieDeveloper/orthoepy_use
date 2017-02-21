package com.shahbazly_dev.orthoepy_use.Activies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.shahbazly_dev.orthoepy_use.ButtonChar;
import com.shahbazly_dev.orthoepy_use.Managers.StudyManager;
import com.shahbazly_dev.orthoepy_use.Models.WordsModel;
import com.shahbazly_dev.orthoepy_use.R;

import co.mobiwise.library.ProgressLayout;

public class StudyActivity extends AppCompatActivity {

    ProgressLayout progressLayout;
    StudyManager studyManager;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        linearLayout = (LinearLayout) findViewById(R.id.liner);
        progressLayout = (ProgressLayout) findViewById(R.id.progressLayout);

        WordsModel wordsModel = new WordsModel(getApplicationContext());
        studyManager = new StudyManager(wordsModel);

        startTrain();
    }

    protected void startTrain() {
        if (studyManager.hasNextWord()) {
            progressLayout.setCurrentProgress(studyManager.getProgress());
            String word = studyManager.getNextWord();
            String trueLetter = studyManager.getTrueLetter();
            trainWord(word, trueLetter);
        } else {
            Intent intent = new Intent(StudyActivity.this, ResultsActivity.class);
            startActivity(intent);
        }
    }

    public void trainWord(String word, String trueLetter) {
        if (trueLetter != null) {
            int wordLength = word.length();
            for (int i = 0; i < wordLength; i++) {
                boolean isLast = i == wordLength - 1;
                createButton(word.charAt(i), trueLetter, i, isLast, word);
            }
        } else {
            Log.e("ERROR", "Не нашел правильный символ в слове: \"" + word + "\"");
        }
    }

    public void createButton(char charInButton, String true_letter, int item, boolean isLast,String word) {
        ButtonChar buttonChar = new ButtonChar(this , charInButton);
        if (isLast) {
            buttonChar.inAnim(item, animationListenerInButton());
        } else {
            buttonChar.inAnim(item);
        }
        boolean isVowelsChar = studyManager.getWordsModel().isVowelsChar(charInButton);
        if (isVowelsChar) {
            boolean isCorrectButton = true_letter.equals(Character.toString(charInButton));
            View.OnClickListener onClickListener = isCorrectButton
                    ? onClickListenerButtonCorrect(word)
                    : onClickListenerButtonInCorrect(word);
            buttonChar.createVowelsButton(onClickListener);
        } else {
            buttonChar.createConsonantButton();
        }
        linearLayout.addView(buttonChar);
    }

    public View.OnClickListener onClickListenerButtonCorrect(final String word) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studyManager.increaseLevel(word);
                studyManager.setStudied(word);
                ButtonChar buttonChar = (ButtonChar) v;
                progressLayout.setCurrentProgress(studyManager.getProgress());
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

    public View.OnClickListener onClickListenerButtonInCorrect(final String word) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonChar buttonChar = (ButtonChar) v;
                buttonChar.inCorrectAnswer();
                studyManager.addMistake(word);
                studyManager.reduceLevel(word);
                studyManager.setStudied(word);
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
