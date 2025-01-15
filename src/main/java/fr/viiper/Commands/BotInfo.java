package fr.viiper.Commands;

import fr.viiper.Utils.RandomColor;
import fr.viiper.Utils.UptimeFormatter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.atomic.AtomicInteger;

public class BotInfo {
    public static void run(SlashCommandInteractionEvent event) {
        User botUser = event.getJDA().getSelfUser();

        String botName = botUser.getName();
        String avatarUrl = botUser.getAvatarUrl();

        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Bot Info");
        eb.setDescription(botName);
        eb.setColor(RandomColor.generate());

        if(avatarUrl != null) {
            eb.setThumbnail(botUser.getAvatarUrl());
        }

        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        long ms = rb.getUptime();

        String uptime = UptimeFormatter.formatUptime(ms);

        int guildsCount = botUser.getMutualGuilds().size();
        AtomicInteger usersCount = new AtomicInteger();

        botUser.getMutualGuilds().forEach(guild -> {
            usersCount.addAndGet(guild.getMemberCount());
        });

        eb.addField("ID", botUser.getId(), true);
        eb.addField("Name", botName, true);
        eb.addField("Guild count", String.valueOf(guildsCount), true);
        eb.addField("User count", String.valueOf(usersCount.get()), true);
        eb.addField("Uptime", uptime, true);

        event.replyEmbeds(eb.build()).queue();
    }
}
