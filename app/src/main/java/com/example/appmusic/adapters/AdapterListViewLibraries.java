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

import com.example.appmusic.R;
import com.example.appmusic.models.Music;

import java.util.List;

public class AdapterListViewLibraries extends ArrayAdapter<Music> {
    private List<Music> musicList;
    private Context context;


    public AdapterListViewLibraries(@NonNull Context context, List<Music> musicList) {
        super(context,R.layout.item_song_libraries , musicList);
        this.context=context;
        this.musicList = musicList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_song_libraries,parent,false);
        TextView tv1,tv2;
        tv1=view.findViewById(R.id.nameSong_libraries);
        tv2=view.findViewById(R.id.nameArtist_libraries);
        ImageView img= view.findViewById(R.id.imgSong_libraries);
        Music music = musicList.get(position);

        tv1.setText(music.getName());
        tv2.setText(music.getArtist());
        //img.setImageResource(music.getImageUrl());
        return view;
    }
}
