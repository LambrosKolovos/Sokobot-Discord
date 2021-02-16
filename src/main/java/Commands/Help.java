package Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.internal.entities.UserById;

public class Help extends Command{

    public Help(){
        super("help", 0, "$help");
    }
    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {
        Guild guild = event.getGuild();
        event.getChannel().sendMessage(helpMessage(guild).build()).queue();
    }

    private EmbedBuilder helpMessage(Guild g){
        Member dev = g.getMemberById(279739792319840257L);
        EmbedBuilder message = new EmbedBuilder();
        message.setTitle("Help ");
        message.setThumbnail(g.getSelfMember().getUser().getAvatarUrl());
        message.setDescription("This is a discord bot for the classic game of Sokoban!");
        message.addField("__How to play__", "Type **$play id** ." +
                "\nCurrently there are 10 levels availabe." +
                "\n``WASD``: Use W A S D to move while in game" , false);
        message.addField("__Goal__","Place every box - \u23F9 on top of a box target - ❎ to win!", false);
        message.addField("__Reactions__",
                "⬅ - Undo your last move" +
                        "\n\uD83D\uDD04 - Resets the level" +
                        "\n❌ - Stops current level.",false);
        message.addField("__Commands__",
                "``$play id``: Begins a game for the selected level"+"" +
                        "\n``$stop``: Stops current game and allows user to select a new level"+
                        "\n``$help``: Displays help message",false);
        message.addBlankField(false);
        message.addField("__Developed__", "One day @Lambros was bored. So he made this." +"\n" +
                "\uD83D\uDCBB https://github.com/LambrosKolovos/Sokobot-Discord", false);

        return message;
    }
}
