package Handlers;

import bot.BotReplies;
import bot.GameManagement;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import Commands.*;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CommandHandler extends ListenerAdapter {

    private final HashMap<String, Command> commands;
    private final String prefix;
    private String message;
    private User user;

    public CommandHandler(){
        commands = new HashMap<>();
        loadCommands();
        prefix = "$";
    }

    private void loadCommands(){
        Command[] cmdArr = {
                new Play(),
                new Stop(),
                new Help(),
        };

        for (Command cmd : cmdArr) {
            String name = cmd.getName();
            commands.put(name, cmd);
        }
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if(!event.getAuthor().isBot()) {
            ArrayList<String> direction = new ArrayList<>();
            direction.add("w");
            direction.add("a");
            direction.add("s");
            direction.add("d");

            user = event.getAuthor();
            message = event.getMessage().getContentRaw().toLowerCase();

            if (direction.contains(message)) {
                //Received direction
                processDirection(event);

            } else {
                //Received a command

                String input[] = message.split(" ");
                String[] args = Arrays.copyOfRange(input, 1, input.length);
                String name = input[0].substring(1);

                processCommand(event, name, args);
            }
        }
    }


    private void processDirection(GuildMessageReceivedEvent event){
        if(GameManagement.hasGame(user.getIdLong())) {
            GameManagement.getGame(user.getIdLong()).move(message);
            event.getChannel().sendMessage(GameManagement.getGame(user.getIdLong()).gameMessage(user).build()).queue(
                    msg -> {
                        msg.addReaction("U+2b05").queue();
                        msg.addReaction("U+1f504").queue();
                        msg.addReaction("U+274c").queue();
                    }
            );
            if(GameManagement.getGame(user.getIdLong()).isOver()){
                event.getChannel().sendMessage(BotReplies.levelComplete(GameManagement.getGame(user.getIdLong()).getLvID())).queue();
                GameManagement.removeGame(user.getIdLong());
            }
        }
    }
    private void processCommand(GuildMessageReceivedEvent event, String name, String[] args){

        Command currentCommand = commands.get(name);

        if(message.startsWith(prefix) && currentCommand == null){
            event.getChannel().sendMessage(BotReplies.notFound()).queue();
            return;
        }

        if (!message.startsWith(prefix)) {
            //Not a command
            return;
        }

        if(currentCommand.getArgs() != args.length){
            event.getChannel().sendMessage(BotReplies.incorrectUsage(currentCommand)).queue();
            return;
        }

        currentCommand.execute(event, args);
    }
 }
