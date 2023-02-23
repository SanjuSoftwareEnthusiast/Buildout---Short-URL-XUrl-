package com.crio.jukebox.entities;

import java.util.LinkedList;
import java.util.List;

public class Playlist extends BaseEntity{
    private final String playListName;
    private final LinkedList<Song> songs;
    private final User creator;
    private PlaylistStatus playlistStatus;
    private int indexOfCurrentSongPlaylist;

    public Playlist(String id,String playlistName,List<Song> songs,User creator,PlaylistStatus playlistStatus,int indexOfCurrentSongPlaylist){
        this(playlistName,songs,creator,playlistStatus,indexOfCurrentSongPlaylist);
        this.id = id;
    }

    public Playlist(String playlistName,List<Song> songs,User creator,PlaylistStatus playlistStatus,int indexOfCurrentSongPlaylist){
        this.playListName = playlistName;
        this.songs = (LinkedList<Song>)songs;
        this.creator = creator;
        this.playlistStatus = playlistStatus;
        this.indexOfCurrentSongPlaylist =indexOfCurrentSongPlaylist;
    }

    public String getPlaylistName() {
        return playListName;
    }

    public LinkedList<Song> getSongs() {
        return songs;
    } 

    public User getCreator() {
        return creator;
    }

    public PlaylistStatus getPlaylistStatus() {
        return playlistStatus;
    }

    public int getIndexOfCurrentSongPlaylist() {
        return indexOfCurrentSongPlaylist;
    }

    public void playingPlaylist() {
        this.playlistStatus = PlaylistStatus.PLAYING;
    }

    public void setIndexOfCurrentSongPlaylist(int indexOfCurrentSongPlaylist){
        this.indexOfCurrentSongPlaylist = indexOfCurrentSongPlaylist;
    }

    @Override
    public String toString(){
        return "Playlist [id="+id+", playlistName=" + playListName +  ", creator=" + creator.getName() + ", songs="+ songs + ", playlistStatus" + "]";
    }
}