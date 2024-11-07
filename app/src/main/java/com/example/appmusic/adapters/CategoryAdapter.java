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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<ItemSearch> categories;
    private Context context;

    public CategoryAdapter(Context context,List<ItemSearch> categories) {
        this.categories = categories;
        this.context=context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        ItemSearch category = categories.get(position);
        holder.imageViewCategory.setImageResource(category.getImageResId());
        holder.textViewCategory.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCategory;
        TextView textViewCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCategory = itemView.findViewById(R.id.imageViewCategory);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
        }
    }
}
