package com.example.spacecraft.base;

import android.graphics.Canvas;

public class GameContext {
    private GameState currentState;

    public GameContext(GameState gameState) {
        this.currentState = gameState;
    }

    public void setState(GameState newState) {
        this.currentState = newState;
    }

    public void update() {
        currentState.update();
    }

    public void draw(Canvas canvas) {
        currentState.draw(canvas);
    }

    public GameState getCurrentState() {
        return currentState;
    }

}
