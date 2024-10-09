package com.example.appmusic.models;

public class MusicItem {

    private int musicImage;
    private String musicName;
    private String musicArtist;
    public MusicItem(int musicImage, String musicName,String musicArtist) {
        this.musicImage = musicImage;
        this.musicName = musicName;
        this.musicArtist=musicArtist;
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

    public String getMusicArtist() {
        return musicArtist;
    }

    public void setMusicArtist(String musicArtist) {
        this.musicArtist = musicArtist;
    }
}
