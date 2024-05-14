package me.ppgome.nerdminigames.nerdminigames;

import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import me.ppgome.nerdminigames.nerdminigames.data.*;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
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
        data.clear();
        getConfig().set(arenaname + ".Items", "");
        for(Item item : arena.getItems()) {
            data.add(String.valueOf(arena.getItems().indexOf(item)));
            NamespacedKey key = new NamespacedKey(NerdMinigames.getPlugin(), "if-uuid");
            ItemMeta itemMeta = item.getItem().getItemMeta();
            if(itemMeta.getPersistentDataContainer().has(key)) {
                itemMeta.getPersistentDataContainer().remove(key);
                item.getItem().setItemMeta(itemMeta);
            }
        }

        for(int i = 0; i < arena.getItems().size(); i++) {
            getConfig().set(arenaname + ".Items." + data.get(i) + ".Item", arena.getItems().get(i).getItem());
            getConfig().set(arenaname + ".Items." + data.get(i) + ".Team", arena.getItems().get(i).getTeam().getTeamName());
            getConfig().set(arenaname + ".Items." + data.get(i) + ".Chance", arena.getItems().get(i).getChance());
            getConfig().set(arenaname + ".Items." + data.get(i) + ".IsCurrency", arena.getItems().get(i).isCurrency());
        }

//        data = new ArrayList<>();
//        for(Item item : arena.getItems()) {
//            data.add(PlainTextComponentSerializer.plainText().serialize(item.getItem().displayName()));
//            NamespacedKey key = new NamespacedKey(NerdMinigames.getPlugin(), "if-uuid");
//            ItemMeta itemMeta = item.getItem().getItemMeta();
//            itemMeta.getPersistentDataContainer().remove(key);
//            item.getItem().setItemMeta(itemMeta);
//        }
//
//        for(int i = 0; i < arena.getItems().size(); i++) {
//            getConfig().set(arenaname + ".Items." + data.get(i) + ".Item", arena.getItems().get(i).getItem());
//            getConfig().set(arenaname + ".Items." + data.get(i) + ".Team", arena.getItems().get(i).getTeam().getTeamName());
//            getConfig().set(arenaname + ".Items." + data.get(i) + ".Chance", arena.getItems().get(i).getChance());
//            getConfig().set(arenaname + ".Items." + data.get(i) + ".IsCurrency", arena.getItems().get(i).isCurrency());
//        }

        // Currency
        getConfig().set(arenaname + ".Currency.Rate", arena.getCurrencyrate());

        // Spawns
        getConfig().set(arenaname + ".Spawns", arena.getSpawns());
        data.clear();

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

        //Storage
        getConfig().set(arenaname + ".Storage", arena.getStorage());
        for(Storage storage : arena.getStorage()) {
            String name = String.valueOf(arena.getStorage().indexOf(storage));
            getConfig().set(arenaname + ".Storage." + name + ".Type", storage.getContainer().getType().toString());
            getConfig().set(arenaname + ".Storage." + name + ".Location.World", storage.getLocation().getWorld().getName());
            getConfig().set(arenaname + ".Storage." + name + ".Location.X", storage.getLocation().getX());
            getConfig().set(arenaname + ".Storage." + name + ".Location.Y", storage.getLocation().getY());
            getConfig().set(arenaname + ".Storage." + name + ".Location.Z", storage.getLocation().getZ());
            for(StorageItem storageItem : storage.getItems()) {
                getConfig().set(arenaname + ".Storage." + name + ".Items." + storageItem.getID() + ".Min.", storageItem.getMin());
                getConfig().set(arenaname + ".Storage." + name + ".Items." + storageItem.getID() + ".Max.", storageItem.getMax());
                getConfig().set(arenaname + ".Storage." + name + ".Items." + storageItem.getID() + ".Chance.", storageItem.getChance());
            }
            // Store the IDs of items, not items themselves
        }

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

        // Items
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

        // Storage
        List<Storage> storage = new ArrayList<>();
        Location location;
        if(config.getConfigurationSection(arena + ".Storage") != null) {
            for(String key : config.getConfigurationSection(arena + ".Storage").getKeys(false)) {
                location = new Location(
                        Bukkit.getWorld(getConfig().getString(arena + ".Storage." + key + ".Location.World")),
                        getConfig().getDouble(arena + ".Storage." + key + ".Location.X"),
                        getConfig().getDouble(arena + ".Storage." + key + ".Location.Y"),
                        getConfig().getDouble(arena + ".Storage." + key + ".Location.Z")
                );
                System.out.println("gets here");
                Block block = location.getWorld().getBlockAt(location);
                System.out.println(block);
                if(block != null && block.getType() != Material.AIR) {
                    if(block.getState() instanceof Container) {
                        storage.add(new Storage((Container) block.getState(), location));
                    } else {
                        config.set(arena + ".Storage." + key, null);
                    }
                } else {
                    System.out.println("now here!");
                    System.out.println(arena + ".Storage." + key);
                    config.set(arena + ".Storage." + key, null);
                }
            }
        }

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
