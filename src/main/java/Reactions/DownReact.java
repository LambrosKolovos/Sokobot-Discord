package Reactions;

import bot.BotReplies;
import bot.Game;
import bot.GameManagement;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;

public class DownReact extends Reaction {

    public DownReact() {
        super("U+2b07");
    }

    @Override
    public void execute(GenericGuildMessageReactionEvent event, Game currentGame, User user) {
        currentGame.move("s");
        event.getChannel().editMessageById(currentGame.getGameMessageID(), GameManagement.getGame(user.getIdLong()).gameMessage(user, false).build()).queue();
        event.getChannel().editMessageById(currentGame.getPlaceHolderID(), "_ _").queue();

        if(GameManagement.getGame(user.getIdLong()).isOver()){
            event.getChannel().editMessageById(currentGame.getPlaceHolderID(), BotReplies.levelComplete(currentGame, user)).queue();
            GameManagement.removeGame(user.getIdLong());
        }
    }

}
