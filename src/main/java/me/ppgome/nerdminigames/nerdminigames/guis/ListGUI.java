package me.ppgome.nerdminigames.nerdminigames.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import me.ppgome.nerdminigames.nerdminigames.ArenasConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListGUI implements NerdGUI {

    //TODO If GUI is null when back button pressed, close GUI completely

    // Possible titles are: arenas, teams, items, objectives, spawns, chests
    private Player player;
    private String title = "";
    private NerdGUI backgui;
    private String arena;
    private DataInputGUI datagui;
    private ItemInputGUI itemInputGUI;
    private List<String> entries = new ArrayList<>();

    public ListGUI(Player player, String title, NerdGUI backgui, String arena) {
        this.player = player;
        this.title = title;
        this.backgui = backgui;
        this.arena = arena;
    }

    public ListGUI(Player player, String title, NerdGUI backgui) {
        this.player = player;
        this.title = title;
        this.backgui = backgui;
    }

    @Override
    public void displayGUI() {

        ChestGui gui = new ChestGui(5, title.substring(0, 1).toUpperCase(Locale.ROOT) + title.substring(1));

        gui.setOnGlobalClick(e -> e.setCancelled(true));

        OutlinePane whitebars = new OutlinePane(0, 0, 9, 5, Pane.Priority.LOWEST);
        whitebars.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
        whitebars.setRepeat(true);

        OutlinePane itemslots = new OutlinePane(0, 1, 9, 3, Pane.Priority.LOW);
        itemslots.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        itemslots.setRepeat(true);


        OutlinePane body = new OutlinePane(0, 1, 9, 3, Pane.Priority.NORMAL);
        ArenasConfig arenaconfig = new ArenasConfig(NerdMinigames.getPlugin());

//        if(datagui != null) {
//            if(title.equalsIgnoreCase("arenas")) {
//                arenaconfig.newArena(datagui.getInput(), player.getName());
//            }
//            switch (title) {
//                case "arenas":
//                    arenaconfig.newArena(datagui.getInput(), player.getName());
//                case "teams":
//                    arenaconfig.addTeam(arena, datagui.getInput());
//                case "items":
//                    System.out.println("A");
//            }
//        }

        if(title.equalsIgnoreCase("arenas")) {
            entries = arenaconfig.getArenas();
        } else if(title.equalsIgnoreCase("teams")) {
            entries = arenaconfig.getTeams(arena);
        }

        switch (title) {
            case "arenas":
                entries = arenaconfig.getArenas();
            case "teams":
                entries = arenaconfig.getTeams(arena);
            case "items":
                System.out.println("A");
        }

        if(!title.equals("items")) {
            for(String object : entries) {
                body.addItem(new GuiItem(createButton(Material.REDSTONE, object, "#FFFFFF")));
            }
        } else {
            System.out.println("NEED TO WORK OUT THE ITEM SYSTEM");
        }

        body.setOnClick(inventoryClickEvent -> {
            if(!inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equals("")) {
                System.out.println("MHM!");
                new ArenaGUI(player, inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName(), this).displayGUI();
            }
        });

        StaticPane newbutton = new StaticPane(4, 4, 1, 1, Pane.Priority.HIGH);
        newbutton.addItem(new GuiItem(createButton(Material.LIME_WOOL, "Add new...", "#FFFFFF"), inventoryClickEvent -> {
            newbutton.addItem(new GuiItem(createButton(Material.LIME_WOOL, "Add new...", "#FFFFFF")), 0, 0);
            newbutton.setOnClick(e -> {
                switch (title) {
                    case "arenas":
                        datagui = new DataInputGUI(player, "Input new arena name", this);
                        datagui.displayGUI();
                    case "teams":
                        datagui = new DataInputGUI(player, "Input new team name", this);
                        datagui.displayGUI();
                    case "items":
                        System.out.println("A");
                }
            });
        }), 0, 0);

        gui.addPane(whitebars);
        gui.addPane(itemslots);
        gui.addPane(body);
        gui.addPane(newbutton);

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
