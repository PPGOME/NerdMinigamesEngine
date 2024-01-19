package me.ppgome.nerdminigames.nerdminigames.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemInputGUI extends InputGUI implements NerdGUI {

    private Player player;
    private String arena = "";
    private String team = "";
    private String chance = "";
    private NerdGUI backgui;
    private ChestGui gui;
    private ItemStack item;

    public ItemInputGUI(Player player, String arena, String team, String chance, NerdGUI backgui) {
        this.player = player;
        this.arena = arena;
        this.team = team;
        this.chance = chance;
        this.backgui = backgui;
    }

    @Override
    public void displayGUI() {

        ChestGui gui = new ChestGui(5, "Adding item to " + arena);

        // Background items
        OutlinePane background1 = new OutlinePane(0, 1, 4, 1, Pane.Priority.LOWEST);
        background1.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), inventoryClickEvent -> {
            inventoryClickEvent.setCancelled(true);
        }));
        background1.setRepeat(true);

        OutlinePane background2 = new OutlinePane(5, 1, 4, 1, Pane.Priority.LOWEST);
        background2.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), inventoryClickEvent -> {
            inventoryClickEvent.setCancelled(true);
        }));
        background2.setRepeat(true);

        OutlinePane topbackground = new OutlinePane(0, 0, 9, 1, Pane.Priority.LOW);
        topbackground.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE), inventoryClickEvent -> {
            inventoryClickEvent.setCancelled(true);
        }));
        topbackground.setRepeat(true);

        OutlinePane bottombackground = new OutlinePane(0, 2, 9, 1, Pane.Priority.LOW);
        bottombackground.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE), inventoryClickEvent -> {
            inventoryClickEvent.setCancelled(true);
        }));
        bottombackground.setRepeat(true);

        // Cancel button
        StaticPane cancelButton = new StaticPane(1, 1, 1, 1, Pane.Priority.NORMAL);
        cancelButton.addItem(new GuiItem((createButton(Material.RED_WOOL, "No", "#ff5151")), inventoryClickEvent -> {
            backgui.displayGUI();
        }), 0, 0);

        // Slot where item is inputted
        StaticPane depositItem = new StaticPane(4, 1, 1, 1, Pane.Priority.NORMAL);
        if(item != null) {
            depositItem.addItem(new GuiItem(item), 0, 0);
        }

        // Confirmation button
        StaticPane confirmButton = new StaticPane(7, 1, 1, 1, Pane.Priority.NORMAL);
        confirmButton.addItem(new GuiItem(new ItemStack((createButton(Material.LIME_WOOL, "Yes", "#b5ff20"))), inventoryClickEvent -> {
            System.out.println("test");
            inventoryClickEvent.setCancelled(true);
            item = inventoryClickEvent.getClickedInventory().getItem(13);
            DataInputGUI test = new DataInputGUI(player, "arena", this);
            if(item != null) {
                System.out.println(item);
            } else {
                System.out.println("empty");
            }
        }), 0, 0);

        // TODO Add chance, amount, and currency options

        gui.addPane(background1);
        gui.addPane(background2);
        gui.addPane(bottombackground);
        gui.addPane(topbackground);
        gui.addPane(cancelButton);
        gui.addPane(depositItem);
        gui.addPane(confirmButton);

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
