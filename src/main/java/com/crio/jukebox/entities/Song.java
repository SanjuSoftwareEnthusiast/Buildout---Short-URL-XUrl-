package com.crio.jukebox.entities;

import java.util.List;

public class Song extends BaseEntity{
    private final String songName;
    private final String genre;
    private final String albumName;
    private final String albumOwner;
    private final List<String> artists;
    
    public Song(String id,String songName,String genre,String albumName,String albumOwner,List<String> artists){
        this(songName,genre,albumName,albumOwner,artists);
        this.id = id;
    }

    public Song(String songName,String genre,String albumName,String albumOwner,List<String> artists){
        this.songName = songName;
        this.genre = genre;
        this.albumName = albumName;
        this.albumOwner = albumOwner;
        this.artists = artists;
    }

    public String getId() {
        return id;
    }

    public String getSongName() {
        return songName;
    }

    public String getGenre() {
        return genre;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getAlbumOwner() {
        return albumOwner;
    }

    public List<String> getArtists() {
        return artists;
    }

    @Override
    public String toString() {
        return "Song[id=" + id + "songName=" + songName +  ",genre=" + genre + ", albumName="+ albumName + ", albumOwner=" + albumOwner + ", artists=" + artists + "]";
    }    

}
