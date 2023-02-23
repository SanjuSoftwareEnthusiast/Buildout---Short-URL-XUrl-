package com.crio.jukebox.dto;

public class ModifyPlayListSummaryDto {
    private final String playlistid;
    private final String playlistname;
    private final String songids;

    public ModifyPlayListSummaryDto(String playlistid,String playlistname,String songids){
        this.playlistid=playlistid;
        this.playlistname=playlistname;
        this.songids=songids;
    }

    public String getPlaylistid() {
        return playlistid;
    }

    public String getPlaylistname() {
        return playlistname;
    }

    public String getSongIds() {
        return songids;
    }

    @Override
    public String toString() {
        return "Playlist ID - " + playlistid + "\n"+ "Playlist Name - " + playlistname + "\n" + "Song IDs - " + songids + "\n"; 
    }
}
