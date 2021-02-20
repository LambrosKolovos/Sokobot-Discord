package Commands;

import Database.SQLiteDataSource;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;


public class Help extends Command{

    public Help(){
        super("help", 0, "help");
    }
    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {
        Guild guild = event.getGuild();
        String prf = SQLiteDataSource.getGuildPrefix(event.getGuild().getId());
        event.getChannel().sendMessage(helpMessage(guild, prf).build()).queue();
    }

    private EmbedBuilder helpMessage(Guild g, String prf){
        EmbedBuilder message = new EmbedBuilder();
        message.setTitle("Help ");
        message.setThumbnail(g.getSelfMember().getUser().getAvatarUrl());
        message.setDescription("This is a discord bot for the classic game of Sokoban!");
        message.setColor(new Color(02,212,56));
        message.addField("__How to play__", "Type **$play id** ." +
                "\nCurrently there are 10 levels availabe." +
                "\n`Arrows` - Moves your character.\n", false);
        message.addField("__Goal__","Place every box - \u23F9 on top of a box target - ❎ to win!", false);
        message.addField("__Reactions__",
                "↩️- Undo your last move" +
                        "\n\uD83D\uDD04 - Resets the level.",false);
        message.addField("__Commands__",
                  "``" + prf + "play id``: Begins a game for the selected level."+"" +
                        "\n``"+ prf + "stop``: Stops current game and allows user to select a new level."+
                        "\n``"+ prf + "help``: Displays help message." +
                        "\n``"+ prf + "stats``: Shows bot stats.",false);
        message.addField("__Developed__", "One day I was bored. So I started building this." +"\n" +
                "\uD83D\uDCBB  [Source code](https://github.com/LambrosKolovos/Sokobot-Discord)", false);
        message.addBlankField(false);
        message.setFooter("Created by Lambros #9202", "https://i.ibb.co/82fNczJ/avatar.png");

        return message;
    }
}
