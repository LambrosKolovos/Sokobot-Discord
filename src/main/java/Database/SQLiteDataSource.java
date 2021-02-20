package Database;

import java.io.File;
import java.sql.*;

public class SQLiteDataSource {

    private static String url;
    private static Connection conn;

    public static void findDatabase() {
        url = "jdbc:sqlite:database.db";

        File file = new File("database.db");
        if(file.exists()){
            System.out.println("Database found!");
            connect();
        }else{
            System.out.println("Didn't find a DB. Creating file!");
        }

    }

    private static void connect(){
        try {
             conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* GUILD FUNCTIONS */
    public static void insertGuild(String guid){
        String query = "INSERT INTO guilds(guildID, guildPref) VALUES(?, ?)";
        try {
            PreparedStatement prepStmt = conn.prepareStatement(query);

            prepStmt.setString(1, guid);
            prepStmt.setString(2,"$");

            prepStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateGuildPrefix(String guid, String prefix){
        String query = "UPDATE guilds SET prefix = ? WHERE guid = ?";

        try{
            PreparedStatement prepStmt = conn.prepareStatement(query);

            prepStmt.setString(1, prefix);
            prepStmt.setString(2, guid);

            prepStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getGuildPrefix(String guid){

        String query = "SELECT guildPref FROM guilds WHERE guid = ?";
        String prefix = "";
        try {
            PreparedStatement prepStmt = conn.prepareStatement(query);
            prepStmt.setString(1, guid);

            ResultSet rs = prepStmt.executeQuery(query);

            if(rs.getFetchSize() == 1){
                prefix = rs.getString("guildPref");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return prefix;
    }

    public static void removeGuild(String guid){
        String query = "DELETE FROM guilds WHERE guildID = ?";
        try{
            PreparedStatement prepStmt = conn.prepareStatement(query);
            prepStmt.setString(1, guid);

            prepStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* PLAYER FUNCTIONS */
}
