package Reactions;

import bot.BotReplies;
import bot.Game;
import bot.GameManagement;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public class DownReact extends Reaction {

    public DownReact() {
        super("U+2b07");
    }

    @Override
    public void execute(GuildMessageReactionAddEvent event, Game currentGame, User user) {
        currentGame.move("s");
        event.getChannel().editMessageById(currentGame.getGameMessageID(), GameManagement.getGame(user.getIdLong()).gameMessage(user).build()).queue(
                msg -> msg.removeReaction(super.getCode(), user).queue()
        );

        if(GameManagement.getGame(user.getIdLong()).isOver()){
            event.getChannel().sendMessage(BotReplies.levelComplete(GameManagement.getGame(user.getIdLong()))).queue();
            GameManagement.removeGame(user.getIdLong());
        }
    }

}
