package bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.ArrayList;


public class Game {

    private ArrayList<String> boardHistory;
    private char[][] boardState;
    private boolean stateChanged;

    private int[][] boxPositions;
    private int boxes;
    private int boxOnTarget;

    private int playerX;
    private int playerY;
    private int moves;

    private String lvID;
    private Level lv;

    private Long gameMessageID;
    private Long placeHolderID;
    private String boardMessage;

    public Game(User player, String id){
        lv = new Level(Integer.parseInt(id));
        lvID = id;
        boxPositions= new int[lv.getLevelBoxes()][2];
        boxes = lv.getLevelBoxes();
        boxOnTarget = 0;
        boardState = new char[lv.getLevelHeight()][lv.getLevelWidth()];
        boardHistory = new ArrayList<>();
        fillBoardState(lv);
        drawBoard();
        moves = 0;
    }

    private void convertToCharArr(int index){
        int rIndex = 0;
        int colIndex = 0;
        for(int i=0; i<boardHistory.get(index).length(); i++){
            if(boardHistory.get(index).charAt(i) == '\n'){
                colIndex = 0;
                rIndex++;
            }
            else if(boardHistory.get(index).charAt(i) == '@'){
                boardState[rIndex][colIndex] = '@';
                colIndex++;
            }
            else if(boardHistory.get(index).charAt(i) == '$'){
                boardState[rIndex][colIndex] = '$';
                colIndex++;
            }
            else if(boardHistory.get(index).charAt(i) == '.'){
                boardState[rIndex][colIndex] = '.';
                colIndex++;
            }
            else if(boardHistory.get(index).charAt(i) == '#'){
                boardState[rIndex][colIndex] = '#';
                colIndex++;
            }
            else if(boardHistory.get(index).charAt(i) == '^'){
                boardState[rIndex][colIndex] = '^';
                colIndex++;
            }
        }

    }

    private void addStateToHistory(){
        String state = "";
        for(int i=0; i<boardState.length; i++) {
            for (int j=0; j<boardState[i].length; j++) {
              state += boardState[i][j];
            }
            state += "\n";
        }

        boardHistory.add(state);
    }

    public void setGameMessageID(Long gameMessageID) {
        this.gameMessageID = gameMessageID;
    }

    public void setPlaceHolderID(Long placeHolderID) {
        this.placeHolderID = placeHolderID;
    }

    public Long getPlaceHolderID() {
        return placeHolderID;
    }
    public Long getGameMessageID() {
        return gameMessageID;
    }

    public int getMoves() {
        return moves;
    }

    private void fillBoardState(Level board){
        int rowIndex = 0;
        int boxIndex = 0;
        for (String row : board.getBoard()) {
            for(int i=0; i<row.length(); i++){
                boardState[rowIndex][i] = row.charAt(i);
                if(row.charAt(i) == '@'){
                    playerX = rowIndex;
                    playerY = i;
                }
                else if(row.charAt(i) == '.'){
                    boxPositions[boxIndex][0] = i;
                    boxPositions[boxIndex][1] = rowIndex;
                    boxIndex++;
                }
            }
            rowIndex++;
        }
    }

    public void reset(){
        boardHistory.clear();
        moves = 0;
        fillBoardState(lv);
        drawBoard();
    }

    public void move(String dir){
        stateChanged = false;
        if(dir.equalsIgnoreCase("w")){
            handleUP();
        }
        else if(dir.equalsIgnoreCase("s")){
            handleDown();
        }
        else if(dir.equalsIgnoreCase("d")){
            handleRight();
        }
        else if(dir.equalsIgnoreCase("a")){
            handleLeft();
        }

        if(stateChanged){
            addStateToHistory();
            moves++;
            drawBoard();
        }
    }

    public void undoMove(){
        if(boardHistory.size() == 0)
            return;

        if(boardHistory.size() - 1 == 0){
            fillBoardState(lv);
            moves = 0;
        }
        else {
            boardHistory.remove(boardHistory.size()-1);
            convertToCharArr(boardHistory.size() - 1);
            moves--;
        }

        drawBoard();
    }

    private void handleUP(){
        if(boardState[playerX-1][playerY] == '#'){
            //You hit a wall
            return;
        }
        else if(boardState[playerX-1][playerY] == '$'){
            //You hit a box
            if(boardState[playerX-2][playerY] == '#' || boardState[playerX-2][playerY] == '$'){
                //There is a wall or a box above this box
                return;
            }
            else{
                boardState[playerX][playerY] = '^';
                boardState[playerX-1][playerY] = '@';
                boardState[playerX-2][playerY] = '$';

                playerX = playerX-1;
                stateChanged = true;
            }
        }
        else{
            //Free map
            boardState[playerX][playerY] = '^';
            boardState[playerX-1][playerY] = '@';

            playerX = playerX-1;
            stateChanged = true;
        }
    }

    private void handleDown(){
        if(boardState[playerX+1][playerY] == '#'){
            //You hit a wall
            return;
        }
        else if(boardState[playerX+1][playerY] == '$' ){
            //You hit a box
            if(boardState[playerX+2][playerY] == '#' || boardState[playerX+2][playerY] == '$'){
                //There is a wall or a box above this box
                return;
            }
            else{
                boardState[playerX][playerY] = '^';
                boardState[playerX+1][playerY] = '@';
                boardState[playerX+2][playerY] = '$';

                playerX = playerX+1;
                stateChanged = true;
            }
        }
        else{
            //Free map
            boardState[playerX][playerY] = '^';
            boardState[playerX+1][playerY] = '@';

            playerX = playerX+1;
            stateChanged = true;
        }
    }

    private void handleLeft(){
        if(boardState[playerX][playerY-1] == '#'){
            //You hit a wall
            return;
        }
        else if(boardState[playerX][playerY-1] == '$'){
            //You hit a box
            if(boardState[playerX][playerY-2] == '#' || boardState[playerX][playerY-2] == '$'){
                //Can it move up?
                return;
            }
            else{
                boardState[playerX][playerY] = '^';
                boardState[playerX][playerY-1] = '@';
                boardState[playerX][playerY-2] = '$';

                playerY = playerY-1;
                stateChanged = true;
            }
        }
        else{
            //Free map
            boardState[playerX][playerY] = '^';
            boardState[playerX][playerY-1] = '@';

            playerY = playerY-1;
            stateChanged = true;
        }
    }

    private void handleRight(){
        if(boardState[playerX][playerY+1] == '#'){
            //You hit a wall
            return;
        }
        else if(boardState[playerX][playerY+1] == '$'){
            //You hit a box
            if(boardState[playerX][playerY+2] == '#' || boardState[playerX][playerY+2] == '$'){
                //Can it move up?
                return;
            }
            else{
                boardState[playerX][playerY] = '^';
                boardState[playerX][playerY+1] = '@';
                boardState[playerX][playerY+2] = '$';

                playerY = playerY+1;
                stateChanged = true;
            }
        }
        else{
            //Free map
            boardState[playerX][playerY] = '^';
            boardState[playerX][playerY+1] = '@';

            playerY = playerY+1;
            stateChanged = true;
        }
    }

    private void getPlayerPos(){
        for(int i=0; i<boardState.length; i++) {
            for (int j=0; j<boardState[i].length; j++) {
               if(boardState[i][j] == '@'){
                   playerX = i;
                   playerY = j;
               }
            }
        }
    }
    private String drawTile(char c){
        String modified = "";
            switch(c){
                case '^':
                    modified += ":yellow_square:";
                    break;
                case '#':
                    modified += ":red_square:";
                    break;
                case '$':
                    modified += ":stop_button:";
                    break;
                case '@':
                    modified += ":superhero:";
                    break;
                case '.':
                    modified += ":negative_squared_cross_mark:";
            }

        return modified;
    }

    private void drawBoxTargets(){
        for(int i=0; i<boardState.length; i++) {
            for (int j=0; j<boardState[i].length; j++) {
                if(isBoxPos(j, i) && boardState[i][j] != '@' && boardState[i][j] != '$')
                    boardState[i][j] = '.';
            }
        }
    }

    private void drawBoard(){
        getPlayerPos();
        drawBoxTargets();
        boardMessage = "";
        for(int i=0; i<boardState.length; i++) {
            for (int j=0; j<boardState[i].length; j++) {
                boardMessage += drawTile(boardState[i][j]);
            }
            boardMessage += "\n";
        }
    }

    private boolean isBoxPos(int x, int y){
        for(int i=0; i<boxPositions.length; i++) {
            if(boxPositions[i][0] == x && boxPositions[i][1] == y)
                return true;
        }

        return false;
    }

    public String getLvID() {
        return lvID;
    }

    public boolean isOver(){
        boxOnTarget = 0;
        for(int i=0; i<boardState.length; i++) {
            for (int j=0; j<boardState[i].length; j++) {
                if(isBoxPos(j, i) && boardState[i][j] == '$'){
                   boxOnTarget++;
               }
            }
        }

        return boxOnTarget == boxes;
    }

    public EmbedBuilder gameMessage(User user, boolean reset){
        EmbedBuilder message = new EmbedBuilder();

        message.setTitle("Sokoban | Level " + lvID);
        message.setDescription(boardMessage);
        message.setColor(new Color(253,203,88));
        message.addField("Moves - " + moves,reset? "Resetting" : " ",false);
        message.addField("Player", user.getAsMention(), true);
        return message;
    }

    public MessageEmbed resetPlaceholder(){
        return BotReplies.resetLevelMessage(lvID);
    }

}
