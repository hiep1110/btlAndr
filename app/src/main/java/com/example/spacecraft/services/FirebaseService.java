package com.example.spacecraft.services;

import androidx.annotation.NonNull;

import com.example.spacecraft.models.app.Profile;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FirebaseService {
    private FirebaseDatabase firebaseDatabase;
    public FirebaseService() {
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void storeGoogleAuthUser(FirebaseUser user,int highestScore) {
        firebaseDatabase.getReference("users").child(user.getUid()).child("username").setValue(user.getDisplayName());
        firebaseDatabase.getReference("users").child(user.getUid()).child("highestScore").setValue(highestScore);
    }

    public Query getRankingOnFirebase(){
        return firebaseDatabase.getReference("users").orderByChild("highestScore");
    }
}
