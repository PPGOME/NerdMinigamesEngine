package me.ppgome.nerdminigames.nerdminigames.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import me.ppgome.nerdminigames.nerdminigames.data.Team;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TeamCreationGUI implements NerdGUI {

    private Team team;
    private Player player;
    private NerdGUI backgui;
    private DataInputGUI teamName;
    private DataInputGUI maxPlayers;
    private DataInputGUI minPlayers;

    public TeamCreationGUI(Player player, NerdGUI backgui) {
        this.player = player;
        this.backgui = backgui;
    }

    public TeamCreationGUI(Team team, Player player, NerdGUI backgui) {
        this.team = team;
        this.player = player;
        this.backgui = backgui;
    }

    @Override
    public void displayGUI() {

        ChestGui gui = new ChestGui(5, "Creating a new team...");

        OutlinePane whitebars = new OutlinePane(0, 0, 9, 5, Pane.Priority.LOWEST);
        whitebars.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
        whitebars.setRepeat(true);

        OutlinePane body = new OutlinePane(0, 1, 9, 3, Pane.Priority.LOW);
        body.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        body.setRepeat(true);

        StaticPane buttons = new StaticPane(2, 1, 5, 3, Pane.Priority.NORMAL);
        buttons.addItem(new GuiItem(createButton(Material.RED_STAINED_GLASS_PANE, "Cancel", "#ff5151"), inventoryClickEvent -> {
            backgui.displayGUI();
        }), Slot.fromIndex(0));

        gui.addPane(whitebars);
        gui.addPane(body);
        gui.addPane(buttons);

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
