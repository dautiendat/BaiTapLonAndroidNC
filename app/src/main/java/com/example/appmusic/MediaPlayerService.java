package com.example.appmusic;

import static com.example.appmusic.WidgetNotification.CHANEL_ID;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.session.MediaSessionManager;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.appmusic.activities.MainActivity;
import com.example.appmusic.activities.PlaySongActivity;
import com.example.appmusic.models.Song;

import java.io.IOException;
import java.util.ArrayList;


public class MediaPlayerService extends Service implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnInfoListener,
        MediaPlayer.OnBufferingUpdateListener, AudioManager.OnAudioFocusChangeListener {

    private final IBinder iBinder = new LocalBinder();
    private MediaPlayer mediaPlayer;
    //lưu trạng thái pause bài hát để tiếp tục phát cho những lần sau
    private int resumePosition, currentPosition=0;
    private AudioManager audioManager;
    private AudioFocusRequest request;
    private ArrayList<Song> songArrayList;
    private int songIndex = -1;
    private Song songActive;
    private Handler handler = new Handler();
    public static final String ACTION_PLAY_PAUSE = "ACTION_PLAY_PAUSE";
    public static final String ACTION_PREVIOUS = "ACTION_PREVIOUS";
    public static final String ACTION_NEXT = "ACTION_NEXT";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String UPDATE_SEEKBAR = "UPDATE_SEEKBAR";

    //AudioPlayer notification ID
    private static final int NOTIFICATION_ID = 101;
    private void initMediaPlayer(){
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        mediaPlayer.setOnInfoListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);

        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(songActive.getSongFileUrl());
        }catch (IOException e){
            e.printStackTrace();
            stopSelf();
        }
        //chuẩn bị phát nhạc (tải nhạc) -> ko làm gián đoạn app (app tiếp tục chạy)
        mediaPlayer.prepareAsync();
    }
    public void seekTo(int pos){
        if(mediaPlayer.isPlaying()){
            pos=mediaPlayer.getCurrentPosition();
            mediaPlayer.seekTo(pos);
        }
    }
    private void playMedia(){
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }

    }
    private void stopMedia(){
        if(mediaPlayer==null)return;
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
    }
    private void pauseMedia(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            resumePosition=mediaPlayer.getCurrentPosition();
        }
    }
    private void playPauseMedia(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            resumePosition=mediaPlayer.getCurrentPosition();
        }else{
            mediaPlayer.seekTo(resumePosition);
            mediaPlayer.start();
        }
    }
    private void nextSong(){
        if(songIndex == songArrayList.size()-1){
            songIndex=0;
            songActive=songArrayList.get(songIndex);
        }else songActive = songArrayList.get(++songIndex);

        new StorageSong(getApplicationContext()).storeSongIndex(songIndex);
        stopMedia();
        mediaPlayer.reset();
        initMediaPlayer();
    }
    private void previousSong(){
        if(songIndex == 0){
            songIndex=songArrayList.size()-1;
            songActive=songArrayList.get(songIndex);
        }else songActive = songArrayList.get(--songIndex);
        new StorageSong(getApplicationContext()).storeSongIndex(songIndex);
        stopMedia();
        mediaPlayer.reset();
        initMediaPlayer();
    }
    private Runnable updateSeekbarRunnale = new Runnable() {
        @Override
        public void run() {
            if(mediaPlayer != null && mediaPlayer.isPlaying()){
                currentPosition = mediaPlayer.getCurrentPosition();
                sendSeekbar();
            }
            handler.postDelayed(this,1000);
        }
    };
    private void sendSeekbar(){
        Intent intent = new Intent(UPDATE_SEEKBAR);
        intent.putExtra("currentPosition", currentPosition);
        sendBroadcast(intent);
    }
    private BroadcastReceiver controlReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case ACTION_NEXT:
                    nextSong();
                    break;
                case ACTION_PLAY_PAUSE:
                    playPauseMedia();
                    break;
                case ACTION_PREVIOUS:
                    previousSong();
                    break;
            }
        }
    };
    private void registerControlReceiver(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_NEXT);
        filter.addAction(ACTION_PLAY_PAUSE);
        filter.addAction(ACTION_PREVIOUS);
        registerReceiver(controlReceiver,filter, Context.RECEIVER_EXPORTED);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    //xử lý các sự kiện khi đang phát nhạc
    @Override
    public void onAudioFocusChange(int focusState) {
        switch (focusState){
            //phát lại nhạc đang nghe dở
            case AudioManager.AUDIOFOCUS_GAIN:
                if(mediaPlayer==null) initMediaPlayer();
                else if(!mediaPlayer.isPlaying()) mediaPlayer.start();
                mediaPlayer.setVolume(1.0f,1.0f);
                break;
            //khi ng dùng chuyển qua app khác và phát âm thanh trên app đó -> hủy service để ko phát nhạc
            case AudioManager.AUDIOFOCUS_LOSS:
                if(mediaPlayer.isPlaying()) mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer=null;
                break;
            //khi chuyển qua app khác -> dừng nhạc
            case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                if(mediaPlayer.isPlaying()) mediaPlayer.pause();
                break;
            //khi có âm thanh ngoài app xảy ra (Vd: có thông báo từ app khác) -> giảm nhỏ âm lượng
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                if(mediaPlayer.isPlaying()) mediaPlayer.setVolume(0.1f,0.1f);
                break;
        }
    }

//    private boolean requestAudioFocus(){
//         request = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
//                .setOnAudioFocusChangeListener(this)
//                .build();
//        int result = audioManager.requestAudioFocus(request);
//        if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
//            return true;
//        return false;
//    }
//    private boolean removeAudioFocus(){
//        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED
//                == audioManager.abandonAudioFocusRequest(request);
//    }
    //xử lý khi có thay đổi output (Vd: tháo tai nghe) -> dừng phát nhạc
    private BroadcastReceiver becomingNoisyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            pauseMedia();
        }
    };
    private void registerBecomingNoisyReceiver(){
        IntentFilter intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerReceiver(becomingNoisyReceiver,intentFilter);
    }
    private BroadcastReceiver playNewSong = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            songIndex=new StorageSong(getApplicationContext()).loadSongIndex();
            if(songIndex!=-1 && songIndex < songArrayList.size()){
                songActive = songArrayList.get(songIndex);
            }
            else {
                stopSelf();
            }

            stopMedia();
            mediaPlayer.reset();
            initMediaPlayer();

        }
    };
    private void registerPlayNewSong(){
        IntentFilter filter = new IntentFilter(PlaySongActivity.PLAY_NEW_S0NG_ACTION);
        registerReceiver(playNewSong,filter, Context.RECEIVER_EXPORTED);

    }
    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        //dừng media, service sau khi đã phát hết bài hát
        stopMedia();
        stopSelf();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int error, int code) {
        switch (error){
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Log.e("MediaPlayer Error","MEDIA ERROR NOT VALID FOR PROGRESSIVE PLAYBACK"+code);
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Log.e("MediaPlayer Error","MEDIA ERROR UNKNOWN"+code);
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Log.e("MediaPlayer Error","MEDIA ERROR SERVER DIED"+code);
                break;
        }
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        playMedia();
    }

    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {

    }
    public class LocalBinder extends Binder{
        public MediaPlayerService getService(){
            return MediaPlayerService.this;
        }
    }


//    @SuppressLint("ForegroundServiceType")
//    private void sendNotification(Song song){
//        Intent intent = new Intent(this, PlaySongActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0
//                ,intent,PendingIntent.FLAG_MUTABLE);
////        @SuppressLint("RemoteViewLayout")
////        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.item_minimized_player);
////        remoteViews.setTextViewText(R.id.miniNameSong,song.getName());
////        remoteViews.setTextViewText(R.id.miniArtist,song.getArtist());
////        remoteViews.setImageViewResource(R.id.miniImage,R.drawable.ic_android_black_24dp);
//        Notification notification = new NotificationCompat.Builder(this,CHANEL_ID)
//                //.setCustomContentView(remoteViews)
//                .setSmallIcon(R.drawable.ic_android_black_24dp)
//                .setContentIntent(pendingIntent)
//                .setOngoing(true)
//                .build();
//        startForeground(1,notification);
//
//    }
//    private void removeNotification(){
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.cancelAll();
//    }
    //service life cycle
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            StorageSong storage = new StorageSong(getApplicationContext());
            songArrayList = storage.loadSongArrayList();
            songIndex = storage.loadSongIndex();
            if(songIndex != -1 && songIndex < songArrayList.size())
                songActive = songArrayList.get(songIndex);
            else stopSelf();
        }catch (NullPointerException e){
            stopSelf();
        }
        //if(!requestAudioFocus()) stopSelf();

        if(songActive.getSongFileUrl() !=null && !songActive.getSongFileUrl().isEmpty()){
            initMediaPlayer();
            handler.post(updateSeekbarRunnale);
            //sendNotification(songActive);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerControlReceiver();
        registerBecomingNoisyReceiver();
        registerPlayNewSong();

    }

    //hủy service để app không có quyền kiểm soát media từ thiết bị người dùng
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            stopMedia();
            mediaPlayer.release();
        }
        //removeAudioFocus();
        unregisterReceiver(becomingNoisyReceiver);
        unregisterReceiver(playNewSong);
        unregisterReceiver(controlReceiver);
        //removeNotification();
        handler.removeCallbacks(updateSeekbarRunnale);
        new StorageSong(getApplicationContext()).clearCachedSong();
    }
}
