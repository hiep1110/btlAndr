package com.example.spacecraft.utils;

import android.content.Context;
import android.view.animation.AnimationUtils;

import com.example.spacecraft.R;

public class Animation {
    public static android.view.animation.Animation moveUp(Context context, AnimationEndListener listener){
       android.view.animation.Animation moveUp = AnimationUtils.loadAnimation(context, R.anim.move_up);
        moveUp.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {
            }

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {
                listener.onAnimationEnd();
            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {}
        });
        return moveUp;
    }

    public static android.view.animation.Animation moveDown(Context context, AnimationEndListener listener){
       android.view.animation.Animation moveDown = AnimationUtils.loadAnimation(context, R.anim.move_down);
        moveDown.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {
            }

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {
                listener.onAnimationEnd();
            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {}
        });
        return moveDown;
    }

    public interface AnimationEndListener{
        void onAnimationEnd();
    }
}
