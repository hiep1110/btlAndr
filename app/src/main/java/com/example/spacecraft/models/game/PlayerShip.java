package com.example.spacecraft.models.game;


import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.example.spacecraft.R;
import com.example.spacecraft.base.GameObject;
import com.example.spacecraft.utils.Constants;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerShip extends GameObject implements SensorEventListener {
    private float velocityX = 0;
    private float velocityY = 0;
    private long lastUpdateTime = 0;
    private final List<Bullet> bullets = new CopyOnWriteArrayList<>();
    private long lastShotTime = 0;

    /**
     * Tạo một đối tượng PlayerShip mới.
 *
     * @param screenWidth  chiều rộng của màn hình
     * @param screenHeight chiều cao của màn hình
     * @param sensorManager SensorManager để đăng ký cảm biến con quay hồi chuyển
     * @param gyroscope cảm biến con quay hồi chuyển
     * @param res tài nguyên để tải drawable
     * @param drawable ID tài nguyên drawable cho tàu của người chơi
     * @param screenRatioX tỷ lệ màn hình ngang
     * @param screenRatioY tỷ lệ màn hình dọc
 */
public PlayerShip(float screenWidth, float screenHeight, SensorManager sensorManager, Sensor gyroscope, Resources res, int drawable, float screenRatioX, float screenRatioY) {
    super(screenWidth, screenHeight, res, drawable, screenRatioX, screenRatioY);
    sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_GAME);
}

    /**
     * Bắn một viên đạn từ tàu của người chơi nếu thời gian hồi chiêu đã qua.
     */
    public void shoot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= 500) {
            Bullet bullet = new Bullet(getScreenWidth(), getScreenHeight(), getRes(), Constants.BULLET, getScreenRatioX(), getScreenRatioY());
            bullet.setPoint(new Point(getPoint().x + getWIDTH() / 2 + bullet.getWIDTH() / 2, getPoint().y - getHEIGHT() / 2));
            bullets.add(bullet);
            lastShotTime = currentTime;
            bullet.setSpeed(bullet.getSpeed() * 2);
        }
    }

    /**
     * Vẽ tàu của người chơi và các viên đạn của nó lên canvas.
     *
     * @param canvas canvas để vẽ lên
     * @param paint sơn ��ể sử dụng cho việc vẽ
     */
    @Override
    public void draw(Canvas canvas, Paint paint) {
        if (getExplosion() != null && !getExplosion().isFinished()) {
            getExplosion().draw(canvas, paint);
        } else {
            canvas.drawBitmap(getBackground(), getPoint().x, getPoint().y, paint);
            synchronized (bullets) {
                for (Bullet bullet : bullets) {
                    bullet.draw(canvas, paint);
                }
            }
        }
    }

    /**
     * Cập nhật vị trí của tàu của người chơi và các viên đạn của nó.
     * Phương thức này thực hiện các hành động sau:
     * 1. Tính toán thời gian hiện t��i và thời gian đã trôi qua kể từ lần cập nhật cuối cùng.
     * 2. Cập nhật vị trí của tàu của người chơi dựa trên vận tốc của nó và thời gian đã trôi qua.
     * 3. Đảm bảo tàu của người chơi vẫn nằm trong giới hạn màn hình.
     * 4. Cập nhật thời gian cập nhật cuối cùng thành thời gian hiện tại.
     * 5. Duyệt qua danh sách các viên đạn:
     *    - Loại bỏ các viên đạn đã di chuyển ra khỏi màn hình.
     *    - Cập nhật vị trí của từng viên đạn.
     */
    @Override
    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime >= 16) {   //cập nhật sau mỗi 16ms
            getPoint().x += (int) velocityX;
            getPoint().y += (int) velocityY;

            if (getPoint().x < 0) getPoint().x = 0;
            if (getPoint().x > getScreenWidth() - getWIDTH())
                getPoint().x = (int) (getScreenWidth() - getWIDTH());
            if (getPoint().y < 0) getPoint().y = 0;
            if (getPoint().y > getScreenHeight() - getHEIGHT())
                getPoint().y = (int) (getScreenHeight() - getHEIGHT());

            lastUpdateTime = currentTime;
        }
        //5. Loai bỏ đạn khi bay ra khỏi màn hình
        for (Bullet bullet : bullets) {
            bullet.update();    //cập nhật vị trí đạn
            if (bullet.getPoint().y < 0) {
                bullets.remove(bullet);
            }
        }

        if (getExplosion() != null) {
            getExplosion().update();
        }
    }

    /**
     * Xử lý các sự kiện cảm biến con quay hồi chuyển để điều khiển chuyển động của tàu của người chơi và bắn đạn.
     * R01
     *
     * @param event sự kiện cảm biến
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float rotationRateX = event.values[0];      //Tốc độ xoay (rotation rate) theo trục X.
            float rotationRateY = event.values[1];      //Tốc độ xoay theo trục Y
            velocityX += rotationRateY * 0.5f;          //tốc độ di chuyển của phi thuyển
            velocityY += rotationRateX * 0.5f;
            if (getPoint().x + velocityX < 0 || getPoint().x + velocityX > getScreenWidth() - getWIDTH()) {
                velocityX = 0;
            }
            if (getPoint().y + velocityY < 0 || getPoint().y + velocityY > getScreenHeight() - getHEIGHT()) {
                velocityY = 0;
            }
            getPoint().x += (int) velocityX;
            getPoint().y += (int) velocityY;
            shoot();
        }
    }

    /**
     * Được gọi khi độ chính xác của cảm biến đã đăng ký thay đổi.
     *
     * @param sensor cảm biến đang được giám sát
     * @param accuracy độ chính xác mới của cảm biến này
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    /**
     * Trả về danh sách các viên đạn được bắn bởi tàu của người chơi.
     *
     * @return danh sách các viên đạn
     */
    public List<Bullet> getBullets() {
        return bullets;
    }
}