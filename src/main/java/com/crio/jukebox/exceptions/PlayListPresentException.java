package com.crio.jukebox.exceptions;

public class PlayListPresentException extends RuntimeException {
    public PlayListPresentException(){
        super();
    }

    public PlayListPresentException(String msg) {
        super(msg);
    }
}
