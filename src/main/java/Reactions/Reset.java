package Reactions;

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
            event.getChannel().sendMessage("```Resseting level...```").queue();
            event.getChannel().sendMessage(currentGame.gameMessage(user).build()).queue(
                    msg -> {
                        msg.addReaction("U+2b05").queue();
                        msg.addReaction("U+1f504").queue();
                        msg.addReaction("U+274c").queue();
                    }
            );
        }
        else{
            event.getChannel().sendMessage("```You are not allowed to do this!```").queue();
        }
    }
}
