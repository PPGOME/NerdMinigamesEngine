package me.ppgome.nerdminigames.nerdminigames.data;

import org.bukkit.Location;

/**
 * The object representing a spawn point for a team in an arena
 *
 * @since 0.0.4
 * @author Keelan Delorme
 */
public class Spawn {

    private String name;
    private Location location;
    private Team team;

    /**
     * The constructor
     * @since 0.0.4
     * @param location The location of the spawn point
     * @param team The team that will spawn at this spawn point
     */
    public Spawn(String name, Location location, Team team) {
        this.name = name;
        this.location = location;
        this.team = team;
    }

    /**
     * Returns the name of the spawn
     * @return the name of the spawn
     */
    public String getName() {
        return name;
    }

    /**
     * The method for setting the name of the spawn
     * @param name The name of the spawn
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The method for setting the location of a spawn point
     * @param location The location of the spawn point
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * The method for setting the team of a spawn point
     * @param team The team that will spawn at this spawn point
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * Returns the spawn point's location
     * @return The spawn point's location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns the team that spawns at this spawn point
     * @return The team that spawns at this spawn point
     */
    public Team getTeam() {
        return team;
    }
}
