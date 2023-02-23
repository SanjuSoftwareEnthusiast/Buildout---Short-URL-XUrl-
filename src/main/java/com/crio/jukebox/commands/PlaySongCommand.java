package com.crio.jukebox.commands;

import java.util.List;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.jukebox.dto.PlayPlayListSummarydto;
import com.crio.jukebox.exceptions.InvalidSongException;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.SongNotPartOfPlaylistException;
import com.crio.jukebox.services.IPlayListService;

public class PlaySongCommand implements ICommand{

    private final IPlayListService playListService;

    public PlaySongCommand(IPlayListService playListService){
        this.playListService =playListService;
    }

    @Override
    public void execute(List<String> tokens) {
        String userid = tokens.get(1);
        String command = tokens.get(2);

        try{
            PlayPlayListSummarydto Retout = playListService.playPlayList(userid, command);
            System.out.print(Retout);
        }catch(UserNotFoundException e){
            System.out.println(e.getMessage());
        }catch(PlaylistNotFoundException e){
            System.out.println(e.getMessage());
        }catch(InvalidSongException e){
            System.out.println(e.getMessage());
        }catch(SongNotPartOfPlaylistException e){
            System.out.println(e.getMessage());
        }
    }
    
}
