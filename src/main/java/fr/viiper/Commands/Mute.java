package fr.viiper.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.util.List;

public class Mute {
    public static void run(SlashCommandInteractionEvent event) {
        Role mutedRole;
        Guild guild = event.getGuild();

        OptionMapping userOption = event.getOption("user");

        assert guild != null;
        assert userOption != null;

        Member bot = guild.getSelfMember();

        Member member = userOption.getAsMember();

        assert member != null;

        EmbedBuilder embedBuilder = new EmbedBuilder();

        if(member.getId().equals(bot.getId())) {
            embedBuilder.setTitle("Error");
            embedBuilder.setDescription("You can't mute me");
            embedBuilder.setColor(Color.RED);
            embedBuilder.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
        }

        if(member.hasPermission(Permission.MODERATE_MEMBERS)) {
            embedBuilder.setTitle("Error");
            embedBuilder.setDescription("This member cannot be muted.");
            embedBuilder.setColor(Color.RED);
            embedBuilder.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
        } else if(!guild.getSelfMember().hasPermission(Permission.MODERATE_MEMBERS)) {
            embedBuilder.setTitle("Error");
            embedBuilder.setDescription("I don't have permission to moderate members.");
            embedBuilder.setColor(Color.RED);
            embedBuilder.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
        }

        int guildRoles = guild.getRoles().size();
        int mutedRoles = guild.getRolesByName("Muted", true).size();

        if(guildRoles > 0 && mutedRoles == 0) {
            mutedRole = guild.createRole().setName("Muted").complete();

            guild.getTextChannels().forEach(channel -> {
                channel.upsertPermissionOverride(mutedRole)
                        .grant(Permission.EMPTY_PERMISSIONS)
                        .deny(Permission.MESSAGE_SEND)
                        .queue();
            });
        } else {
            mutedRole = guild.getRolesByName("Muted", true).getFirst();
        }

        if(member.hasPermission(Permission.MODERATE_MEMBERS)) {
            embedBuilder.setTitle("Error");
            embedBuilder.setDescription("This member cannot be muted because the user can moderate members.");
            embedBuilder.setColor(Color.RED);
            embedBuilder.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
        } else if(!guild.getSelfMember().hasPermission(Permission.MODERATE_MEMBERS)) {
            embedBuilder.setTitle("Error");
            embedBuilder.setDescription("I cannot mute this user because I don't have permission to moderate members.");
            embedBuilder.setColor(Color.RED);
            embedBuilder.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
        } else if(member.getRoles().contains(mutedRole)) {
            embedBuilder.setTitle("Error");
            embedBuilder.setDescription("This member cannot be muted because the user is already muted.");
            embedBuilder.setColor(Color.RED);
            embedBuilder.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
        } else {
            guild.addRoleToMember(member, mutedRole).queue(
                    _ -> {
                        embedBuilder.setTitle("Success");
                        embedBuilder.setDescription(member.getEffectiveName() + " has been muted successfully.");
                        embedBuilder.setColor(Color.GREEN);
                        embedBuilder.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

                        event.replyEmbeds(embedBuilder.build()).queue();
                    },
                    _ -> {
                        embedBuilder.setTitle("Error");
                        embedBuilder.setDescription("An error occurred.");
                        embedBuilder.setColor(Color.RED);
                        embedBuilder.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

                        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                    }
            );
        }
    }
}
