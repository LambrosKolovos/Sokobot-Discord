package Commands;

import bot.BotReplies;
import bot.Game;
import bot.GameManagement;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Stop extends Command{

    public Stop(){
        super("stop", 0, "stop");
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {
        Game currentGame =  GameManagement.getGame(event.getAuthor().getIdLong());

        if(GameManagement.hasGame(event.getAuthor().getIdLong())){
            event.getChannel().sendMessage(BotReplies.stopLevelMessage(currentGame.getLvID(), true)).queue();
            GameManagement.removeGame(event.getAuthor().getIdLong());

        }
        else{
            event.getChannel().sendMessage(BotReplies.stopLevelMessage(" ", false)).queue();
        }

    }
}
