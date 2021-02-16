package bot;

import Commands.Command;

public class BotReplies {

    public static String levelComplete(String id){
        return "```css\n" + "Congratulations! \uD83C\uDF89 \n" + "Level " + id + " complete!\n" + "```";
    }

    public static String activeGameWarn(){
        return "```diff\n- You have an active game. Do $stop to play another level -\n```";
    }

    public static String stopLevelMessage(String id, boolean active){
        return active? "```diff\n- Level " + id + " stopped -\n```" : "```diff\n- You don't have an active game to stop! -\n```";
    }

    public static String resetLevelMessage(String id){
        return "```fix\n - Resetting level "+ id + " -\n```";
    }

    public static String incorrectUsage(Command cmd){
        return "```diff\n" +
                "- Incorrect usage!\n" +
                "+ Correct usage -> " + cmd.getUsage() + "\n```";
    }

    public static String notFound(){
        return "```diff\n- No such command found -\n```";
    }
}
