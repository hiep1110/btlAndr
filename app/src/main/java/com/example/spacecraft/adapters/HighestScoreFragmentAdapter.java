package com.example.spacecraft.adapters;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.spacecraft.fragments.GlobalHighestScoreFragment;
import com.example.spacecraft.fragments.LocalHighestScoreFragment;

public class HighestScoreFragmentAdapter extends FragmentStateAdapter {
    private final Activity activity;

    public HighestScoreFragmentAdapter(@NonNull Fragment fragment, Activity activity) {
        super(fragment);
        this.activity = activity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new GlobalHighestScoreFragment();
        }
        return new LocalHighestScoreFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
