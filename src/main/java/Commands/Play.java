package Commands;

import bot.BotReplies;
import bot.Game;
import bot.GameManagement;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
public class Play extends Command {

    public Play(){
        super("play", 1, "$play id");
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {
        User user = event.getAuthor();

        if(GameManagement.hasGame(user.getIdLong())){
            event.getChannel().sendMessage(BotReplies.activeGameWarn()).queue();
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
