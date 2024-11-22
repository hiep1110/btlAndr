package com.example.spacecraft.models.game;


import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.appcompat.graphics.drawable.DrawableContainerCompat;

import com.example.spacecraft.base.GameObject;

public class EnemyShip extends GameObject {
    private float velocityX, velocityY;
    private float accelerationX, accelerationY;
    private float targetX, targetY;
    private boolean exploding = false;

    public EnemyShip(float screenWidth, float screenHeight, Resources res, int drawable, float screenRatioX, float screenRatioY) {
        super(screenWidth, screenHeight, res, drawable, screenRatioX, screenRatioY);
        setPoint(new Point());
        getPoint().y = (int) (screenHeight / 2 + Math.random() * (screenHeight / 2));
        velocityX = 0;
        velocityY = 0;
        accelerationX = 0;
        accelerationY = 0;
        targetX = (float) (Math.random() * screenWidth);
        targetY = (float) (Math.random() * screenHeight);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        if (getExplosion() != null && !getExplosion().isFinished()) {
            getExplosion().draw(canvas, paint);
        } else {
            canvas.drawBitmap(getBackground(), getPoint().x, getPoint().y, paint);
        }
    }

    @Override
    public void update() {
        if (getExplosion() != null && !getExplosion().isFinished()) {
            getExplosion().update();
        }else{
            float directionX = targetX - getPoint().x;
            float directionY = targetY - getPoint().y;
            float distance = (float) Math.sqrt(directionX * directionX + directionY * directionY);
            if (distance > 0) {
                directionX /= distance;
                directionY /= distance;
            }
            accelerationX = directionX * 0.5f;
            accelerationY = directionY * 0.5f;
            velocityX += accelerationX;
            velocityY += accelerationY;
            if (Math.abs(velocityX) > getSpeed()) velocityX = Math.signum(velocityX) * getSpeed();
            if (Math.abs(velocityY) > getSpeed()) velocityY = Math.signum(velocityY) * getSpeed();
            getPoint().x += (int) velocityX;
            getPoint().y += (int) velocityY;

            if (getPoint().x < 0) getPoint().x = 0;
            if (getPoint().x > getScreenWidth() - getBackground().getWidth())
                getPoint().x = (int) (getScreenWidth() - getBackground().getWidth());
            if (getPoint().y < 0) getPoint().y = 0;
            if (getPoint().y > getScreenHeight() - getBackground().getHeight())
                getPoint().y = (int) (getScreenHeight() - getBackground().getHeight());
            if (distance < 50) {
                targetX = (float) (Math.random() * getScreenWidth());
                targetY = (float) (Math.random() * getScreenHeight());
            }

            if (getExplosion() != null) {
                getExplosion().update();
            }
        }
    }

    @Override
    public void setHealth(int health) {
        super.setHealth(health);
        if (health <= 0 && !exploding) {
            triggerExplosion(getRes());
            exploding = true;
        }
    }

    public boolean isExploding() {
        return exploding;
    }
}
