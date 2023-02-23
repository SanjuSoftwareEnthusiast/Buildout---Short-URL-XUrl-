package com.crio.jukebox.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.crio.jukebox.entities.Playlist;

public class PlayListRepository implements IPlayListRepository {
    private final Map<String,Playlist> playListMap;
    private Integer autoIncrement = 0;

    public PlayListRepository() {
        playListMap = new HashMap<String,Playlist>();
    }

    public PlayListRepository(Map<String,Playlist> playListMap) {
        this.playListMap = playListMap;
        this.autoIncrement = playListMap.size();
    }

    @Override
    public Playlist savePlayList(Playlist playList) {
        if (playList.getId() == null){
            autoIncrement++;
            Playlist p = new Playlist(Integer.toString(autoIncrement), playList.getPlaylistName(), playList.getSongs(), playList.getCreator(), playList.getPlaylistStatus(), playList.getIndexOfCurrentSongPlaylist());
            playListMap.put(p.getId(), p);
            return p;
        }
        playListMap.put(playList.getId(), playList);
        return playList;
    }

    @Override
    public Optional<Playlist> findByName(String name) {
        List<Playlist> playlists = new ArrayList<>(playListMap.values());
        Optional<Playlist> playlist = playlists.stream().filter(i->i.getPlaylistName().equals(name)).findAny();
        return playlist;
    }

    @Override
    public Optional<Playlist> findById(String id) {
        return Optional.ofNullable(playListMap.get(id));
    }

    @Override
    public void deleteById(String id) {
        playListMap.remove(id);
    }
}
