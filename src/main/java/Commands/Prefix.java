package Commands;

import Database.SQLiteDataSource;
import bot.BotReplies;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Objects;

public class Prefix extends Command{

    public Prefix() {
        super("setprefix", 1, "setprefix [string]");
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

        if(Objects.requireNonNull(event.getMessage().getMember()).hasPermission(Permission.ADMINISTRATOR)){
            SQLiteDataSource.updateGuildPrefix(event.getGuild().getId(), args[0]);
            event.getChannel().sendMessage(BotReplies.prefixSet(args[0])).queue();
        }else{
            event.getChannel().sendMessage(BotReplies.noPermission()).queue();
        }
    }
}
