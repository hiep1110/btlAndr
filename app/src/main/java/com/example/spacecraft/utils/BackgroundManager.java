package com.example.spacecraft.utils;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.spacecraft.models.game.Background;

public class BackgroundManager {
    private final Background[] backgrounds;
    private final float[] backgroundOffset;
    private final float backgroundMaxScrollingSpeed;
    private final float screenWidth;
    private final float screenHeight;
    private final Resources resources;

    public float getScreenHeight() {
        return screenHeight;
    }

    public float getScreenWidth() {
        return screenWidth;
    }

    public Resources getResources() {
        return resources;
    }

    ;

    public BackgroundManager(float screenWidth, float screenHeight, float screenRatioY, int[] backgroundImages, Resources resources) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.resources = resources;
        backgroundOffset = new float[backgroundImages.length];
        backgrounds = new Background[backgroundImages.length];

        int DEFAULT_BACKGROUND_SPEED = 100;
        backgroundMaxScrollingSpeed = DEFAULT_BACKGROUND_SPEED * screenRatioY;

        for (int i = 0; i < backgroundImages.length; i++) {
            backgrounds[i] = new Background(screenWidth, screenHeight, this.resources, backgroundImages[i]);
        }
    }

    public void update() {
        for (int i = 0; i < backgroundOffset.length; i++) {
            backgroundOffset[i] += backgroundMaxScrollingSpeed / (8 - 2 * i);
            if (backgroundOffset[i] >= screenHeight) {
                backgroundOffset[i] -= screenHeight;
            }
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        for (int i = 0; i < backgrounds.length; i++) {
            canvas.drawBitmap(backgrounds[i].background, 0, backgroundOffset[i], paint);
            canvas.drawBitmap(backgrounds[i].background, 0, backgroundOffset[i] - screenHeight, paint);
        }
    }

}
