package me.ppgome.nerdminigames.nerdminigames.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import me.ppgome.nerdminigames.nerdminigames.ArenasConfig;
import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import me.ppgome.nerdminigames.nerdminigames.data.Arena;
import me.ppgome.nerdminigames.nerdminigames.data.Storage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.addBackground;
import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.createButton;
import static org.apache.commons.lang3.text.WordUtils.capitalizeFully;

public class StorageCreationGUI implements NerdGUI{

    private Player player;
    private Arena arena;
    private NerdGUI backgui;
    private ChestGui gui;
    private ConfirmationGUI deleteStorage;

    private Storage storage;
    private boolean firstOpen = true;

    private Container container;

    NamespacedKey arenastorage = new NamespacedKey(NerdMinigames.PLUGIN, "arenastorage");

    public StorageCreationGUI(Player player, Arena arena, NerdGUI backgui) {
        this.player = player;
        this.backgui = backgui;
        this.arena = arena;
    }

    public StorageCreationGUI(Player player, Arena arena, NerdGUI backgui, Storage storage) {
        this.player = player;
        this.backgui = backgui;
        this.arena = arena;
        this.storage = storage;
    }

    public void setContainer(Container container) {
        System.out.println(container.getType());
        this.container = container;
        System.out.println(this.container.getType());
        displayGUI();
    }

    @Override
    public void displayGUI() {

        if(storage != null) {
            gui = new ChestGui(5, "Editing storage...");
            if(firstOpen) {
                container = storage.getContainer();
                firstOpen = false;
            }
        } else {
            gui = new ChestGui(5, "Creating storage...");
        }

        gui.setOnGlobalClick(c -> c.setCancelled(true));

        addBackground(gui);

        StaticPane buttons = new StaticPane(0, 1, 9, 3, Pane.Priority.HIGHEST);

        int nudge = 9;
        if(storage != null) {
            nudge = 0;
        }

        // Cancel
        buttons.addItem(new GuiItem(createButton(Material.RED_STAINED_GLASS_PANE, "Cancel", "#ff5151"), inventoryClickEvent -> {
            backgui.displayGUI();
        }), Slot.fromIndex(2 + nudge));

        // Confirm
        buttons.addItem(new GuiItem(createButton(Material.LIME_STAINED_GLASS_PANE, "Confirm", "#b5ff20"), inventoryClickEvent -> {
            if(storage == null) {
                storage = new Storage();
                if(container != null) {
                    storage.setLocation(container.getLocation());
                    storage.setContainer(container);
                    arena.addStorage(storage);
                    new ArenasConfig(NerdMinigames.PLUGIN).editArena(arena);
                    backgui.displayGUI();

                    if(!container.getPersistentDataContainer().has(arenastorage)) {
                        container.getPersistentDataContainer().set(arenastorage, PersistentDataType.BOOLEAN, true);
                        container.update();
                    }
                }
            } else {
                // TODO add items
                storage.getContainer().getPersistentDataContainer().set(arenastorage, PersistentDataType.BOOLEAN, false);
                storage.getContainer().update();
                container.getPersistentDataContainer().set(arenastorage, PersistentDataType.BOOLEAN, true);
                container.update();
                storage.setLocation(container.getLocation());
                storage.setContainer(container);
                new ArenasConfig(NerdMinigames.PLUGIN).editArena(arena);
                backgui.displayGUI();
            }
        }), Slot.fromIndex(6 + nudge));

        Material icon = Material.CHEST;

        if(container != null) {
            icon = this.container.getType();
        }

        // Get Container
        String storagelabel = "Set Storage";
        if(container != null) {
            storagelabel = capitalizeFully(container.getType().toString().replace("_", " "));
        }
        buttons.addItem(new GuiItem(createButton(icon, storagelabel, "#FFFFFF"), clicc -> {
            NerdMinigames.addPendingInput(player.getUniqueId(), this);
            player.closeInventory();
            BukkitScheduler scheduler = Bukkit.getScheduler();
            scheduler.runTaskTimer(NerdMinigames.PLUGIN, task -> {
                if(NerdMinigames.getPendingInput().containsKey(player.getUniqueId())) {
                    player.sendActionBar(Component.text("Selecting a storage block...", TextColor.fromHexString("#ff3a3a")));
                } else {
                    task.cancel();
                }
            }, 0L, 20L);
        }), Slot.fromIndex(4 + nudge));

        if(storage != null) {
            // Add Items
            buttons.addItem(new GuiItem(createButton(Material.DIAMOND, "Add Items", "#FFFFFF"), clicc -> {
                new StorageContentsGUI(player, arena, this, storage).displayGUI();
            }), Slot.fromIndex(21));

            // Delete Storage
            buttons.addItem(new GuiItem(createButton(Material.BARRIER, "Delete Storage", "#FFFFFF"), clicc -> {
                deleteStorage = new ConfirmationGUI(player, "Delete this storage?", this);
                deleteStorage.displayGUI();
            }), Slot.fromIndex(23));
        }

        gui.addPane(buttons);
        gui.show(player);

        if(deleteStorage != null && deleteStorage.getInput()) {
            arena.deleteStorage(storage);
            new ArenasConfig(NerdMinigames.PLUGIN).editArena(arena);
            backgui.displayGUI();
            if(container.getPersistentDataContainer().has(arenastorage)) {
                container.getPersistentDataContainer().set(arenastorage, PersistentDataType.BOOLEAN, false);
                container.update();
            }
        }

    }
}
