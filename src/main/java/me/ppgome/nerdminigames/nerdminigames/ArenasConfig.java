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
import java.io.IOException;
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
        FileConfiguration editconfig = getConfig();
        String arenaname = arena.getArenaName().toUpperCase(Locale.ROOT);
        if(arena.getArenaName().toUpperCase(Locale.ROOT).substring(0, 2).equalsIgnoreCase("§F")) {
            arenaname = arenaname.substring(2);
            System.out.println("this is the " + arenaname);
        }
        editconfig.set(arenaname + ".Owner", arena.getOwner());
        editconfig.set(arenaname + ".World", arena.getWorld());
        editconfig.set(arenaname + ".Boundaries", arena.getBoundaries());

        System.out.println(arena.getTeams().size());

        // Teams
        List<String> data = new ArrayList<>();

        for(Team team : arena.getTeams()) {
            data.add(team.getTeamName());
        }
        editconfig.set(arenaname + ".Teams", data);

        for(int i = 0; i < arena.getTeams().size(); i++) {
            editconfig.set(arenaname + ".Teams." + data.get(i) + ".Min", arena.getTeams().get(i).getMinPlayers());
            editconfig.set(arenaname + ".Teams." + data.get(i) + ".Max", arena.getTeams().get(i).getMaxPlayers());
        }

        // Items
        data.clear();
        editconfig.set(arenaname + ".Items", "");
        for(Item item : arena.getItems()) {
            data.add(String.valueOf(arena.getItems().indexOf(item)));
            NamespacedKey key = new NamespacedKey(NerdMinigames.PLUGIN, "if-uuid");
            ItemMeta itemMeta = item.getItem().getItemMeta();
            if(itemMeta.getPersistentDataContainer().has(key)) {
                itemMeta.getPersistentDataContainer().remove(key);
                item.getItem().setItemMeta(itemMeta);
            }
        }

        for(Item item : arena.getItems()) {
            editconfig.set(arenaname + ".Items." + item.getID() + ".Item", item.getItem());
            editconfig.set(arenaname + ".Items." + item.getID() + ".Team", item.getTeam().getTeamName());
            editconfig.set(arenaname + ".Items." + item.getID() + ".Chance", item.getChance());
            editconfig.set(arenaname + ".Items." + item.getID() + ".IsCurrency", item.isCurrency());
        }

        editconfig.set(arenaname + ".maxItemID", arena.getMaxitemid());

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
//            editconfig.set(arenaname + ".Items." + data.get(i) + ".Item", arena.getItems().get(i).getItem());
//            editconfig.set(arenaname + ".Items." + data.get(i) + ".Team", arena.getItems().get(i).getTeam().getTeamName());
//            editconfig.set(arenaname + ".Items." + data.get(i) + ".Chance", arena.getItems().get(i).getChance());
//            editconfig.set(arenaname + ".Items." + data.get(i) + ".IsCurrency", arena.getItems().get(i).isCurrency());
//        }

        // Currency
        editconfig.set(arenaname + ".Currency.Rate", arena.getCurrencyrate());

        // Spawns
        editconfig.set(arenaname + ".Spawns", arena.getSpawns());
        data.clear();

        for(Spawn spawn : arena.getSpawns()) {
            editconfig.set(arenaname + ".Spawns." + spawn.getName() + ".Location.World", spawn.getLocation().getWorld().getName());
            editconfig.set(arenaname + ".Spawns." + spawn.getName() + ".Location.X", spawn.getLocation().getX());
            editconfig.set(arenaname + ".Spawns." + spawn.getName() + ".Location.Y", spawn.getLocation().getY());
            editconfig.set(arenaname + ".Spawns." + spawn.getName() + ".Location.Z", spawn.getLocation().getZ());
            editconfig.set(arenaname + ".Spawns." + spawn.getName() + ".Location.Pitch", spawn.getLocation().getPitch());
            editconfig.set(arenaname + ".Spawns." + spawn.getName() + ".Location.Yaw", spawn.getLocation().getYaw());
            editconfig.set(arenaname + ".Spawns." + spawn.getName() + ".Team", spawn.getTeam().getTeamName());
        }

        editconfig.set(arenaname + ".Objectives", arena.getObjectives());

        //Storage
        editconfig.set(arenaname + ".Storage", arena.getStorage());
        for(Storage storage : arena.getStorage()) {
            String name = String.valueOf(arena.getStorage().indexOf(storage));
            editconfig.set(arenaname + ".Storage." + name + ".Type", storage.getContainer().getType().toString());
            editconfig.set(arenaname + ".Storage." + name + ".Location.World", storage.getLocation().getWorld().getName());
            editconfig.set(arenaname + ".Storage." + name + ".Location.X", storage.getLocation().getX());
            editconfig.set(arenaname + ".Storage." + name + ".Location.Y", storage.getLocation().getY());
            editconfig.set(arenaname + ".Storage." + name + ".Location.Z", storage.getLocation().getZ());
            for(StorageItem storageItem : storage.getItems()) {
                editconfig.set(arenaname + ".Storage." + name + ".Items." + storageItem.getID() + ".Min", storageItem.getMin());
                editconfig.set(arenaname + ".Storage." + name + ".Items." + storageItem.getID() + ".Max", storageItem.getMax());
                editconfig.set(arenaname + ".Storage." + name + ".Items." + storageItem.getID() + ".Chance", storageItem.getChance());
            }
            // Store the IDs of items, not items themselves
        }

        editconfig.set(arenaname + ".Armour", arena.getArmour());
        save();
    }

    public Arena getArenaByName(String arenaName) {
        FileConfiguration readconfig = getConfig();
        String arena = arenaName;
        if(arena.toUpperCase(Locale.ROOT).substring(0, 2).equalsIgnoreCase("§F")) {
            arena = arenaName.toUpperCase().substring(2);
        }
        String owner = readconfig.getString(arena + ".Owner");
        String world = readconfig.getString(arena + ".World");
        HashMap<String, Integer> boundaries = new HashMap<>();

        if(config.getConfigurationSection(arena + ".Boundaries") != null) {
            for(String key : config.getConfigurationSection(arena + ".Boundaries").getKeys(false)) {
                boundaries.put(key, Integer.valueOf(config.get(arena + ".Boundaries." + key).toString()));
            }
        }

        List<Team> teams = new ArrayList<>();
        if(config.getConfigurationSection(arena + ".Teams") != null) {
            for(String key : config.getConfigurationSection(arena + ".Teams").getKeys(false)) {
                teams.add(new Team(key, readconfig.getInt(arena + ".Teams." + key + ".Min"), readconfig.getInt(arena + ".Teams." + key + ".Max")));
            }
        }

        // Items
        List<Item> items = new ArrayList<>();
        if(config.getConfigurationSection(arena + ".Items") != null) {
            for(String key : config.getConfigurationSection(arena + ".Items").getKeys(false)) {
                for(Team team : teams) {
                    if(team.getTeamName().equalsIgnoreCase(readconfig.getString(arena + ".Items." + key + ".Team"))) {
                        items.add(new Item(Integer.parseInt(key), readconfig.getItemStack(arena + ".Items." + key + ".Item"), team,
                                readconfig.getInt(arena + ".Items." + key + ".Chance"), readconfig.getBoolean(arena + ".Items." + key + ".IsCurrency")));
                    }
                }
            }
        }

        int maxitemid = readconfig.getInt(arena + ".maxItemID");

        // Spawns
        List<Spawn> spawns = new ArrayList<>();
        if(config.getConfigurationSection(arena + ".Spawns") != null) {
            for(String key : config.getConfigurationSection(arena + ".Spawns").getKeys(false)) {
                for(Team team : teams) {
                    if(team.getTeamName().equalsIgnoreCase(readconfig.getString(arena + ".Spawns." + key + ".Team"))) {
                        spawns.add(new Spawn(
                                key,
                                new Location(
                                        Bukkit.getWorld(readconfig.getString(arena + ".Spawns." + key + ".Location.World")),
                                        readconfig.getDouble(arena + ".Spawns." + key + ".Location.X"),
                                        readconfig.getDouble(arena + ".Spawns." + key + ".Location.Y"),
                                        readconfig.getDouble(arena + ".Spawns." + key + ".Location.Z"),
                                        (float) readconfig.getDouble(arena + ".Spawns." + key + ".Location.Pitch"),
                                        (float) readconfig.getDouble(arena + ".Spawns." + key + ".Location.Yaw")
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
                        Bukkit.getWorld(readconfig.getString(arena + ".Storage." + key + ".Location.World")),
                        readconfig.getDouble(arena + ".Storage." + key + ".Location.X"),
                        readconfig.getDouble(arena + ".Storage." + key + ".Location.Y"),
                        readconfig.getDouble(arena + ".Storage." + key + ".Location.Z")
                );
                Block block = location.getWorld().getBlockAt(location);
                List<StorageItem> storageItems = new ArrayList<>();

                if(config.getConfigurationSection(arena + ".Storage." + key + ".Items")!= null) {
                    for(String key2 : config.getConfigurationSection(arena + ".Storage." + key + ".Items").getKeys(false)) {
                        storageItems.add(new StorageItem(
                                Integer.parseInt(key2),
                                readconfig.getInt(arena + ".Storage." + key + ".Items." + key2 + ".Min"),
                                readconfig.getInt(arena + ".Storage." + key + ".Items." + key2 + ".Max"),
                                readconfig.getInt(arena + ".Storage." + key + ".Items." + key2 + ".Chance")
                        ));
                    }
                }

                if(block != null && block.getType() != Material.AIR) {
                    if(block.getState() instanceof Container) {
                        storage.add(new Storage((Container) block.getState(), location, storageItems));
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

        return new Arena(arena, owner, world, boundaries, teams, items, maxitemid, spawns, objectives, storage, armour,
                currencyrate);
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
