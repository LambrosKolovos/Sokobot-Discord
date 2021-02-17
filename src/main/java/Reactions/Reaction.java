package Reactions;

import bot.Game;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public abstract class Reaction {

    private String code;

    public Reaction(String code){
        this.code = code;
    }

    public abstract void execute(GuildMessageReactionAddEvent event, Game game, User user);

    public String getCode() {
        return code;
    }

}
