package bot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Level {

    private int levelNum;
    private int levelBoxes;
    private int levelWidth = 0;
    private int levelHeight = 0;
    private ArrayList<String> board;

    public Level(int level){
        levelNum = level;
        board = new ArrayList<>();
        buildBoard();
    }

    private void buildBoard(){
        String currentLevel = String.valueOf(levelNum);
        String nextLevel = String.valueOf(levelNum+1);

        try
        {
            File file = new File("src/levels.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;

            while((line = br.readLine())!= null)
            {
                if(line.equals(currentLevel)){
                    //We found our level now read until next level
                   while(!(line = br.readLine()).equals(nextLevel)){
                       //Count level boxes
                       for (int i = 0; i < line.length(); i++) {
                           if (line.charAt(i) == '$') {
                               levelBoxes++;
                           }
                       }

                       board.add(line);
                       if(levelWidth < line.length())
                           levelWidth = line.length();

                       levelHeight++;
                   }
                   break;
                }
            }
            fr.close();    //closes the stream and release the resources
        }
        catch(IOException e) {
            e.printStackTrace();
        }

    }

    public int getLevelBoxes() {
        return levelBoxes;
    }

    public int getLevelHeight() {
        return levelHeight;
    }

    public int getLevelWidth() {
        return levelWidth;
    }

    public ArrayList<String> getBoard() {
        return board;
    }
}
