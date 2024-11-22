package com.example.spacecraft.models.game;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.spacecraft.base.GameObject;

public class Bullet extends GameObject {


    public Bullet(float screenWidth, float screenHeight, Resources res, int drawable, float screenRatioX, float screenRatioY) {
        super(screenWidth, screenHeight, res, drawable, screenRatioX, screenRatioY);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(getBackground(), getPoint().x, getPoint().y, paint);
    }

    @Override
    public void update() {
        getPoint().y -= (int) (50 * getScreenRatioY());
    }
}
