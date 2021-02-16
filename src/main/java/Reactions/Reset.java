package Reactions;

import bot.BotReplies;
import bot.Game;
import bot.GameManagement;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public class Reset extends Reaction{

    public Reset(){
        super("U+1f504");
    }
    @Override
    public void execute(GuildMessageReactionAddEvent event) {
        User user = event.getUser();
        Game currentGame =  GameManagement.getGame(user.getIdLong());

        if(GameManagement.hasGame(user.getIdLong())){
            currentGame.reset();
            event.getChannel().sendMessage(BotReplies.resetLevelMessage(currentGame.getLvID())).queue();
            event.getChannel().editMessageById(currentGame.getGameMessageID(), currentGame.gameMessage(user).build()).queue(
                    msg -> {
                        msg.addReaction("U+2b05").queue();
                        msg.addReaction("U+1f504").queue();
                        msg.addReaction("U+274c").queue();
                    }
            );
        }

    }
}
