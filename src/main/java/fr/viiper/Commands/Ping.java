package fr.viiper.Commands;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Ping {
    public static void run(SlashCommandInteractionEvent event) {
        JDA api = event.getJDA();

        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("Pong!");
        embedBuilder.setDescription("Latency: " + api.getGatewayPing() + "ms");
        embedBuilder.setColor(Color.RED);
        embedBuilder.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

        MessageEmbed embed = embedBuilder.build();

        event.replyEmbeds(embed).queue();
    }
}