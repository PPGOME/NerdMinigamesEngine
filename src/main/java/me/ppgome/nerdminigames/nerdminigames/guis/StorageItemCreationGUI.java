package me.ppgome.nerdminigames.nerdminigames.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import me.ppgome.nerdminigames.nerdminigames.ArenasConfig;
import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import me.ppgome.nerdminigames.nerdminigames.data.*;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static me.ppgome.nerdminigames.nerdminigames.Utils.removeBrackets;
import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.*;
import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.createButton;

/**
 * GUI for creating a StorageItem
 *
 * @since 0.0.4
 * @author Keelan Delorme
 */
public class StorageItemCreationGUI implements NerdGUI {

    private Player player;
    private Arena arena;
    private NerdGUI backgui;
    private ChestGui gui;

    private Storage storage;
    private StorageItem storageItem;
    private Item item;

    private DataInputGUI minInput;
    private DataInputGUI maxInput;
    private DataInputGUI chanceInput;
    private ConfirmationGUI deleteItem;

    public StorageItemCreationGUI(Player player, Arena arena, NerdGUI backgui, Storage storage, Item item) {
        this.player = player;
        this.arena = arena;
        this.backgui = backgui;
        this.storage = storage;
        this.item = item;
    }

    public StorageItemCreationGUI(Player player, Arena arena, NerdGUI backgui, Storage storage, StorageItem storageItem) {
        this.player = player;
        this.arena = arena;
        this.backgui = backgui;
        this.storage = storage;
        this.storageItem = storageItem;
    }

    @Override
    public void displayGUI() {
        gui = new ChestGui(5, "Modifying item...");
        gui.setOnGlobalClick(clicc -> clicc.setCancelled(true));

        addBackground(gui);

        StaticPane buttons = new StaticPane(0, 0, 9, 5, Pane.Priority.HIGHEST);

        int nudge = 0;
        if (storageItem == null) nudge += 1;

        //REMOVE
        System.out.println(arena.getItems().indexOf(item));
        //REMOVE

        // Cancel
        buttons.addItem(new GuiItem(createButton(Material.RED_STAINED_GLASS_PANE, "Cancel", "#ff5151"), inventoryClickEvent -> {
            backgui.displayGUI();
        }), Slot.fromIndex(11));

        // Item
        ItemStack cloneditem = new ItemStack(item.getItem());
        cloneditem.setAmount(1);
        buttons.addItem(new GuiItem(cloneditem), Slot.fromIndex(13));

        // Confirm
        buttons.addItem(new GuiItem(createButton(Material.LIME_STAINED_GLASS_PANE, "Confirm", "#b5ff20"), inventoryClickEvent -> {
            if(minInput != null) {
                if(isInteger(minInput.getInput()) && maxInput.getInput() != null) {
                    if(isInteger(maxInput.getInput()) && chanceInput.getInput() != null) {
                        if(isInteger(chanceInput.getInput())) {
                            if(Integer.parseInt(chanceInput.getInput()) >= 0 && Integer.parseInt(chanceInput.getInput()) <= 100) {
                                List<StorageItem> itemstoaddto = storage.getItems();
                                itemstoaddto.add(new StorageItem(arena.getItems().indexOf(item), Integer.parseInt(minInput.getInput()),
                                        Integer.parseInt(maxInput.getInput()), Integer.parseInt(chanceInput.getInput())));
                            }
                        }
                    }
                }
            }
        }), Slot.fromIndex(15));

        // Min
        String label;
        if (minInput != null) {
            label = "Min: " + minInput.getInput();
        } else if (storageItem != null) {
            label = "Min: " + storageItem.getMin();
        } else {
            label = "Click to set min item count";
        }
        buttons.addItem(new GuiItem(createButton(Material.LEATHER_CHESTPLATE, label, "#b5ff20"), inventoryClickEvent -> {
            minInput = new DataInputGUI(player, "Setting min amount...", this);
            minInput.displayGUI();
        }), Slot.fromIndex(28 + nudge));

        // Max
        if (maxInput != null) {
            label = "Max: " + maxInput.getInput();
        } else if (storageItem != null) {
            label = "Max: " + storageItem.getMax();
        } else {
            label = "Click to set max item count";
        }
        buttons.addItem(new GuiItem(createButton(Material.NAME_TAG, label, "#b5ff20"), inventoryClickEvent -> {
            maxInput = new DataInputGUI(player, "Setting max amount...", this);
            maxInput.displayGUI();
        }), Slot.fromIndex(30 + nudge));

        // Chance
        if(chanceInput != null){
            label = "Chance: " + chanceInput.getInput();
        } else if(storageItem != null) {
            label = "Chance: " + storageItem.getChance();
        } else {
            label = "Click to set chance";
        }

        buttons.addItem(new GuiItem(createButton(Material.EXPERIENCE_BOTTLE, label, "#b5ff20"), inventoryClickEvent -> {
            chanceInput = new DataInputGUI(player, "Setting item chance...", this);
            chanceInput.displayGUI();
        }), Slot.fromIndex(32 + nudge));

        // TODO
        // Delete
        if (storageItem != null) {
            buttons.addItem(new GuiItem(createButton(Material.BARRIER, "Delete Item", "#ff5151"), inventoryClickEvent -> {

                String itemname = PlainTextComponentSerializer.plainText().serialize(arena.getItems().get(storageItem.getID()).getItem().displayName());
                if(itemname.substring(0, 1).equalsIgnoreCase("[") && itemname.substring(itemname.length() - 1).equalsIgnoreCase("]")) {
                    itemname = itemname.substring(1, itemname.length() - 1);
                }

                deleteItem = new ConfirmationGUI(player, "Delete item " + itemname + "?", this);
                deleteItem.displayGUI();
            }), Slot.fromIndex(34));
        }

        gui.addPane(buttons);
        gui.show(player);

    }
}
