package Reactions;

import bot.BotReplies;
import bot.Game;
import bot.GameManagement;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;

public class UpReact extends Reaction{

    public UpReact() {
        super("U+2b06");
    }

    @Override
    public void execute(GenericGuildMessageReactionEvent event, Game currentGame, User user) {
        currentGame.move("w");
        event.getChannel().editMessageById(currentGame.getGameMessageID(), GameManagement.getGame(user.getIdLong()).gameMessage(user, false).build()).queue();
        event.getChannel().editMessageById(currentGame.getPlaceHolderID(), "_ _").queue();

        if(GameManagement.getGame(user.getIdLong()).isOver()){
            event.getChannel().editMessageById(currentGame.getPlaceHolderID(), BotReplies.levelComplete(currentGame, user)).queue();
            GameManagement.removeGame(user.getIdLong());
        }
    }


}
