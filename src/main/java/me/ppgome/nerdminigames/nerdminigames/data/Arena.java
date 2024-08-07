package me.ppgome.nerdminigames.nerdminigames.data;

import com.sk89q.worldedit.math.BlockVector3;
import me.ppgome.nerdminigames.nerdminigames.ArenasConfig;
import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The object representing a minigame arena
 */
public class Arena implements Serializable {

    private String arenaName;
    private String owner;
    private String world;
    private HashMap<String, Integer> boundaries = new HashMap<>();
    private List<Team> teams = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private Integer maxitemid;
    private List<Spawn> spawns = new ArrayList<>();
    private List<Objective> objectives = new ArrayList<>();
    private List<Storage> storage = new ArrayList<>();
    private List<Armour> armour = new ArrayList<>();

    private int currencyrate;

    Arena copyArena;

    /**
     * Constructor for an arena - initial creation
     * @param arenaName The name of the arena
     * @param owner The owner of the arena
     * @param world The world the arena is in
     * @param corner1 One corner of the arena's worldguard region
     * @param corner2 The opposite corner of the arena's worldguard region
     */
    public Arena(String arenaName, Player owner, String world, BlockVector3 corner1, BlockVector3 corner2) {
        this.arenaName = arenaName;
        this.owner = owner.getUniqueId().toString();
        this.world = world;
        this.boundaries.put("x1", corner1.getX());
        this.boundaries.put("y1", corner1.getY());
        this.boundaries.put("z1", corner1.getZ());
        this.boundaries.put("x2", corner2.getX());
        this.boundaries.put("y2", corner2.getY());
        this.boundaries.put("z2", corner2.getZ());
        this.currencyrate = 16;
        this.maxitemid = 0;
    }

    /**
     * Constructor for the arena - loading from config
     * @param arenaName The name of the arena
     * @param owner The owner of the arena
     * @param world The world the arena is in
     * @param boundaries The coordinates that define the worldguard region of the arena
     * @param teams The teams of this arena
     * @param items The items of this arena
     * @param maxitemid The highest ID of an item in the arena (allows for database-like numbering system)
     * @param spawns The spawns of the arena
     * @param objectives The objectives of the arena
     * @param storage The storage of the arena
     * @param armour The armour of the arena
     * @param currencyrate The rate of conversion for this arena's currency
     */
    public Arena(String arenaName, String owner, String world, HashMap<String, Integer> boundaries, List<Team> teams,
                 List<Item> items, int maxitemid, List<Spawn> spawns, List<Objective> objectives, List<Storage> storage,
                 List<Armour> armour, int currencyrate) {
        this.arenaName = arenaName;
        this.owner = owner;
        this.world = world;
        this.boundaries = boundaries;
        this.teams = teams;
        this.items = items;
        this.maxitemid = maxitemid;
        this.spawns = spawns;
        this.storage = storage;
        this.currencyrate = currencyrate;
    }

    /**
     * Gets the arena name
     * @return the arena's name
     */
    public String getArenaName() {
        return arenaName;
    }

    /**
     * Gets the arena's owner's name
     * @return The arena's owner's name
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Gets the world the arena is in
     * @return The world the arena is in
     */
    public String getWorld() {
        return world;
    }

    /**
     * Gets the worldguard region boundaries of the arena
     * @return The worldguard region boundaries of the arena
     */
    public HashMap<String, Integer> getBoundaries() {
        return boundaries;
    }

    /**
     * Gets the teams of the arena
     * @return a list of teams in the arena
     */
    public List<Team> getTeams() {
        return teams;
    }

    /**
     * Gets a specific team from the arena
     * @param name The name of the team
     * @return The team
     */
    public Team getTeamByName(String name) {
        for(Team team : getTeams()) {
            if (team.getTeamName().equalsIgnoreCase(name)) {
                return team;
            }
        }
        return null;
    }

    /**
     * Add a team to the arena
     * @param team The team to add
     */
    public void addTeam(Team team) {
        this.teams.add(team);
    }

    /**
     * Edit an arena's team
     * @param team The team being edited
     */
    public void editTeam(Team team) {
        Team tempteam = this.getTeamByName(team.getTeamName());
        if(tempteam != null) {
            tempteam.setTeamName(team.getTeamName());
            tempteam.setMinPlayers(team.getMinPlayers());
            tempteam.setMaxPlayers(team.getMaxPlayers());
        } else {
            this.addTeam(team);
        }
        new ArenasConfig(NerdMinigames.PLUGIN).editArena(this);
    }

    /**
     * Delete a team from an arena
     * @param team The team to be deleted from the arena
     */
    public void deleteTeam(Team team) {
        this.teams.remove(team);
        for(Item item : items) {
            if(item.getTeam().getTeamName().equalsIgnoreCase(team.getTeamName())) {
                item.setTeam(null);
            }
        }
    }

    /**
     * Get the items from an arena
     * @return A list of items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Checks if an item is the same as an item in the arena's list
     * @param checkItem
     * @return
     */
    public Item getItemByItem(ItemStack checkItem) {
        for(Item item : getItems()) {
            System.out.println(item.getItem());
            if(item.getItem().isSimilar(checkItem)) {
                return item;
            }
        }
        return null;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        System.out.println("max: " + this.getMaxitemid());
        item.setID(this.getMaxitemid() + 1);
        this.setMaxitemid(item.getID());
        this.items.add(item);
    }

    public Integer getMaxitemid() {
        return maxitemid;
    }

    public void setMaxitemid(Integer maxid) {
        this.maxitemid = maxid;
    }

    public void deleteItem(Item item) {
        this.items.remove(item);
    }

    public List<Spawn> getSpawns() {
        return spawns;
    }

    public Spawn getSpawnByName(String name) {
        for(Spawn spawn : this.getSpawns()) {
            if(spawn.getName().equalsIgnoreCase(name)) {
                return spawn;
            }
        }
        return null;
    }

    public void setSpawns(List<Spawn> spawns) {
        this.spawns = spawns;
    }

    public void addSpawn(Spawn spawn) {
        this.spawns.add(spawn);
    }

    public void deleteSpawn(Spawn spawn) {
        this.spawns.remove(spawn);
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }

    public List<Storage> getStorage() {
        return storage;
    }

    public void addStorage(Storage storage) {
        this.storage.add(storage);
    }

    public void deleteStorage(Storage storage) {
        this.storage.remove(storage);
    }

    public void setStorage(List<Storage> storage) {
        this.storage = storage;
    }

    public List<Armour> getArmour() {
        return armour;
    }

    public void setArmour(List<Armour> armour) {
        this.armour = armour;
    }

    public void setCurrencyrate(int rate) {
        this.currencyrate = rate;
    }

    public int getCurrencyrate() {
        return this.currencyrate;
    }

}
