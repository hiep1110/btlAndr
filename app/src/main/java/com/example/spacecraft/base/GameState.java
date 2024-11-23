package com.example.spacecraft.base;

import android.graphics.Canvas;

public interface GameState {
    void update();
    void draw(Canvas canvas);
}
