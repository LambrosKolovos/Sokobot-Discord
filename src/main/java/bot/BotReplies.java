package bot;

import Commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;

public class BotReplies {

    public static MessageEmbed levelComplete(Game g, User user){
        return response("Congratulations! \uD83C\uDF89","\n⭐ " + user.getAsMention() + " completed **Level " + g.getLvID() + "** in **" + g.getMoves()+
                "** moves! ⭐", false).build();
    }

    public static MessageEmbed activeGameWarn(){
        return response("Error","❌ You have already have an active game!\n\n" +
                "✅ Do **$stop** to play another level", true).build();
    }

    public static MessageEmbed stopLevelMessage(String id, boolean active){
        return active?
                response("Stop","✅ Level " + id + " stopped! ", false).build() :
                response("Error","❌You don't have an active game to stop!", true).build() ;
    }

    public static MessageEmbed resetLevelMessage(String id){
        return  response("Reset","✅ Resetting level " + id + " ! ", false).build();
    }

    public static MessageEmbed incorrectUsage(String prf, Command cmd){
        return response("Error","❌ Incorrect usage!\n\n✅ Correct usage -> **" + prf + cmd.getUsage()+"**", true).build() ;
    }

    public static MessageEmbed notFound(){
        return response("Error","❌ No such command found!", true).build();
    }

    public static MessageEmbed noPermission(){
        return response("Error", "❌ You are not an admin to use this command!", true).build();
    }

    public static MessageEmbed prefixSet(String prefix){
        return  response("Success", "✅ Prefix successfully set to ` " + prefix + " ` !", false).build();
    }
    private static EmbedBuilder response(String title, String text, boolean isError){
        EmbedBuilder msg = new EmbedBuilder();

        msg.setTitle(title);
        msg.setColor(isError ? Color.RED : Color.GREEN);
        msg.setDescription(text);
        return msg;
    }

}
