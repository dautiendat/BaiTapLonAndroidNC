package com.example.appmusic.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appmusic.adapters.ViewPagerAdapterScrollBar;
import com.example.appmusic.fragments.FragmentForYou;
import com.example.appmusic.fragments.FragmentLibraries;
import com.example.appmusic.fragments.FragmentMain;
import com.example.appmusic.R;
import com.example.appmusic.fragments.FragmentSearch;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView menuBottom;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
        loadFragments();
        onClickBottomNavigation();
    }

    private void initView(){
        menuBottom=findViewById(R.id.bottomBar);

    }

    private void loadFragments(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fg;
        fg = new FragmentMain();
        transaction.replace(R.id.fragMain,fg);
        transaction.commit();
    }

    private void onClickBottomNavigation(){
        menuBottom.setOnItemSelectedListener(menuItem -> {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment fg;
            switch (menuItem.getItemId()){
                case R.id.homeIcon:
                    fg=new FragmentMain();
                    break;
                case R.id.libraryIcon:
                    fg=new FragmentLibraries();
                    break;
                case R.id.searchIcon:
                    fg=new FragmentSearch();
                    break;
                default:
                    fg=new FragmentMain();
                    break;
            }
            if(fg!=null){
                transaction.replace(R.id.fragMain,fg);
                transaction.addToBackStack(null);
            }
            transaction.commit();

            return true;
        });

    }
}
