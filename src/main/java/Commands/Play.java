package Commands;

import bot.Game;
import bot.GameManagement;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
public class Play extends Command {

    public Play(){
        super("play", 1);
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {
        User user = event.getAuthor();

        if(GameManagement.hasGame(user.getIdLong())){
            event.getChannel().sendMessage(
                    "```You already have an active game!" +"\nPlease use !stop to end current game.```").queue();
            return;
        }
        else{
            GameManagement.addGame(user.getIdLong(), new Game(args[0]));
            event.getChannel().sendMessage(GameManagement.getGame(user.getIdLong()).gameMessage(user).build()).queue(
                    msg -> {
                        msg.addReaction("U+2b05").queue();
                        msg.addReaction("U+1f504").queue();
                        msg.addReaction("U+274c").queue();
                    });
        }
    }
}
