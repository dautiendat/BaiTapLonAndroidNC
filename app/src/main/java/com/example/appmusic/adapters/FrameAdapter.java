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
import com.example.appmusic.R;
import com.example.appmusic.models.ItemSearch;

import java.util.List;

public class FrameAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<ItemSearch> listSong;
    private Context context;
    private int layoutType;

    public FrameAdapter(Context context,List<ItemSearch> listSong, int layoutType) {
        this.context=context;
        this.listSong = listSong;
        this.layoutType=layoutType;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //nếu là vị trí chẵn
        if(layoutType % 2 == 0){
            View view = LayoutInflater.from(context).inflate(R.layout.item_recently_song
                    ,parent,false);
            return new FrameViewHolder(view);
        }
        // nếu là vị trí lẻ
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.item_song_play_list
                    ,parent,false);
            return new FrameViewHolder2(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemSearch itemSearch = listSong.get(position);
        if(holder instanceof FrameViewHolder){
            Glide.with(context).load(itemSearch.getImageUrl()).into(((FrameViewHolder) holder).imgSong);
            ((FrameViewHolder) holder).tvSong.setText(itemSearch.getName());
        }else{
            Glide.with(context).load(itemSearch.getImageUrl()).into(((FrameViewHolder2) holder).imgSong);
            ((FrameViewHolder2) holder).tvSong.setText(itemSearch.getName());
        }
    }

    @Override
    public int getItemCount() {
        if (listSong != null)
            return listSong.size();
        return 0;
    }

    public class FrameViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSong;
        TextView tvSong;

        public FrameViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSong = itemView.findViewById(R.id.imgSong);
            tvSong = itemView.findViewById(R.id.nameSong);
        }
    }
    public class FrameViewHolder2 extends RecyclerView.ViewHolder {
        ImageView imgSong;
        TextView tvSong;

        public FrameViewHolder2(@NonNull View itemView) {
            super(itemView);
            imgSong = itemView.findViewById(R.id.imgSongPlayList);
            tvSong = itemView.findViewById(R.id.nameSongPlayList);
        }
    }
}
