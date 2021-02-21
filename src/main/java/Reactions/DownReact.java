package Reactions;

import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;

public class DownReact extends MoveReaction {

    public DownReact() {
        super("U+2b07");
    }

    @Override
    public void execute(GenericGuildMessageReactionEvent event) {
        super.move(event, "s");
        super.checkGameOver(event);
    }

}
