package fr.viiper.Events;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

public class GuildJoin implements EventListener {
    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if(event instanceof GuildJoinEvent guildJoinEvent) {
            int guildCount = guildJoinEvent.getJDA().getSelfUser().getMutualGuilds().size();

            guildJoinEvent.getJDA().getPresence().setActivity(Activity.playing(String.valueOf(guildCount) + (guildCount > 1 ? " guilds" : " guild")));
        }
    }
}
