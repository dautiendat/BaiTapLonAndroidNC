package com.example.appmusic.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appmusic.IMyOnClickListener;
import com.example.appmusic.R;
import com.example.appmusic.activities.PlaySongActivity;
import com.example.appmusic.adapters.ResultSearchSongAdapter;
import com.example.appmusic.models.Song;

import java.util.ArrayList;
import java.util.List;

public class ResultSeachFragment extends Fragment implements IMyOnClickListener {
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

        // Chuyển songList sang ArrayList
        ArrayList<Song> arrayListSong = new ArrayList<>(songList);

        // Cài đặt RecyclerView
        adapter = new ResultSearchSongAdapter(getActivity(), arrayListSong, this); // Truyền "this" cho IMyOnClickListener
        recyclerViewSongs.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewSongs.setAdapter(adapter);
    }

    @Override
    public void myOnClick(View view, Song song) {
        // Xử lý khi người dùng nhấp vào một bài hát
        Toast.makeText(getActivity(), "Bài hát: " + song.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void myClickToSendArrayList(int position, ArrayList<Song> songList) {
        Intent intent = new Intent(getActivity(), PlaySongActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("songList", songList);
        startActivity(intent);
    }
}
