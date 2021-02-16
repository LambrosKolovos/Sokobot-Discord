package Commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class Command {

    private final String name;
    private final int args;
    private final String usage;

    public Command(String name, int args, String usage) {
        this.name = name;
        this.args = args;
        this.usage = usage;
    }

    public abstract void execute(GuildMessageReceivedEvent event, String args[]);

    public String getName() {
        return name;
    }

    public int getArgs() {
        return args;
    }

    public String getUsage() {
        return usage;
    }
}
