package com.example.appmusic.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appmusic.R;
import com.example.appmusic.adapters.CategoryAdapter;
import com.example.appmusic.adapters.TrendingArtistAdapter;
import com.example.appmusic.models.Artist;
import com.example.appmusic.models.Category;

import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerViewTrendingArtists;
    private RecyclerView recyclerViewCategories;
    private TrendingArtistAdapter trendingArtistAdapter;
    private CategoryAdapter categoryAdapter;
    private List<Artist> trendingArtists;
    private List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Trending artists RecyclerView
        recyclerViewTrendingArtists = findViewById(R.id.recyclerViewTrendingArtists);
        trendingArtists = getTrendingArtists();
        trendingArtistAdapter = new TrendingArtistAdapter(trendingArtists);
        LinearLayoutManager horizontalLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTrendingArtists.setLayoutManager(horizontalLayout);
        recyclerViewTrendingArtists.setAdapter(trendingArtistAdapter);

        // Categories RecyclerView
        recyclerViewCategories = findViewById(R.id.recyclerViewCategories);
        categories = getCategories();
        categoryAdapter = new CategoryAdapter(categories);
        recyclerViewCategories.setLayoutManager(new GridLayoutManager(this, 2));  // 2 columns
        recyclerViewCategories.setAdapter(categoryAdapter);
    }

    private List<Artist> getTrendingArtists() {
        // Dummy data for trending artists
        return Arrays.asList(
                new Artist("Childish Gambino", R.drawable.cat),
                new Artist("Marvin Gaye", R.drawable.cat),
                new Artist("Kanye West", R.drawable.cat),
                new Artist("Justin Bieber", R.drawable.cat)
        );
    }

    private List<Category> getCategories() {
        // Dummy data for categories
        return Arrays.asList(
                new Category("Tamil", R.drawable.vpop_artists),
                new Category("International", R.drawable.vpop_artists),
                new Category("Pop", R.drawable.vpop_artists),
                new Category("Hip-hop", R.drawable.vpop_artists),
                new Category("Dance", R.drawable.vpop_artists),
                new Category("Country", R.drawable.vpop_artists),
                new Category("Indie", R.drawable.vpop_artists),
                new Category("Jazz", R.drawable.vpop_artists)
        );
    }
}