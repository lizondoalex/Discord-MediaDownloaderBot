package com.pm.Main;

import com.pm.EventListener.EventListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.IntegrationType;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;

import static net.dv8tion.jda.api.interactions.commands.build.Commands.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        String token = System.getenv("DISCORD_TOKEN");
        if (token == null){
            token = dotenv.get("DISCORD_TOKEN");

        }
        if (token == null || token.isEmpty()) {
            System.err.println("CRITICAL ERROR: Discord Token not found!");
            System.err.println("Make sure your .env file is in the ROOT folder and contains DISCORD_TOKEN=your_token");
            System.exit(1);
        }
        JDA jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new EventListener())
                .build();

        jda.awaitReady();
        jda.updateCommands().addCommands(
                slash("download", "Download content from social media")
                        .setIntegrationTypes(IntegrationType.GUILD_INSTALL, IntegrationType.USER_INSTALL)
                        .setContexts(InteractionContextType.GUILD, InteractionContextType.BOT_DM, InteractionContextType.PRIVATE_CHANNEL)
                        .addOptions(
                                new OptionData(OptionType.STRING, "format", "Choose the output format", true)
                                        .addChoice("MP3 (Audio Only)", "mp3")
                                        .addChoice("MP4 (Video)", "mp4"),
                                new OptionData(OptionType.STRING, "url", "Link from youtube, soundcloud, etc...", true)
                        )
        ).queue();

        System.out.println("Bot connected and ready");

    }
}
