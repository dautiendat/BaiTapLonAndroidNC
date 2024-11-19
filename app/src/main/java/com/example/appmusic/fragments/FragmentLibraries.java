package com.example.appmusic.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusic.R;
import com.example.appmusic.adapters.AdapterListViewLibraries;
import com.example.appmusic.adapters.AdapterRecyLibraries;
import com.example.appmusic.models.ItemLibraries;
import com.example.appmusic.models.MusicItem;

import java.util.ArrayList;
import java.util.List;

public class FragmentLibraries extends Fragment {
    private RecyclerView recyclerView;
    private ListView listView;
    private AdapterRecyLibraries adapterRecy;
    private  AdapterListViewLibraries adapterListView;
    private List<MusicItem> musicItemList;
    private List<ItemLibraries> libraries;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_libraries,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recLibraries);
        listView = view.findViewById(R.id.listView_libraries);

        initData();
        GridLayoutManager manager = new GridLayoutManager(getActivity(),2);
        adapterRecy = new AdapterRecyLibraries(getActivity(),libraries);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterRecy);

        adapterListView = new AdapterListViewLibraries(getActivity(),musicItemList);
        listView.setAdapter(adapterListView);
    }

    private void initData() {
        String[] strings = {"Đã nghe gần đây","Nghệ sĩ bạn theo dõi","Bài hát mới","Danh sách hàng đầu"};
        int[] imgs={R.drawable.song,R.drawable.song,R.drawable.song,R.drawable.song};
        String[] strings2 = {"Bảo Anh","Bích Phương","Karik ft.OnlyC","Phan Mạnh Quỳnh"};
        musicItemList=new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            musicItemList.add(new MusicItem(imgs[i],strings[i],strings2[i]));
        }

        String[] stringNameItem = {"Đã thích","Download", "Danh sách","Nghệ sĩ"};
        int[] icons={R.drawable.favorite_icon,R.drawable.download_for_offline,
                R.drawable.queue_music,R.drawable.artist};
        String[] stringNumSong = {"120 bài","30 bài", "12 danh sách","20 nghệ sĩ"};
        libraries=new ArrayList<>();
        for (int i = 0; i < stringNameItem.length; i++) {
            libraries.add(new ItemLibraries(icons[i],stringNameItem[i],stringNumSong[i]));
        }
    }
}
