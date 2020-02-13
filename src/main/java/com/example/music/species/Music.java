package com.example.music.species;

public class Music {
    private String title; //歌名
    private String artist;//歌手
    private String duration;//总时长
    private String data; //地址

    public Music(String title, String artist, String duration, String data) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.data = data;
    }


    public Music() {
        super();
    }

    @Override
    public String toString() {
        return "Music{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", duration='" + duration + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
