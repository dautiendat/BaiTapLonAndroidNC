package com.example.appmusic.activities;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.os.IBinder;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.appmusic.MediaPlayerService;
import com.example.appmusic.R;
import com.example.appmusic.StorageSong;
import com.example.appmusic.models.Song;

import java.util.ArrayList;

public class PlaySongActivity extends AppCompatActivity {
    private ArrayList<Song> songArrayList;
    private ImageView imgSong;
    private ProgressBar progressBar;
    private TextView tvNameSong;
    private MediaPlayerService player;
    boolean serviceBound = false;
    public static final String PLAY_NEW_S0NG_ACTION="com.example.appmusic.PLAY_NEW_SONG";
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
        //lấy bài hát
        int songIndex = getIntent().getIntExtra("position",-1);
        songArrayList = (ArrayList<Song>) getIntent().getSerializableExtra("songList");
        if(songArrayList!=null){
            Song song = songArrayList.get(songIndex);
            Glide.with(this).load(song.getImageUrl()).into(imgSong);
            tvNameSong.setText(song.getName());
            //phát bài hát
            playAudio(songIndex);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState,
                                    @NonNull PersistableBundle outPersistentState) {
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
        tvNameSong=findViewById(R.id.tvNameSong_ActiPlaySong);
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
    private void playAudio(int songIndex){
        if(!serviceBound){
            StorageSong storage = new StorageSong(getApplicationContext());
            storage.storeSongArrayList(songArrayList);
            storage.storeSongIndex(songIndex);

            Intent intent = new Intent(this, MediaPlayerService.class);
            startService(intent);
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }else{
            StorageSong storage = new StorageSong(getApplicationContext());
            storage.storeSongIndex(songIndex);
            Intent intent = new Intent(PLAY_NEW_S0NG_ACTION);
            sendBroadcast(intent);
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