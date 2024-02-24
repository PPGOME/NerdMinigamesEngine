package me.ppgome.nerdminigames.nerdminigames.data;

import com.sk89q.worldedit.math.BlockVector3;
import it.unimi.dsi.fastutil.Hash;
import me.ppgome.nerdminigames.nerdminigames.ArenasConfig;
import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Arena {

    private String arenaName;
    private String owner;
    private String world;
    private HashMap<String, Integer> boundaries = new HashMap<>();
    private List<Team> teams = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private List<Spawn> spawns = new ArrayList<>();
    private List<Objective> objectives = new ArrayList<>();
    private List<Storage> storage = new ArrayList<>();
    private List<Armour> armour = new ArrayList<>();

    private int currencyrate;

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
    }

    public Arena(String arenaName, String owner, String world, HashMap<String, Integer> boundaries, List<Team> teams,
                 List<Item> items, List<Spawn> spawns, List<Objective> objectives, List<Storage> storage, List<Armour> armour,
                 int currencyrate) {
        this.arenaName = arenaName;
        this.owner = owner;
        this.world = world;
        this.boundaries = boundaries;
        this.teams = teams;
        this.items = items;
        this.spawns = spawns;
        this.currencyrate = currencyrate;
    }

    public String getArenaName() {
        return arenaName;
    }

    public void setArenaName(String arenaName) {
        this.arenaName = arenaName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public HashMap<String, Integer> getBoundaries() {
        return boundaries;
    }

    public void setBoundaries(HashMap<String, Integer> boundaries) {
        this.boundaries = boundaries;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public Team getTeamByName(String name) {
        for(Team team : getTeams()) {
            if (team.getTeamName().equalsIgnoreCase(name)) {
                return team;
            }
        }
        return null;
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public void editTeam(Team team, Arena arena) {
        Team tempteam = arena.getTeamByName(team.getTeamName());
        if(tempteam != null) {
            tempteam.setTeamName(team.getTeamName());
            tempteam.setMinPlayers(team.getMinPlayers());
            tempteam.setMaxPlayers(team.getMaxPlayers());
        } else {
            arena.addTeam(team);
        }
        new ArenasConfig(NerdMinigames.getPlugin()).editArena(arena);
    }

    public void deleteTeam(Team team) {
        this.teams.remove(team);
    }

    public List<Item> getItems() {
        return items;
    }

    public Item getItemByItem(ItemStack checkItem) {
        for(Item item : getItems()) {
            System.out.println(item.getItem());
            if(item.getItem().isSimilar(checkItem)) {
                return item;
            }
        }
        System.out.println("No result");
        return null;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void deleteItem(Item item) {
        this.items.remove(item);
    }

    public List<Spawn> getSpawns() {
        return spawns;
    }

    public void setSpawns(List<Spawn> spawns) {
        this.spawns = spawns;
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
