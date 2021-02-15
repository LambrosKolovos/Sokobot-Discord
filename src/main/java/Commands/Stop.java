package Commands;

import bot.GameManagement;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Stop extends Command{

    public Stop(){
        super("stop", 0);
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {
        if(GameManagement.hasGame(event.getAuthor().getIdLong())){
            GameManagement.removeGame(event.getAuthor().getIdLong());
            event.getChannel().sendMessage("```Game stopped! Play again with !play id```").queue();
        }
        else{
            event.getChannel().sendMessage("```You don't have an active game to stop!```").queue();
        }

    }
}
