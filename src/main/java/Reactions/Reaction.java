package Reactions;

import bot.Game;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;
public abstract class Reaction {

    private String code;
    private User user;
    private Game game;

    public Reaction(String code){
        this.code = code;
    }

    public abstract void execute(GenericGuildMessageReactionEvent event, Game game, User user);

    public String getCode() {
        return code;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
