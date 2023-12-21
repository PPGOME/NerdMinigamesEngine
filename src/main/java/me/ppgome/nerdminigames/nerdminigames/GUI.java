package me.ppgome.nerdminigames.nerdminigames;

import com.github.stefvanschie.inventoryframework.font.util.Font;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.AnvilGui;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.component.Label;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import me.ppgome.nerdminigames.nerdminigames.data.ArenasConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class GUI {

    public static void arenaSelector(String player) {
        ChestGui arenaselector = new ChestGui(5, "Arena Selector");

        arenaselector.setOnGlobalClick(event -> event.setCancelled(true));

        OutlinePane background = new OutlinePane(0, 0, 9, 5, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        background.setRepeat(true);

        OutlinePane topbackground = new OutlinePane(0, 0, 9, 1, Pane.Priority.LOW);
        topbackground.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
        topbackground.setRepeat(true);

        OutlinePane bottombackground = new OutlinePane(0, 4, 9, 1, Pane.Priority.LOW);
        bottombackground.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
        bottombackground.setRepeat(true);

        ItemStack topicon = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta topiconmeta = topicon.getItemMeta();
        topiconmeta.displayName(Component.text("Pick an arena to edit below"));
        topicon.setItemMeta(topiconmeta);
        StaticPane topitem = new StaticPane(4, 0, 1, 1, Pane.Priority.HIGHEST);
        topitem.addItem(new GuiItem(topicon), 0, 0);

        ItemStack newarenabutton = new ItemStack(Material.LIME_WOOL);
        ItemMeta newarenabuttonmeta = newarenabutton.getItemMeta();
        newarenabuttonmeta.displayName(Component.text("New Arena").color(TextColor.fromHexString("#b5ff20")));
        newarenabutton.setItemMeta(newarenabuttonmeta);
        StaticPane newbutton = new StaticPane(4, 4, 1, 1, Pane.Priority.HIGHEST);
        newbutton.addItem(new GuiItem(newarenabutton), 0, 0);
        newbutton.setOnClick(event -> {
            dataInputGUI(player, "Creating new arena...", "newArena");
        });

        OutlinePane body = new OutlinePane(0, 1, 9, 3);
        List<String> arenas = new ArrayList<>();
        ArenasConfig config = new ArenasConfig(NerdMinigames.getPlugin(), "arenas.yml");
        arenas = config.getArenas();

        body.setOnClick(inventoryClickEvent -> {
            if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName() != "") {
                arenaMenu(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName(), player);
            }
        });

        for(String arena : arenas) {
            ItemStack arenastack = new ItemStack(Material.REDSTONE);
            ItemMeta arenastackmeta = arenastack.getItemMeta();
            arenastackmeta.displayName(Component.text(arena));
            arenastack.setItemMeta(arenastackmeta);
            body.addItem(new GuiItem(arenastack));
        }

        arenaselector.addPane(background);
        arenaselector.addPane(topbackground);
        arenaselector.addPane(bottombackground);
        arenaselector.addPane(topitem);
        arenaselector.addPane(newbutton);
        arenaselector.addPane(body);
        arenaselector.show(Bukkit.getPlayer(player));
    }

    public static void arenaMenu(String arenaname, String player) {
        ChestGui gui = new ChestGui(4, "Editing " + arenaname);

        gui.setOnGlobalClick(event -> event.setCancelled(true));

        OutlinePane background = new OutlinePane(0, 0, 9, 4, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        background.setRepeat(true);

        OutlinePane topbackground = new OutlinePane(0, 0, 9, 1, Pane.Priority.LOW);
        topbackground.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
        topbackground.setRepeat(true);

        OutlinePane bottombackground = new OutlinePane(0, 3, 9, 1, Pane.Priority.LOW);
        bottombackground.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
        bottombackground.setRepeat(true);

        StaticPane buttons = new StaticPane(0, 1, 9, 2, Pane.Priority.NORMAL);
        buttons.addItem(new GuiItem(createButton(Material.DIAMOND_PICKAXE, "Arena Options", "#ffffff"),
                inventoryClickEvent -> {
                    Bukkit.getPlayer(player).sendMessage(Component.text("AAAAAA"));
                }), 4, 0);
        buttons.addItem(new GuiItem(createButton(Material.PLAYER_HEAD, "Teams", "#ffffff"),
                inventoryClickEvent -> {
                    Bukkit.getPlayer(player).sendMessage(Component.text("BBBBBB"));
                }), 1, 0);
        buttons.addItem(new GuiItem(createButton(Material.CHEST, "Items", "#ffffff"),
                inventoryClickEvent -> {
                    Bukkit.getPlayer(player).sendMessage(Component.text("CCCCCC"));
                }), 2, 1);
        buttons.addItem(new GuiItem(createButton(Material.EXPERIENCE_BOTTLE, "Objectives", "#ffffff"),
                inventoryClickEvent -> {
                    Bukkit.getPlayer(player).sendMessage(Component.text("DDDDDD"));
                }), 6, 1);
        buttons.addItem(new GuiItem(createButton(Material.RED_BED, "Spawns", "#ffffff"),
                inventoryClickEvent -> {
                    Bukkit.getPlayer(player).sendMessage(Component.text("EEEEEE"));
                }), 7, 0);

        StaticPane backbutton = new StaticPane(4, 3, 1, 1, Pane.Priority.NORMAL);
        backbutton.addItem(new GuiItem(createButton(Material.ARROW, "Back", "#FFFFFF"),
                inventoryClickEvent -> {
                    arenaSelector(player);
                }), 0, 0);

        gui.addPane(background);
        gui.addPane(topbackground);
        gui.addPane(bottombackground);
        gui.addPane(buttons);
        gui.addPane(backbutton);

        gui.show(Bukkit.getPlayer(player));
    }

    public static void dataInputGUI(String player, String guiname, String action) {
        // Valid actions: newArena, newTeam, newSpawn, newObjective
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
            arenaSelector(player);
        });

        lastslot.setOnClick(inventoryClickEvent -> {
            if(action.equalsIgnoreCase("newarena")) {
                ArenasConfig arenaconfig = new ArenasConfig(NerdMinigames.getPlugin(), "arenas.yml");
                arenaconfig.newArena(gui.getRenameText(), player);
            }
            arenaSelector(player);
        });

        gui.getFirstItemComponent().addPane(renameitemslot);
        gui.getSecondItemComponent().addPane(firstslot);
        gui.getResultComponent().addPane(lastslot);

        gui.show(Bukkit.getPlayer(player));

    }

    public static ItemStack createButton(Material material, String name, String color) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.displayName(Component.text(name).color(TextColor.fromHexString(color)));
        item.setItemMeta(itemmeta);
        return item;
    }

}
