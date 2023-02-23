package com.crio.jukebox.repositories;

import java.util.Optional;
import com.crio.jukebox.entities.Playlist;

public interface IPlayListRepository {
    Playlist savePlayList(Playlist playlist);
    Optional<Playlist> findByName(String name);
    Optional<Playlist> findById(String id);
    public void deleteById(String id);
}
