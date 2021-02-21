package Reactions;

import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;

public class LeftReact extends MoveReaction {

    public LeftReact() {
        super("U+2b05");
    }

    @Override
    public void execute(GenericGuildMessageReactionEvent event) {
       super.move(event, "a");
       super.checkGameOver(event);
    }


}
