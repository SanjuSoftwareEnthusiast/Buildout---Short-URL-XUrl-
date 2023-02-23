package com.crio.jukebox.entities;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class User extends BaseEntity{
    private final String name;
    private final List<Playlist> playlists;
    
    public String getName() {
        return name;
    }

    public User(String id,String name){
        this(name);
        this.id=id;
    }

    public User(String name){
        this.name =name;
        this.playlists = new LinkedList<>();
    }

    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    @Override
    public String toString(){
        return id + " " + name;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public Optional<Playlist> getPlaylist_playing() {
        return playlists.stream().filter(i->i.getPlaylistStatus()==PlaylistStatus.PLAYING).findFirst();
    }

    public void deletePlaylist(Playlist playlist){
        playlists.removeIf(c->c.getId() == playlist.getId());
    }

}
