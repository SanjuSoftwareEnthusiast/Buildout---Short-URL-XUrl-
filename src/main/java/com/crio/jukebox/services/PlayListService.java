package com.crio.jukebox.services;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import com.crio.jukebox.exceptions.InvalidSongException;
import com.crio.jukebox.exceptions.PlayListEmptyException;
import com.crio.jukebox.exceptions.PlayListPresentException;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.SongNotPartOfPlaylistException;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.repositories.IUserRepository;
import com.crio.jukebox.dto.ModifyPlayListSummaryDto;
import com.crio.jukebox.dto.PlayPlayListSummarydto;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.PlaylistStatus;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.repositories.IPlayListRepository;
import com.crio.jukebox.repositories.ISongRepository;

public class PlayListService implements IPlayListService {
    private final IUserRepository userRepository;
    private final ISongRepository songRepository;
    private final IPlayListRepository playListRepository;

    public PlayListService(IUserRepository userRepository,ISongRepository songRepository,IPlayListRepository playListRepository){
        this.playListRepository = playListRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
    }

    @Override
    public String createPlaylist(String userid, String playlistname, List<String> songids) {
        User user = userRepository.findById(userid).orElseThrow(() -> new UserNotFoundException("Cannot create playlist . User for given id: "+userid+"not found!"));
        Optional<Playlist> playlistcheck = playListRepository.findByName(playlistname);
        if(playlistcheck.isPresent()){
            throw new PlayListPresentException("Cannot create new Playlist for this playlist name :"+playlistname);
        }
        List<Song> lstsong = songRepository.ReturnLstSongs(songids);

        Playlist playlist =new Playlist(playlistname, lstsong, user, PlaylistStatus.NOT_STARTED, 0);
        Playlist playlistwithid = playListRepository.savePlayList(playlist);

        user.addPlaylist(playlistwithid);
        return playlistwithid.getId();
    }

    @Override
    public String deletePlayList(String userid, String playListid) {
        User user = userRepository.findById(userid).orElseThrow(()->new UserNotFoundException("User not found for this userid:" + userid ));
        Playlist playlist = playListRepository.findById(playListid).orElseThrow(()->new PlaylistNotFoundException("Cannot delete Playlist. Playlist for given playlist id : " +playListid+" doesn't exists"));

        playListRepository.deleteById(playListid);
        user.deletePlaylist(playlist);
        userRepository.saveUser(user);
        return "Playlist deleted";
    }

    @Override
    public ModifyPlayListSummaryDto modifyPayList(String operation, String userid,
            String playlistid, List<String> songids) {
        User user = userRepository.findById(userid).orElseThrow(()->new UserNotFoundException("Cannot modify Playlist . User for given id:" + userid + "not found!"));
        Playlist playlist = playListRepository.findById(userid).orElseThrow(()->new PlaylistNotFoundException("Cannot modify Playlist. Playlsit with given id"+playlistid+"not found!"));
        
        List<Song> lstSong = playlist.getSongs();


        for(String songid:songids){
            Song song = songRepository.findById(songid).orElseThrow(()->new InvalidSongException("Song not found for given id:" +songid));
            if(operation.equals("ADD_SONG")){
                if(!lstSong.contains(song)){
                    lstSong.add(song);
                }else{
                    if(lstSong.contains(song)){
                        lstSong.remove(song);
                    }
                }
            }
        }
        user.deletePlaylist(playlist);
        Playlist newPlaylist = new Playlist(playlist.getId(), playlist.getPlaylistName(), lstSong, user, playlist.getPlaylistStatus(), playlist.getIndexOfCurrentSongPlaylist());
        playListRepository.savePlayList(newPlaylist);
        user.addPlaylist(newPlaylist);
        userRepository.saveUser(user);
        List<String> newssongids = songRepository.getSongIds(lstSong);
    
        return new ModifyPlayListSummaryDto(newPlaylist.getId(), newPlaylist.getPlaylistName(), String.join(" ",newssongids));
    }

    @Override
    public PlayPlayListSummarydto playPlayList(String userid, String playlistid) {
       User user = userRepository.findById(userid).orElseThrow(()->new UserNotFoundException("Cannot play playlist. Uset for given id:"+userid+"not found@!"));
       Playlist playlist = playListRepository.findById(playlistid).orElseThrow(()->new PlaylistNotFoundException("Cannot play playlist. Playlist for given id:"+playlistid+"not found!"));

       List<Playlist> lstUserPlayList = user.getPlaylists();

       if(!lstUserPlayList.contains(playlist)){
            throw new PlaylistNotFoundException("Cannot play Playlist. Playlist for given id:"+playlistid+"not found!");
       } else{
            if(playlist.getSongs().size()==0){
                throw new PlayListEmptyException("Playlist is empty");
            }
       }
       Song song = playlist.getSongs().getFirst();

       playlist.playingPlaylist();

       return new PlayPlayListSummarydto(song.getSongName(), playlist.getPlaylistName(), String.join("",song.getArtists()));
    }

    @Override
    public PlayPlayListSummarydto playSongPlayList(String userid, String command) {
        User user = userRepository.findById(userid).orElseThrow(()->new UserNotFoundException("Cannot platy playlist. User with give user id: "+userid+"not found!"));
        Playlist playlist = user.getPlaylist_playing().orElseThrow(()->new PlaylistNotFoundException("Cannot play song. No playlist is currently playing for this user"));

        LinkedList<Song> playlistsongs = playlist.getSongs();
        int currentSongindex = playlist.getIndexOfCurrentSongPlaylist();
        ListIterator<Song> iterator = playlistsongs.listIterator(currentSongindex);
        Song song;

        if(command.equals("NEXT")){
            if(iterator.hasNext()==false) {
                iterator = playlistsongs.listIterator(0);
                song = iterator.next();
                playlist.setIndexOfCurrentSongPlaylist(0);
                playListRepository.savePlayList(playlist);
            }
            else{
                iterator = playlistsongs.listIterator(++currentSongindex);
                song = iterator.next();
                playlist.setIndexOfCurrentSongPlaylist(++currentSongindex);
                playListRepository.savePlayList(playlist);
            }
        }else if(command.equals("BACK")){
            if(iterator.hasPrevious()==false){
                iterator = playlistsongs.listIterator(playlistsongs.size());
                song=iterator.previous();
                playlist.setIndexOfCurrentSongPlaylist(playlistsongs.size());
                playListRepository.savePlayList(playlist);
            }
            else {
                iterator = playlistsongs.listIterator(--currentSongindex);
                song = iterator.previous();
                playlist.setIndexOfCurrentSongPlaylist(--currentSongindex);
                playListRepository.savePlayList(playlist);
            }
        }else {
            song = songRepository.findById(command).orElseThrow(()->new InvalidSongException("song not found for given id:"+command));
            if(playlistsongs.contains(song)){
                int pos = playlistsongs.indexOf(song);
                playlist.setIndexOfCurrentSongPlaylist(pos);
                playListRepository.savePlayList(playlist);
            }else{
                throw new SongNotPartOfPlaylistException("Given song is not the part of the active playlist");
            }
        }

        return new PlayPlayListSummarydto(song.getSongName(),song.getAlbumName(),String.join(",", song.getArtists()));
    }
    
}
