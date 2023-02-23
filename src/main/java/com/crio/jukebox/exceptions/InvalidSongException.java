package com.crio.jukebox.exceptions;

public class InvalidSongException extends RuntimeException{
    public InvalidSongException(){
        super();
    }

    public InvalidSongException(String msg){
        super(msg);
    }
}
