package Handlers;

import Reactions.*;
import bot.Game;
import bot.GameManagement;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class MoveReactionHandler extends ListenerAdapter {
    private final HashMap<String, MoveReaction> moveReactions;

    public MoveReactionHandler(){
        moveReactions = new HashMap<>();
        loadReactions();
    }

    private void loadReactions(){
        MoveReaction[] moveReactArr = {
                new UpReact(),
                new LeftReact(),
                new DownReact(),
                new RightReact(),
        };

        for (MoveReaction react : moveReactArr) {
            String code = react.getCode();
            moveReactions.put(code, react);
        }
    }

    public void onGenericGuildMessageReaction(@Nonnull GenericGuildMessageReactionEvent event) {
        String reactCode = event.getReactionEmote().getAsCodepoints();
        MoveReaction currentReact = moveReactions.get(reactCode);

        if(event.getUser().isBot())
            return;

        if(currentReact == null)
            return;

        if(GameManagement.hasGame(event.getUserIdLong())){

            Game currentGame = GameManagement.getGame(event.getUserIdLong());
            User user = event.getUser();

            if(event.getReaction().getMessageIdLong() == currentGame.getGameMessageID()){
                //User reacted to correct game - proceed
                currentReact.setUser(user);
                currentReact.setCurrentGame(currentGame);

                currentReact.execute(event);
            }
        }
    }
}
