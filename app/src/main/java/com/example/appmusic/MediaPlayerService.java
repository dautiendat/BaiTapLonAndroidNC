package com.example.appmusic;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;


public class MediaPlayerService extends Service implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnInfoListener,
        MediaPlayer.OnBufferingUpdateListener, AudioManager.OnAudioFocusChangeListener {

    private final IBinder iBinder = new LocalBinder();
    private MediaPlayer mediaPlayer;
    private String mediaFile;
    //lưu trạng thái pause bài hát để tiếp tục phát cho những lần sau
    private int resumePosition;
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
            mediaPlayer.setDataSource(mediaFile);
        }catch (IOException e){
            e.printStackTrace();
            stopSelf();
        }
        //chuẩn bị phát nhạc (tải nhạc) -> ko làm gián đoạn app (app tiếp tục chạy)
        mediaPlayer.prepareAsync();
    }
    private void playMedia(){
        if(!mediaPlayer.isPlaying())
            mediaPlayer.start();
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
    private void resumeMedia(){
        if (!mediaPlayer.isPlaying()){
            mediaPlayer.seekTo(resumePosition);
            mediaPlayer.start();
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onAudioFocusChange(int i) {

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

    //service life cycle
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            //lấy file nhạc qua key "mediaFile"
            mediaFile=intent.getStringExtra("mediaFile");
        }catch (NullPointerException e){
            stopSelf();
        }
        if(mediaFile !=null && mediaFile!=""){
            initMediaPlayer();
        }

        return super.onStartCommand(intent, flags, startId);
    }
    //hủy service để app không có quyền kiểm soát media từ thiết bị người dùng
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            stopMedia();
            mediaPlayer.release();
        }
    }
}
