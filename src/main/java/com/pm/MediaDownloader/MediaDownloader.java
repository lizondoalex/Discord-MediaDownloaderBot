package com.pm.MediaDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MediaDownloader {

    public File download(String url, String format) throws Exception{
        String folder = "downloads/";

        List<String> getFilenameCmd = new ArrayList<>();
        getFilenameCmd.add("yt-dlp");
        getFilenameCmd.add("--get-filename");
        getFilenameCmd.add("--restrict-filenames");
        getFilenameCmd.add("-o");
        getFilenameCmd.add("%(title)s." + format);
        getFilenameCmd.add(url);

        Process nameProcess = new ProcessBuilder(getFilenameCmd).start();
        String finalName;
        try (java.util.Scanner s = new java.util.Scanner(nameProcess.getInputStream())) {
            finalName = s.hasNextLine() ? s.nextLine() : "video_" + System.currentTimeMillis() + "." + format;
        }
        nameProcess.waitFor();

        File outputFile = new File(folder + finalName);

        List<String> command = new ArrayList<>();
        command.add("yt-dlp");

        if (format.equalsIgnoreCase("mp3")) {
            command.add("-x");
            command.add("--audio-format");
            command.add("mp3");
        } else {
            command.add("-f");
            command.add("bestvideo[ext=mp4][vcodec^=avc1]+bestaudio[ext=m4a]/best[ext=mp4]/best");
        }

        command.add("--max-filesize");
        command.add("25M");
        command.add("-o");
        command.add(outputFile.getAbsolutePath());
        command.add("--restrict-filenames");
        command.add(url);

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.inheritIO();
        Process process = pb.start();

        int exitCode = process.waitFor();
        if (exitCode == 0) {
            return outputFile;
        } else {
            throw new RuntimeException("yt-dlp failed with exit code " + exitCode);
        }
    }
}
