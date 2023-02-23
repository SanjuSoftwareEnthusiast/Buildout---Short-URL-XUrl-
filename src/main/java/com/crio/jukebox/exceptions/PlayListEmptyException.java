package com.crio.jukebox.exceptions;

public class PlayListEmptyException extends RuntimeException {
    public PlayListEmptyException(){
        super();
    }

    public PlayListEmptyException(String msg){
        super(msg);
    }
}
