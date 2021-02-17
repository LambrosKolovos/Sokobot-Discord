package Reactions;

import bot.Game;
import bot.GameManagement;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public class Undo extends Reaction{

    public Undo(){
        super("U+2b05");
    }
    @Override
    public void execute(GuildMessageReactionAddEvent event) {
        User user = event.getUser();
        Game currentGame =  GameManagement.getGame(user.getIdLong());
        currentGame.undoMove();
        event.getChannel().editMessageById(currentGame.getGameMessageID(), currentGame.gameMessage(user).build()).queue(
                msg -> {
                    msg.removeReaction("U+2b05", user).queue();
                } );
    }
}
