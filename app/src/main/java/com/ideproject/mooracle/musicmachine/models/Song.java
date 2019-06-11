package com.ideproject.mooracle.musicmachine.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {
    private long id;
    private String title;
    private int duration;
    private String artist;
    private String label;
    private int yearReleased;
    private long albumId;
    private boolean isFavorite;

    public Song(long id, String title, int duration, String artist, String label, int yearReleased, long albumId, boolean isFavorite) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.artist = artist;
        this.label = label;
        this.yearReleased = yearReleased;
        this.albumId = albumId;
        this.isFavorite = isFavorite;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getYearReleased() {
        return yearReleased;
    }

    public void setYearReleased(int yearReleased) {
        this.yearReleased = yearReleased;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return  " id=" + id + '\n' +
                ", title='" + title + '\n' +
                ", duration=" + duration + '\n' +
                ", artist='" + artist + '\n' +
                ", label='" + label + '\n' +
                ", yearReleased=" + yearReleased + '\n' +
                ", albumId=" + albumId + '\n' +
                ", isFavorite=" + isFavorite ;
    }

    protected Song(Parcel in) {
        id = in.readLong();
        title = in.readString();
        duration = in.readInt();
        artist = in.readString();
        label = in.readString();
        yearReleased = in.readInt();
        albumId = in.readLong();
        isFavorite = (in.readInt() != 0);
        //NOTE: the isFavorite is sent as int with 1 is Favorite and 0 is not thus this code test that
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    @Override
    public int describeContents() {
        return 0; //ignored
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeInt(duration);
        dest.writeString(artist);
        dest.writeString(label);
        dest.writeInt(yearReleased);
        dest.writeLong(albumId);
        dest.writeInt(isFavorite? 1: 0);
        //NOTE: boolean favorite is replaced by int 1 id it isFavorite and 0 if it is not
    }

}
