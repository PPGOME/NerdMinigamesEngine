package me.ppgome.nerdminigames.nerdminigames.data;

import org.bukkit.entity.Player;

public class MinigamePlayer {

    private Player player;
    private int gameid;
    private Team team;

    public MinigamePlayer(Player player, int gameid, Team team) {
        this.player = player;
        this.gameid = gameid;
        this.team = team;
    }

    public MinigamePlayer(Player player, int gameid) {
        this.player = player;
        this.gameid = gameid;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}
