package com.example.spacecraft.base;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.example.spacecraft.R;
import com.example.spacecraft.models.game.Explosion;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp GameObject đại diện cho một đối tượng trong trò chơi.
 * Lớp này chứa các thuộc tính và phương thức cơ bản cho một đối tượng trò chơi.
 */
public class GameObject extends Subject {
    private int health = 3;
    private int speed = 20;
    private int score;
    private float screenWidth;
    private float screenHeight;
    private int WIDTH;
    private int HEIGHT;
    private Point point;
    private Bitmap background;
    private float screenRatioX;
    private float screenRatioY;
    private Resources res;
    private Explosion explosion;


    /**
     * Khởi tạo một đối tượng GameObject mới.
     *
     * @param screenWidth  chiều rộng màn hình
     * @param screenHeight chiều cao màn hình
     * @param res          tài nguyên của ứng dụng
     * @param drawable     mã tài nguyên hình ảnh
     * @param screenRatioX tỉ lệ màn hình theo chiều ngang
     * @param screenRatioY tỉ lệ màn hình theo chiều dọc
     */
    public GameObject(float screenWidth, float screenHeight, Resources res, int drawable, float screenRatioX, float screenRatioY) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.screenRatioX = screenRatioX;
        this.screenRatioY = screenRatioY;
        this.res = res;
        this.background = BitmapFactory.decodeResource(res, drawable);
        this.WIDTH = (int) (background.getWidth() / 3);
        this.HEIGHT = (int) (background.getHeight() / 3);
        try {
            this.background = Bitmap.createScaledBitmap(this.background, (int) (WIDTH * this.screenRatioX), (int) (HEIGHT * this.screenRatioX), false);

        } catch (Exception e) {
            Log.d("GameObject", "GameObject: " + WIDTH * this.screenRatioX);
        }
    }

    /**
     * Vẽ đối tượng lên canvas.
     *
     * @param canvas canvas để vẽ lên
     * @param paint  sơn để sử dụng cho việc vẽ
     */
    public void draw(Canvas canvas, Paint paint) {
    }

    ;

    /**
     * Cập nhật trạng thái của đối tượng.
     */
    public void update() {
    }

    ;

    /**
     * Trả về giới hạn của đối tượng dưới dạng một hình chữ nhật.
     *
     * @return giới hạn của đối tượng
     */
    public Rect getBounds() {
        return new Rect(point.x, point.y, point.x + WIDTH, point.y + HEIGHT);
    }

    public void triggerExplosion(Resources res) {
        if (explosion == null) {
            explosion = new Explosion(res, R.drawable.hieuung, point, 128, 4);
        }
    }


    /**
     * Gọi phương thức này khi đối tượng thay đổi.
     */
    public void onGameObjectChanged() {
        notifyObservers(this, null);
    }

    /**
     * Trả về giá trị sức khỏe của đối tượng.
     *
     * @return giá trị sức khỏe
     */
    public int getHealth() {
        return health;
    }

    /**
     * Đặt giá trị sức khỏe cho đối tượng.
     *
     * @param health giá trị sức khỏe mới
     */
    public void setHealth(int health) {
        this.health = health;
        onGameObjectChanged();
    }

    /**
     * Trả về tốc độ của đối tượng.
     *
     * @return tốc độ
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Đặt tốc độ cho đối tượng.
     *
     * @param speed tốc độ mới
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Trả về chiều rộng màn hình.
     *
     * @return chiều rộng màn hình
     */
    public float getScreenWidth() {
        return screenWidth;
    }

    /**
     * Đặt chiều rộng màn hình.
     *
     * @param screenWidth chiều rộng màn hình mới
     */
    public void setScreenWidth(float screenWidth) {
        this.screenWidth = screenWidth;
    }

    /**
     * Trả về chiều cao màn hình.
     *
     * @return chiều cao màn hình
     */
    public float getScreenHeight() {
        return screenHeight;
    }

    /**
     * Đặt chiều cao màn hình.
     *
     * @param screenHeight chiều cao màn hình mới
     */
    public void setScreenHeight(float screenHeight) {
        this.screenHeight = screenHeight;
    }

    /**
     * Trả về chiều rộng của đối tượng.
     *
     * @return chiều rộng của đối tượng
     */
    public int getWIDTH() {
        return WIDTH;
    }

    /**
     * Đặt chiều rộng cho đối tượng.
     *
     * @param WIDTH chiều rộng mới
     */
    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    /**
     * Trả về chiều cao của đối tượng.
     *
     * @return chiều cao của đối tượng
     */
    public int getHEIGHT() {
        return HEIGHT;
    }

    /**
     * Đặt chiều cao cho đối tượng.
     *
     * @param HEIGHT chiều cao mới
     */
    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    /**
     * Trả về vị trí của đối tượng.
     *
     * @return vị trí của đối tượng
     */
    public Point getPoint() {
        return point;
    }

    /**
     * Đặt vị trí cho đối tượng.
     *
     * @param point vị trí mới
     */
    public void setPoint(Point point) {
        this.point = point;
    }

    /**
     * Trả về hình nền của đối tượng.
     *
     * @return hình nền của đối tượng
     */
    public Bitmap getBackground() {
        return background;
    }

    /**
     * Đặt hình nền cho đối tượng.
     *
     * @param background hình nền mới
     */
    public void setBackground(Bitmap background) {
        this.background = background;
    }

    /**
     * Trả về tỉ lệ màn hình theo chiều ngang.
     *
     * @return tỉ lệ màn hình theo chiều ngang
     */
    public float getScreenRatioX() {
        return screenRatioX;
    }

    /**
     * Đặt tỉ lệ màn hình theo chiều ngang.
     *
     * @param screenRatioX tỉ lệ màn hình theo chiều ngang mới
     */
    public void setScreenRatioX(float screenRatioX) {
        this.screenRatioX = screenRatioX;
    }

    /**
     * Trả về tỉ lệ màn hình theo chiều dọc.
     *
     * @return tỉ lệ màn hình theo chiều dọc
     */
    public float getScreenRatioY() {
        return screenRatioY;
    }

    /**
     * Đặt tỉ lệ màn hình theo chiều dọc.
     *
     * @param screenRatioY tỉ lệ màn hình theo chiều dọc mới
     */
    public void setScreenRatioY(float screenRatioY) {
        this.screenRatioY = screenRatioY;
    }

    /**
     * Trả về tài nguyên của ứng dụng.
     *
     * @return tài nguyên của ứng dụng
     */
    public Resources getRes() {
        return res;
    }

    /**
     * Đặt tài nguyên cho ứng dụng.
     *
     * @param res tài nguyên mới
     */
    public void setRes(Resources res) {
        this.res = res;
    }

    public Explosion getExplosion() {
        return explosion;
    }

    public void setExplosion(Explosion explosion) {
        this.explosion = explosion;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
