package com.example.appmusic.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusic.R;
import com.example.appmusic.models.Artist;

import java.util.List;

public class TrendingArtistAdapter extends RecyclerView.Adapter<TrendingArtistAdapter.ArtistViewHolder> {

    private List<Artist> artists;

    public TrendingArtistAdapter(List<Artist> artists) {
        this.artists = artists;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trending_artist, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        Artist artist = artists.get(position);
        holder.imageViewArtist.setImageResource(artist.getImageResId());  // Đặt ảnh nghệ sĩ
        holder.textViewArtistName.setText(artist.getName());               // Đặt tên nghệ sĩ
    }

    @Override
    public int getItemCount() {
        return artists.size();
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


