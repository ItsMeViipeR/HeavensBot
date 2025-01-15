package fr.viiper.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import fr.viiper.Utils.RandomColor;

public class Help {
    public static void run(SlashCommandInteractionEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("Help");
        embedBuilder.setDescription("Here are the available commands:");
        embedBuilder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
        embedBuilder.addField("ping", "Check the bot's latency", false);
        embedBuilder.addField("help", "Display this message", false);
        embedBuilder.addField("botinfo", "Display bot information", false);
        embedBuilder.addField("serverinfo", "Display server information", false);
        embedBuilder.addField("userinfo", "Display user information", false);
        embedBuilder.addField("clear", "Clear the specified number of messages", false);
        embedBuilder.addField("kick", "Kick a specified user", false);
        embedBuilder.setColor(RandomColor.generate());

        event.replyEmbeds(embedBuilder.build()).queue();
    }
}
