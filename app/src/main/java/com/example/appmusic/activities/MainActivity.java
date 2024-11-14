package com.example.appmusic.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appmusic.adapters.ActivityMainAdapter;
import com.example.appmusic.adapters.ViewPagerAdapterScrollBar;
import com.example.appmusic.fragments.FragmentForYou;
import com.example.appmusic.fragments.FragmentLibraries;
import com.example.appmusic.fragments.FragmentMain;
import com.example.appmusic.R;
import com.example.appmusic.fragments.FragmentSearch;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView menuBottom;
    private ViewPager2 viewPager2;
    private ActivityMainAdapter adapterVG;

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
                switch (menuItem.getItemId()){
                    case R.id.homeIcon:
                        viewPager2.setCurrentItem(0);
                        break;
                    case R.id.searchIcon:
                        viewPager2.setCurrentItem(1);
                        break;
                    case R.id.libraryIcon:
                        viewPager2.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });

    }
}
