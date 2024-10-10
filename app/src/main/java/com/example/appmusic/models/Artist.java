package com.example.appmusic.models;

public class Artist {
    private String name; // Tên nghệ sĩ
    private int imageResId; // ID của hình ảnh trong drawable

    // Constructor
    public Artist(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    // Getter cho tên nghệ sĩ
    public String getName() {
        return name;
    }

    // Getter cho hình ảnh nghệ sĩ
    public int getImageResId() {
        return imageResId;
    }
}

