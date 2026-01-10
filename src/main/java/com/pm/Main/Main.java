package com.pm.Main;

import com.pm.EventListener.EventListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

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
        System.out.println("Bot connected and ready");

    }
}
