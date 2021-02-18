package Commands;

import Handlers.StatsHandler;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Stats extends Command {

    public Stats(){
        super("stats", 0,"$stats");
    }
    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage(StatsHandler.statsMessage().build()).queue();
    }
}
