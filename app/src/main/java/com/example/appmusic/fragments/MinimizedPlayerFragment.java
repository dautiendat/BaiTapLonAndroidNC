package com.example.appmusic.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appmusic.R;

public class MinimizedPlayerFragment extends Fragment {

    private TextView miniNameSong, miniNameArtist;
    private ImageView miniImgSong, icPlay, icNext, icPre;
    public MinimizedPlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_minimized_player, container, false);
        miniImgSong = view.findViewById(R.id.miniImage);
        miniNameSong = view.findViewById(R.id.miniNameSong);
        miniNameArtist = view.findViewById(R.id.miniArtist);
        icNext = view.findViewById(R.id.miniplayNextSong);
        icPlay = view.findViewById(R.id.miniplaySong_icon);
        icPre = view.findViewById(R.id.minibuttonPreSong);
        return view;
    }
}