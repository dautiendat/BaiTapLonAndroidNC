package com.example.appmusic.models;

public class MusicItem {

    private int musicImage;
    private String musicName;

    public MusicItem(int musicImage, String musicName) {
        this.musicImage = musicImage;
        this.musicName = musicName;
    }

    public int getMusicImage() {
        return musicImage;
    }

    public void setMusicImage(int musicImage) {
        this.musicImage = musicImage;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }
}
