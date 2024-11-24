package com.example.spacecraft.services;


import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.example.spacecraft.R;
import com.example.spacecraft.models.game.EnemyShip;
import com.example.spacecraft.models.game.PlayerShip;
import com.example.spacecraft.utils.BackgroundManager;
import com.example.spacecraft.utils.CommonHelper;
import com.example.spacecraft.utils.Constants;

public class GameCharacterService {

    private final Context context;
    private final PlayerShip playerShip;
    private final BackgroundManager backgroundManager;
    private final Point deviceSize;
    private final float screenRatioX;
    private final float screenRatioY;

    public GameCharacterService(Context context) {
        this.context = context;
        deviceSize = CommonHelper.getSizeDevice(this.context);
        this.playerShip = defaultPlayerShip();
        this.backgroundManager = defaultBackgroundManager();
        this.screenRatioX = 1920f / deviceSize.x;
        this.screenRatioY = 1080f / deviceSize.y;
    }

    public EnemyShip createEnemyShip(Point point, int drawable) {
        assert this.deviceSize != null;
        EnemyShip enemyShip = new EnemyShip(this.deviceSize.x, deviceSize.y, context.getResources(), drawable, 1920f / deviceSize.x, 1080f / deviceSize.y);
        enemyShip.setPoint(point);
        return enemyShip;
    }

    public PlayerShip defaultPlayerShip() {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        PlayerShip playerShip = new PlayerShip(this.deviceSize.x, deviceSize.y, sensorManager, gyroscope, context.getResources(), Constants.PLAYER_SHIP, screenRatioX, screenRatioY);
        playerShip.setPoint( new Point((int) (deviceSize.x / 2f - playerShip.getWIDTH() / 2f), (int) (deviceSize.y - playerShip.getHEIGHT() * 10 * screenRatioY)));;
        playerShip.setHealth(Constants.PLAYER_SHIP_HEALTH);
        return playerShip;
    }

    public  BackgroundManager defaultBackgroundManager() {
        return new BackgroundManager(deviceSize.x, deviceSize.y, 1080f / deviceSize.y, Constants.BACKGROUND_GAME, context.getResources());
    }


    public PlayerShip getPlayerShip() {
        return playerShip;
    }

    public BackgroundManager getBackgroundManager() {
        return backgroundManager;
    }
}
