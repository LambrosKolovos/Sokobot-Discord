package Reactions;

import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;

public class UpReact extends MoveReaction{

    public UpReact() {
        super("U+2b06");
    }

    @Override
    public void execute(GenericGuildMessageReactionEvent event) {
        super.move(event, "w");
        super.checkGameOver(event);
    }


}
