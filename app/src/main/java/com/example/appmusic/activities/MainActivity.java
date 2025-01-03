package com.example.appmusic.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appmusic.StorageSong;
import com.example.appmusic.adapters.ActivityMainAdapter;

import com.example.appmusic.R;

import com.example.appmusic.fragments.ResultSeachFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;



public class MainActivity extends AppCompatActivity {
    private BottomNavigationView menuBottom;
    private ViewPager2 viewPager2;
    private ActivityMainAdapter adapterVG;
    private final String STORAGE = "com.example.appmusic.STORAGESONG";
    public static boolean show_minimized_player = false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        menuBottom=findViewById(R.id.bottomBar);
        viewPager2 = findViewById(R.id.vg_fragMain);
        //tắt hiệu ứng vuốt của fragment (hiệu ứng chuyển qua lại giữa home-search-library)
        viewPager2.setUserInputEnabled(false);

        adapterVG = new ActivityMainAdapter(this);
        viewPager2.setAdapter(adapterVG);
        onClickBottomNavigation();


    }

    private void onClickBottomNavigation(){
        //đăng ký onclick cho icon bottom menu
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        menuBottom.getMenu().findItem(R.id.homeIcon).setChecked(true);
                        break;
                    case 1:
                        menuBottom.getMenu().findItem(R.id.searchIcon).setChecked(true);
                        break;
                    case 2:
                        menuBottom.getMenu().findItem(R.id.libraryIcon).setChecked(true);
                        break;
                }
            }
        });
        // bắt sự kiện click cho bottom menu
        menuBottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.homeIcon){
                    viewPager2.setCurrentItem(0);
                }else if(menuItem.getItemId()==R.id.searchIcon){
                    viewPager2.setCurrentItem(1);
                }else{
                    viewPager2.setCurrentItem(2);
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        StorageSong storage = new StorageSong(getApplicationContext());
        int index = storage.loadSongIndex();
        if(index!=-1)
            show_minimized_player = true;
        else show_minimized_player = false;
    }
}
