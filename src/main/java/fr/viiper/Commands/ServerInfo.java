package fr.viiper.Commands;

import fr.viiper.Utils.Date;
import fr.viiper.Utils.RandomColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class ServerInfo {
    public static void run(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        if(guild == null) {
            event.reply("This command can only be used in a server!").setEphemeral(true).queue();
            return;
        }

        Member user = guild.retrieveOwner().complete();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Server Info");
        embedBuilder.setDescription("Here's the info for " + guild.getName() + "!");
        embedBuilder.setColor(RandomColor.generate());
        embedBuilder.addField("Server name", guild.getName(), true);
        embedBuilder.addField("Server ID", guild.getId(), true);

        if(user != null) {
            embedBuilder.addField("Server owner", user.getAsMention(), true);
        }

        embedBuilder.addField("Server members", String.valueOf(guild.getMemberCount()), true);
        embedBuilder.addField("Server creation date", guild.getTimeCreated().format(Date.format()).toString(), true);

        event.replyEmbeds(embedBuilder.build()).queue();
    }
}
