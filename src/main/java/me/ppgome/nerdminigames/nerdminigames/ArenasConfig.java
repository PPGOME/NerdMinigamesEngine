package me.ppgome.nerdminigames.nerdminigames;

import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import me.ppgome.nerdminigames.nerdminigames.data.*;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;
import java.util.logging.Level;

public class ArenasConfig {
    private final File file;
    private final FileConfiguration config;
    private final List<String> emptylist = Collections.emptyList();
    private final List<String> arenas = new ArrayList<String>();

    public ArenasConfig(NerdMinigames plugin) {
        this(plugin.getDataFolder().getAbsolutePath() + "/arenas.yml");
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

    public void editArena(Arena arena) {
        String arenaname = arena.getArenaName().toUpperCase(Locale.ROOT);
        if(arena.getArenaName().toUpperCase(Locale.ROOT).substring(0, 2).equalsIgnoreCase("§F")) {
            arenaname = arenaname.substring(2);
            System.out.println("this is the " + arenaname);
        }
        getConfig().set(arenaname + ".Owner", arena.getOwner());
        getConfig().set(arenaname + ".World", arena.getWorld());
        getConfig().set(arenaname + ".Boundaries", arena.getBoundaries());

        System.out.println(arena.getTeams().size());

        // Teams
        List<String> data = new ArrayList<>();

        for(Team team : arena.getTeams()) {
            data.add(team.getTeamName());
        }
        getConfig().set(arenaname + ".Teams", data);

        for(int i = 0; i < arena.getTeams().size(); i++) {
            getConfig().set(arenaname + ".Teams." + data.get(i) + ".Min", arena.getTeams().get(i).getMinPlayers());
            getConfig().set(arenaname + ".Teams." + data.get(i) + ".Max", arena.getTeams().get(i).getMaxPlayers());
        }

        // Items
        data = new ArrayList<>();
        for(Item item : arena.getItems()) {
            data.add(PlainTextComponentSerializer.plainText().serialize(item.getItem().displayName()));
        }
        getConfig().set(arenaname + ".Items", data);

        for(int i = 0; i < arena.getItems().size(); i++) {
            getConfig().set(arenaname + ".Items." + data.get(i) + ".Item", arena.getItems().get(i).getItem());
            getConfig().set(arenaname + ".Items." + data.get(i) + ".Team", arena.getItems().get(i).getTeam());
            getConfig().set(arenaname + ".Items." + data.get(i) + ".Chance", arena.getItems().get(i).getChance());
            getConfig().set(arenaname + ".Items." + data.get(i) + ".IsCurrency", arena.getItems().get(i).isCurrency());
        }

        getConfig().set(arenaname + ".Currency.Rate", arena.getCurrencyrate());

        getConfig().set(arenaname + ".Spawns", arena.getSpawns());
        getConfig().set(arenaname + ".Objectives", arena.getObjectives());
        getConfig().set(arenaname + ".Storage", arena.getStorage());
        getConfig().set(arenaname + ".Armour", arena.getArmour());
        save();
    }

    public Arena getArena(String arenaName) {
        String arena = arenaName;
        if(arena.toUpperCase(Locale.ROOT).substring(0, 2).equalsIgnoreCase("§F")) {
            arena = arenaName.toUpperCase().substring(2);
        }
        System.out.println(arena);
        String owner = getConfig().getString(arena + ".Owner");
        String world = getConfig().getString(arena + ".World");
        HashMap<String, Integer> boundaries = new HashMap<>();

        if(config.getConfigurationSection(arena + ".Boundaries") != null) {
            for(String key : config.getConfigurationSection(arena + ".Boundaries").getKeys(false)) {
                boundaries.put(key, Integer.valueOf(config.get(arena + ".Boundaries." + key).toString()));
            }
        }

        List<Team> teams = new ArrayList<>();
        if(config.getConfigurationSection(arena + ".Teams") != null) {
            for(String key : config.getConfigurationSection(arena + ".Teams").getKeys(false)) {
                teams.add(new Team(key, getConfig().getInt(arena + ".Teams." + key + ".Min"), getConfig().getInt(arena + ".Teams." + key + ".Max")));
            }
        }

        List<Item> items = new ArrayList<>();
        if(config.getConfigurationSection(arena + ".Items") != null) {
            for(String key : config.getConfigurationSection(arena + ".Items").getKeys(false)) {
                items.add(new Item(getConfig().getItemStack(arena + ".Items." + key + ".Item"), getConfig().getString(arena + ".Items." + key + ".Team"),
                        getConfig().getInt(arena + ".Items." + key + ".Chance"), getConfig().getBoolean(arena + ".Items." + key + ".IsCurrency")));
            }
        }

        List<Spawn> spawns = new ArrayList<>();
        List<Objective> objectives = new ArrayList<>();
        List<Storage> storage = new ArrayList<>();
        List<Armour> armour = new ArrayList<>();

        int currencyrate = 16;
        if(config.getConfigurationSection(arena + ".Currency") != null) {
            currencyrate = getConfig().getInt(arena + ".Currency.Rate");
        }

        return new Arena(arena, owner, world, boundaries, teams, items, spawns, objectives, storage, armour, currencyrate);
    }

    public void addTeam(String arena, String team) {
        List<String> teams = getTeams(arena);
        if(teams.stream().noneMatch(e -> e.equalsIgnoreCase(team))) {
            teams.add(team);
            getConfig().set(arena + ".Teams", teams);
            save();
        }
    }

    public void deleteTeam(String arena, String team) {
        List<String> teams = getTeams(arena);
        System.out.println(teams);
        teams.removeIf(teamz -> teamz.equals(team));
        System.out.println(teams);
        getConfig().set(arena + ".Teams", teams);
        save();
        teams = null;
    }

    public void additem(String arena, ItemStack item, String team, int chance) {

    }

    public List<String> getTeams(String arena) {
        List<String> teams = new ArrayList<>();
        teams = getConfig().getStringList(arena + ".Teams");
        return teams;
    }

    public List<String> getArenas() {
        ConfigurationSection configsec = getConfig().getConfigurationSection("");
        for(String key : configsec.getKeys(false)) {
            arenas.add(key.toUpperCase(Locale.ROOT));
        }
        return arenas;
    }

    public File getFile() {
        return this.file;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

}
