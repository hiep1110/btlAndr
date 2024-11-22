package com.example.spacecraft.models.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {
    public float WIDTH ;
    public float HEIGHT;
    public int x;
    public int y;
    public Bitmap background;

    public Background(float width, float height, Resources res,int drawable) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.background = BitmapFactory.decodeResource(res, drawable);
        this.background = Bitmap.createScaledBitmap(this.background, (int) WIDTH, (int) HEIGHT, false);
    }
}
