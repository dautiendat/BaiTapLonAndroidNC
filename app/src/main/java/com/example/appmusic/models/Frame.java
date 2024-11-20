package com.example.appmusic.models;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.PropertyName;

import java.util.List;

public class Frame {
    private String nameFrame;
    private List<ItemSearch> listSongs;
    private int typeFrame;
    public Frame() {

    }

    public Frame(String nameFrame, List<ItemSearch> listSongs) {
        this.nameFrame = nameFrame;
        this.listSongs = listSongs;
    }

    public String getNameFrame() {
        return nameFrame;
    }

    public void setNameFrame(String nameFrame) {
        this.nameFrame = nameFrame;
    }

    public List<ItemSearch> getListSongs() {
        return listSongs;
    }

    public void setListSongs(List<ItemSearch> listSongs) {
        this.listSongs = listSongs;
    }

    public int getTypeFrame() {
        return typeFrame;
    }

    public void setTypeFrame(int typeFrame) {
        this.typeFrame = typeFrame;
    }
}
