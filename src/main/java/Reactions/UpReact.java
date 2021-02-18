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
        event.getChannel().editMessageById(currentGame.getGameMessageID(), currentGame.gameMessage(user).build()).queue();

        if(GameManagement.getGame(user.getIdLong()).isOver()){
            event.getChannel().sendMessage(BotReplies.levelComplete(currentGame)).queue();
            GameManagement.removeGame(user.getIdLong());
        }
    }


}
