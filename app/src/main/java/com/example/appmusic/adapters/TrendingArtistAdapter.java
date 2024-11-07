package com.example.appmusic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusic.R;
import com.example.appmusic.models.ItemSearch;

import java.util.List;

public class TrendingArtistAdapter extends RecyclerView.Adapter<TrendingArtistAdapter.ArtistViewHolder> {

    private List<ItemSearch> artists;
    private Context context;

    public TrendingArtistAdapter(Context context,List<ItemSearch> artists) {
        this.artists = artists;
        this.context=context;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trending_artist, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        ItemSearch artist = artists.get(position);
        holder.imageViewArtist.setImageResource(artist.getImageResId());  // Đặt ảnh nghệ sĩ
        holder.textViewArtistName.setText(artist.getName());               // Đặt tên nghệ sĩ
    }

    @Override
    public int getItemCount() {
        if(artists!=null)
            return artists.size();
        return 0;
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewArtist;
        TextView textViewArtistName;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewArtist = itemView.findViewById(R.id.imageViewArtist);
            textViewArtistName = itemView.findViewById(R.id.textViewArtistName);
        }
    }
}


