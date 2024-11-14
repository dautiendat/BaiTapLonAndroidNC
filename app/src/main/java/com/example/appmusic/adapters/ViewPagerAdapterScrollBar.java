package com.example.appmusic.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appmusic.fragments.FragmentForYou;
import com.example.appmusic.fragments.FragmentRelax;

public class ViewPagerAdapterScrollBar extends FragmentStateAdapter {
    private static final int NUM_OF_FRAGMENT = 5;
    public ViewPagerAdapterScrollBar(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new FragmentForYou();
            case 1:
                return new FragmentRelax();
            default:
                return new FragmentForYou();
        }
    }

    @Override
    public int getItemCount() {
        return NUM_OF_FRAGMENT;
    }

}
