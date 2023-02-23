package com.crio.jukebox.commands;

import java.util.ArrayList;
import java.util.List;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.jukebox.exceptions.InvalidSongException;
import com.crio.jukebox.exceptions.PlaylistAlreadyPresentException;
import com.crio.jukebox.services.IPlayListService;

public class CreatePlayListCommand implements ICommand{
    private final IPlayListService playListService;

    public CreatePlayListCommand(IPlayListService playListService){
        this.playListService=playListService;
    }

    @Override
    public void execute(List<String> tokens){
        String userid = tokens.get(1);
        String playlistname = tokens.get(2);
        
        List<String> lstSongids = new ArrayList<String>();

        for(int i=3;i<tokens.size();i++){
            lstSongids.add(tokens.get(i));
        }

        try{
            String playlistid = playListService.createPlaylist(userid, playlistname, lstSongids);
            System.out.print("Playlist ID - " + playlistid+"\n");
        }
        catch(UserNotFoundException e){
            System.out.println(e.getMessage());
        }catch(PlaylistAlreadyPresentException e){
            System.out.println(e.getMessage());
        }catch(InvalidSongException e){
            System.out.println("Some Requested Songs Not Available. Please try again.");
        }
    }

}
