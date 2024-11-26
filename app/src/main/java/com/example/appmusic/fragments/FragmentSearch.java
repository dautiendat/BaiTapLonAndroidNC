package com.example.appmusic.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusic.R;
import com.example.appmusic.adapters.CategoryAdapter;
import com.example.appmusic.adapters.TrendingArtistAdapter;
import com.example.appmusic.models.Song;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FragmentSearch extends Fragment {
    private RecyclerView recyclerView1, recyclerView2;
    private TrendingArtistAdapter adapterArtist;
    private CategoryAdapter adapterCategory;
    private SearchView searchView;
    private List<Song> artistList;
    private List<Song> categoryList;
    private List<Song> allSongs; // Danh sách bài hát toàn bộ



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView1 = view.findViewById(R.id.recyclerViewTrendingArtists);
        recyclerView2 = view.findViewById(R.id.recyclerViewCategories);
        searchView = view.findViewById(R.id.searchView);

        // Khởi tạo danh sách
        artistList = new ArrayList<>();
        categoryList = new ArrayList<>();
        allSongs = new ArrayList<>();

        // Cài đặt RecyclerView
        LinearLayoutManager linear = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(linear);
        adapterArtist = new TrendingArtistAdapter(getActivity(), artistList);
        recyclerView1.setAdapter(adapterArtist);

        GridLayoutManager grid = new GridLayoutManager(getActivity(), 2);
        recyclerView2.setLayoutManager(grid);
        adapterCategory = new CategoryAdapter(getActivity(), categoryList);
        recyclerView2.setAdapter(adapterCategory);

        // Tải dữ liệu từ Firebase
        fetchArtistsFromFirebase();
        fetchCategoriesFromFirebase();
        fetchAllSongsFromFirebase();


    // Xử lý tìm kiếm
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            navigateToResultFragment(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    });
        searchView.setOnCloseListener(() -> {
            // Kiểm tra nếu ResultSearchFragment đang hiển thị
            if (getChildFragmentManager().getBackStackEntryCount() > 0) {
                getChildFragmentManager().popBackStack(); // Quay lại fragment trước đó
                View resultContainer = getView().findViewById(R.id.result_container);
                if (resultContainer != null) {
                    resultContainer.setVisibility(View.GONE); // Ẩn container (nếu cần)
                }
            }
            return true; // Xử lý sự kiện đã hoàn tất
        });
}

    private void fetchAllSongsFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("FrameList/2/listSongs");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allSongs.clear();
                for (DataSnapshot songSnapshot : snapshot.getChildren()) {
                    String name = songSnapshot.child("name").getValue(String.class);
                    String imageUrl = songSnapshot.child("imageUrl").getValue(String.class);
                    String songFileUrl = songSnapshot.child("songFileUrl").getValue(String.class);
                    if (name != null && imageUrl != null) { // Kiểm tra null
                        allSongs.add(new Song(name, imageUrl,songFileUrl));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error: " + error.getMessage());
            }
        });
    }

    private void navigateToResultFragment(String query) {
        List<Song> filteredSongs = new ArrayList<>();

        for (Song song : allSongs) {
            if (song.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredSongs.add(song);
            }
        }

        // Gửi dữ liệu qua Bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable("songs", (Serializable) filteredSongs);

        ResultSeachFragment resultFragment = new ResultSeachFragment();
        resultFragment.setArguments(bundle);

        // Hiển thị ResultSearchFragment
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.result_container, resultFragment);
        transaction.addToBackStack(null); // Cho phép quay lại
        transaction.commit();

        // Hiển thị FrameLayout
        View resultContainer = getView().findViewById(R.id.result_container);
        if (resultContainer != null) {
            resultContainer.setVisibility(View.VISIBLE);
        }
    }


    private void fetchArtistsFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("FrameList/1/listSongs");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                artistList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
                    if (name != null && imageUrl != null) {
                        artistList.add(new Song(name, imageUrl));
                    }
                }
                adapterArtist.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Failed to read value: " + error.toException());
            }
        });
    }

    private void fetchCategoriesFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("FrameList/0/listSongs");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
                    if (name != null && imageUrl != null) {
                        categoryList.add(new Song(name, imageUrl));
                    }
                }
                adapterCategory.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Failed to read value: " + error.toException());
            }
        });
    }
}
