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

public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.FrameViewHolder>{
    private List<ItemSearch> listSong;
    private Context context;

    public FrameAdapter(Context context,List<ItemSearch> listSong) {
        this.context=context;
        this.listSong = listSong;
    }

    @NonNull
    @Override
    public FrameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_recently_song,viewGroup,false);
        return new FrameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FrameViewHolder holder, int i) {
        ItemSearch itemSong = listSong.get(i);
        Glide.with(this.context).load(itemSong.getImageUrl()).into(holder.imgSong);
        holder.tvSong.setText(itemSong.getName());
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
}
