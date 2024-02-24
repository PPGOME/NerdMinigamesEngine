package me.ppgome.nerdminigames.nerdminigames.guis;

import me.ppgome.nerdminigames.nerdminigames.data.Arena;
import org.bukkit.entity.Player;

public class SpawnListGUI implements NerdGUI {

    Player player;
    NerdGUI backgui;
    Arena arena;

    public SpawnListGUI(Player player, NerdGUI backgui, Arena arena) {
        this.player = player;
        this.backgui = backgui;
        this.arena = arena;
    }

    @Override
    public void displayGUI() {

    }
}
