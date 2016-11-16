package com.shahbazly_dev.orthoepy_use;

import android.content.Context;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import info.hoang8f.widget.FButton;

public class ButtonChar extends FButton {

    private View.OnClickListener clickLister;

    public ButtonChar(Context context, char charInButton) {
        super(context);
        createButton(charInButton);
    }

    public void createButton(char charInButton) {
        this.setLayoutParams(new LinearLayout.LayoutParams(120, 120));
        this.setText(Character.toString(charInButton));
        this.setTextSize(17);
        this.setCornerRadius(100);
        this.setShadowEnabled(false);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) this.getLayoutParams();
        params.setMargins(0, 0, 10, 0); //слева, сверху, справа, снизу
        this.setLayoutParams(params);
    }

    public void createVowelsButton(View.OnClickListener clickListener) {
        this.clickLister = clickListener;
        this.setEnabled(true);
        this.setButtonColor(getResources().getColor(R.color.color_sun_flower));
        this.setShadowColor(getResources().getColor(R.color.color_orange));
    }

    public void createConsonantButton() {
        this.clickLister = null;
        this.setEnabled(false);
        this.setButtonColor(getResources().getColor(R.color.color_silver));
        this.setShadowColor(getResources().getColor(R.color.color_concrete));
    }

    public void correctAnswer() {
        this.setButtonColor(getResources().getColor(R.color.color_emerald));
        this.setShadowColor(getResources().getColor(R.color.color_nephritis));
    }

    public void inAnim(int i) {
        this.inAnim(i, null);
    }
    public void inAnim(int i, TranslateAnimation.AnimationListener animationListener) {
        TranslateAnimation translateAnimation = new TranslateAnimation(2000.0f, 0.0f, 0.0f, 0.0f);
        translateAnimation.setStartOffset((25*i)+25/(i+1));
        translateAnimation.setDuration(300);
        translateAnimation.setFillAfter(true);
        if (animationListener != null) {
            translateAnimation.setAnimationListener(animationListener);
        }
        this.startAnimation(translateAnimation);
    }

    public void outAnim(int i) {
        this.outAnim(i, null);
    }
    public void outAnim(int i, TranslateAnimation.AnimationListener animationListener) {
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

    public void enableClickLister() {
        this.setOnClickListener(clickLister);
    }

    public void disableClickLister() {
        this.setOnClickListener(null);
    }
}
