package me.ppgome.nerdminigames.nerdminigames.listeners;

import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import me.ppgome.nerdminigames.nerdminigames.guis.NerdGUI;
import me.ppgome.nerdminigames.nerdminigames.guis.SpawnCreationGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import java.util.UUID;

public class CreationListeners implements Listener {

    @EventHandler
    public void onHandSwap(PlayerSwapHandItemsEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        if(NerdMinigames.getPendingInput().containsKey(uuid)) {
            NerdGUI gui = NerdMinigames.getPendingInput().get(uuid);
            if(gui instanceof SpawnCreationGUI) {
                ((SpawnCreationGUI) gui).setLocation();
            }
        }
    }
}
