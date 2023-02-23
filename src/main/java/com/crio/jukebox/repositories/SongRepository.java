package com.crio.jukebox.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.exceptions.InvalidSongException;

public class SongRepository implements ISongRepository {

    private final LinkedList<Song> songList;
    private Integer autoIncrement = 0;

    public SongRepository() {
        songList = new LinkedList<Song>();
    }

    @Override
    public List<Song> getAllSongs() {
        return songList;
    }

    @Override
    public void saveSong(Song song) {
        if(song.getId()==null){
            autoIncrement++;
            Song s=new Song(Integer.toString(autoIncrement), song.getSongName(),song.getGenre(),song.getAlbumName(),song.getAlbumOwner(),song.getArtists());
            songList.add(s);
        }
    }

    @Override
    public Optional<Song> findById(String id) {
        Song song = songList.get(Integer.parseInt(id));
        return Optional.ofNullable(song);
    }

    @Override
    public List<Song> ReturnLstSongs(List<String> songids) {
        List<Song> lstSong = new LinkedList<>();
        for(String element:songids){
            Song song = findById(element).orElseThrow(()-> new InvalidSongException("Song not found for given id:" + element));
            lstSong.add(song);
        }
        return lstSong;
    }

    @Override
    public List<String> getSongIds(List<Song> songs) {
        List<String> songlst = new LinkedList<>();
        for(Song song: songs){
            songlst.add(song.getId());
        }
        return songlst; 
    }
    
}
