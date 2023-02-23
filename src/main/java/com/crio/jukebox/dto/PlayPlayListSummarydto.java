package com.crio.jukebox.dto;


public class PlayPlayListSummarydto {
    private final String songName;
    private final String albumName;
    private final String artists;

    public PlayPlayListSummarydto (String songName,String albumName,String artists) {
        this.songName = songName;
        this.albumName = albumName;
        this.artists = artists;
    }

    public String getSongName() {
        return songName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getArtists() {
        return artists;
    }

    @Override
    public String toString() {
        return "Current Song Playing"+"\n"+"Song - "+songName+"\n"+"Album - " + albumName + "\n" + "Artists - " + artists +"\n";
    }
}