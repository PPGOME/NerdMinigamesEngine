package me.ppgome.nerdminigames.nerdminigames.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import me.ppgome.nerdminigames.nerdminigames.ArenasConfig;
import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import me.ppgome.nerdminigames.nerdminigames.data.Arena;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.*;

public class ArenaGUI implements NerdGUI {

    private final Player player;
    private final Arena arena;
    private final NerdGUI backgui;

    public ArenaGUI(Player player, Arena arena, NerdGUI backgui) {
        this.player = player;
        this.arena = arena;
        this.backgui = backgui;
    }

    @Override
    public void displayGUI() {
        ChestGui gui = new ChestGui(5, "Editing " + arena.getArenaName());

        gui.setOnGlobalClick(event -> event.setCancelled(true));

        addBackground(gui);

        StaticPane buttons = new StaticPane(0, 1, 9, 3, Pane.Priority.HIGHEST);

        // Teams
        buttons.addItem(new GuiItem(createButton(Material.PLAYER_HEAD, "Teams", "#ffffff"),
                inventoryClickEvent -> {
                    new TeamListGUI(player, this, arena).displayGUI();
                }), Slot.fromIndex(1));

        // Items
        buttons.addItem(new GuiItem(createButton(Material.CHEST, "Items", "#ffffff"),
                inventoryClickEvent -> {
                    new ItemListGUI(player, this, arena).displayGUI();
                }), Slot.fromIndex(3));

        // Spawns
        buttons.addItem(new GuiItem(createButton(Material.RED_BED, "Spawns", "#ffffff"),
                inventoryClickEvent -> {
                    new SpawnListGUI(player, this, arena).displayGUI();
                }), Slot.fromIndex(5));

        // Objectives
        buttons.addItem(new GuiItem(createButton(Material.EXPERIENCE_BOTTLE, "Objectives", "#ffffff"),
                inventoryClickEvent -> {
                    player.sendMessage(Component.text("DDDDDD"));
                }), Slot.fromIndex(7));

        // Storage

        buttons.addItem(new GuiItem(createButton(Material.CHEST, "Storage", "#FFFFFF"), clicc -> {
            new StorageListGUI(player, this, arena).displayGUI();
        }), Slot.fromIndex(20));

        // Server Options
        buttons.addItem(new GuiItem(createButton(Material.DIAMOND_PICKAXE, "Arena Options", "#ffffff"),
                inventoryClickEvent -> {
                    player.sendMessage(Component.text("AAAAAA"));
                }), Slot.fromIndex(22));

        // Armour
        buttons.addItem(new GuiItem(createButton(Material.DIAMOND_CHESTPLATE, "Armour", "#FFFFFF"), clicc -> {

        }), Slot.fromIndex(24));

        // Back
        StaticPane backbutton = new StaticPane(4, 3, 1, 1, Pane.Priority.NORMAL);
        backbutton.addItem(new GuiItem(createButton(Material.ARROW, "Back", "#FFFFFF"),
                inventoryClickEvent -> {
                    if(backgui != null) {
                        backgui.displayGUI();
                    }
                }), 0, 0);

        gui.addPane(buttons);
        gui.addPane(backbutton);

        gui.show(player);
    }

}
