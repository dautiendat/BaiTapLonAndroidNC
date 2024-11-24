package com.example.appmusic.models;

import java.util.List;

public class Frame {
    private String nameFrame;
    private List<Song> listSongs;
    private int typeFrame;
    public Frame() {

    }

    public Frame(String nameFrame, List<Song> listSongs) {
        this.nameFrame = nameFrame;
        this.listSongs = listSongs;
    }

    public String getNameFrame() {
        return nameFrame;
    }

    public void setNameFrame(String nameFrame) {
        this.nameFrame = nameFrame;
    }

    public List<Song> getListSongs() {
        return listSongs;
    }

    public void setListSongs(List<Song> listSongs) {
        this.listSongs = listSongs;
    }

    public int getTypeFrame() {
        return typeFrame;
    }

    public void setTypeFrame(int typeFrame) {
        this.typeFrame = typeFrame;
    }
}
