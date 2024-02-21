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
import me.ppgome.nerdminigames.nerdminigames.data.Team;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.createButton;

public class TeamListGUI implements NerdGUI {

    //TODO If GUI is null when back button pressed, close GUI completely

    private final Player player;
    private final NerdGUI backgui;
    private final Arena arena;
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
            itemlist.add(createButton(Material.LEATHER_CHESTPLATE, team.getTeamName(), "#FFFFFF"));
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
                String team = inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName();
                if(team.substring(0, 2).equalsIgnoreCase("§f")) {
                    team = team.substring(2);
                }
                new TeamCreationGUI(arena.getTeamByName(team), player, arena, this).displayGUI();
            }
        });

        StaticPane nonPageButton = new StaticPane(3, 4, 3, 1, Pane.Priority.HIGH);
        nonPageButton.addItem(new GuiItem(createButton(Material.RED_WOOL, "Back", "#FFFFFF"), inventoryClickEvent -> {
            backgui.displayGUI();
        }), Slot.fromIndex(0));
        nonPageButton.addItem(new GuiItem(createButton(Material.NAME_TAG, "Add new...", "#FFFFFF"), inventoryClickEvent -> {
            new TeamCreationGUI(player, arena, this).displayGUI();
        }), Slot.fromIndex(2));

        gui.addPane(whitebars);
        gui.addPane(nonPageButton);
        gui.addPane(pages);
        gui.addPane(pageButtonBack);
        gui.addPane(pageButtonNext);
        gui.addPane(body);

        gui.show(player);

    }

}
