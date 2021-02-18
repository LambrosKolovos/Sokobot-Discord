package Handlers;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StatsHandler extends ListenerAdapter {

    private static int totalServers;
    private static int totalMembers;
    private List<Guild> guildList;

    private String homeID;
    private String joinroomID;
    private String leaveroomID;

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        System.out.println("Bot ready!");
        guildList = new ArrayList<>();

        try {
            readHomeData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        guildList = event.getJDA().getGuildCache().asList();
        calculateAudience(guildList);
    }

    @Override
    public void onGuildJoin(@Nonnull GuildJoinEvent event) {
        TextChannel join = (TextChannel) Objects.requireNonNull(event.getJDA().getGuildById(homeID)).getGuildChannelById(joinroomID);
        join.sendMessage(notification(
                "✅ I joined **" + event.getGuild().getName() +
                        "** that has **" + event.getGuild().getMemberCount() + "** members!", true).build()).queue();

        guildList = event.getJDA().getGuildCache().asList();
        calculateAudience(guildList);
    }

    @Override
    public void onGuildLeave(@Nonnull GuildLeaveEvent event) {
        TextChannel join = (TextChannel) Objects.requireNonNull(event.getJDA().getGuildById(homeID)).getGuildChannelById(joinroomID);
        join.sendMessage(notification(
                "❌ I left **" + event.getGuild().getName() +
                        "** that had **" + event.getGuild().getMemberCount() + "** members!", false).build()).queue();

        guildList = event.getJDA().getGuildCache().asList();
        calculateAudience(guildList);
    }

    private void calculateAudience(List<Guild> guilds){
        totalMembers = 0;
        totalServers = 0;
        for(Guild g : guilds){
            totalMembers += g.getMemberCount();
            totalServers++;
        }
    }

    private void readHomeData() throws IOException {
        File file = new File("src/Token.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while((line = br.readLine()) != null){
            String[] data = line.split(" ");
            if(data[0].equals("HOME"))
                homeID = data[1];
            else if(data[0].equals("JOIN"))
                joinroomID = data[1];
            else if(data[0].equals("EXIT"))
                leaveroomID = data[1];
        }
    }

    private static EmbedBuilder notification(String text, boolean hasJoined){
        EmbedBuilder message = new EmbedBuilder();

        message.setTitle(hasJoined ? "JOIN" : "KICK");
        message.setDescription(text);
        message.setColor(hasJoined ? Color.GREEN : Color.RED);

        return message;
    }

    public static EmbedBuilder statsMessage(){
        EmbedBuilder message = new EmbedBuilder();

        message.setTitle("Stats \uD83D\uDCC8");
        message.setDescription(
                "\n\uD83D\uDDD3 Published at: `01/01/2000`\n\n" +
                "\uD83C\uDFAE Total server count `" + totalServers+ "`\n\n" +
                        "\uD83E\uDDB8\u200D Total member count `" + totalMembers+ "`\n"
        );
        message.setColor(Color.GREEN);

        return message;
    }
}
