package com.crio.jukebox.commands;

import java.util.Arrays;
import java.util.List;
import com.crio.jukebox.exceptions.InvalidSongException;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.dto.ModifyPlayListSummaryDto;
import com.crio.jukebox.services.IPlayListService;

public class ModifyPlayListCommand implements ICommand {
    
    private final IPlayListService playListService;

    public ModifyPlayListCommand(IPlayListService playListService) {
        this.playListService = playListService;
    }

    @Override
    public void execute (List<String> tokens) {
        String action = tokens.get(1);
        String userid = tokens.get(2);
        String playlistid = tokens.get(3);
        String songids = tokens.get(4);
        List<String> lstSongids = Arrays.asList(songids.split(" "));

        try{
            ModifyPlayListSummaryDto Retout = playListService.modifyPayList(action, userid, playlistid, lstSongids);
            System.out.print(Retout);
        }catch(UserNotFoundException e){
            System.out.println(e.getMessage());
        }catch(PlaylistNotFoundException e){
            System.out.println(e.getMessage());
        }catch(InvalidSongException e) {
            System.out.println(e.getMessage());
        }
    }
}
