package com.example.spacecraft.state;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.spacecraft.base.GameState;
import com.example.spacecraft.services.GameCharacterService;
import com.example.spacecraft.utils.BackgroundManager;
import com.example.spacecraft.utils.Constants;

public class BackgroundState implements GameState {
    public static final String TAG = "BackgroundState";
    private final GameCharacterService gameCharacterService;

    public BackgroundState(Context context) {
        gameCharacterService = new GameCharacterService(context);
        Constants.CURRENT_STATE = BackgroundState.TAG;
    }

    @Override
    public void update() {
        gameCharacterService.getBackgroundManager().update();
    }

    @Override
    public void draw(Canvas canvas) {
        gameCharacterService.getBackgroundManager().draw(canvas,new Paint());
    }
}
