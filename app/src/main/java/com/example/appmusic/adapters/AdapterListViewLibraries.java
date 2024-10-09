package com.example.appmusic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusic.R;
import com.example.appmusic.models.MusicItem;

import java.util.List;

public class AdapterListViewLibraries extends ArrayAdapter<MusicItem> {
    private List<MusicItem> musicItemList;
    private Context context;


    public AdapterListViewLibraries(@NonNull Context context, List<MusicItem> musicItemList) {
        super(context,R.layout.item_song_libraries ,musicItemList);
        this.context=context;
        this.musicItemList=musicItemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_song_libraries,parent,false);
        TextView tv1,tv2;
        tv1=view.findViewById(R.id.nameSong_libraries);
        tv2=view.findViewById(R.id.nameArtist_libraries);
        ImageView img= view.findViewById(R.id.imgSong_libraries);
        MusicItem music = musicItemList.get(position);

        tv1.setText(music.getMusicName());
        tv2.setText(music.getMusicArtist());
        img.setImageResource(music.getMusicImage());
        return view;
    }
}
