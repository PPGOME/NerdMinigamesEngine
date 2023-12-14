package me.ppgome.nerdminigames.nerdminigames;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import me.ppgome.nerdminigames.nerdminigames.arenabuilder.CreationCommands;
import me.ppgome.nerdminigames.nerdminigames.db.Database;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class NerdMinigames extends JavaPlugin {

    private Database database;

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(true));
        CreationCommands.nerdGames();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        try {

            if(!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }

            database = new Database(getDataFolder().getAbsolutePath() + "/mingames.db");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database. Disabling plugin as database is needed. " + e.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        try {
            database.closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
