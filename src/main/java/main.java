import Handlers.CommandHandler;
import Handlers.ReactionHandler;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.io.*;

public class main {



    public static void main(String[] args) throws LoginException, IOException {
        JDABuilder
                .createDefault(token())
                .setActivity(Activity.playing("$play to start"))
                .addEventListeners(new CommandHandler())
                .addEventListeners(new ReactionHandler())
                .build();

        System.out.println("Bot is ready!");
    }

    private static String token() throws IOException {
        File file = new File("src/Token.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String tokenStr = br.readLine();
        return tokenStr;
    }
}