package com.example.spacecraft.activities;

import android.os.Bundle;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.spacecraft.R;
import com.example.spacecraft.components.AppToast;
import com.example.spacecraft.components.GameOverDialog;
import com.example.spacecraft.components.GameView;
import com.example.spacecraft.components.ProfileDialog;
import com.example.spacecraft.databinding.ActivityGameBinding;
import com.example.spacecraft.models.app.Profile;
import com.example.spacecraft.services.FirebaseService;
import com.example.spacecraft.services.GoogleAuthService;
import com.example.spacecraft.services.ProfileService;
import com.example.spacecraft.state.GamePlayingState;
import com.example.spacecraft.utils.Constants;

public class GameActivity extends AppCompatActivity implements ProfileDialog.DialogListener {
    private GameView gameView;
    private ActivityGameBinding binding;
    private boolean isPaused = false;
    private ProfileService profileService;
    private DialogFragment dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameBinding.inflate(getLayoutInflater());
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(binding.getRoot());
        setView();
        setInputEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        profileService = new ProfileService(this);
    }

    private void setInputEvent() {
        binding.pauseBtn.setOnClickListener(v -> togglePause());

//        binding.homeBtn.setOnClickListener(v -> {
//            gameView.pause();
//            AppToast.makeText(this,"Saved score", Toast.LENGTH_LONG);
//            finish();
//        });
//
//        binding.replayGameBtn.setOnClickListener(v -> replayGame());
    }

    private void togglePause() {
        if (isPaused) {
            gameView.resume();
        } else {
            gameView.pause();
        }
        isPaused = !isPaused;
        binding.pauseBtn.setImageResource(isPaused ? R.drawable.play : R.drawable.baseline_pause_24);
    }


    private void replayGame() {
        isPaused = false;
        gameView.getGameContext().setState(new GamePlayingState(this));
        binding.pauseBtn.setImageResource(R.drawable.baseline_pause_24);
        binding.score.setText("0");
    }

    private void setView() {
        gameView = new GameView(this);
        gameView.getGameContext().setState(new GamePlayingState(this));
        binding.game.addView(gameView, 0);
        updateHealthBar(Constants.PLAYER_SHIP_HEALTH);
    }


    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }


    public ActivityGameBinding getBinding() {
        return this.binding;
    }

    public void showGameOverDialog() {
        gameView.pause();
        dialog = new GameOverDialog(this, binding.score.getText().toString());
        dialog.show(getSupportFragmentManager(), GameOverDialog.TAG);
    }

    @Override
    public void onDialogPositiveClick(String inputText) {
        FirebaseService firebaseService = new FirebaseService();
        GoogleAuthService googleAuthService = new GoogleAuthService(this);
        if (googleAuthService.getCurrentUser() != null) {
            if (profileService.getProfileHaveHighestScores().getHighestScore() < Integer.parseInt(inputText)) {
                firebaseService.storeGoogleAuthUser(googleAuthService.getCurrentUser(), Integer.parseInt(inputText));
            }
        }
        profileService.updateProfileScore(Integer.parseInt(inputText));
        finish();
    }


    @Override
    public void onDialogNegativeClick() {
        dialog.dismiss();
        binding.score.setText("0");
        gameView.resume();
        replayGame();
    }

    public void updateHealthBar(int health){
        binding.healthContainer.removeAllViews();
        for (int i = 0; i < health; i++) {
            ImageView healthIcon = new ImageView(this);
            healthIcon.setImageResource(Constants.HEALTH_ICON);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    50,
                    50
            );
            params.setMargins(0, 0, 10, 0);
            healthIcon.setLayoutParams(params);
            binding.healthContainer.addView(healthIcon);
        }
    }
}