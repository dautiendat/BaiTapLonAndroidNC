package com.example.appmusic.activities;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.os.IBinder;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.appmusic.MediaPlayerService;
import com.example.appmusic.R;

public class PlaySongActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private ImageView imgSong;
    private TextView textView;
    private ProgressBar progressBar;
    private MediaPlayerService player;
    boolean serviceBound = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //ánh xạ
        initViews();
        //lấy ảnh bài hát
        String imageUrl = getIntent().getStringExtra("imageUrl");
        //load ảnh lên imageview
        Glide.with(this).load(imageUrl).into(imgSong);
        //lấy file bài hát
        String songUrl = getIntent().getStringExtra("songUrl");
        //phát bài hát
        playAudio(songUrl);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        outState.putBoolean("ServiceState", serviceBound);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        serviceBound=savedInstanceState.getBoolean("ServiceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void initViews(){
        imgSong = findViewById(R.id.imgSong_ActiPlaySong);
        progressBar=findViewById(R.id.seekBarSong);
        textView=findViewById(R.id.tvNameSong_ActiPlaySong);
    }
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) iBinder;
            player=binder.getService();
            serviceBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            serviceBound=false;
        }
    };
    private void playAudio(String mediaUrl){
        if(!serviceBound){
            Intent intent = new Intent(this, MediaPlayerService.class);
            intent.putExtra("mediaFile",mediaUrl);
            startService(intent);
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceBound){
            unbindService(serviceConnection);
            player.stopSelf();
        }
    }
}