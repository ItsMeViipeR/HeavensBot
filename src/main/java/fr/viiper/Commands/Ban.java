package fr.viiper.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Ban {
    public static void run(SlashCommandInteractionEvent event) {
        OptionMapping targetOption = event.getOption("target");

        assert targetOption != null;

        Member targetMember = targetOption.getAsMember();

        assert targetMember != null;

        Member author = event.getMember();
        Member bot = Objects.requireNonNull(event.getGuild()).getSelfMember();

        OptionMapping reasonOption = event.getOption("reason");
        OptionMapping timeOption = event.getOption("clear_messages");

        assert timeOption != null;

        int time = timeOption.getAsInt();

        EmbedBuilder eb = new EmbedBuilder();

        if(time > 7) {
            eb.setTitle("Error");
            eb.setDescription("Messages can only be deleted for the 7 past days");
            eb.setColor(Color.RED);
            eb.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

            event.replyEmbeds(eb.build()).setEphemeral(true).queue();
        }

        assert author != null;

        if(!author.hasPermission(Permission.BAN_MEMBERS)) {
            eb.setTitle("Error");
            eb.setDescription("You don't have permission to use this command");
            eb.setColor(Color.RED);
            eb.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

            event.replyEmbeds(eb.build()).setEphemeral(true).queue();
        } else if (!bot.hasPermission(Permission.BAN_MEMBERS)) {
            eb.setTitle("Error");
            eb.setDescription("I don't have permission to ban members");
            eb.setColor(Color.RED);
            eb.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

            event.replyEmbeds(eb.build()).setEphemeral(true).queue();
        }

        if(reasonOption != null) {
            String reason = reasonOption.getAsString();

            targetMember.ban(time, TimeUnit.DAYS).reason(reason).queue(
                    _ -> {
                        eb.setTitle("Success");
                        eb.setDescription(targetMember.getEffectiveName() + " has been banned successfully");
                        eb.setColor(Color.GREEN);
                        eb.addField("Reason", reason, false);
                        eb.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

                        event.replyEmbeds(eb.build()).queue();
                    },
                    error -> {
                        eb.setTitle("Error");
                        eb.setDescription("Something went wrong while banning the user");
                        eb.setColor(Color.RED);
                        eb.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

                        event.replyEmbeds(eb.build()).setEphemeral(true).queue();
                    }
            );
        } else {
            targetMember.ban(time, TimeUnit.DAYS).reason("Banned by " + author.getEffectiveName()).queue(
                    _ -> {
                        eb.setTitle("Success");
                        eb.setDescription(targetMember.getEffectiveName() + " has been banned successfully");
                        eb.setColor(Color.GREEN);
                        eb.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

                        event.replyEmbeds(eb.build()).queue();
                    },
                    error -> {
                        eb.setTitle("Error");
                        eb.setDescription("Something went wrong while banning the user");
                        eb.setColor(Color.RED);
                        eb.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

                        event.replyEmbeds(eb.build()).setEphemeral(true).queue();
                    }
            );
        }
    }
}
