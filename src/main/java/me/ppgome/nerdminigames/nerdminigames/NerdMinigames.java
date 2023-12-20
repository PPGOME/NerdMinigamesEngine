package me.ppgome.nerdminigames.nerdminigames;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import me.ppgome.nerdminigames.nerdminigames.arenabuilder.CreationCommands;
import me.ppgome.nerdminigames.nerdminigames.data.ArenasConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
public final class NerdMinigames extends JavaPlugin {
    private static NerdMinigames instance;

    public static NerdMinigames getPlugin() {
        return instance;
    }

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(true));
        CreationCommands.nerdGames();

    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;

        saveDefaultConfig();

        // Check if arenas file exists. If no create it.
        File arenaconfigfile = new File(getDataFolder(), "arenas.yml");

        if(!arenaconfigfile.exists()) {
            arenaconfigfile.getParentFile().mkdirs();
            saveResource("arenas.yml", false);
        }

        ArenasConfig arenaconfig = new ArenasConfig(instance, "arenas.yml");
        arenaconfig.newArena("Test", "PPGOME");
        arenaconfig.newArena("Balls", "PPGOME");

        arenaconfig.getArenas();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
