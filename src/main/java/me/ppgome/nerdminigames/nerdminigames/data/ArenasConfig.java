package me.ppgome.nerdminigames.nerdminigames.data;

import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;
import java.util.logging.Level;

public class ArenasConfig {
    private File file;
    private FileConfiguration config;
    private NerdMinigames plugin;
    private final List<String> emptylist = Collections.<String> emptyList();
    private final List<String> arenas = new ArrayList<String>();

    public ArenasConfig(NerdMinigames plugin, String path) {
        this(plugin.getDataFolder().getAbsolutePath() + "/" + path);
        this.plugin = plugin;
    }

    public ArenasConfig(String path) {
        this.file = new File(path);
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public boolean save() {
        try {
            this.config.save(this.file);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void newArena(String arenaname, String arenaowner) {
        if(!getConfig().contains(arenaname)) {
            getConfig().set(arenaname.toUpperCase(Locale.ROOT) + ".Owner", arenaowner);
            getConfig().set(arenaname.toUpperCase(Locale.ROOT) + ".Teams", emptylist);
            getConfig().set(arenaname.toUpperCase(Locale.ROOT) + ".Spawns", emptylist);
            getConfig().set(arenaname.toUpperCase(Locale.ROOT) + ".Items", emptylist);
            getConfig().set(arenaname.toUpperCase(Locale.ROOT) + ".Objectives", emptylist);
            save();
        } else {
            Bukkit.getLogger().log(Level.SEVERE, "ALREADY EXISTS");
        }
    }

    public List<String> getArenas() {
        ConfigurationSection configsec = getConfig().getConfigurationSection("");
        for(String key : configsec.getKeys(false)) {
            Bukkit.getLogger().log(Level.SEVERE, key);
            arenas.add(key.toUpperCase(Locale.ROOT));
        }
        return arenas;
    }

    public void addTeam(String arenaname, String team) {

    }

    public File getFile() {
        return this.file;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

}
