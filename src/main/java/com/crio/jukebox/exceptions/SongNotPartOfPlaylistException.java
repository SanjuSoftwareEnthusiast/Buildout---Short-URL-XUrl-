package com.crio.jukebox.exceptions;

public class SongNotPartOfPlaylistException extends RuntimeException{
    public SongNotPartOfPlaylistException(){
        super();
    }

    public SongNotPartOfPlaylistException(String msg){
        super(msg);
    }
}
