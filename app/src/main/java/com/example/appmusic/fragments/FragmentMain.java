package com.example.appmusic.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appmusic.R;
import com.example.appmusic.activities.MainActivity;
import com.example.appmusic.adapters.ViewPagerAdapterScrollBar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FragmentMain extends Fragment{

    private TabLayout tabLayoutScrollBar;
    private ViewPagerAdapterScrollBar VPAdapterScrollBar;
    private ViewPager2 viewPagerFragmenMain;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPagerFragmenMain= view.findViewById(R.id.viewPagerFragmentMain);
        tabLayoutScrollBar= view.findViewById(R.id.tabLayoutScrollBar);

        VPAdapterScrollBar = new ViewPagerAdapterScrollBar(getActivity());
        viewPagerFragmenMain.setAdapter(VPAdapterScrollBar);

        new TabLayoutMediator(tabLayoutScrollBar, viewPagerFragmenMain, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText(R.string.bar_foryou);
                    break;
                case 1:
                    tab.setText(R.string.bar_relax);
                    break;
                case 2:
                    tab.setText(R.string.bar_energize);
                    break;
                case 3:
                    tab.setText(R.string.bar_travel);
                    break;
                case 4:
                    tab.setText(R.string.bar_workout);
                    break;
                default:
                    tab.setText(R.string.bar_foryou);
                    break;
            }
        }).attach();


        tabLayoutScrollBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.view.setBackgroundResource(R.drawable.rounded_background_solid);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.view.setBackgroundResource(R.drawable.rounded_background);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        TabLayout.Tab firstTab = tabLayoutScrollBar.getTabAt(0);
        firstTab.view.setBackgroundResource(R.drawable.rounded_background_solid);
    }




}
