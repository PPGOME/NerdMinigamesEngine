package me.ppgome.nerdminigames.nerdminigames.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArenaGUI implements NerdGUI {

    private Player player;
    private String arena = "";
    private NerdGUI backgui;

    public ArenaGUI(Player player, String arena, NerdGUI backgui) {
        this.player = player;
        this.arena = arena;
        this.backgui = backgui;
    }

    @Override
    public void displayGUI() {
        ChestGui gui = new ChestGui(4, "Editing " + arena);

        gui.setOnGlobalClick(event -> event.setCancelled(true));

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
                    new ListGUI(player, "teams", this, arena).displayGUI();
                }), 1, 0);
        buttons.addItem(new GuiItem(createButton(Material.CHEST, "Items", "#ffffff"),
                inventoryClickEvent -> {
                    new ListGUI(player, "items", this, arena).displayGUI();
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

    @Override
    public ItemStack createButton(Material material, String name, String color) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.displayName(Component.text(name).color(TextColor.fromHexString(color)));
        item.setItemMeta(itemmeta);
        return item;
    }

}