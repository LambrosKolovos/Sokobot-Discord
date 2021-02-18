package Reactions;

import bot.BotReplies;
import bot.Game;
import bot.GameManagement;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;

public class Reset extends Reaction{

    public Reset(){
        super("U+1f504");
    }
    @Override
    public void execute(GenericGuildMessageReactionEvent event,  Game currentGame, User user) {

        if (GameManagement.hasGame(user.getIdLong())) {
            currentGame.reset();
            event.getChannel().sendMessage(BotReplies.resetLevelMessage(currentGame.getLvID())).queue();
            event.getChannel().editMessageById(currentGame.getGameMessageID(), currentGame.gameMessage(user).build()).queue(

            );
        }
    }
}
