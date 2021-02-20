package Handlers;

import bot.BotReplies;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.RichPresence;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import Commands.*;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

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
                new Stats(),
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
            message = event.getMessage().getContentRaw().toLowerCase();
            if (message.startsWith(prefix)) {
                user = event.getAuthor();
                String input[] = message.split(" ");
                String[] args = Arrays.copyOfRange(input, 1, input.length);
                processCommand(event, input, args);
            }
        }
    }

    private void processCommand(GuildMessageReceivedEvent event,String[] input, String[] args){

        String name = input[0].substring(1);
        Command currentCommand = commands.get(name);

        if(message.startsWith(prefix) && currentCommand == null){
            event.getChannel().sendMessage(BotReplies.notFound()).queue();
            return;
        }


        if(currentCommand.getArgs() != args.length){
            event.getChannel().sendMessage(BotReplies.incorrectUsage(currentCommand)).queue();
            return;
        }

        currentCommand.execute(event, args);
    }
 }
