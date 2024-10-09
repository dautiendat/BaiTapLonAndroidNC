package com.example.appmusic.models;

public class Category {
    private String name; // Tên danh mục
    private int imageResId; // ID của hình ảnh trong drawable

    // Constructor
    public Category(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    // Getter cho tên danh mục
    public String getName() {
        return name;
    }

    // Getter cho hình ảnh của danh mục
    public int getImageResId() {
        return imageResId;
    }
}
