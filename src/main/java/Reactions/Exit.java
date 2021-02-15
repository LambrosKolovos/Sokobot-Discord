package Reactions;

import bot.Game;
import bot.GameManagement;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public class Exit extends Reaction {

    public Exit(){
        super("U+274c");
    }

    @Override
    public void execute(GuildMessageReactionAddEvent event) {
        User user = event.getUser();

        GameManagement.removeGame(user.getIdLong());
        event.getChannel().sendMessage("```Game stopped! Play again with !play id```").queue();
    }
}
