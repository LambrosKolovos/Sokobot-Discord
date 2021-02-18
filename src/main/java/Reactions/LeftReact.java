package Reactions;

import bot.BotReplies;
import bot.Game;
import bot.GameManagement;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;

public class LeftReact extends Reaction {

    public LeftReact() {
        super("U+2b05");
    }

    @Override
    public void execute(GenericGuildMessageReactionEvent event, Game currentGame, User user) {
        currentGame.move("a");
        event.getChannel().editMessageById(currentGame.getGameMessageID(), GameManagement.getGame(user.getIdLong()).gameMessage(user).build()).queue();

        if(GameManagement.getGame(user.getIdLong()).isOver()){
            event.getChannel().sendMessage(BotReplies.levelComplete(GameManagement.getGame(user.getIdLong()))).queue();
            GameManagement.removeGame(user.getIdLong());
        }
    }


}
