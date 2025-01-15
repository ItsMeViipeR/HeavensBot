package fr.viiper.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Clear {
    public static void run(@NotNull SlashCommandInteractionEvent event) {
        TextChannel channel = event.getChannel().asTextChannel();
        OptionMapping optionValue = event.getOption("amount");

        assert optionValue != null;

        var amount = optionValue.getAsInt();

        EmbedBuilder embedBuilder = new EmbedBuilder();

        if(amount <= 1) {
            embedBuilder.setTitle("Error");
            embedBuilder.setDescription("Amount must be greater than 1");
            embedBuilder.setColor(Color.RED);
            embedBuilder.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

            event.replyEmbeds(embedBuilder.build()).queue();
        } else if(amount > 100) {
            embedBuilder.setTitle("Error");
            embedBuilder.setDescription("Amount must be less than 100");
            embedBuilder.setColor(Color.RED);
            embedBuilder.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

            event.replyEmbeds(embedBuilder.build()).queue();
        }

        channel.getHistory().retrievePast(amount).queue(messages -> {
            channel.deleteMessages(messages).queue(
                    success -> {
                        embedBuilder.setTitle("Success");
                        embedBuilder.setDescription("You have successfully cleared " + amount + " messages");
                        embedBuilder.setColor(Color.GREEN);
                        embedBuilder.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

                        event.replyEmbeds(embedBuilder.build()).queue();
                    },
                    error -> {
                        embedBuilder.setTitle("Error");
                        embedBuilder.setDescription("Failed to delete " + amount + " messages");
                        embedBuilder.setColor(Color.RED);
                        embedBuilder.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

                        event.replyEmbeds(embedBuilder.build()).queue();
                    }
            );
        });
    }
}
