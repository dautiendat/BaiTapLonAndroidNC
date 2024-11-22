package com.example.appmusic.activities;

import android.os.Bundle;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.appmusic.R;

public class PlaySongActivity extends AppCompatActivity {
    private ImageView imgSong;
    private TextView textView;
    private ProgressBar progressBar;
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

        String imageUrl = getIntent().getStringExtra("imageUrl");
        Glide.with(this).load(imageUrl).into(imgSong);
//        int pos = getIntent().getIntExtra("int",-1);
//        if(pos < 0)
//            Toast.makeText(this,"nothing",Toast.LENGTH_LONG).show();
//        else
//            textView.setText(pos+"");
    }
    private void initViews(){
        imgSong = findViewById(R.id.imgSong_ActiPlaySong);
        progressBar=findViewById(R.id.seekBarSong);
        textView=findViewById(R.id.tvNameSong_ActiPlaySong);
    }
}