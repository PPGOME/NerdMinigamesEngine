package me.ppgome.nerdminigames.nerdminigames.data;

import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class Minigame {

    private final int gameid;
    private final Arena arena;
    private GameStatus gamestatus;
    private ArrayList<MinigamePlayer> players;
    private ArrayList<Integer> teamMinimums = new ArrayList<>();
    private ArrayList<Integer> teamMaximums = new ArrayList<>();
    private int totalminimum = 0;
    private int totalmaximum = 0;
    private HashMap<Team, Integer> teamMap = new HashMap<>();

    //----------------------------------------------------------------------------------------------------------

    public Minigame(int gameid, Arena arena, GameStatus gamestatus) {
        this.gameid = gameid;
        this.arena = arena;
        this.gamestatus = gamestatus;
    }

    //----------------------------------------------------------------------------------------------------------

    public int getGameid() {
        return gameid;
    }

    public Arena getArena() {
        return arena;
    }

    public GameStatus getGamestatus() {
        return gamestatus;
    }

    public void setGamestatus(GameStatus gamestatus) {
        this.gamestatus = gamestatus;
    }

    //----------------------------------------------------------------------------------------------------------

    public void start() {
        this.gamestatus = GameStatus.STARTING;

        // TEAMS
        Collections.shuffle(players);
        for(Team team : arena.getTeams()) {
            teamMap.put(team, 0);
        }

        /*
        Team selection system

        Assumes all previous checks passed, and forces all teams to grab the minimum number
        of players they need. Then, spreads players across teams equally.
         */
        int teams = teamMap.size();
        int iterate = 0;
        for(Map.Entry<Team, Integer> team : teamMap.entrySet()) {
            if(players.size() > 0) {
                if(team.getKey().getMinPlayers() >= team.getValue()) {
                    for(int i = 0; i < team.getKey().getMinPlayers(); i++) {
                        players.get(0).setTeam(team.getKey());
                        team.setValue(team.getValue() + 1);
                    }
                } else {
                    players.get(0).setTeam(team.getKey());
                    team.setValue(team.getValue() + 1);
                }
            }
        }



        gameLoop();
    }

    //----------------------------------------------------------------------------------------------------------

    public void addPlayer(Player player) {
        if(players.size() < totalmaximum) {
            players.add(new MinigamePlayer(player, gameid));
        }
    }

    //----------------------------------------------------------------------------------------------------------

    /**
     * Initiates the queue system that allows players to queue for a game.
     */
    public void queue() {

        // Countdown time
        final int[] time = {60};

        // Visual timer for players
        BossBar timer = BossBar.bossBar(Component.text("You are queued! Time left: " + time[0]), 1,
                BossBar.Color.GREEN, BossBar.Overlay.PROGRESS);

        // Get min and max numbers of players for all teams
        for(Team team : arena.getTeams()) {
            teamMinimums.add(team.getMinPlayers());
            teamMaximums.add(team.getMaxPlayers());
        }

        // Add for absolute minimum
        for(int i : teamMinimums) {
            totalminimum += i;
        }

        // Add for absolute maximum
        for(int i : teamMaximums) {
            totalmaximum += i;
        }

        // Main queue loop
        Bukkit.getScheduler().scheduleSyncRepeatingTask(NerdMinigames.PLUGIN, () -> {
            time[0] -= 1;

            // Adds each player to the queue loop
            for(MinigamePlayer player : players) {
                timer.addViewer(player.getPlayer());
            }

            // Refresh the visual timer for players
            if(time[0] > 10) {
                timer.name(Component.text("You are queued! Time left: " + time[0])).progress(((float) time[0])/60);
            } else {
                timer.name(Component.text("You are queued! Time left: " + time[0])).progress(((float) time[0])/60)
                        .color(BossBar.Color.RED);
            }

            // If enough players, begin.
            if(time[0] == 0) {
                if(players.size() < totalminimum) {
                    time[0] = 30;
                }
            }

        }, 0, 20);

    }

    //----------------------------------------------------------------------------------------------------------

    public void gameLoop() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(NerdMinigames.PLUGIN, new Runnable() {
            @Override
            public void run() {

            }
        }, 0, 1);
    }

    //----------------------------------------------------------------------------------------------------------

    public void end() {
    }

}
