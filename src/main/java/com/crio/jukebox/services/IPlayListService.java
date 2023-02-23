package com.crio.jukebox.services;

import java.util.List;
import com.crio.jukebox.dto.ModifyPlayListSummaryDto;
import com.crio.jukebox.dto.PlayPlayListSummarydto;

public interface IPlayListService {
    public String createPlaylist(String userid,String playlistname,List<String> songids);
    public String deletePlayList(String userid,String playListid);
    public ModifyPlayListSummaryDto modifyPayList(String operation,String userid, String playlistid ,List<String> songids);
    public PlayPlayListSummarydto playPlayList(String userid,String playlistid);
    public PlayPlayListSummarydto playSongPlayList(String userid,String command);
}
