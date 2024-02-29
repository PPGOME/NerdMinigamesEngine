package me.ppgome.nerdminigames.nerdminigames;

import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import me.ppgome.nerdminigames.nerdminigames.data.*;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
            NamespacedKey key = new NamespacedKey(NerdMinigames.getPlugin(), "if-uuid");
            ItemMeta itemMeta = item.getItem().getItemMeta();
            itemMeta.getPersistentDataContainer().remove(key);
            item.getItem().setItemMeta(itemMeta);
        }
        getConfig().set(arenaname + ".Items", data);

        for(int i = 0; i < arena.getItems().size(); i++) {
            getConfig().set(arenaname + ".Items." + data.get(i) + ".Item", arena.getItems().get(i).getItem());
            getConfig().set(arenaname + ".Items." + data.get(i) + ".Team", arena.getItems().get(i).getTeam().getTeamName());
            getConfig().set(arenaname + ".Items." + data.get(i) + ".Chance", arena.getItems().get(i).getChance());
            getConfig().set(arenaname + ".Items." + data.get(i) + ".IsCurrency", arena.getItems().get(i).isCurrency());
        }

        // Currency
        getConfig().set(arenaname + ".Currency.Rate", arena.getCurrencyrate());

        // Spawns
        getConfig().set(arenaname + ".Spawns", arena.getSpawns());
        for(Spawn spawn : arena.getSpawns()) {
            getConfig().set(arenaname + ".Spawns." + spawn.getName() + ".Location.World", spawn.getLocation().getWorld().getName());
            getConfig().set(arenaname + ".Spawns." + spawn.getName() + ".Location.X", spawn.getLocation().getX());
            getConfig().set(arenaname + ".Spawns." + spawn.getName() + ".Location.Y", spawn.getLocation().getY());
            getConfig().set(arenaname + ".Spawns." + spawn.getName() + ".Location.Z", spawn.getLocation().getZ());
            getConfig().set(arenaname + ".Spawns." + spawn.getName() + ".Location.Pitch", spawn.getLocation().getPitch());
            getConfig().set(arenaname + ".Spawns." + spawn.getName() + ".Location.Yaw", spawn.getLocation().getYaw());
            getConfig().set(arenaname + ".Spawns." + spawn.getName() + ".Team", spawn.getTeam().getTeamName());
        }

        getConfig().set(arenaname + ".Objectives", arena.getObjectives());
        getConfig().set(arenaname + ".Storage", arena.getStorage());
        getConfig().set(arenaname + ".Armour", arena.getArmour());
        save();
    }

    public Arena getArenaByName(String arenaName) {
        String arena = arenaName;
        if(arena.toUpperCase(Locale.ROOT).substring(0, 2).equalsIgnoreCase("§F")) {
            arena = arenaName.toUpperCase().substring(2);
        }
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
                for(Team team : teams) {
                    if(team.getTeamName().equalsIgnoreCase(getConfig().getString(arena + ".Items." + key + ".Team"))) {
                        items.add(new Item(getConfig().getItemStack(arena + ".Items." + key + ".Item"), team,
                                getConfig().getInt(arena + ".Items." + key + ".Chance"), getConfig().getBoolean(arena + ".Items." + key + ".IsCurrency")));
                    }
                }
            }
        }

        // Spawns
        List<Spawn> spawns = new ArrayList<>();
        if(config.getConfigurationSection(arena + ".Spawns") != null) {
            for(String key : config.getConfigurationSection(arena + ".Spawns").getKeys(false)) {
                for(Team team : teams) {
                    if(team.getTeamName().equalsIgnoreCase(getConfig().getString(arena + ".Spawns." + key + ".Team"))) {
                        spawns.add(new Spawn(
                                key,
                                new Location(
                                        Bukkit.getWorld(getConfig().getString(arena + ".Spawns." + key + ".Location.World")),
                                        getConfig().getDouble(arena + ".Spawns." + key + ".Location.X"),
                                        getConfig().getDouble(arena + ".Spawns." + key + ".Location.Y"),
                                        getConfig().getDouble(arena + ".Spawns." + key + ".Location.Z"),
                                        (float) getConfig().getDouble(arena + ".Spawns." + key + ".Location.Pitch"),
                                        (float) getConfig().getDouble(arena + ".Spawns." + key + ".Location.Yaw")
                                ),
                                team
                                )
                        );
                    }
                }
            }
        }

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
