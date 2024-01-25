package me.ppgome.nerdminigames.nerdminigames.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.AnvilGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DataInputGUI extends InputGUI implements NerdGUI{

    private Player player;
    private String guiname = "";
    private NerdGUI backgui;
    private String input = "";

    public DataInputGUI(Player player, String guiname, NerdGUI backgui) {
        this.player = player;
        this.guiname = guiname;
        this.backgui = backgui;
    }

    @Override
    public void displayGUI() {
        AnvilGui gui = new AnvilGui(guiname);

        gui.setOnGlobalClick(event -> event.setCancelled(true));

        ItemStack renameitem = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta renameitemmeta = renameitem.getItemMeta();
        renameitemmeta.displayName(Component.text(""));
        renameitem.setItemMeta(renameitemmeta);

        ItemStack cancel = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta cancelmeta = cancel.getItemMeta();
        cancelmeta.displayName(Component.text("Cancel").color(TextColor.fromHexString("#ff5151"))
                .decoration(TextDecoration.BOLD, true));
        cancel.setItemMeta(cancelmeta);

        ItemStack confirm = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta confirmmeta = confirm.getItemMeta();
        confirmmeta.displayName(Component.text("Confirm").color(TextColor.fromHexString("#b5ff20"))
                .decoration(TextDecoration.BOLD, true));
        confirm.setItemMeta(confirmmeta);

        StaticPane renameitemslot = new StaticPane(Slot.fromIndex(0), 1, 1);
        renameitemslot.addItem(new GuiItem(renameitem), 0, 0);

        StaticPane firstslot = new StaticPane(Slot.fromIndex(0), 1, 1);
        firstslot.addItem(new GuiItem(cancel), 0, 0);

        StaticPane lastslot = new StaticPane(Slot.fromIndex(0), 1, 1);
        lastslot.addItem(new GuiItem(confirm), 0 ,0);

        firstslot.setOnClick(inventoryClickEvent -> {
            backgui.displayGUI();
        });
        lastslot.setOnClick(inventoryClickEvent -> {
            input = gui.getRenameText();
            backgui.displayGUI();
        });

        gui.getFirstItemComponent().addPane(renameitemslot);
        gui.getSecondItemComponent().addPane(firstslot);
        gui.getResultComponent().addPane(lastslot);

        gui.show(player);
    }

    public String getInput() {
        return input;
    }
}
