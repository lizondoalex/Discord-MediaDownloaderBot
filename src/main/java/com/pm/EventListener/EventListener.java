package com.pm.EventListener;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        MessageChannel channel = event.getChannel();

        channel.sendMessage("Message received with content: " + content).queue();
        System.out.println("Printing to console the content received: " + content);
    }
}