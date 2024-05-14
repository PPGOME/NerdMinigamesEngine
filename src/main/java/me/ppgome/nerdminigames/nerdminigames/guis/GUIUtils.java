package me.ppgome.nerdminigames.nerdminigames.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.gui.type.util.Gui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GUIUtils {

    public static ItemStack createButton(Material material, String name, String color) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.displayName(Component.text(name).color(TextColor.fromHexString(color)));
        item.setItemMeta(itemmeta);
        return item;
    }

    public static ItemStack createLocationButton(Material material, Location location) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemmeta = item.getItemMeta();
        if(location != null) {
            Component name = Component.text("X: " + String.format("%.2f", location.getX()))
                    .color(TextColor.fromHexString("#ff5050"))
                    .append(Component.text(" Y: " + String.format("%.2f", location.getY()))
                            .color(TextColor.fromHexString("#ffff66")))
                    .append(Component.text(" Z: " + String.format("%.2f", location.getZ()))
                            .color(TextColor.fromHexString("#3399ff")));
            if(location.getPitch() != 0.0f && location.getYaw() != 0.0f) {
                name = name.append(Component.text(" Pitch: " + String.format("%.2f", location.getPitch()))
                        .color(TextColor.fromHexString("#33cc33"))
                        .append(Component.text(" Yaw: " + String.format("%.2f", location.getYaw()))
                                .color(TextColor.fromHexString("#ff9900"))));
            }
            itemmeta.displayName(name);
        } else {
            itemmeta.displayName(Component.text("Click to set location"));
        }
        item.setItemMeta(itemmeta);
        return item;
    }

    public static void addBackground(ChestGui gui) {
        OutlinePane whitebars = new OutlinePane(0, 0, 9, 5, Pane.Priority.LOWEST);
        whitebars.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
        whitebars.setRepeat(true);

        OutlinePane body = new OutlinePane(0, 1, 9, 3, Pane.Priority.NORMAL);
        body.addItem(new GuiItem(createButton(Material.BLACK_STAINED_GLASS_PANE, "", "#FFFFFF")));
        body.setRepeat(true);

        gui.addPane(whitebars);
        gui.addPane(body);
    }

    public static boolean isInteger(String string) {
        for (char c : string.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

}
