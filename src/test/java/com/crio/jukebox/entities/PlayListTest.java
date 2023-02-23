package com.crio.jukebox.entities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PlayListTest")
public class PlayListTest {

    @Test 
    @DisplayName("Set status of playlist as PLAYING")
    public void setStatusPlaying(){

        //Arrange
        List<Song> songlst1 = new LinkedList<Song>() ;
        List<String> artists1 = new ArrayList<>();
        artists1.add("Ed Sheeran");
        artists1.add("Eminem");
		final User user = new User("1","Kiran");        

        songlst1.add(new Song("1","South of the Border","Pop","No.6 Collaborations Project","Ed Sheeran",artists1)); 
        songlst1.add(new Song("4","Remember The Name","Pop","No.6 Collaborations Project","Ed Sheeran",artists1));   
        songlst1.add(new Song("5","Cross Me","Pop","No.6 Collaborations Project","Ed Sheeran",artists1));   
        songlst1.add(new Song("6","Give Life Back To Music","Pop","Random Access Memories","Daft Punk",artists1));   
		
        final Playlist playlist = new Playlist( "1","MY_PLAYLIST_1",songlst1,user,PlaylistStatus.NOT_STARTED,0);

        //Act
        playlist.playingPlaylist();
        Assertions.assertEquals(PlaylistStatus.PLAYING, playlist.getPlaylistStatus());

    }
    
}