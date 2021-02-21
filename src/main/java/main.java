import Database.SQLiteDataSource;
import Handlers.CommandHandler;
import Handlers.MoveReactionHandler;
import Handlers.ReactionHandler;
import Handlers.StatsHandler;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.io.*;

public class main {

    public static void main(String[] args) throws LoginException, IOException {

        SQLiteDataSource.findDatabase();

        JDABuilder
                .createDefault(token())
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setActivity(Activity.playing("$play to start"))
                .addEventListeners(new CommandHandler())
                .addEventListeners(new ReactionHandler())
                .addEventListeners(new MoveReactionHandler())
                .addEventListeners(new StatsHandler())
                .build();
    }

    private static String token() throws IOException {
        File file = new File("src/Token.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String tokenStr = br.readLine();
        fr.close();
        return tokenStr;
    }

}