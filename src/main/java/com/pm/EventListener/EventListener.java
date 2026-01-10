package com.pm.EventListener;

import com.pm.MediaDownloader.MediaDownloader;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.utils.FileUpload;

import java.io.File;

public class EventListener extends ListenerAdapter {

    @Override public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if (!event.getName().equals("download")) return;

        String url = event.getOption("url").getAsString();
        String format = event.getOption("format").getAsString();

        event.deferReply().queue();

        new Thread(() -> {
            try {
                MediaDownloader downloader = new MediaDownloader();
                File result = downloader.download(url, format);

                if (result.exists()) {
                    event.getHook().sendFiles(FileUpload.fromData(result)).queue(success -> {
                        result.delete();
                    });
                }
            } catch (Exception e) {
                event.getHook().sendMessage("Error downloading media: " + e.getMessage()).queue();
                e.printStackTrace();
            }
        }).start();
    }
}