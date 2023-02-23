package com.crio.jukebox.commands;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.repositories.ISongRepository;

public class LoadDataCommand implements ICommand {

    private final ISongRepository songRepository;

    public LoadDataCommand(ISongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public void execute(List<String> tokens) {
        List<String> lines;

        try {
            String filePath = "";
            String completeFilepath = filePath + tokens.get(1);
            lines = Files.readAllLines(Paths.get(completeFilepath));

            for (String element : lines) {
                String[] str = element.split(",");
                String songName = str[0];
                String genre = str[1];
                String albumName = str[2];
                String albumOwner = str[3];
                String artist = str[4];
                List<String> artists = Arrays.asList(artist.split("#"));
                addSong(songName, genre, albumName, albumOwner, artists);
            }
        } catch (Exception io) {
            io.printStackTrace();
        }

        System.out.println("Songs Loaded successfully");
    }

    private void addSong(String songName, String genre, String albumName, String albumOwner,
            List<String> artists) {
        songRepository.saveSong(new Song(songName, genre, albumName, albumOwner, artists));
    }
}
