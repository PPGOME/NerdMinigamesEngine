package me.ppgome.nerdminigames.nerdminigames.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.regions.Region;
import me.ppgome.nerdminigames.nerdminigames.ArenasConfig;
import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import me.ppgome.nerdminigames.nerdminigames.data.Arena;
import me.ppgome.nerdminigames.nerdminigames.data.Team;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TeamListGUI implements NerdGUI {

    //TODO If GUI is null when back button pressed, close GUI completely

    private Player player;
    private NerdGUI backgui;
    private Arena arena;
    private List<ItemStack> itemlist = new ArrayList<>();

    public TeamListGUI(Player player, NerdGUI backgui, Arena arena) {
        this.player = player;
        this.backgui = backgui;
        this.arena = arena;
    }

    @Override
    public void displayGUI() {

        ChestGui gui = new ChestGui(5, "Teams");

        gui.setOnGlobalClick(e -> e.setCancelled(true));
        ArenasConfig arenaconfig = new ArenasConfig(NerdMinigames.getPlugin());

        OutlinePane whitebars = new OutlinePane(0, 0, 9, 5, Pane.Priority.LOWEST);
        whitebars.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
        whitebars.setRepeat(true);

        PaginatedPane pages = new PaginatedPane(0, 1, 9, 3, Pane.Priority.HIGH);
        itemlist = new ArrayList<>();
        pages.clear();

        OutlinePane body = new OutlinePane(0, 1, 9, 3, Pane.Priority.NORMAL);
        body.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        body.setRepeat(true);

        for(Team team : arena.getTeams()) {
            itemlist.add(createButton(Material.REDSTONE, team.getTeamName(), "#FFFFFF"));
        }

        pages.populateWithItemStacks(itemlist);

        StaticPane pageButtonBack = new StaticPane(1, 4, 1, 1, Pane.Priority.HIGHEST);
        pageButtonBack.addItem(new GuiItem(createButton(Material.ARROW, "Back", "#FFFFFF"), inventoryClickEvent -> {
            if(pages.getPage() > 0) {
                pages.setPage(pages.getPage() - 1);
                gui.update();
            }
        }), 0, 0);
        StaticPane pageButtonNext = new StaticPane(7, 4, 1, 1, Pane.Priority.HIGHEST);
        pageButtonNext.addItem(new GuiItem(createButton(Material.ARROW, "Next", "#FFFFFF"), inventoryClickEvent -> {
            System.out.println(pages.getPage());
            if(pages.getPage() != pages.getPages() - 1) {
                pages.setPage(pages.getPage() + 1);
                gui.update();
            }
        }), 0, 0);

        pages.setOnClick(inventoryClickEvent -> {
            System.out.println(pages.getPage());
            if(!inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("")) {
                // TODO Make confirmation for delete screen
            }
        });

        StaticPane newbutton = new StaticPane(4, 4, 1, 1, Pane.Priority.HIGH);
        newbutton.addItem(new GuiItem(createButton(Material.LIME_WOOL, "Add new...", "#FFFFFF"), inventoryClickEvent -> {
            newbutton.addItem(new GuiItem(createButton(Material.LIME_WOOL, "Add new...", "#FFFFFF")), 0, 0);
            newbutton.setOnClick(e -> {
                new TeamCreationGUI(player, this).displayGUI();
            });
        }), 0, 0);

        gui.addPane(whitebars);
        gui.addPane(newbutton);
        gui.addPane(pages);
        gui.addPane(pageButtonBack);
        gui.addPane(pageButtonNext);
        gui.addPane(body);

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
