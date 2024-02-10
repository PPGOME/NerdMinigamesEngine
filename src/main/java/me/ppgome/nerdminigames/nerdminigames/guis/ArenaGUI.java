package me.ppgome.nerdminigames.nerdminigames.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
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

import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.createButton;

public class ArenaGUI implements NerdGUI {

    private Player player;
    private Arena arena;
    private NerdGUI backgui;

    public ArenaGUI(Player player, Arena arena, NerdGUI backgui) {
        this.player = player;
        this.arena = arena;
        this.backgui = backgui;
    }

    @Override
    public void displayGUI() {
        ChestGui gui = new ChestGui(4, "Editing " + arena.getArenaName());

        gui.setOnGlobalClick(event -> event.setCancelled(true));

        ArenasConfig arenasConfig = new ArenasConfig(NerdMinigames.getPlugin());
        arenasConfig.getConfig().set("thisisatest.hello", "a");

        System.out.println(arena.getBoundaries());
        System.out.println(arena.getOwner());
        System.out.println(arena.getWorld());

        OutlinePane background = new OutlinePane(0, 0, 9, 4, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        background.setRepeat(true);

        OutlinePane topbackground = new OutlinePane(0, 0, 9, 1, Pane.Priority.LOW);
        topbackground.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
        topbackground.setRepeat(true);

        OutlinePane bottombackground = new OutlinePane(0, 3, 9, 1, Pane.Priority.LOW);
        bottombackground.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
        bottombackground.setRepeat(true);

        StaticPane buttons = new StaticPane(0, 1, 9, 2, Pane.Priority.NORMAL);
        buttons.addItem(new GuiItem(createButton(Material.DIAMOND_PICKAXE, "Arena Options", "#ffffff"),
                inventoryClickEvent -> {
                    player.sendMessage(Component.text("AAAAAA"));
                }), 4, 0);
        buttons.addItem(new GuiItem(createButton(Material.PLAYER_HEAD, "Teams", "#ffffff"),
                inventoryClickEvent -> {
                    new TeamListGUI(player, this, arena).displayGUI();
                }), 1, 0);
        buttons.addItem(new GuiItem(createButton(Material.CHEST, "Items", "#ffffff"),
                inventoryClickEvent -> {
                    new ItemListGUI(player, this, arena).displayGUI();
                }), 2, 1);
        buttons.addItem(new GuiItem(createButton(Material.EXPERIENCE_BOTTLE, "Objectives", "#ffffff"),
                inventoryClickEvent -> {
                    player.sendMessage(Component.text("DDDDDD"));
                }), 6, 1);
        buttons.addItem(new GuiItem(createButton(Material.RED_BED, "Spawns", "#ffffff"),
                inventoryClickEvent -> {
                    player.sendMessage(Component.text("EEEEEE"));
                }), 7, 0);

        StaticPane backbutton = new StaticPane(4, 3, 1, 1, Pane.Priority.NORMAL);
        backbutton.addItem(new GuiItem(createButton(Material.ARROW, "Back", "#FFFFFF"),
                inventoryClickEvent -> {
                    if(backgui != null) {
                        backgui.displayGUI();
                    }
                }), 0, 0);

        gui.addPane(background);
        gui.addPane(topbackground);
        gui.addPane(bottombackground);
        gui.addPane(buttons);
        gui.addPane(backbutton);

        gui.show(player);
    }

}
