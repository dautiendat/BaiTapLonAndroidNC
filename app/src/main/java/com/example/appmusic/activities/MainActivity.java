package com.example.appmusic.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusic.fragments.FragmentForYou;
import com.example.appmusic.fragments.FragmentLibraries;
import com.example.appmusic.fragments.FragmentMain;
import com.example.appmusic.fragments.FragmentRelax;
import com.example.appmusic.R;
import com.example.appmusic.adapters.AdapterScrollBarInfor;
import com.example.appmusic.models.ItemLibraries;
import com.example.appmusic.models.MusicItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


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

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fg2 = new FragmentMain();
        transaction.add(R.id.main, fg2);
        if (savedInstanceState ==null) {
            Fragment fg = new FragmentForYou();
            transaction.add(R.id.fragmentMainActivity, fg);
        }
        transaction.commit();
        onClickBottomNavigation();
    }

    private void initView(){
        menuBottom=findViewById(R.id.bottomBar);
    }


    private void onClickBottomNavigation(){

        menuBottom.setOnItemSelectedListener(menuItem -> {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment fg =null;
            Fragment fg2 =null;
            switch (menuItem.getItemId()){
                case R.id.homeIcon:
                    fg=new FragmentMain();
                    fg2 = new FragmentForYou();
                    break;
                case R.id.libraryIcon:
                    fg=new FragmentLibraries();
                    break;

            }
            if(fg!=null)
                transaction.replace(R.id.main,fg);
            if(fg2!=null)
                transaction.replace(R.id.fragmentMainActivity, fg2);
            
            transaction.commit();
            return true;
        });

    }
}
