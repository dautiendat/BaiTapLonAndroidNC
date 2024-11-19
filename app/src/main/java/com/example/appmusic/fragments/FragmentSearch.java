package com.example.appmusic.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusic.R;
import com.example.appmusic.adapters.CategoryAdapter;
import com.example.appmusic.adapters.TrendingArtistAdapter;
import com.example.appmusic.models.ItemSearch;

import java.util.ArrayList;
import java.util.List;

public class FragmentSearch extends Fragment {
    private RecyclerView recyclerView1, recyclerView2;
    private TrendingArtistAdapter adapterArtist;
    private CategoryAdapter adapterCategory;

    private List<ItemSearch> artistList;
    private List<ItemSearch> categoryList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView1 =view.findViewById(R.id.recyclerViewTrendingArtists);
        recyclerView2 = view.findViewById(R.id.recyclerViewCategories);

        initData();
        LinearLayoutManager linear = new LinearLayoutManager(getActivity()
                ,LinearLayoutManager.HORIZONTAL,false);
        GridLayoutManager grid = new GridLayoutManager(getActivity(),2);

        adapterArtist = new TrendingArtistAdapter(getActivity(),artistList);
        recyclerView1.setLayoutManager(linear);
        recyclerView1.setAdapter(adapterArtist);

        adapterCategory=new CategoryAdapter(getActivity(),categoryList);
        recyclerView2.setLayoutManager(grid);
        recyclerView2.setAdapter(adapterCategory);


    }
    private void initData() {
        String[] strings = {"V-pop","K-pop","us-uk","dance","hip-hop","jazz","indie","tamil"};
        String[] strings2 = {"Binz","Sơn Tùng M-TP","Ricky Star","Karik","Wowy","HieuThuHai"};
        int[] imgArtist ={R.drawable.cat,R.drawable.cat,R.drawable.cat,R.drawable.cat,
                R.drawable.cat,R.drawable.cat};
        int[] imgBrowse={R.drawable.vpop_artists,R.drawable.vpop_artists,R.drawable.vpop_artists,
                R.drawable.vpop_artists,R.drawable.vpop_artists,R.drawable.vpop_artists,
                R.drawable.vpop_artists,R.drawable.vpop_artists};

//        artistList = new ArrayList<>();
//        for (int i = 0; i < strings2.length; i++) {
//            artistList.add(new ItemSearch(strings2[i],imgArtist[i]));
//        }
//
//        categoryList = new ArrayList<>();
//        for (int i = 0; i < strings.length; i++) {
//            categoryList.add(new ItemSearch(strings[i],imgBrowse[i]));
//        }
    }
}
