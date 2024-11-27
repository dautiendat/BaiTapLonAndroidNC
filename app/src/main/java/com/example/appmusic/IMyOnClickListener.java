package com.example.appmusic;

import android.view.View;

import com.example.appmusic.models.Song;

import java.util.ArrayList;
import java.util.List;

public interface IMyOnClickListener {
    public void myOnClick(View view, Song song);
    public void myClickToSendArrayList(int position, ArrayList<Song> songList);
}
