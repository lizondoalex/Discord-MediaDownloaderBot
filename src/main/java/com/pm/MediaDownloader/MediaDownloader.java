package com.pm.MediaDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MediaDownloader {

    public File download(String url, String format) throws Exception{
        String outputName = "downloads/" + UUID.randomUUID() + "." + format;

        List<String> command = new ArrayList<>();
        command.add("yt-dlp");

        if(format.equalsIgnoreCase("mp3")){
            command.add("-x");
            command.add("--audio-format");
            command.add("mp3");
        } else {
            command.add("-f");
            command.add("bestvideo[ext=mp4][vcodec^=avc1]+bestaudio[ext=m4a]/best[ext=mp4]/best");
        }

        command.add("-o");
        command.add(outputName);
        command.add(url);



        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.inheritIO();

        Process process = processBuilder.start();
        int exitCode = process.waitFor();

        if(exitCode == 0){
            return new File(outputName);
        } else {
            throw new RuntimeException("yt-dlp failed with exit code " + exitCode);
        }
    }
}
