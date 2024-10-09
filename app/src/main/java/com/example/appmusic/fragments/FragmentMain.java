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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusic.R;
import com.example.appmusic.adapters.AdapterScrollBarInfor;

public class FragmentMain extends Fragment implements AdapterScrollBarInfor.itemListener{

    private AdapterScrollBarInfor adapterScrollBarInfor;
    private String[] listBarInfor;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recyScrollBarInfor);
        initData();
        adapterScrollBarInfor=new AdapterScrollBarInfor(getActivity(),listBarInfor);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),
                RecyclerView.HORIZONTAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterScrollBarInfor);
        adapterScrollBarInfor.setItemClickListener(this);
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
        FragmentManager manager = getParentFragmentManager();
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
