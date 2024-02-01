package vn.id.music.models;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.Serializable;

public class Song implements Serializable {
    private String name;
    private String artist;
    private int songResourceId;
    private int duration;
    private int image;
    private String category;

    public Song(String name, String artist, int songResourceId, int image, String category) {
        this.name = name;
        this.artist = artist;
        this.songResourceId = songResourceId;
        this.image = image;
        this.category = category;
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

    public int getSongResourceId() {
        return songResourceId;
    }

    public void setSongResourceId(int songResourceId) {
        this.songResourceId = songResourceId;
    }

    public int getDuration(Context context) {
        MediaPlayer player = MediaPlayer.create(context, songResourceId);
        this.duration = player.getDuration();
        return duration;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
