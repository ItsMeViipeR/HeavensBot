package fr.viiper.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Unban {
    public static void run(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        OptionMapping unbanOption = event.getOption("id");

        assert guild != null;
        assert unbanOption != null;

        String id = unbanOption.getAsString();

        List<Guild.Ban> bans = guild.retrieveBanList().complete();

        AtomicBoolean found = new AtomicBoolean(false);
        @Nullable User user = null;

        for(Guild.Ban ban : bans) {
            if(ban.getUser().getId().equals(id)) {
                found.set(true);
                user = ban.getUser();
            }
        }

        if(found.get()) {
            if(user != null) {
                guild.unban(UserSnowflake.fromId(user.getId())).queue();

                EmbedBuilder eb = new EmbedBuilder();

                eb.setTitle("Unbanned");
                eb.setDescription(user.getAsMention() + " has been unbanned");
                eb.setColor(Color.GREEN);
                eb.setFooter("Requested by " + event.getUser().getGlobalName() + " (" + event.getUser().getName() + ")", event.getUser().getAvatarUrl());

                event.replyEmbeds(eb.build()).queue();
            }
        } else {
            event.reply("Not found").setEphemeral(true).queue();
        }
    }
}
