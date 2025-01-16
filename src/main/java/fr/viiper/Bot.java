package fr.viiper;

import fr.viiper.Events.*;
import fr.viiper.Listeners.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
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
                Commands.slash("userinfo", "Displays information about a user")
                        .addOption(
                                OptionType.USER,
                                "user",
                                "User to get information about",
                                false
                        ),
                Commands.slash("serverinfo", "Displays information about the server"),
                Commands.slash("botinfo", "Displays information about the bot"),
                Commands.slash("clear", "Clear a specified amount of messages in the current channel")
                        .addOption(
                                OptionType.INTEGER,
                                "amount",
                                "Number of messages to clear",
                                true
                        )
                        .setDefaultPermissions(
                                DefaultMemberPermissions.enabledFor(Permission.MESSAGE_MANAGE)
                        ),
                Commands.slash("kick", "Kick a specified user")
                        .addOption(
                                OptionType.USER,
                                "target",
                                "The member to kick",
                                true
                        )
                        .addOption(
                                OptionType.STRING,
                                "reason",
                                "The reason why you kick this member",
                                false
                        )
                        .setDefaultPermissions(
                                DefaultMemberPermissions.enabledFor(Permission.MODERATE_MEMBERS)
                        ),
                Commands.slash("ban", "Ban a specified user")
                        .addOption(
                                OptionType.USER,
                                "target",
                                "The member to ban",
                                true
                        )
                        .addOption(OptionType.INTEGER, "clear_messages", "Clear the messages for x days", true, false)
                        .addOption(
                                OptionType.STRING,
                                "reason",
                                "The reason why you ban this member",
                                false
                        )
                        .setDefaultPermissions(
                                DefaultMemberPermissions.enabledFor(Permission.MODERATE_MEMBERS, Permission.BAN_MEMBERS)
                        ),
                Commands.slash("unban", "Unban a specified user")
                        .addOption(OptionType.STRING, "id", "the id of the member you want to unbar", true, false)
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.BAN_MEMBERS)),
                Commands.slash("mute", "Mute a specified user")
                        .addOption(OptionType.USER, "user", "User to mute", true, false)
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_ROLES, Permission.MODERATE_MEMBERS)),
                Commands.slash("unmute", "Unmute a specified user")
                        .addOption(OptionType.USER, "user", "User to unmute", true, false)
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_ROLES, Permission.MODERATE_MEMBERS))
        ).queue();
    }
}
