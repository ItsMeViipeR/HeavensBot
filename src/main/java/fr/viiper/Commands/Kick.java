package fr.viiper.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.util.Objects;

public class Kick {
    public static void run(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        Member target = Objects.requireNonNull(event.getOption("target")).getAsMember();
        OptionMapping reason = event.getOption("reason");

        assert guild != null;
        assert target != null;

        Member bot = guild.getSelfMember();

        EmbedBuilder embedBuilder = new EmbedBuilder();

        if(target.getId().equals(bot.getId())) {
            embedBuilder.setTitle("Error");
            embedBuilder.setDescription("You can't kick me");
            embedBuilder.setColor(Color.RED);
            embedBuilder.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
        }

        if(target.hasPermission(Permission.KICK_MEMBERS)) {
            embedBuilder.setTitle("Error");
            embedBuilder.setDescription("You cannot kick this member 'cause this member has moderator permissions");
            embedBuilder.setColor(Color.RED);
            embedBuilder.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

            event.replyEmbeds(embedBuilder.build()).queue();
        } else if(!Objects.requireNonNull(event.getGuild()).getSelfMember().hasPermission(Permission.KICK_MEMBERS)) {
            embedBuilder.setTitle("Error");
            embedBuilder.setDescription("I don't have permission to kick this member");
            embedBuilder.setColor(Color.RED);
            embedBuilder.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

            event.replyEmbeds(embedBuilder.build()).queue();
        }

        if(reason != null) {
            Objects.requireNonNull(event.getGuild()).kick(target).reason(reason.getAsString()).queue();

            embedBuilder.setTitle("Success");
            embedBuilder.setDescription("Kicked " + target.getEffectiveName() + " for " + reason.getAsString());
            embedBuilder.setColor(Color.GREEN);
            embedBuilder.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

            event.replyEmbeds(embedBuilder.build()).queue();

        } else {
            Objects.requireNonNull(event.getGuild()).kick(target).reason("Kicked by " + event.getUser().getEffectiveName()).queue();

            embedBuilder.setTitle("Success");
            embedBuilder.setDescription("Kicked " + target.getEffectiveName());
            embedBuilder.setColor(Color.GREEN);
            embedBuilder.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

            event.replyEmbeds(embedBuilder.build()).queue();
        }
    }
}
