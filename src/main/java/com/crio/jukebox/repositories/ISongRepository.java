package com.crio.jukebox.repositories;

import java.util.List;
import java.util.Optional;

import com.crio.jukebox.entities.Song;

public interface ISongRepository {
    List<Song> getAllSongs();
    void saveSong(Song song);
    Optional<Song> findById(String id);
    List<Song> ReturnLstSongs(List<String> songids);
    List<String> getSongIds (List<Song> songs);
}
