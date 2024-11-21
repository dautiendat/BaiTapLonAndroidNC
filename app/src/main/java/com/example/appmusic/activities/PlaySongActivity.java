package com.example.appmusic.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.appmusic.R;

public class PlaySongActivity extends AppCompatActivity {
    private ImageView imgSong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        byte[] byteArray = getIntent().getByteArrayExtra("image");

        if (byteArray != null) {
            // Chuyển mảng byte thành Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            // Hiển thị Bitmap lên ImageView
            imgSong.setImageBitmap(bitmap);
        }
    }
    private void initViews(){
        imgSong = findViewById(R.id.imgSong_ActiPlaySong);
    }
}