package fr.viiper.Listeners;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashCommandsAutocompleteListener extends ListenerAdapter {
    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        String focusedOption = event.getFocusedOption().getValue();

        switch(focusedOption) {
            case "userinfo" -> {
                var users = event.getJDA().getUsers();

                event.replyChoices(users.stream().map(user -> new net.dv8tion.jda.api.interactions.commands.Command.Choice(user.getName(), user.getId())).toList()).queue();
            }
        }
    }
}