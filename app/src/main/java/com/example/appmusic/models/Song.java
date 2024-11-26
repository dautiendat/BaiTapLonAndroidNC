package com.example.appmusic.models;


import java.io.Serializable;

public class Song implements Serializable {
    private String name; // Tên nghệ sĩ
    private String imageUrl; // ID của hình ảnh bai hat

    public Song() {
    }

    public Song(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
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

}
