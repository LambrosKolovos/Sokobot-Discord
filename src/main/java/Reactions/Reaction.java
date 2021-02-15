package Reactions;

import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public abstract class Reaction {

    private String code;

    public Reaction(String code){
        this.code = code;
    }

    public abstract void execute(GuildMessageReactionAddEvent event);

    public String getCode() {
        return code;
    }

}
