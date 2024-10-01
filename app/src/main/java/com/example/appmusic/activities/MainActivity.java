package com.example.appmusic.activities;

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
import com.example.appmusic.fragments.FragmentRelax;
import com.example.appmusic.R;
import com.example.appmusic.adapters.AdapterScrollBarInfor;


public class MainActivity extends AppCompatActivity implements AdapterScrollBarInfor.itemListener{

    private RecyclerView recyclerView;
    private AdapterScrollBarInfor adapterScrollBarInfor;
    private String[] listBarInfor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView=findViewById(R.id.recyScrollBarInfor);
        initData();
        adapterScrollBarInfor=new AdapterScrollBarInfor(this,listBarInfor);
        adapterScrollBarInfor.setItemClickListener(this); //set click cho item recyclerview
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterScrollBarInfor);

        if (savedInstanceState ==null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment fg = new FragmentForYou();
            transaction.add(R.id.fragmentMainActivity, fg);
            transaction.commit();
        }
    }

    private void initData(){
        String[] strings = {"Dành cho bạn","Thư giãn","Thể thao","Du lịch","Năng lượng"};
        listBarInfor=new String[strings.length];
        for (int i = 0; i < listBarInfor.length; i++) {
            listBarInfor[i]=strings[i];
        }
    }

    @Override
    public void onItemClick(int position) {
        String str = adapterScrollBarInfor.getItem(position);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fg;
        switch (position){
            case 0:
                fg = new FragmentForYou();
                break;
            case 1:
                fg=new FragmentRelax();
                break;
            default:
                return;
        }

        if(fg!=null){
            transaction.replace(R.id.fragmentMainActivity,fg);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }
}
