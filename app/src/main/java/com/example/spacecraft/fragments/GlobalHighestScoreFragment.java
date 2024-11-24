package com.example.spacecraft.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.spacecraft.R;
import com.example.spacecraft.adapters.HighestScoreAdapter;
import com.example.spacecraft.databinding.GlobalHighestScoreFragmentBinding;
import com.example.spacecraft.models.app.Profile;
import com.example.spacecraft.services.FirebaseService;
import com.example.spacecraft.services.GoogleAuthService;
import com.example.spacecraft.services.ProfileService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GlobalHighestScoreFragment extends Fragment {
    public static final String TAG = "Global";
    private GlobalHighestScoreFragmentBinding binding;
    private GoogleAuthService googleAuthService;
    private ProfileService profileService;
    private FirebaseService firebaseService;
    private ActivityResultLauncher<Intent> signInLauncher;

    public GlobalHighestScoreFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        googleAuthService = new GoogleAuthService((Activity) context);
        profileService = new ProfileService(context);
        firebaseService = new FirebaseService();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = GlobalHighestScoreFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeUI();
        setEventListeners();
        registerSignInLauncher();
    }

    private void setEventListeners() {
        binding.connectToGlobalBtn.setOnClickListener(v -> {
            if (googleAuthService.getCurrentUser() != null) {
                googleAuthService.signOut();
            } else {
                Log.d("Google", "signInWithGoogle: ");
                googleAuthService.signInWithGoogle(signInLauncher);
            }
            IsConnected();
        });
    }

    private void initializeUI() {
        binding.progressBar.setVisibility(View.GONE);
        IsConnected();
        setAdapter();
    }

    private void setAdapter() {
        if(googleAuthService.getCurrentUser() !=null){
            List<Profile> profiles = new ArrayList<>();
            binding.progressBar.setVisibility(View.VISIBLE);
            firebaseService.getRankingOnFirebase().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    profiles.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()
                    ) {
                        Profile profile = new Profile();
                        profile.setUsername(Objects.requireNonNull(dataSnapshot.child("username").getValue()).toString());
                        profile.setHighestScore(Integer.parseInt(Objects.requireNonNull(dataSnapshot.child("highestScore").getValue()).toString()));
                        profiles.add(profile);
                    }
                    binding.globalHighestScoreRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.globalHighestScoreRv.setAdapter(new HighestScoreAdapter(profiles));
                    binding.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Firebase", "onCancelled: " + error.getMessage());
                    binding.progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    private void IsConnected() {
        if (googleAuthService.getCurrentUser() != null) {
            binding.connectToGlobalBtn.setText("Disconnect");
            binding.messageConnectStateTv.setText("Connected as " + googleAuthService.getCurrentUser().getDisplayName());
            binding.globalHighestScoreRv.setVisibility(View.VISIBLE);
        } else {
            binding.connectToGlobalBtn.setText(R.string.connect_to_global);
            binding.messageConnectStateTv.setText(R.string.you_did_nt_connect_to_global_competition_yet);
            binding.globalHighestScoreRv.setVisibility(View.GONE);
        }
    }

    private void registerSignInLauncher() {
        signInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        googleAuthService.handleSignInResult(result.getData(), this::IsConnected);
                    }
                }
        );
    }
}