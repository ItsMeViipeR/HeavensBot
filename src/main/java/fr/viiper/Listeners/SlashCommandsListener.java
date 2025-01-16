package fr.viiper.Listeners;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import fr.viiper.Commands.*;

public class SlashCommandsListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch(event.getName()) {
            case "ping":
                Ping.run(event);
                break;
            case "help":
                Help.run(event);
                break;
            case "userinfo":
                UserInfo.run(event);
                break;
            case "serverinfo":
                ServerInfo.run(event);
                break;
            case "botinfo":
                BotInfo.run(event);
                break;
            case "clear":
                Clear.run(event);
                break;
            case "kick":
                Kick.run(event);
                break;
            case "ban":
                Ban.run(event);
                break;
            case "unban":
                Unban.run(event);
        }
    }
}
