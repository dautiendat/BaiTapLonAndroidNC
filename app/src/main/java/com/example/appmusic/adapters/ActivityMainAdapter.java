package com.example.appmusic.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appmusic.fragments.FragmentLibraries;
import com.example.appmusic.fragments.FragmentMain;
import com.example.appmusic.fragments.FragmentSearch;

public class ActivityMainAdapter extends FragmentStateAdapter {
    private static final int NUM_OF_FRAGMENT = 3;
    public ActivityMainAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int i) {
        switch (i){
            case 0:
                return new FragmentMain();
            case 1:
                return new FragmentSearch();
            case 2:
                return new FragmentLibraries();
            default:
                return new FragmentMain();
        }
    }

    @Override
    public int getItemCount() {
        return NUM_OF_FRAGMENT;
    }
}
