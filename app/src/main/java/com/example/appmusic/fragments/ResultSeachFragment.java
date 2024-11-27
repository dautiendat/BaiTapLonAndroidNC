package com.example.appmusic.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appmusic.R;
import com.example.appmusic.adapters.ResultSearchSongAdapter;
import com.example.appmusic.models.Song;

import java.util.ArrayList;
import java.util.List;

public class ResultSeachFragment extends Fragment {
    private RecyclerView recyclerViewSongs;
    private ResultSearchSongAdapter adapter; // Sử dụng ResultSearchSongAdapter để hiển thị danh sách
    private List<Song> songList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result_seach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewSongs = view.findViewById(R.id.recyclerViewSongs);

        // Khởi tạo danh sách bài hát
        songList = new ArrayList<>();

        // Nhận dữ liệu từ FragmentSearch (nếu có)
        Bundle bundle = getArguments();
        if (bundle != null) {
            // Lấy danh sách bài hát từ Bundle (với Serializable)
            songList = (List<Song>) bundle.getSerializable("songs");
        }

        // Kiểm tra nếu danh sách trống, tránh lỗi null
        if (songList == null) {
            songList = new ArrayList<>();
        }

        // Cài đặt RecyclerView
        adapter = new ResultSearchSongAdapter(getActivity(), songList);
        recyclerViewSongs.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewSongs.setAdapter(adapter);
    }
}
