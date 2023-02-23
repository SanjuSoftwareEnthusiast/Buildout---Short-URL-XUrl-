package com.crio.jukebox.services;

import com.crio.jukebox.dto.ModifyPlayListSummaryDto;
import com.crio.jukebox.dto.PlayPlayListSummarydto;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.PlaylistStatus;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.*;
import com.crio.jukebox.repositories.IPlayListRepository;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.IUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("PlayListServiceTest")
@ExtendWith(MockitoExtension.class)
public class PlayListServiceTest {

    @Mock
    private IUserRepository userRepositoryMock;

    @Mock
    private ISongRepository songRepositoryMock;

    @Mock
    private IPlayListRepository playListRepositoryMock;

    @InjectMocks
    private PlayListService playListService;

    @Test
    @DisplayName("create method Should Throw UserNotFoundException If No Creator User Found")
    public void create_ShouldThrowUserNotFoundException(){
        //Arrange
        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.empty());

        //Act and Assert
        List<String> songLst = new LinkedList<String>() ;
        songLst.add("1");
        songLst.add("2");
        songLst.add("3");
        songLst.add("4");
        songLst.add("5");
        
        Assertions.assertThrows(UserNotFoundException.class, ()->playListService.createPlaylist("1", "MY_PLAY_LIST1",songLst));
        verify(userRepositoryMock,times(1)).findById(anyString());

    }

    @Test
    @DisplayName("Create method should throw InvalidSongException when song is not found")
    public void create_shouldthrowInvlaidSongException(){
        //Arrange
        final User user = new User("Kiran");

        List<String> songidLst = new LinkedList<String>() ;
        songidLst.add("1");
        songidLst.add("2");
        songidLst.add("3");
        songidLst.add("4");
        songidLst.add("5");
        
        when(playListRepositoryMock.findByName(anyString())).thenReturn(Optional.empty());                        
        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        when(songRepositoryMock.ReturnLstSongs(anyList())).thenThrow(InvalidSongException.class);

        //Act and Assert
        
        Assertions.assertThrows(InvalidSongException.class, ()->playListService.createPlaylist("1", "MY_PLAY_LIST1",songidLst));       
    }

    // @Test
    // @DisplayName("Create method should throw PlaylistAlreadyPresent when playlist is alreay present")
    // public void create_shouldthrowPlaylistAlreadyPresentException(){
    //     //Arrange
    //     final User user = new User("Kiran");
    //     List<Song> songlst = new LinkedList<Song>() ;
    //     List<String> artists = new ArrayList<>();
    //     artists.add("Ed Sheeran");
    //     artists.add("Eminem");
    //     songlst.add(new Song("Remember The Name","Pop","No.6 Collaborations Project","Ed Sheeran",artists));        
        
       
    //     Playlist playlist = new Playlist( "MY_PLAY_LIST", songlst, user,PlaylistStatus.NOT_STARTED,0);
    //     when(playListRepositoryMock.findByName(anyString())).thenReturn(Optional.of(playlist));
    //     when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));

    //     //Act and Assert
    //     List<String> songLst = new LinkedList<String>() ;
    //     songLst.add("1");
    //     songLst.add("2");
    //     songLst.add("3");
    //     songLst.add("4");
    //     songLst.add("5");
        
    //     Assertions.assertThrows(PlaylistAlreadyPresentException.class, ()->playListService.createPlaylist("1", "MY_PLAY_LIST1",songLst));
    //     verify(playListRepositoryMock,times(1)).findByName(anyString());

    // }
    @Test
    @DisplayName("create PlayList should create playlist ")
    public void create_ShouldCreatePlaylist(){
        //Arrange        
        final User user = new User("Kiran");
        List<String> songLstid = new LinkedList<String>() ;
        songLstid.add("1");
        songLstid.add("2");
        songLstid.add("3");
        songLstid.add("4");
        songLstid.add("5");
        
        List<Song> songlst1 = new LinkedList<Song>() ;
        List<String> artists1 = new ArrayList<>();
        artists1.add("Ed Sheeran");
        artists1.add("Eminem");
        songlst1.add(new Song("1","Remember The Name","Pop","No.6 Collaborations Project","Ed Sheeran",artists1));           
        final Playlist playlist = new Playlist( "1","MY_PLAYLIST_1",songlst1,user,PlaylistStatus.NOT_STARTED,0);

        String expectedPlayListId = "1";
        when(playListRepositoryMock.savePlayList(any(Playlist.class))).thenReturn(playlist);
        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        when(playListRepositoryMock.findByName(anyString())).thenReturn(Optional.empty());                        
        //Act
        String  playlistid = playListService.createPlaylist("1", "MY_PLAY_LIST1", songLstid);

        //Assert
        Assertions.assertEquals(expectedPlayListId, playlistid);
        verify(playListRepositoryMock,times(1)).savePlayList(any(Playlist.class));
    }

    @Test
    @DisplayName("delete method Should Throw UserNotFoundException If No  User Found")
    public void delete_ShouldThrowUserNotFoundException(){
        //Arrange
        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.empty());

        //Act and Assert
        
        Assertions.assertThrows(UserNotFoundException.class, ()->playListService.deletePlayList("1", "1"));
        verify(userRepositoryMock,times(1)).findById(anyString());

    }

    @Test
    @DisplayName("delete method should throw PlayListNotFoundException if playlist id is not found")
    public void delete_shouldThrowPlayListNotFoundException(){
        //Arrange
        final User user = new User("Kiran");
        when(playListRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));

        //Act and Assert
        Assertions.assertThrows(PlaylistNotFoundException.class, ()->playListService.deletePlayList("1", "1"));
        verify(playListRepositoryMock,times(1)).findById(anyString());

    }

    // @Test
    // @DisplayName("delete method should delete the playlist")
    // public void delete_playlist(){
    // //Arrange   
    // final User user = new User("Kiran");
    // List<Song> songlst = new LinkedList<Song>() ;
    // List<String> artists = new ArrayList<>();
    // artists.add("Ed Sheeran");
    // artists.add("Eminem");
    // songlst.add(new Song("Remember The Name","Pop","No.6 Collaborations Project","Ed Sheeran",artists));        
        
    // final Playlist playlist = new Playlist( "MY_PLAYLIST_1",songlst,user,PlaylistStatus.NOT_STARTED,0);
    
    // when(playListRepositoryMock.findById(anyString())).thenReturn(Optional.of(playlist));
    // when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));

    // //Act
    // String expectedmsg = "Delete Successful" ;

    // //Assert
    // Assertions.assertEquals(expectedmsg,playListService.deletePlayList("1", "1"));
    // verify(playListRepositoryMock,times(1)).deleteById(anyString());
    // verify(userRepositoryMock,times(1)).saveUser(user);

    // }

    @Test
    @DisplayName("modifyPlayList should throw UserNotFoundException")
    public void modifyPlayList_ShouldThrowUserNotFoundException(){
        //Arrange
        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
        List<String> songLstid = new LinkedList<String>() ;
        songLstid.add("1");
        songLstid.add("2");
        songLstid.add("3");
        songLstid.add("4");
        songLstid.add("5");
        

        //Act and Assert
        
        Assertions.assertThrows(UserNotFoundException.class, ()->playListService.modifyPayList("ADD-SONG","1", "1", songLstid));
        verify(userRepositoryMock,times(1)).findById(anyString());

    }   
    

    @Test
    @DisplayName("modifyPlayList method should throw PlayListNotFoundException if playlist id is not found")
    public void modifyPlayList_shouldThrowPlayListNotFoundException(){
        //Arrange
        final User user = new User("Kiran");
        when(playListRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        List<String> songLstid = new LinkedList<String>() ;
        songLstid.add("1");
        songLstid.add("2");
        songLstid.add("3");
        songLstid.add("4");
        songLstid.add("5");
        
        //Act and Assert
        Assertions.assertThrows(PlaylistNotFoundException.class, ()->playListService.modifyPayList("ADD-SONG","1", "1", songLstid));
        verify(playListRepositoryMock,times(1)).findById(anyString());
        verify(userRepositoryMock,times(1)).findById(anyString());        
    }

    @Test
    @DisplayName("modifyPlayList method should throw InvalidSongException if song is not found")
    public void modifyPlayList_shouldThrowInvalidSongException(){
        //Arrange
        final User user = new User("Kiran");
        
        List<String> songLstid = new LinkedList<String>() ;
        songLstid.add("1");
        songLstid.add("2");
        songLstid.add("3");
        songLstid.add("4");
        songLstid.add("5");

        List<Song> songlst = new LinkedList<Song>() ;
        List<String> artists = new ArrayList<>();
        artists.add("Ed Sheeran");
        artists.add("Eminem");
        songlst.add(new Song("Remember The Name","Pop","No.6 Collaborations Project","Ed Sheeran",artists));        
        final Playlist playlist = new Playlist( "1","MY_PLAYLIST_1",songlst,user,PlaylistStatus.NOT_STARTED,0);

        when(playListRepositoryMock.findById(anyString())).thenReturn(Optional.of(playlist));
        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        when(songRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
        
        
        //Act and Assert
       
        Assertions.assertThrows(InvalidSongException.class,()->playListService.modifyPayList("ADD-SONG","1", "1", songLstid));
        verify(playListRepositoryMock,times(1)).findById(anyString());
        verify(userRepositoryMock,times(1)).findById(anyString());      
        verify(songRepositoryMock,times(1)).findById(anyString());  
    }


    @Test
    @DisplayName("modifyPlayList method should add new songs to the playlist")
    public void modifyPlayList_shouldaddsongs(){
        //Arrange
        final User user = new User("Kiran");
        
        List<String> songLstid = new LinkedList<String>() ;       
        songLstid.add("1");
        

        List<Song> songlst = new LinkedList<Song>() ;
        List<String> artists = new ArrayList<>();
        artists.add("Ed Sheeran");
        artists.add("Eminem");
        songlst.add(new Song("1","Remember The Name","Pop","No.6 Collaborations Project","Ed Sheeran",artists));   
        Song  song = new Song("1","Remember The Name","Pop","No.6 Collaborations Project","Ed Sheeran",artists);
        final Playlist playlist = new Playlist( "1","MY_PLAYLIST_1",songlst,user,PlaylistStatus.NOT_STARTED,0);

        when(playListRepositoryMock.findById(anyString())).thenReturn(Optional.of(playlist));
        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        when(songRepositoryMock.findById(anyString())).thenReturn(Optional.of(song));
        when(songRepositoryMock.getSongIds(anyList())).thenReturn(songLstid);
        
        //Act and Assert
        ModifyPlayListSummaryDto  actualmodifyPlayListSummaryDto = playListService.modifyPayList("ADD-SONG","1", "1", songLstid);
        ModifyPlayListSummaryDto  expectedmodifyPlayListSummaryDto = new ModifyPlayListSummaryDto("1","MY_PLAYLIST_1",String.join(" ",songLstid));
        
        Assertions.assertEquals(expectedmodifyPlayListSummaryDto.toString(),actualmodifyPlayListSummaryDto.toString());
        verify(playListRepositoryMock,times(1)).findById(anyString());
        verify(userRepositoryMock,times(1)).findById(anyString());      
        //verify(songRepositoryMock,times(1)).findById(anyString());  
        verify(playListRepositoryMock,times(1)).savePlayList(any(Playlist.class));
        verify(userRepositoryMock,times(1)).saveUser(any(User.class));
    }
   
    @Test
    @DisplayName("modifyPlayList method should remove  songs from the playlist")
    public void modifyPlayList_shoulddeletesongs(){
        //Arrange
        final User user = new User("Kiran");
        
        List<String> songLstid = new LinkedList<String>() ;       
        songLstid.add("1");
        

        List<Song> songlst = new LinkedList<Song>() ;
        List<String> artists = new ArrayList<>();
        artists.add("Ed Sheeran");
        artists.add("Eminem");
        songlst.add(new Song("1","Remember The Name","Pop","No.6 Collaborations Project","Ed Sheeran",artists));   
        Song  song = new Song("1","Remember The Name","Pop","No.6 Collaborations Project","Ed Sheeran",artists);
        final Playlist playlist = new Playlist( "1","MY_PLAYLIST_1",songlst,user,PlaylistStatus.NOT_STARTED,0);

        when(playListRepositoryMock.findById(anyString())).thenReturn(Optional.of(playlist));
        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        when(songRepositoryMock.findById(anyString())).thenReturn(Optional.of(song));
        when(songRepositoryMock.getSongIds(anyList())).thenReturn(songLstid);
        
        //Act and Assert
        ModifyPlayListSummaryDto  actualmodifyPlayListSummaryDto = playListService.modifyPayList("DELETE-SONG","1", "1", songLstid);
        ModifyPlayListSummaryDto  expectedmodifyPlayListSummaryDto = new ModifyPlayListSummaryDto("1","MY_PLAYLIST_1",String.join(" ",songLstid));
        
        Assertions.assertEquals(expectedmodifyPlayListSummaryDto.toString(),actualmodifyPlayListSummaryDto.toString());
        verify(playListRepositoryMock,times(1)).findById(anyString());
        verify(userRepositoryMock,times(1)).findById(anyString());      
        //verify(songRepositoryMock,times(1)).findById(anyString());  
        verify(playListRepositoryMock,times(1)).savePlayList(any(Playlist.class));
        verify(userRepositoryMock,times(1)).saveUser(any(User.class));
    }
    @Test
    @DisplayName("playPlaylist should throw UserNotFoundException")
    public void playPlaylist_ShouldThrowUserNotFoundException(){
        //Arrange
        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
        List<String> songLstid = new LinkedList<String>() ;
        songLstid.add("1");
        songLstid.add("2");
        songLstid.add("3");
        songLstid.add("4");
        songLstid.add("5");
        

        //Act and Assert
        
        Assertions.assertThrows(UserNotFoundException.class, ()->playListService.playPlayList("1", "1"));
        verify(userRepositoryMock,times(1)).findById(anyString());

    }   
      

    @Test
    @DisplayName("playPlaylist method should throw PlayListNotFoundException if playlist id is not found")
    public void playPlaylist_shouldThrowPlayListNotFoundException(){
        //Arrange
        final User user = new User("Kiran");
        when(playListRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        
        //Act and Assert
        Assertions.assertThrows(PlaylistNotFoundException.class, ()->playListService.playPlayList("1", "1"));
        verify(playListRepositoryMock,times(1)).findById(anyString());
        verify(userRepositoryMock,times(1)).findById(anyString());        
    }
 
    @Test
    @DisplayName("playPlaylist method should throw PlayListNotFoundException if playlist id is not part of User playlist")
    public void playPlaylist_shouldThrowUserPlayListNotFoundException(){
        //Arrange
        final User user = new User("Kiran");

        List<Song> songlst1 = new LinkedList<Song>() ;
        List<String> artists1 = new ArrayList<>();
        artists1.add("Ed Sheeran");
        artists1.add("Eminem");
        songlst1.add(new Song("1","Remember The Name","Pop","No.6 Collaborations Project","Ed Sheeran",artists1));           
        final Playlist playlist1 = new Playlist( "1","MY_PLAYLIST_1",songlst1,user,PlaylistStatus.NOT_STARTED,0);
        user.addPlaylist(playlist1);

        
        List<Song> songlst2 = new LinkedList<Song>() ;
        List<String> artists2 = new ArrayList<>();        
        artists2.add("Nirvana");
        songlst2.add(new Song("2","Lithium","Rock","Nevermind","Ed Sheeran",artists2));           
        final Playlist playlist2 = new Playlist( "2","MY_PLAYLIST_2",songlst2,user,PlaylistStatus.NOT_STARTED,0);


        

        when(playListRepositoryMock.findById(anyString())).thenReturn(Optional.of(playlist2));
        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        
        //Act and Assert
        Assertions.assertThrows(PlaylistNotFoundException.class, ()->playListService.playPlayList("1", "2"));
        verify(playListRepositoryMock,times(1)).findById(anyString());
        verify(userRepositoryMock,times(1)).findById(anyString());        
    }
 
    @Test
    @DisplayName("playPlaylist method should throw PlayListEmptyException if playlist is empty")
    public void playPlaylist_shouldThrowPlayListEmptyException(){
        //Arrange
        final User user = new User("Kiran");

        List<Song> songlst1 = new LinkedList<Song>() ;
        List<String> artists1 = new ArrayList<>();
        artists1.add("Ed Sheeran");
        artists1.add("Eminem");
        //songlst1.add(new Song("1","Remember The Name","Pop","No.6 Collaborations Project","Ed Sheeran",artists1));           
        final Playlist playlist1 = new Playlist( "1","MY_PLAYLIST_1",songlst1,user,PlaylistStatus.NOT_STARTED,0);
        user.addPlaylist(playlist1);
        

        when(playListRepositoryMock.findById(anyString())).thenReturn(Optional.of(playlist1));
        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        
        //Act and Assert
        Assertions.assertThrows(PlayListEmptyException.class, ()->playListService.playPlayList("1", "1"));
        verify(playListRepositoryMock,times(1)).findById(anyString());
        verify(userRepositoryMock,times(1)).findById(anyString());        
    }
 
    // @Test
    // @DisplayName("playPlaylist method should play playlist ")
    // public void playPlaylist_shouldPlayPlayList(){
    //     //Arrange
    //     final User user = new User("Kiran");

    //     List<Song> songlst1 = new LinkedList<Song>() ;
    //     List<String> artists1 = new ArrayList<>();
    //     List<String> artists2 = new ArrayList<>();        
    //     artists2.add("Nirvana");
    //     artists1.add("Ed Sheeran");
    //     artists1.add("Eminem");
    //     songlst1.add(new Song("1","Remember The Name","Pop","No.6 Collaborations Project","Ed Sheeran",artists1));  
    //     songlst1.add(new Song("2","Lithium","Rock","Nevermind","Ed Sheeran",artists2));                    
    //     final Playlist playlist1 = new Playlist( "1","MY_PLAYLIST_1",songlst1,user,PlaylistStatus.NOT_STARTED,0);
    //     user.addPlaylist(playlist1);
        

    //     when(playListRepositoryMock.findById(anyString())).thenReturn(Optional.of(playlist1));
    //     when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        
    //     //Act and Assert
        
    //     PlayPlayListSummarydto  actualplayPlayListSummaryDto = playListService.playPlayList("1", "1");
    //     PlayPlayListSummarydto  expectedplayPlayListSummaryDto = new PlayPlayListSummarydto("Remember The Name","No.6 Collaborations Project",String.join(",",artists1));
    //     Assertions.assertEquals(expectedplayPlayListSummaryDto.toString(),actualplayPlayListSummaryDto.toString());
     
    //     verify(playListRepositoryMock,times(1)).findById(anyString());
    //     verify(userRepositoryMock,times(1)).findById(anyString());        
    // }
 
    @Test
    @DisplayName("playSongPlayList should throw UserNotFoundException")
    public void playSongPlayList_ShouldThrowUserNotFoundException(){
        //Arrange
        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.empty()); 

        //Act and Assert
        
        Assertions.assertThrows(UserNotFoundException.class, ()->playListService.playSongPlayList("1", "NEXT"));
        verify(userRepositoryMock,times(1)).findById(anyString());

    }  

    
    @Test
    @DisplayName("playSongPlayList method should throw PlayListNotFoundException if playlist id is not found")
    public void playSongPlayList_shouldThrowPlayListNotFoundException(){
        //Arrange
        List<Song> songlst1 = new LinkedList<Song>() ;
        List<String> artists1 = new ArrayList<>();
        artists1.add("Ed Sheeran");
        artists1.add("Eminem");
		final User user = new User("1","Kiran");
        songlst1.add(new Song("1","Remember The Name","Pop","No.6 Collaborations Project","Ed Sheeran",artists1));           
        final Playlist playlist = new Playlist( "1","MY_PLAYLIST_1",songlst1,user,PlaylistStatus.NOT_STARTED,0);
		user.addPlaylist(playlist);
		
        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        
        //Act and Assert
        Assertions.assertThrows(PlaylistNotFoundException.class, ()->playListService.playSongPlayList("1", "NEXT"));        
        verify(userRepositoryMock,times(1)).findById(anyString());        
    }

    @Test
    @DisplayName("playSongPlayList method should throw InvalidSongException if song is not found")
    public void playSongPlayList_InvalidSongException(){
        //Arrange
        List<Song> songlst1 = new LinkedList<Song>() ;
        List<String> artists1 = new ArrayList<>();
        artists1.add("Ed Sheeran");
        artists1.add("Eminem");
		final User user = new User("1","Kiran");
        songlst1.add(new Song("1","Remember The Name","Pop","No.6 Collaborations Project","Ed Sheeran",artists1));           
        final Playlist playlist = new Playlist( "1","MY_PLAYLIST_1",songlst1,user,PlaylistStatus.PLAYING,0);
		user.addPlaylist(playlist);
		
        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        when(songRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
        
        //Act and Assert
        Assertions.assertThrows(InvalidSongException.class, ()->playListService.playSongPlayList("1", "2"));        
        verify(userRepositoryMock,times(1)).findById(anyString());    
        verify(songRepositoryMock,times(1)).findById(anyString());      
    }


    @Test
    @DisplayName("playSongPlayList method should throw SongNotPartOfPlayListException if song is not part of playlist")
    public void playSongPlayList_SongNotPartOfPlayListException(){
        //Arrange
        List<Song> songlst1 = new LinkedList<Song>() ;
        List<String> artists1 = new ArrayList<>();
        artists1.add("Ed Sheeran");
        artists1.add("Eminem");
		final User user = new User("1","Kiran");
        songlst1.add(new Song("1","Remember The Name","Pop","No.6 Collaborations Project","Ed Sheeran",artists1));           
        final Playlist playlist = new Playlist( "1","MY_PLAYLIST_1",songlst1,user,PlaylistStatus.PLAYING,0);
        user.addPlaylist(playlist);
        Song song =  new Song("4","Remember The Name","Pop","No.6 Collaborations Project","Ed Sheeran",artists1);
		
        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        when(songRepositoryMock.findById(anyString())).thenReturn(Optional.of(song));
        
        //Act and Assert
        Assertions.assertThrows(SongNotPartOfPlaylistException.class, ()->playListService.playSongPlayList("1", "4"));        
        verify(userRepositoryMock,times(1)).findById(anyString());    
        verify(songRepositoryMock,times(1)).findById(anyString());      
    }  
    
    // @Test
    // @DisplayName("playSongPlayList method should play song for a particular song id")
    // public void playSongPlayList_Shouldplaysongforsongid(){
    //     //Arrange
    //     List<Song> songlst1 = new LinkedList<Song>() ;
    //     List<String> artists1 = new ArrayList<>();
    //     artists1.add("Ed Sheeran");
    //     artists1.add("Eminem");
	// 	final User user = new User("1","Kiran");        

    //     songlst1.add(new Song("1","South of the Border","Pop","No.6 Collaborations Project","Ed Sheeran",artists1)); 
    //     songlst1.add(new Song("4","Remember The Name","Pop","No.6 Collaborations Project","Ed Sheeran",artists1));   
    //     songlst1.add(new Song("5","Cross Me","Pop","No.6 Collaborations Project","Ed Sheeran",artists1));   
    //     songlst1.add(new Song("6","Give Life Back To Music","Pop","Random Access Memories","Daft Punk",artists1));   
		
    //     final Playlist playlist = new Playlist( "1","MY_PLAYLIST_1",songlst1,user,PlaylistStatus.PLAYING,0);
    //     user.addPlaylist(playlist);        
	// 	Song song =  new Song("5","Cross Me","Pop","No.6 Collaborations Project","Ed Sheeran",artists1);
			   
    //     when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
	// 	when(songRepositoryMock.findById(anyString())).thenReturn(Optional.of(song));
       
        
    //     //Act and Assert
         
    //     PlayPlayListSummarydto  actualsongSummaryDto = playListService.playSongPlayList("1", "5");
    //     PlayPlayListSummarydto  expectesongSummaryDto = new PlayPlayListSummarydto("Cross Me","No.6 Collaborations Project",String.join(",",artists1));
    //     Assertions.assertEquals(expectesongSummaryDto.toString(),actualsongSummaryDto.toString());
         
    //     actualsongSummaryDto = playListService.playSongPlayList("1", "NEXT");
    //     expectesongSummaryDto = new PlayPlayListSummarydto("Give Life Back To Music","Random Access Memories",String.join(",",artists1));
    //     Assertions.assertEquals(expectesongSummaryDto.toString(),actualsongSummaryDto.toString());

    //     actualsongSummaryDto = playListService.playSongPlayList("1", "NEXT");
    //     expectesongSummaryDto = new PlayPlayListSummarydto("South of the Border","No.6 Collaborations Project",String.join(",",artists1));
    //     Assertions.assertEquals(expectesongSummaryDto.toString(),actualsongSummaryDto.toString());
         
    //     actualsongSummaryDto = playListService.playSongPlayList("1", "BACK");
    //     expectesongSummaryDto = new PlayPlayListSummarydto("Give Life Back To Music","Random Access Memories",String.join(",",artists1));
    //     Assertions.assertEquals(expectesongSummaryDto.toString(),actualsongSummaryDto.toString());
         
    //     actualsongSummaryDto = playListService.playSongPlayList("1", "BACK");
    //     expectesongSummaryDto = new PlayPlayListSummarydto("Cross Me","No.6 Collaborations Project",String.join(",",artists1));
    //     Assertions.assertEquals(expectesongSummaryDto.toString(),actualsongSummaryDto.toString());
        
    //     verify(userRepositoryMock,times(5)).findById(anyString());    
    //     verify(songRepositoryMock,times(1)).findById(anyString());      
    // }
                
}