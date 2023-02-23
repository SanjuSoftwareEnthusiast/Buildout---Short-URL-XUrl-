package com.crio.jukebox.entities;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UserTest")
public class UserTest {

    @Test
    @DisplayName("Add playlist should add new playlist")
    public void addPlaylist_shouldAdd(){
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
		
        final Playlist playlist = new Playlist( "1","MY_PLAYLIST_1",songlst1,user,PlaylistStatus.PLAYING,0);
              
        
        //Act
        user.addPlaylist(playlist);  
        Assertions.assertEquals(1, user.getPlaylists().size());
    }

    @Test
    @DisplayName("Delete playlist should delete the playlist from user")
    public void deletePlaylist_shouldDelete(){
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
		
        final Playlist playlist = new Playlist( "1","MY_PLAYLIST_1",songlst1,user,PlaylistStatus.PLAYING,0);
        user.addPlaylist(playlist); 
        user.deletePlaylist(playlist);       
        
        //Act
        Assertions.assertEquals(0, user.getPlaylists().size());
    }
 
}