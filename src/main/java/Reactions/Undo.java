package Reactions;

import bot.Game;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public class Undo extends Reaction{

    public Undo(){
        super("U+21a9");
    }
    @Override
    public void execute(GuildMessageReactionAddEvent event,  Game currentGame, User user) {
        currentGame.undoMove();
        event.getChannel().editMessageById(currentGame.getGameMessageID(), currentGame.gameMessage(user).build()).queue(
                msg -> msg.removeReaction(super.getCode(), user).queue());
    }

}
