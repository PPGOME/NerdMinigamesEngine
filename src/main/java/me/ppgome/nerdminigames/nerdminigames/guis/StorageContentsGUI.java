package me.ppgome.nerdminigames.nerdminigames.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import me.ppgome.nerdminigames.nerdminigames.data.Arena;
import me.ppgome.nerdminigames.nerdminigames.data.Item;
import me.ppgome.nerdminigames.nerdminigames.data.Storage;
import me.ppgome.nerdminigames.nerdminigames.data.StorageItem;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.addBackground;
import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.createButton;

public class StorageContentsGUI implements NerdGUI {

    private Player player;
    private Arena arena;
    private NerdGUI backgui;
    private Storage storage;

    private List<ItemStack> itemlist = new ArrayList<>();

    private ChestGui gui;

    public StorageContentsGUI(Player player, Arena arena, NerdGUI backgui, Storage storage) {
        this.player = player;
        this.arena = arena;
        this.backgui = backgui;
        this.storage = storage;
    }

    @Override
    public void displayGUI() {

        gui = new ChestGui(5, "Items in this storage block");
        gui.setOnGlobalClick(c -> c.setCancelled(true));

        addBackground(gui);

        StaticPane buttons = new StaticPane(0, 0, 9, 5, Pane.Priority.HIGHEST);

        for(StorageItem item : storage.getItems()) {
            itemlist.add(arena.getItems().get(item.getID()).getItem());
        }

        PaginatedPane pages = new PaginatedPane(0, 1, 9, 3, Pane.Priority.HIGH);
        itemlist = new ArrayList<>();
        pages.clear();

        pages.populateWithItemStacks(itemlist);

        // Pages back
        buttons.addItem(new GuiItem(createButton(Material.ARROW, "Back", "#FFFFFF"), inventoryClickEvent -> {
            if(pages.getPage() > 0) {
                pages.setPage(pages.getPage() - 1);
                gui.update();
            }
        }), Slot.fromIndex(37));

        // Pages next
        buttons.addItem(new GuiItem(createButton(Material.ARROW, "Next", "#FFFFFF"), inventoryClickEvent -> {
            if(pages.getPage() != pages.getPages() - 1) {
                pages.setPage(pages.getPage() + 1);
                gui.update();
            }
        }), Slot.fromIndex(43));

        // Back
        buttons.addItem(new GuiItem(createButton(Material.RED_WOOL, "Back", "#FFFFFF"), clicc -> {
            backgui.displayGUI();
        }), Slot.fromIndex(39));

        // Add new item
        buttons.addItem(new GuiItem(createButton(Material.NAME_TAG, "Add item", "#FFFFFF"), clicc -> {
            new ItemListGUI(player, this, arena, storage).displayGUI();
        }), Slot.fromIndex(41));

        pages.setOnClick(clicc -> {
        });

        gui.addPane(pages);
        gui.addPane(buttons);
        gui.show(player);

    }
}
