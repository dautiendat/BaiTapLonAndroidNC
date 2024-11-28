package com.example.appmusic.activities;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import android.os.IBinder;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appmusic.MediaPlayerService;
import com.example.appmusic.R;
import com.example.appmusic.StorageSong;
import com.example.appmusic.adapters.PlaySongAdapter;
import com.example.appmusic.models.Song;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.SimpleFormatter;

public class PlaySongActivity extends AppCompatActivity {
    private ArrayList<Song> songArrayList;
    private ImageView imgSong, imgNext,imgPre,imgPlay_Pause, imgMinimized;
    private RecyclerView recyclerView;
    private PlaySongAdapter adapter;
    private SeekBar seekBar;
    private TextView tvNameSong, tvArtist, totalDurationSong, durationSong;
    private MediaPlayerService player;
    private MediaPlayer mediaPlayer;
    boolean serviceBound = false;
    boolean play_pauseState = true;
    private int songIndex;
    public static final String PLAY_NEW_S0NG_ACTION="com.example.appmusic.PLAY_NEW_SONG";
    public static final String ACTION_PLAY_PAUSE = "ACTION_PLAY_PAUSE";
    public static final String ACTION_PREVIOUS = "ACTION_PREVIOUS";
    public static final String ACTION_NEXT = "ACTION_NEXT";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String UPDATE_SEEKBAR = "UPDATE_SEEKBAR";

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
        songIndex = getIntent().getIntExtra("position",-1);
        songArrayList = (ArrayList<Song>) getIntent().getSerializableExtra("songList");

        if(songArrayList!=null){
            Song song = songArrayList.get(songIndex);
            Glide.with(this).load(song.getImageUrl()).into(imgSong);
            tvNameSong.setText(song.getName());
            tvArtist.setText(song.getArtist());
            //phát bài hát
            playAudio(songIndex);
            registerUpdateSeekBarReceiver();
            totalDurationOfSong();
            loadQueueAdapter();
        }

        onChangeSeekbar();
        imgMinimized.setOnClickListener(v->{
            
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ACTION_NEXT);
                sendBroadcast(intent);
                if(songIndex < songArrayList.size()-1){
                    Song song = songArrayList.get(++songIndex);
                    Glide.with(getApplicationContext()).load(song.getImageUrl()).into(imgSong);
                    tvNameSong.setText(song.getName());
                    tvArtist.setText(song.getArtist());
                }else{
                    songIndex=0;
                    Song song = songArrayList.get(songIndex);
                    Glide.with(getApplicationContext()).load(song.getImageUrl()).into(imgSong);
                    tvNameSong.setText(song.getName());
                    tvArtist.setText(song.getArtist());
                }
                play_pauseState = true;
                imgPlay_Pause.setImageResource(R.drawable.pause_icon);
            }
        });
        imgPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ACTION_PREVIOUS);
                sendBroadcast(intent);
                if(songIndex == 0){
                    songIndex=songArrayList.size()-1;
                    Song song = songArrayList.get(songIndex);
                    Glide.with(getApplicationContext()).load(song.getImageUrl()).into(imgSong);
                    tvNameSong.setText(song.getName());
                    tvArtist.setText(song.getArtist());
                }else{
                    Song song = songArrayList.get(--songIndex);
                    Glide.with(getApplicationContext()).load(song.getImageUrl()).into(imgSong);
                    tvNameSong.setText(song.getName());
                    tvArtist.setText(song.getArtist());
                }
                play_pauseState = true;
                imgPlay_Pause.setImageResource(R.drawable.pause_icon);
            }
        });
        imgPlay_Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!play_pauseState){
                    play_pauseState = true;
                    imgPlay_Pause.setImageResource(R.drawable.pause_icon);
                }
                else {
                    play_pauseState = false;
                    imgPlay_Pause.setImageResource(R.drawable.play_arrow);
                }
                Intent intent = new Intent(ACTION_PLAY_PAUSE);
                sendBroadcast(intent);
            }
        });
    }
    private BroadcastReceiver updateSeekBarReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int currentPos = intent.getIntExtra("currentPosition",-1);
            seekBar.setProgress(currentPos);
            durationSong.setText(formatTime(currentPos));
            Log.e("duration",String.valueOf(currentPos));
        }
    };
    private void registerUpdateSeekBarReceiver(){
        IntentFilter filter = new IntentFilter(MediaPlayerService.UPDATE_SEEKBAR);
        registerReceiver(updateSeekBarReceiver,filter,RECEIVER_EXPORTED);
    }
    private String formatTime(int timeInMillis) {
        int duration = timeInMillis / 1000;
        int minutes = duration / 60;
        int seconds = duration % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    //tổng thời lượng bài hát
    private void totalDurationOfSong(){
        long duration = songArrayList.get(songIndex).getTotalDuration();
        seekBar.setMax((int) duration);
        totalDurationSong.setText(formatTime((int) duration));
    }
    private void onChangeSeekbar(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean user) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    private void loadQueueAdapter(){
        LinearLayoutManager linear = new LinearLayoutManager(this
                ,LinearLayoutManager.VERTICAL,false);
        adapter = new PlaySongAdapter(songArrayList,this);
        recyclerView.setLayoutManager(linear);
        recyclerView.setAdapter(adapter);
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
        seekBar=findViewById(R.id.seekBarSong);
        tvNameSong=findViewById(R.id.tvNameSong_ActiPlaySong);
        imgNext = findViewById(R.id.playNextSong_icon);
        imgPre = findViewById(R.id.playPreSong_icon);
        imgPlay_Pause = findViewById(R.id.playSong_icon);
        tvArtist = findViewById(R.id.tvNameArtist_ActiPlaySong);
        recyclerView = findViewById(R.id.recy_ActiPlaySong);
        totalDurationSong = findViewById(R.id.totalDuration_song);
        durationSong = findViewById(R.id.duration_song);
        imgMinimized =findViewById(R.id.imgToMinimizePlayer);
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
        unregisterReceiver(updateSeekBarReceiver);
    }
}