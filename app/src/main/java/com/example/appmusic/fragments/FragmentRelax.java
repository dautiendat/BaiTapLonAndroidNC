package com.example.appmusic.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appmusic.R;
import com.example.appmusic.adapters.AdapterContent;
import com.example.appmusic.models.Frame;

public class FragmentRelax extends Fragment {

    private ListView listView;
    private AdapterContent adapter;
    private Frame[] frames;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_relax,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView=view.findViewById(R.id.listView_FragRelax);
        initData();
        adapter=new AdapterContent(getActivity(),frames);
        listView.setAdapter(adapter);
    }

    private void initData() {
        String[] strings = {"Đã nghe gần đây","Nghệ sĩ bạn theo dõi","Bài hát mới","Danh sách hàng đầu"};
        frames=new Frame[strings.length];
        for (int i = 0; i < strings.length; i++) {
            frames[i]=new Frame(strings[i]);
        }

    }
}
