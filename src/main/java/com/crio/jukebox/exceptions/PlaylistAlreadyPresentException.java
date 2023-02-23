package com.crio.jukebox.exceptions;

public class PlaylistAlreadyPresentException extends RuntimeException {
    public PlaylistAlreadyPresentException() {
        super();
    }

    public PlaylistAlreadyPresentException(String msg) {
        super(msg);
    }
}
