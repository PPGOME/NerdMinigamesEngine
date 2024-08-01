package me.ppgome.nerdminigames.nerdminigames.listeners;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import me.ppgome.nerdminigames.nerdminigames.ArenasConfig;
import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import me.ppgome.nerdminigames.nerdminigames.data.Arena;
import me.ppgome.nerdminigames.nerdminigames.data.Storage;
import me.ppgome.nerdminigames.nerdminigames.guis.NerdGUI;
import me.ppgome.nerdminigames.nerdminigames.guis.SpawnCreationGUI;
import me.ppgome.nerdminigames.nerdminigames.guis.StorageCreationGUI;
import me.ppgome.nerdminigames.nerdminigames.guis.StorageListGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
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

    @EventHandler
    public void onContainerClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block block = e.getClickedBlock();
        if(block != null) {
            if(true) {
                if(block.getState() instanceof Container) {
                    if(NerdMinigames.getPendingInput().containsKey(p.getUniqueId())) {
                        NerdGUI gui = NerdMinigames.getPendingInput().get(p.getUniqueId());
                        if(gui instanceof StorageCreationGUI) {
                            NerdMinigames.getPendingInput().remove(p.getUniqueId());
                            e.setCancelled(true);
                            ((StorageCreationGUI) gui).setContainer((Container) block.getState());
                        } else if(gui instanceof StorageListGUI) {
                            StorageListGUI storageGUI = (StorageListGUI) NerdMinigames.getPendingInput().get(p.getUniqueId());
                            Arena arena = storageGUI.getArena();
                            NerdMinigames.getPendingInput().remove(p.getUniqueId());
                            for(Storage storage : arena.getStorage()) {
                                if(storage.getLocation().equals(block.getLocation())) {
                                    e.setCancelled(true);
                                    storageGUI.modifyContainer(storage);
                                } else {
                                    e.setCancelled(true);
                                    p.sendActionBar(Component.text("Is this storage block part of this arena?")
                                            .color(TextColor.fromHexString("#ff3a3a")));
                                }
                            }
                        }
                    } else {
                        NamespacedKey arenastorage = new NamespacedKey(NerdMinigames.PLUGIN, "arenastorage");
                        Container container = (Container) block.getState();
                        PersistentDataContainer pdcontainer = ((Container) block.getState()).getPersistentDataContainer();
                        if(pdcontainer.has(arenastorage)) {
                            if(pdcontainer.get(arenastorage, PersistentDataType.BOOLEAN)) {
                                e.setCancelled(true);
                                p.sendActionBar(Component.text("This storage block is part of an arena. Edit it through the editor!")
                                        .color(TextColor.fromHexString("#ff3a3a")));
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        Location location = block.getLocation();
        if((block.getState() instanceof Container)) {
            Container container = (Container) block.getState();
            NamespacedKey key = new NamespacedKey(NerdMinigames.PLUGIN, "arenastorage");
            if(container.getPersistentDataContainer().has(key, PersistentDataType.BOOLEAN)) {
                if(container.getPersistentDataContainer().get(key, PersistentDataType.BOOLEAN)) {
                    e.setCancelled(true);
                    player.sendActionBar(Component.text("This chest is part of an arena. Please remove it from " +
                            "the arena before breaking.").color(TextColor.fromHexString("#FF5555")));
                }
            }
        }
    }
}
