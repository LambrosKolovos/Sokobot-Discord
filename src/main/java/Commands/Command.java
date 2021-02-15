package Commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class Command {

    private final String name;
    private final int args;

    public Command(String name, int args) {
        this.name = name;
        this.args = args;
    }

    public abstract void execute(GuildMessageReceivedEvent event, String args[]);

    public String getName() {
        return name;
    }

    public int getArgs() {
        return args;
    }
}
