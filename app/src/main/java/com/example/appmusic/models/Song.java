package com.example.appmusic.models;


public class Song {
    private String name; // Tên nghệ sĩ
    private String imageUrl; // ID của hình ảnh bai hat
    private String songFileUrl;
    public Song() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
            return name;
        }
    public String getImageUrl() {
        return imageUrl;
    }

    public String getSongFileUrl() {
        return songFileUrl;
    }

    public void setSongFileUrl(String songFileUrl) {
        this.songFileUrl = songFileUrl;
    }
}
