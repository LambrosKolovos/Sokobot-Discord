package Reactions;

import bot.Game;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;


public class Undo extends Reaction{

    public Undo(){
        super("U+21a9");
    }

    @Override
    public void execute(GenericGuildMessageReactionEvent event, Game currentGame, User user) {
        currentGame.undoMove();
        event.getChannel().editMessageById(currentGame.getGameMessageID(), currentGame.gameMessage(user).build()).queue();
    }
}
