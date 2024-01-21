package me.ppgome.nerdminigames.nerdminigames.data;

public class Team {

    private Arena arena;
    private String teamName;
    private int minPlayers = 1;
    private int maxPlayers = 999;

    public Team(Arena arena, String teamName, int minPlayers, int maxPlayers) {
        this.arena = arena;
        this.teamName = teamName;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
}
