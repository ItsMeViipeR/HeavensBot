package fr.viiper.Events;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

public class Ready implements EventListener
{
    @Override
    public void onEvent(@NotNull GenericEvent event)
    {
        if (event instanceof ReadyEvent)
        {
            SelfUser botUser = event.getJDA().getSelfUser();
            String botName = botUser.getName();
            int guildCount = botUser.getMutualGuilds().size();

            event.getJDA().getPresence().setActivity(Activity.playing(String.valueOf(guildCount) + (guildCount > 1 ? " guilds" : " guild")));
            System.out.printf("Logged in as %s%n", botName);
        }
    }
}