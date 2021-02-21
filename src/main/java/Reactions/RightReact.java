package Reactions;

import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;


public class RightReact extends MoveReaction{
    public RightReact() {
        super("U+27a1");
    }

    @Override
    public void execute(GenericGuildMessageReactionEvent event) {
        super.move(event, "d");
        super.checkGameOver(event);
    }
}
