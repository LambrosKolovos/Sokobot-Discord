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
            Game game = new Game(user,args[0]);
            GameManagement.addGame(user.getIdLong(), game);
            Game currentGame = GameManagement.getGame(user.getIdLong());
            event.getChannel().sendMessage(currentGame.gameMessage(user, false).build()).queue(
                    msg -> {
                        game.setGameMessageID(msg.getIdLong());
                        msg.addReaction("U+2b05").queue();
                        msg.addReaction("U+2B06").queue();
                        msg.addReaction("U+2B07").queue();
                        msg.addReaction("U+27A1").queue();
                        msg.addReaction("U+21A9").queue();
                        msg.addReaction("U+1f504").queue();
                    });
            event.getChannel().sendMessage("_ _").queue(
                    msg ->{
                        game.setPlaceHolderID(msg.getIdLong());
                    }
            );
        }
    }
}
