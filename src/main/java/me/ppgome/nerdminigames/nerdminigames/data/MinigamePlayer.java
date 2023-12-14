package me.ppgome.nerdminigames.nerdminigames.data;

public class MinigamePlayer {

    private int playerid;
    private String uuid;
    private int gameid;
    private String team;

    public MinigamePlayer(int playerid, String uuid, int gameid, String team) {
        this.playerid = playerid;
        this.uuid = uuid;
        this.gameid = gameid;
        this.team = team;
    }

    public int getPlayerid() {
        return playerid;
    }

    public void setPlayerid(int playerid) {
        this.playerid = playerid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

}
