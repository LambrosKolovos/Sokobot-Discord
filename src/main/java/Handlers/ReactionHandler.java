package Handlers;

import Reactions.*;
import bot.Game;
import bot.GameManagement;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
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
                new UpReact(),
                new DownReact(),
                new LeftReact(),
                new RightReact(),
                new Undo(),
                new Reset(),
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

        if(GameManagement.hasGame(event.getUserIdLong())){

            Game currentGame = GameManagement.getGame(event.getUserIdLong());
            User user = event.getUser();

            if(event.getReaction().getMessageIdLong() == currentGame.getGameMessageID()){
                //User reacted to correct game - proceed
                currentReact.execute(event, currentGame, user);
            }
        }

    }

}
