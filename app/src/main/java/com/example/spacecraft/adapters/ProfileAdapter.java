package com.example.spacecraft.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacecraft.databinding.ProfileRowRecycleViewBinding;
import com.example.spacecraft.models.app.Profile;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
    private final List<Profile> profiles;
    private final Context context;

    public ProfileAdapter(Context context,List<Profile> profiles) {
        this.context = context;
        this.profiles = profiles;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ProfileRowRecycleViewBinding binding = ProfileRowRecycleViewBinding.inflate(layoutInflater,parent,false);
        return new ProfileViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        Profile profile = profiles.get(position);
        holder.binding.currentProfileTv.setText(profile.getUsername());
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public Context getContext() {
        return context;
    }

    public static class ProfileViewHolder extends RecyclerView.ViewHolder {
        private final ProfileRowRecycleViewBinding binding;
        public ProfileViewHolder(@NonNull ProfileRowRecycleViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
