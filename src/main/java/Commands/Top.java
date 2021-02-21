package Commands;

import Database.SQLiteDataSource;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Top extends Command {


    private String guid;
    private String serverName;

    public Top(){
        super("top", 0, "top");
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {
        guid = event.getGuild().getId();
        serverName = event.getGuild().getName();
        event.getChannel().sendMessage(buildRankings()).queue();
    }

    private MessageEmbed buildRankings(){
        EmbedBuilder msg = new EmbedBuilder();
        String[][] playerInfo;
        playerInfo = SQLiteDataSource.fetchScores(guid);

        msg.setTitle("Adventure Leaderboard");
        msg.setDescription("```diff\n" +
                "-      Top 10 players in "+ serverName + "\t\t - ```");
        msg.addField(" ", leaderboard(playerInfo), false);
        return msg.build();
    }

    private String leaderboard(String[][] playerInfo){
        String str = "```css\n";

        str += header();

        for(int i=0; i<playerInfo.length; i++){
            if(playerInfo[i][0] != null)
                str += playerRow(i+1, playerInfo[i][0], playerInfo[i][1]);
            else
                str += playerRow(i+1, " ", " ");
        }

        str += "\n```";
        return str;
    }

    private String header(){
        return "Rank\t\t" + "Name\t\t\t\t" + "Level\n";
    }
    private String playerRow(int rank, String name, String score){
        int spaceBetween = 22 - name.length() - score.length();
        String space = "";
        for(int i=0; i<spaceBetween; i++){
            space += " ";
        }
        String playerStats = name + space + "\t" + score;
        return "\n#" + rank + "\t\t  " + playerStats + "\n";
    }
}
