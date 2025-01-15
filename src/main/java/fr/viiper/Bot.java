package fr.viiper;

import fr.viiper.Events.*;
import fr.viiper.Listeners.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import io.github.cdimascio.dotenv.Dotenv;

public class Bot {
    public static void main(String[] args) throws Exception {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("TOKEN");

        JDA api = JDABuilder.createLight(
                        token,
                        GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES
                )
                .addEventListeners(new Messages())
                .addEventListeners(new SlashCommandsListener())
                .addEventListeners(new Ready())
                .addEventListeners(new GuildJoin())
                .addEventListeners(new GuildLeave())
                .build()
                .awaitReady();

        CommandListUpdateAction commands = api.updateCommands();

        commands.addCommands(
                Commands.slash("ping", "Gives the bot latency"),
                Commands.slash("help", "Displays the help message"),
                Commands.slash("userinfo", "Displays information about a user").addOption(OptionType.USER, "user", "User to get information about", false),
                Commands.slash("serverinfo", "Displays information about the server"),
                Commands.slash("botinfo", "DIsplays information about the bot")
        ).queue();
    }
}
