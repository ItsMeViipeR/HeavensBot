package fr.viiper.Events;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

public class GuildLeave implements EventListener {
    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if(event instanceof GuildLeaveEvent guildLeaveEvent) {
            int guildCount = guildLeaveEvent.getJDA().getSelfUser().getMutualGuilds().size();

            guildLeaveEvent.getJDA().getPresence().setActivity(Activity.playing(String.valueOf(guildCount) + (guildCount > 1 ? " guilds" : " guild")));
        }
    }
}
