package me.ppgome.nerdminigames.nerdminigames.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private final Connection connection;


    public Database(String path) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + path);

        try(Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS MinigamePlayer (" +
                    "playerid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "uuid TEXT NOT NULL, " +
                    "gameid INTEGER, " +
                    "team TEXT " +
                    ");");
        }
    }

    public void closeConnection() throws SQLException {

        if(connection != null && !connection.isClosed()) {
            connection.close();
        }

    }

}
