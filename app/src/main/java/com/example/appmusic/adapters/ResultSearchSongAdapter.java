package com.example.appmusic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appmusic.IMyOnClickListener;
import com.example.appmusic.R;
import com.example.appmusic.models.Song;

import java.util.ArrayList;
import java.util.List;

public class ResultSearchSongAdapter extends RecyclerView.Adapter<ResultSearchSongAdapter.ResultSearchViewHolder> {

    private Context context;
    private List<Song> songList;
    private ArrayList<Song> listSong;
    private IMyOnClickListener myOnClick;

    public ResultSearchSongAdapter(Context context, ArrayList<Song> songList, IMyOnClickListener myOnClick) {
        this.context = context;
        this.songList = songList;
        this.myOnClick = myOnClick;
    }
    public ResultSearchSongAdapter(Context context, List<Song> songList) {
        this.context = context;
        this.songList = songList;
    }
    @NonNull
    @Override
    public ResultSearchSongAdapter.ResultSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_result_search, parent, false);
        return new ResultSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultSearchSongAdapter.ResultSearchViewHolder holder, int position) {
        Song song = songList.get(position);

        holder.tvName.setText(song.getName());
        Glide.with(context).load(song.getImageUrl()).into(holder.imgThumbnail); // Sử dụng Glide để tải ảnh
        // Gắn sự kiện click cho itemView
        holder.itemView.setOnClickListener(v -> {
            if (myOnClick != null) {
                myOnClick.myClickToSendArrayList(position, new ArrayList<>(songList)); // Gửi danh sách và vị trí
            }
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class ResultSearchViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView imgThumbnail;

        public ResultSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            imgThumbnail = itemView.findViewById(R.id.imgThumbnail);
        }
    }
}
