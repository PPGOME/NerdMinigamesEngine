package me.ppgome.nerdminigames.nerdminigames.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
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
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.addBackground;
import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.createButton;

public class StorageListGUI implements NerdGUI {

    private Player player;
    private NerdGUI backgui;
    private Arena arena;
    private List<ItemStack> itemlist = new ArrayList<>();
    ChestGui gui;

    public StorageListGUI(Player player, NerdGUI backgui, Arena arena) {
        this.player = player;
        this.backgui = backgui;
        this.arena = arena;
    }

    public void modifyContainer(Storage storage) {
        System.out.println(storage);
        new StorageCreationGUI(player, arena, this, storage).displayGUI();
    }

    @Override
    public void displayGUI() {

        gui = new ChestGui(5, "Storage");

        gui.setOnGlobalClick(e -> e.setCancelled(true));
        ArenasConfig arenaconfig = new ArenasConfig(NerdMinigames.getPlugin());

        addBackground(gui);

        PaginatedPane pages = new PaginatedPane(0, 1, 9, 3, Pane.Priority.HIGH);
        itemlist = new ArrayList<>();
        pages.clear();

        for(Storage storage : arena.getStorage()) {
            ItemStack item = new ItemStack(storage.getContainer().getType());
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.displayName(Component.text("X: " + storage.getLocation().getX()).color(TextColor.fromHexString("#FF5555"))
                    .append(Component.text(" Y: " + storage.getLocation().getY()).color(TextColor.fromHexString("#5555FF")))
                            .append(Component.text(" Z: " + storage.getLocation().getZ()).color(TextColor.fromHexString("#FFFF55"))));
            item.setItemMeta(itemMeta);
            itemlist.add(item);
        }

        pages.populateWithItemStacks(itemlist);

        StaticPane buttons = new StaticPane(0, 0, 9, 5);

        buttons.addItem(new GuiItem(createButton(Material.WRITABLE_BOOK, "Edit a Storage", "#FFFFFF"), inventoryClickEvent -> {
            NerdMinigames.addPendingInput(player.getUniqueId(), this);
            player.closeInventory();
            BukkitScheduler scheduler = Bukkit.getScheduler();
            scheduler.runTaskTimer(NerdMinigames.getPlugin(), task -> {
                if(NerdMinigames.getPendingInput().containsKey(player.getUniqueId())) {
                    player.sendActionBar(Component.text("Selecting a storage block...", TextColor.fromHexString("#ff3a3a")));
                } else {
                    task.cancel();
                }
            }, 0L, 20L);
        }), Slot.fromIndex(4));

        // Page cycling
        buttons.addItem(new GuiItem(createButton(Material.ARROW, "Back", "#FFFFFF"), inventoryClickEvent -> {
            if(pages.getPage() > 0) {
                pages.setPage(pages.getPage() - 1);
                gui.update();
            }
        }), Slot.fromIndex(37));
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

        // Add Spawn
        buttons.addItem(new GuiItem(createButton(Material.NAME_TAG, "New Storage...", "#FFFFFF"), clicc -> {
            new StorageCreationGUI(player, arena, this).displayGUI();
        }), Slot.fromIndex(41));

        gui.addPane(pages);
        gui.addPane(buttons);
        gui.show(player);
    }

    public Arena getArena() {
        return this.arena;
    }
}
