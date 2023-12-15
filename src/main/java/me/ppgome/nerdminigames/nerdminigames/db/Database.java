package me.ppgome.nerdminigames.nerdminigames.db;

import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private final Connection connection;


    public Database(String path) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + path);

        try(Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS Minigame (" +
                    "gameid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "arenaid INTEGER NOT NULL," +
                    "gamestatus TEXT NOT NULL" +
                    ");");
            statement.execute("CREATE TABLE IF NOT EXISTS MinigamePlayer (" +
                    "playerid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "uuid TEXT NOT NULL," +
                    "gameid INTEGER," +
                    "team TEXT," +
                    "FOREIGN KEY (gameid) REFERENCES Minigame(gameid)" +
                    ");");
            statement.execute("CREATE TABLE MinigameArena (" +
                    "arenaid INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "arenaname TEXT NOT NULL," +
                    "arenaowner INTEGER NOT NULL," +
                    "arenagamemode TEXT NOT NULL," +
                    "CONSTRAINT MinigameArena_FK FOREIGN KEY (arenaid) REFERENCES Minigame(gameid)" +
                    ");");
        }
    }

    public void closeConnection() throws SQLException {

        if(connection != null && !connection.isClosed()) {
            connection.close();
        }

    }

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:" + NerdMinigames.getPlugin().getDataFolder()
                .getAbsolutePath() + "/mingames.db");
        return connection;
    }

}
