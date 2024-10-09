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
import com.example.appmusic.models.ItemLibraries;

import java.util.List;

public class AdapterRecyLibraries extends RecyclerView.Adapter<AdapterRecyLibraries.ItemLibrariesViewHolder> {

    private Context context;
    private List<ItemLibraries> libraries;

    public AdapterRecyLibraries(Context context, List<ItemLibraries> libraries) {
        this.context = context;
        this.libraries = libraries;
    }

    @NonNull
    @Override
    public ItemLibrariesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_libraries,viewGroup,false);
        return new ItemLibrariesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemLibrariesViewHolder holder, int position) {
        ItemLibraries library = libraries.get(position);
        holder.tv1.setText(library.getNameItem());
        holder.tv2.setText(library.getNumSong());
        holder.img.setImageResource(library.getIconItem());
    }

    @Override
    public int getItemCount() {
        if(libraries!=null) return libraries.size();
        return 0;
    }

    public class ItemLibrariesViewHolder extends RecyclerView.ViewHolder {
        TextView tv1,tv2;
        ImageView img;
        public ItemLibrariesViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1=itemView.findViewById(R.id.nameItem_libraries);
            tv2=itemView.findViewById(R.id.numSong_libraries);
            img=itemView.findViewById(R.id.icon_libraries);
        }
    }
}
