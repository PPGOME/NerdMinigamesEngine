//package me.ppgome.nerdminigames.nerdminigames.guis;
//
//import com.github.stefvanschie.inventoryframework.font.util.Font;
//import com.github.stefvanschie.inventoryframework.gui.GuiItem;
//import com.github.stefvanschie.inventoryframework.gui.type.AnvilGui;
//import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
//import com.github.stefvanschie.inventoryframework.gui.type.DropperGui;
//import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
//import com.github.stefvanschie.inventoryframework.pane.Pane;
//import com.github.stefvanschie.inventoryframework.pane.StaticPane;
//import com.github.stefvanschie.inventoryframework.pane.component.Label;
//import com.github.stefvanschie.inventoryframework.pane.util.Slot;
//import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
//import me.ppgome.nerdminigames.nerdminigames.ArenasConfig;
//import net.kyori.adventure.text.Component;
//import net.kyori.adventure.text.format.TextColor;
//import net.kyori.adventure.text.format.TextDecoration;
//import org.bukkit.Bukkit;
//import org.bukkit.Material;
//import org.bukkit.entity.Item;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.ItemMeta;
//import org.bukkit.inventory.meta.SkullMeta;
//import org.checkerframework.checker.units.qual.A;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import static me.ppgome.nerdminigames.nerdminigames.guis.DataInputGUI.dataInputGUI;
//
//public class GUI {
//
////    public static void arenaSelector(String player) {
////        ChestGui arenaselector = new ChestGui(5, "Arena Selector");
////
////        arenaselector.setOnGlobalClick(event -> event.setCancelled(true));
////
////        OutlinePane background = new OutlinePane(0, 0, 9, 5, Pane.Priority.LOWEST);
////        background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
////        background.setRepeat(true);
////
////        OutlinePane topbackground = new OutlinePane(0, 0, 9, 1, Pane.Priority.LOW);
////        topbackground.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
////        topbackground.setRepeat(true);
////
////        OutlinePane bottombackground = new OutlinePane(0, 4, 9, 1, Pane.Priority.LOW);
////        bottombackground.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
////        bottombackground.setRepeat(true);
////
////        ItemStack topicon = new ItemStack(Material.DIAMOND_SWORD);
////        ItemMeta topiconmeta = topicon.getItemMeta();
////        topiconmeta.displayName(Component.text("Pick an arena to edit below"));
////        topicon.setItemMeta(topiconmeta);
////        StaticPane topitem = new StaticPane(4, 0, 1, 1, Pane.Priority.HIGHEST);
////        topitem.addItem(new GuiItem(topicon), 0, 0);
////
////        ItemStack newarenabutton = new ItemStack(Material.LIME_WOOL);
////        ItemMeta newarenabuttonmeta = newarenabutton.getItemMeta();
////        newarenabuttonmeta.displayName(Component.text("New Arena").color(TextColor.fromHexString("#b5ff20")));
////        newarenabutton.setItemMeta(newarenabuttonmeta);
////        StaticPane newbutton = new StaticPane(4, 4, 1, 1, Pane.Priority.HIGHEST);
////        newbutton.addItem(new GuiItem(newarenabutton), 0, 0);
////        newbutton.setOnClick(event -> {
////            DataInputGUI newArenaGUI = new DataInputGUI(player, "Creating new arena...", this);
////            displayGUI(player, "Creating new arena...", null, "newarena");
////        });
////
////        OutlinePane body = new OutlinePane(0, 1, 9, 3);
////        List<String> arenas = new ArrayList<>();
////        ArenasConfig arenaconfig = new ArenasConfig(NerdMinigames.getPlugin(), "arenas.yml");
////        arenas = arenaconfig.getArenas();
////
////        body.setOnClick(inventoryClickEvent -> {
////            if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName() != "") {
////                arenaMenu(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName(), player);
////            }
////        });
////
////        for(String arena : arenas) {
////            ItemStack arenastack = new ItemStack(Material.REDSTONE);
////            ItemMeta arenastackmeta = arenastack.getItemMeta();
////            arenastackmeta.displayName(Component.text(arena));
////            arenastack.setItemMeta(arenastackmeta);
////            body.addItem(new GuiItem(arenastack));
////        }
////
////        arenaselector.addPane(background);
////        arenaselector.addPane(topbackground);
////        arenaselector.addPane(bottombackground);
////        arenaselector.addPane(topitem);
////        arenaselector.addPane(newbutton);
////        arenaselector.addPane(body);
////        arenaselector.show(Bukkit.getPlayer(player));
////    }
//
//    public static void arenaMenu(String arenaname, String player) {
//        ChestGui gui = new ChestGui(4, "Editing " + arenaname);
//
//        gui.setOnGlobalClick(event -> event.setCancelled(true));
//
//        OutlinePane background = new OutlinePane(0, 0, 9, 4, Pane.Priority.LOWEST);
//        background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
//        background.setRepeat(true);
//
//        OutlinePane topbackground = new OutlinePane(0, 0, 9, 1, Pane.Priority.LOW);
//        topbackground.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
//        topbackground.setRepeat(true);
//
//        OutlinePane bottombackground = new OutlinePane(0, 3, 9, 1, Pane.Priority.LOW);
//        bottombackground.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
//        bottombackground.setRepeat(true);
//
//        StaticPane buttons = new StaticPane(0, 1, 9, 2, Pane.Priority.NORMAL);
//        buttons.addItem(new GuiItem(createButton(Material.DIAMOND_PICKAXE, "Arena Options", "#ffffff"),
//                inventoryClickEvent -> {
//                    Bukkit.getPlayer(player).sendMessage(Component.text("AAAAAA"));
//                }), 4, 0);
//        buttons.addItem(new GuiItem(createButton(Material.PLAYER_HEAD, "Teams", "#ffffff"),
//                inventoryClickEvent -> {
//                    editMenu(player, arenaname, "Teams");
//                }), 1, 0);
//        buttons.addItem(new GuiItem(createButton(Material.CHEST, "Items", "#ffffff"),
//                inventoryClickEvent -> {
//                    editMenu(player, arenaname, "Items");
//                }), 2, 1);
//        buttons.addItem(new GuiItem(createButton(Material.EXPERIENCE_BOTTLE, "Objectives", "#ffffff"),
//                inventoryClickEvent -> {
//                    Bukkit.getPlayer(player).sendMessage(Component.text("DDDDDD"));
//                }), 6, 1);
//        buttons.addItem(new GuiItem(createButton(Material.RED_BED, "Spawns", "#ffffff"),
//                inventoryClickEvent -> {
//                    Bukkit.getPlayer(player).sendMessage(Component.text("EEEEEE"));
//                }), 7, 0);
//
//        StaticPane backbutton = new StaticPane(4, 3, 1, 1, Pane.Priority.NORMAL);
//        backbutton.addItem(new GuiItem(createButton(Material.ARROW, "Back", "#FFFFFF"),
//                inventoryClickEvent -> {
//                    arenaSelector(player);
//                }), 0, 0);
//
//        gui.addPane(background);
//        gui.addPane(topbackground);
//        gui.addPane(bottombackground);
//        gui.addPane(buttons);
//        gui.addPane(backbutton);
//
//        gui.show(Bukkit.getPlayer(player));
//    }
//
////    public static void editMenu(String player, String arena, String option) {
////
////        ChestGui gui = new ChestGui(5, option);
////
////        gui.setOnGlobalClick(event -> event.setCancelled(true));
////        List<String> storage = new ArrayList<>();
////
////        OutlinePane background = new OutlinePane(0, 0, 9, 5, Pane.Priority.LOWEST);
////        background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
////        background.setRepeat(true);
////
////        OutlinePane topbackground = new OutlinePane(0, 0, 9, 1, Pane.Priority.LOW);
////        topbackground.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
////        topbackground.setRepeat(true);
////
////        OutlinePane bottombackground = new OutlinePane(0, 4, 9, 1, Pane.Priority.LOW);
////        bottombackground.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
////        bottombackground.setRepeat(true);
////
////        gui.addPane(background);
////        gui.addPane(topbackground);
////        gui.addPane(bottombackground);
////
////        if(option.equalsIgnoreCase("teams")) {
////            OutlinePane teamlist = new OutlinePane(0, 1, 9, 3);
////            ArenasConfig arenaconfig = new ArenasConfig(NerdMinigames.getPlugin(), "arenas.yml");
////            storage = arenaconfig.getTeams(arena);
////            for(String team : storage) {
////                ItemStack item = createButton(Material.LEATHER_CHESTPLATE, team, "#FFFFFF");
////                teamlist.addItem(new GuiItem(item, inventoryClickEvent -> {
////                    teamConfirmation(arena, player, team);
////                }));
////            }
////
////            StaticPane options = new StaticPane(3, 4, 3, 1);
////
////            options.addItem(new GuiItem((createButton(Material.ARROW, "Back", "#FFFFFF")),
////                    inventoryClickEvent -> {
////                        arenaMenu(arena, player);
////                    }), 0, 0);
////            options.addItem(new GuiItem((createButton(Material.LIME_WOOL, "Add New", "#FFFFFF")),
////                    inventoryClickEvent -> {
////                        dataInputGUI(player, "Adding new team...", arena, "newteam");
////                    }), 2, 0);
////
////            gui.addPane(options);
////            gui.addPane(teamlist);
////        } else if(option.equalsIgnoreCase("items")) {
////            OutlinePane teamlist = new OutlinePane(0, 1, 9, 3);
////            ArenasConfig arenaconfig = new ArenasConfig(NerdMinigames.getPlugin(), "arenas.yml");
////            storage = arenaconfig.getTeams(arena);
////            for(String team : storage) {
////                ItemStack item = createButton(Material.LEATHER_CHESTPLATE, team, "#FFFFFF");
////                teamlist.addItem(new GuiItem(item, inventoryClickEvent -> {
////                    teamConfirmation(arena, player, team);
////                }));
////            }
////
////            StaticPane options = new StaticPane(3, 4, 3, 1);
////
////            options.addItem(new GuiItem((createButton(Material.ARROW, "Back", "#FFFFFF")),
////                    inventoryClickEvent -> {
////                        arenaMenu(arena, player);
////                    }), 0, 0);
////            options.addItem(new GuiItem((createButton(Material.LIME_WOOL, "Add New", "#FFFFFF")),
////                    inventoryClickEvent -> {
////                        new ItemInputGUI(player, arena, null, null);
////                    }), 2, 0);
////
////            gui.addPane(options);
////            gui.addPane(teamlist);
////        }
////
////        gui.show(Bukkit.getPlayer(player));
////    }
////
//    public static ItemStack createButton(Material material, String name, String color) {
//        ItemStack item = new ItemStack(material);
//        ItemMeta itemmeta = item.getItemMeta();
//        itemmeta.displayName(Component.text(name).color(TextColor.fromHexString(color)));
//        item.setItemMeta(itemmeta);
//        return item;
//    }
////
////    public static void teamConfirmation(String arena, String player, String team) {
////
////        DropperGui gui = new DropperGui("Confirm your changes?");
////
////        gui.setOnGlobalClick(event -> event.setCancelled(true));
////        ArenasConfig arenaconfig = new ArenasConfig(NerdMinigames.getPlugin(), "arenas.yml");
////
////        OutlinePane background = new OutlinePane(0, 0, 3, 3, Pane.Priority.LOWEST);
////        background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
////        background.setRepeat(true);
////
////        StaticPane buttons = new StaticPane(0, 1, 3, 1);
////
////        buttons.addItem(new GuiItem((createButton(Material.RED_WOOL, "No", "#ff5151")),
////                inventoryClickEvent -> {
////                    editMenu(player, arena, "Teams");
////                }), 0, 0);
////        buttons.addItem(new GuiItem((createButton(Material.LIME_WOOL, "Yes", "#b5ff20")),
////                inventoryClickEvent -> {
////                    arenaconfig.deleteTeam(arena, team);
////                    editMenu(player, arena, "Teams");
////                }), 2, 0);
////
////        gui.getContentsComponent().addPane(background);
////        gui.getContentsComponent().addPane(buttons);
////
////        gui.show(Bukkit.getPlayer(player));
////
////    }
//
//}