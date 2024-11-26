package com.example.appmusic.models;

public class Music {

    private String imageUrl;
    private String name;
    private String artist;
    private String path;
    private String duration;

    public Music() {
    }

    public Music(String imageUrl, String name, String artist, String path, String duration) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.artist = artist;
        this.path = path;
        this.duration = duration;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
