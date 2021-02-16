package bot;

import java.util.HashMap;

public  class GameManagement {

    private static final HashMap<Long, Game> activeGames = new HashMap<>();

    public static void addGame(Long userID, Game g){
        activeGames.put(userID, g);
    }

    public static boolean hasGame(Long userID) {
        return activeGames.containsKey(userID);
    }

    public static Game getGame(Long userID){
        return activeGames.get(userID);
    }

    public static void removeGame(Long userID){
        activeGames.remove(userID);
    }
}
