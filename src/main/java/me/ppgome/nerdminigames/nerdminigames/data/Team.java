package me.ppgome.nerdminigames.nerdminigames.data;

/**
 * The object representing a team in an arena
 *
 * @since 0.0.2
 * @author Keelan Delorme
 */
public class Team {

    private String teamName;
    private int minPlayers = 1;
    private int maxPlayers = 999;

    /**
     * The constructor
     * @param teamName The name of the team
     * @param minPlayers The minimum number of players needed on the team
     * @param maxPlayers The maximum players allowed on a team
     */
    public Team(String teamName, int minPlayers, int maxPlayers) {
        this.teamName = teamName;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }

    /**
     * Returns the name of the team
     * @return the name of the team
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Sets the team's name
     * @param teamName the team's name
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * Returns the minimum amount of players needed on the team
     * @return the minimum amount of players needed on the team
     */
    public int getMinPlayers() {
        return minPlayers;
    }

    /**
     * Sets the minimum amount of players needed on the team
     * @param minPlayers Minimum amount of players
     */
    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    /**
     * Returns the maximum players allowed on a team
     * @return the maximum players allowed on a team
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Sets the maximum amount of players allowed on the team
     * @param maxPlayers Maximum amount of players
     */
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
}
