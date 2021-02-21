package Reactions;

import bot.BotReplies;
import bot.Game;
import bot.GameManagement;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;

public abstract class MoveReaction {

    private String code;
    private User user;
    private Game currentGame;

    public MoveReaction(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public abstract void execute(GenericGuildMessageReactionEvent event);

    public void move(GenericGuildMessageReactionEvent event, String dir){
        currentGame.move(dir);
        event.getChannel().editMessageById(currentGame.getGameMessageID(), GameManagement.getGame(user.getIdLong()).gameMessage(user, false).build()).queue();
        event.getChannel().editMessageById(currentGame.getPlaceHolderID(), "_ _").queue();
    }
    public void checkGameOver(GenericGuildMessageReactionEvent event){
        if(GameManagement.getGame(user.getIdLong()).isOver()){
            event.getChannel().editMessageById(currentGame.getPlaceHolderID(), BotReplies.levelComplete(currentGame, user)).queue();
            GameManagement.removeGame(user.getIdLong());
        }
    }
}
