package Handlers;

import Reactions.Exit;
import  Reactions.Reaction;
import Reactions.Reset;
import Reactions.Undo;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class ReactionHandler extends ListenerAdapter {

    private final HashMap<String, Reaction> reactions;

    public ReactionHandler(){
        reactions = new HashMap<>();
        loadReactions();

    }


    private void loadReactions(){
        Reaction[] reactArr = {
                new Reset(),
                new Exit(),
                new Undo()
        };

        for (Reaction react : reactArr) {
            String code = react.getCode();
            reactions.put(code, react);
        }
    }

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        String reactCode = event.getReactionEmote().getAsCodepoints();

        Reaction currentReact = reactions.get(reactCode);

        if(event.getUser().isBot())
            return;

        if(currentReact == null)
            return;

        currentReact.execute(event);
    }
}
