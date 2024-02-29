package me.ppgome.nerdminigames.nerdminigames.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import me.ppgome.nerdminigames.nerdminigames.ArenasConfig;
import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import me.ppgome.nerdminigames.nerdminigames.data.Arena;
import me.ppgome.nerdminigames.nerdminigames.data.Spawn;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static me.ppgome.nerdminigames.nerdminigames.Utils.removeBrackets;
import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.addBackground;
import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.createButton;

public class SpawnListGUI implements NerdGUI {

    private final Player player;
    private final NerdGUI backgui;
    private final Arena arena;
    private List<ItemStack> itemlist = new ArrayList<>();

    ChestGui gui;

    public SpawnListGUI(Player player, NerdGUI backgui, Arena arena) {
        this.player = player;
        this.backgui = backgui;
        this.arena = arena;
    }

    @Override
    public void displayGUI() {

        gui = new ChestGui(5, "Spawns");

        gui.setOnGlobalClick(e -> e.setCancelled(true));
        ArenasConfig arenaconfig = new ArenasConfig(NerdMinigames.getPlugin());

        addBackground(gui);

        PaginatedPane pages = new PaginatedPane(0, 1, 9, 3, Pane.Priority.HIGH);
        itemlist = new ArrayList<>();
        pages.clear();

        for(Spawn spawn : arena.getSpawns()) {
            itemlist.add(createButton(Material.RED_BED, spawn.getName(), "#FFFFFF"));
        }

        pages.populateWithItemStacks(itemlist);

        pages.setOnClick(inventoryClickEvent -> {

            String itemname = PlainTextComponentSerializer.plainText().serialize(inventoryClickEvent.getCurrentItem().displayName());
            if(!itemname.equalsIgnoreCase("")) {
                ItemStack item = inventoryClickEvent.getCurrentItem();
                new SpawnCreationGUI(player, arena, arena.getSpawnByName(removeBrackets(item.displayName())), this).displayGUI();
            }
        });

        StaticPane buttons = new StaticPane(0, 0, 9, 5);

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

        // Add Spawn
        buttons.addItem(new GuiItem(createButton(Material.NAME_TAG, "New Spawn...", "#FFFFFF"), clicc -> {
            new SpawnCreationGUI(player, arena, this).displayGUI();
        }), Slot.fromIndex(40));

        gui.addPane(buttons);
        gui.addPane(pages);
        gui.show(player);
    }
}
