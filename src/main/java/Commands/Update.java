package Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Update extends Command {

    public Update(){
        super("update", 0, "update");
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage(updateMsg()).queue();
    }

    private MessageEmbed updateMsg(){
        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle("Upcoming");
        msg.addField(" ",
                "\uD83D\uDCCC 20 Adventure levels weekly!" +
                "\n\uD83D\uDCCC Leaderboard for each adventure level based on moves" +
                "\n\uD83D\uDCCC Ability to move with ` W A S D ` messages" +
                "\n\uD83D\uDCCC Customize player emoji and map color", false);
        msg.addBlankField(false);
        msg.setFooter("");

        return msg.build();
    }
}
