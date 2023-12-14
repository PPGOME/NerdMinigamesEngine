package me.ppgome.nerdminigames.nerdminigames.data;

public class Minigame {

    private int gameid;
    private int arenaid;
    private String gamestatus;

    public Minigame(int gameid, int arenaid, String gamestatus) {
        this.gameid = gameid;
        this.arenaid = arenaid;
        this.gamestatus = gamestatus;
    }

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public int getArenaid() {
        return arenaid;
    }

    public void setArenaid(int arenaid) {
        this.arenaid = arenaid;
    }

    public String getGamestatus() {
        return gamestatus;
    }

    public void setGamestatus(String gamestatus) {
        this.gamestatus = gamestatus;
    }
}
