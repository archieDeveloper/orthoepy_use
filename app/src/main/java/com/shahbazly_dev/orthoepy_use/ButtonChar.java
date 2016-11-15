package com.shahbazly_dev.orthoepy_use;

import android.content.Context;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import info.hoang8f.widget.FButton;

public class ButtonChar extends FButton {

    public ButtonChar(Context context) {
        super(context);
    }

    public void createButton(char charInButton, int item) {
        this.setLayoutParams(new LinearLayout.LayoutParams(120, 120));
        this.setText(Character.toString(charInButton));
        this.setTextSize(17);
        this.setCornerRadius(100);
        this.setShadowEnabled(false);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) this.getLayoutParams();
        params.setMargins(0, 0, 10, 0); //слева, сверху, справа, снизу
        this.setLayoutParams(params);
        TranslateAnimation translateAnimation = new TranslateAnimation(2000.0f, 0.0f, 0.0f, 0.0f);
        translateAnimation.setStartOffset((25*item)+25/(item+1));
        translateAnimation.setDuration(300);
        translateAnimation.setFillAfter(true);
        this.startAnimation(translateAnimation);
    }

    public void createVowelsButton(View.OnClickListener onClickListener) {
        this.setButtonColor(getResources().getColor(R.color.color_sun_flower));
        this.setShadowColor(getResources().getColor(R.color.color_orange));
        this.setOnClickListener(onClickListener);
    }

    public void createConsonantButton() {
        this.setEnabled(false);
        this.setButtonColor(getResources().getColor(R.color.color_silver));
        this.setShadowColor(getResources().getColor(R.color.color_concrete));
    }

    public void correctAnswer() {
        this.setButtonColor(getResources().getColor(R.color.color_emerald));
        this.setShadowColor(getResources().getColor(R.color.color_nephritis));
    }

    public void outAnim(int i) {
        this.outAnim(i, null);
    }
    public void outAnim(int i, final TranslateAnimation.AnimationListener animationListener) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, -2000.0f, 0.0f, 0.0f);
        translateAnimation.setStartOffset((25*i)+25/(i+1));
        translateAnimation.setDuration(300);
        translateAnimation.setFillAfter(true);

        if (animationListener != null) {
            translateAnimation.setAnimationListener(animationListener);
        }

        this.startAnimation(translateAnimation);
    }

    public void inCorrectAnswer() {
        this.setButtonColor(getResources().getColor(R.color.color_alizarin));
        this.setShadowColor(getResources().getColor(R.color.color_pomegranate));
    }
}
