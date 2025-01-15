package fr.viiper.Commands;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import fr.viiper.Utils.Date;
import fr.viiper.Utils.RandomColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class UserInfo {
    public static void run(SlashCommandInteractionEvent event) {
        OptionMapping optionValue = event.getOption("user");
        EmbedBuilder embedBuilder = new EmbedBuilder();
        DateTimeFormatter formatter = Date.format();

        embedBuilder.setTitle("User Info");
        embedBuilder.setColor(RandomColor.generate());

        User user;
        Member member;

        if (optionValue == null) {
            user = event.getUser();
            member = event.getMember();
            embedBuilder.setDescription("Here's your info!");
        } else {
            user = optionValue.getAsUser();
            member = optionValue.getAsMember();
            embedBuilder.setDescription("Here's the info for " + (member != null ? member.getEffectiveName() : "Unknown") + "!");
        }

        if (member != null) {
            embedBuilder.addField("Name", member.getEffectiveName(), true);
            embedBuilder.addField("Joined At", member.getTimeJoined().format(formatter), true);

            if (member.getTimeBoosted() != null) {
                OffsetDateTime boostedAt = member.getTimeBoosted();

                if(boostedAt != null) {
                    String formattedBoostedAt = boostedAt.format(formatter);

                    embedBuilder.addField("Boosted", formattedBoostedAt, true);
                }
            }

            List<Role> roles = member.getRoles();
            String rolesString = roles.isEmpty() ? "None" : roles.stream().limit(5).map(Role::getName).reduce((a, b) -> a + ", " + b).orElse("");

            embedBuilder.addField("5 First Roles", rolesString, true);
        } else {
            embedBuilder.addField("Name", "Unknown", true);
            embedBuilder.addField("Joined At", "Unknown", true);
        }

        embedBuilder.addField("ID", user.getId(), true);
        embedBuilder.addField("Tag", user.getAsTag(), true);
        embedBuilder.addField("Created At", user.getTimeCreated().format(formatter), true);
        embedBuilder.setThumbnail(user.getAvatarUrl());

        embedBuilder.setFooter("Requested by " + event.getUser().getAsTag(), event.getUser().getAvatarUrl());
        embedBuilder.setTimestamp(event.getTimeCreated());

        MessageEmbed embed = embedBuilder.build();
        event.replyEmbeds(embed).queue();
    }
}
