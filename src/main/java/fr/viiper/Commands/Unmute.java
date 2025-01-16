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

public class Unmute {
    public static void run(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        OptionMapping option = event.getOption("user");

        assert guild != null;
        assert option != null;

        List<Role> mutedRole = guild.getRolesByName("Muted", true);
        Member member = option.getAsMember();
        Member bot = guild.getSelfMember();

        assert member != null;

        EmbedBuilder eb = new EmbedBuilder();

        if(!bot.hasPermission(Permission.MODERATE_MEMBERS)) {
            eb.setTitle("Error");
            eb.setDescription("I can't unmute this user because I don't have permission to moderate members");
            eb.setColor(Color.RED);
            eb.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

            event.replyEmbeds(eb.build()).queue();
        } else if(mutedRole.isEmpty()) {
            eb.setTitle("Error");
            eb.setDescription("There's no \"Muted\" role");
            eb.setColor(Color.RED);
            eb.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

            event.replyEmbeds(eb.build()).queue();
        } else if(member.getRoles().contains(mutedRole.getFirst())) {
            Role role = mutedRole.getFirst();

            guild.removeRoleFromMember(member, role).queue(
                    _ -> {
                        eb.setTitle("Success");
                        eb.setDescription(member.getAsMention() + " has been unmuted successfully");
                        eb.setColor(Color.GREEN);
                        eb.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

                        event.replyEmbeds(eb.build()).queue();
                    },
                    _ -> {
                        eb.setTitle("Error");
                        eb.setDescription("Something went wrong.");
                        eb.setColor(Color.RED);
                        eb.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

                        event.replyEmbeds(eb.build()).queue();
                    }
            );
        } else {
            eb.setTitle("Error");
            eb.setDescription(member.getAsMention() + " is not muted");
            eb.setColor(Color.RED);
            eb.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

            event.replyEmbeds(eb.build()).queue();
        }
    }
}
