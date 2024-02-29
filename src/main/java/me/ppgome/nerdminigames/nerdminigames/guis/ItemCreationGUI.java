package me.ppgome.nerdminigames.nerdminigames.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.component.ToggleButton;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import io.papermc.paper.advancement.AdvancementDisplay;
import me.ppgome.nerdminigames.nerdminigames.ArenasConfig;
import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import me.ppgome.nerdminigames.nerdminigames.data.Arena;
import me.ppgome.nerdminigames.nerdminigames.data.Item;
import me.ppgome.nerdminigames.nerdminigames.data.Team;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.createButton;
import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.isInteger;
import static me.ppgome.nerdminigames.nerdminigames.messages.PlayerMessager.errorMessage;

public class ItemCreationGUI implements NerdGUI {

    private final Player player;
    private final Arena arena;
    private DataInputGUI teamInput;
    private Team team;
    private DataInputGUI chance;
    private boolean isCurrency = false;
    private final NerdGUI backgui;
    private ItemStack itemStack;
    private Item item;
    private ChestGui gui;
    private ConfirmationGUI deleteItem;

    public ItemCreationGUI(Player player, Arena arena, NerdGUI backgui) {
        this.player = player;
        this.arena = arena;
        this.backgui = backgui;
    }

    public ItemCreationGUI(Player player, Arena arena, Item item, NerdGUI backgui) {
        this.player = player;
        this.arena = arena;
        this.item = item;
        this.backgui = backgui;
    }

    @Override
    public void displayGUI() {

        if(item == null) {
            gui = new ChestGui(5, "Adding item to " + arena.getArenaName());
        } else {
            gui = new ChestGui(5, "Editing item from " + arena.getArenaName());
        }

        gui.setOnGlobalClick(e -> e.setCancelled(true));

        OutlinePane white = new OutlinePane(0, 0, 9, 5, Pane.Priority.LOWEST);
        white.addItem(new GuiItem(createButton(Material.WHITE_STAINED_GLASS_PANE, "", "#FFFFFF")));
        white.setRepeat(true);

        OutlinePane black = new OutlinePane(0, 1, 9, 3, Pane.Priority.LOW);
        black.addItem(new GuiItem(createButton(Material.BLACK_STAINED_GLASS_PANE, "", "#FFFFFF")));
        black.setRepeat(true);

        StaticPane buttons = new StaticPane(0, 1, 9, 3, Pane.Priority.NORMAL);

        int nudge = 0;
        if (item == null) nudge += 1;

        // Cancel
        buttons.addItem(new GuiItem(createButton(Material.RED_STAINED_GLASS_PANE, "Cancel", "#ff5151"), inventoryClickEvent -> {
            backgui.displayGUI();
        }), Slot.fromIndex(2));

        // Confirm
        buttons.addItem(new GuiItem(createButton(Material.LIME_STAINED_GLASS_PANE, "Confirm", "#b5ff20"), inventoryClickEvent -> {
            ArenasConfig arenasConfig = new ArenasConfig(NerdMinigames.getPlugin());
            if(item == null) {
                if(chance != null && isInteger(chance.getInput())) {
                    int chanceinput = Integer.parseInt(chance.getInput());
                    if(chanceinput >= 0 && chanceinput <= 100) {
                        if(itemStack != null) {
                            if(teamInput != null) {
                                String teamInputString = teamInput.getInput();
                                if(teamInputString.equalsIgnoreCase("")) teamInputString = null;
                                for(Team team : arena.getTeams()) {
                                    if(team.getTeamName().equalsIgnoreCase(teamInputString) || teamInputString == null) {
                                        arena.addItem(new Item(itemStack, arena.getTeamByName(teamInputString), chanceinput, isCurrency));
                                        arenasConfig.editArena(arena);
                                        backgui.displayGUI();
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                for(Item itemtocheck : arena.getItems()) {
                    if(itemtocheck.equals(item)) {
                        if(itemStack != null) {
                            itemtocheck.setItem(itemStack);
                        }
                        if(teamInput != null) {
                            for(Team team : arena.getTeams()) {
                                if(team.getTeamName().equalsIgnoreCase(teamInput.getInput())) {
                                    itemtocheck.setTeam(arena.getTeamByName(teamInput.getInput()));
                                }
                            }
                        }
                        if(chance != null) {
                            if(isInteger(chance.getInput())) {
                                itemtocheck.setChance(Integer.parseInt(chance.getInput()));
                            }
                        }
                        itemtocheck.setCurrency(isCurrency);
                        arenasConfig.editArena(arena);
                        backgui.displayGUI();
                    }
                }

            }
        }), Slot.fromIndex(6));

        // Team
        String label;
        if (teamInput != null) {
            label = "Assigned team: " + teamInput.getInput();
        } else if (item != null) {
            label = "Assigned team: " + item.getTeam().getTeamName();
        } else {
            label = "Click to assign to a team (if applicable)";
        }
        buttons.addItem(new GuiItem(createButton(Material.LEATHER_CHESTPLATE, label, "#b5ff20"), inventoryClickEvent -> {
            teamInput = new DataInputGUI(player, "Assigning team...", this);
            teamInput.displayGUI();
        }), Slot.fromIndex(19 + nudge));

        // Chance
        if (chance != null) {
            label = "Chance: " + chance.getInput() + "%";
        } else if (item != null) {
            label = "Chance: " + item.getChance() + "%";
        } else {
            label = "Click to set item's chance";
        }
        buttons.addItem(new GuiItem(createButton(Material.NAME_TAG, label, "#b5ff20"), inventoryClickEvent -> {
            chance = new DataInputGUI(player, "Setting item chance...", this);
            chance.displayGUI();
        }), Slot.fromIndex(21 + nudge));

        // Currency
        if(item != null) {
            isCurrency = item.isCurrency();
        }
        boolean othercurrency = false;
        for(Item itemtocheck : arena.getItems()) {
            if(itemtocheck.isCurrency()) {
                if(itemtocheck.equals(item)) {
                    break;
                }
                othercurrency = true;
                break;
            }
        }

        if(othercurrency) {
            buttons.addItem(new GuiItem(createButton(Material.RED_STAINED_GLASS_PANE, "Arena already has a currency!",
                    "#ff5151")), Slot.fromIndex(23 + nudge));
        } else {
            GuiItem currencybutton = new GuiItem(createButton(Material.RED_STAINED_GLASS_PANE, "Is arena's currency: false", "#ff5151"), NerdMinigames.getPlugin());

            currencybutton.setAction(inventoryClickEvent -> {
                if (!checkIfCurrency(item)) {
                    if (isCurrency) {
                        isCurrency = false;
                        currencybutton.setItem(createButton(Material.RED_STAINED_GLASS_PANE, "Is arena's currency: false", "#ff5151"));
                        gui.update();
                    } else {
                        isCurrency = true;
                        currencybutton.setItem(createButton(Material.LIME_STAINED_GLASS_PANE, "Is arena's currency: true", "#b5ff20"));
                        gui.update();
                    }
                }
            });
            if(item != null) {
                if(item.isCurrency()) {
                    currencybutton.setItem(createButton(Material.LIME_STAINED_GLASS_PANE, "Is arena's currency: true", "#b5ff20"));
                    isCurrency = true;
                } else {
                    currencybutton.setItem(createButton(Material.RED_STAINED_GLASS_PANE, "Is arena's currency: false", "#ff5151"));
                }
            }
            buttons.addItem(currencybutton, Slot.fromIndex(23 + nudge));
        }

        // TODO this.
//        ToggleButton currency = new ToggleButton(0, 3, 1, 1);
//        currency.setEnabledItem(new GuiItem(createButton(Material.LIME_STAINED_GLASS_PANE, "Is arena's currency: true", "#b5ff20")));
//        currency.setDisabledItem(new GuiItem(createButton(Material.RED_STAINED_GLASS_PANE, "Is arena's currency: false", "#b5ff20")));

//        if(checkIfCurrency()) {
//            if(!currency.isEnabled()) {
//                currency.toggle();
//            }
//        } else if(currency.isEnabled()) {
//            currency.toggle();
//        }
//
//        currency.setOnClick(e -> {
//            if(checkIfCurrency()) {
//                currency.allowToggle(false);
//            } else {
//                currency.toggle();
//            }
//        });

//        buttons.addItem(new GuiItem(createButton(Material.RED_STAINED_GLASS_PANE, "Is arena's currency: " + isCurrency, "#b5ff20")
//                , inventoryClickEvent -> {
//            if(!checkIfCurrency()) {
//                isCurrency = !isCurrency;
//            }
//        }), Slot.fromIndex(23 + nudge));
//
//        // Delete
        if (item != null) {
            buttons.addItem(new GuiItem(createButton(Material.BARRIER, "Delete Item", "#ff5151"), inventoryClickEvent -> {

                String itemname = PlainTextComponentSerializer.plainText().serialize(item.getItem().displayName());
                if(itemname.substring(0, 1).equalsIgnoreCase("[") && itemname.substring(itemname.length() - 1).equalsIgnoreCase("]")) {
                    itemname = itemname.substring(1, itemname.length() - 1);
                }

                deleteItem = new ConfirmationGUI(player, "Delete item " + itemname + "?", this);
                deleteItem.displayGUI();
            }), Slot.fromIndex(25));
        }

        // Item input logic
        if(item != null) {
            buttons.addItem(new GuiItem(item.getItem()), Slot.fromIndex(4));
        }
        if(itemStack != null) {
            buttons.addItem(new GuiItem(itemStack), Slot.fromIndex(4));
        }
        StaticPane inventory = new StaticPane(0, 5, 9, 4, Pane.Priority.HIGHEST);
        inventory.setOnClick(e -> {
            itemStack = e.getCurrentItem();
            if (itemStack != null) {
                buttons.addItem(new GuiItem(itemStack), Slot.fromIndex(4));
                gui.update();
            }
        });

        gui.addPane(white);
        gui.addPane(black);
        gui.addPane(buttons);
        gui.addPane(inventory);

        gui.show(player);

        if(deleteItem != null && deleteItem.getInput()) {
            arena.deleteItem(item);
            new ArenasConfig(NerdMinigames.getPlugin()).editArena(arena);
            backgui.displayGUI();
        }

    }

    private boolean checkIfCurrency(Item itemtocheck) {
        for(Item item : arena.getItems()) {
            if(item.isCurrency()) {
                    if(!item.isCurrency()) {
                        if(!item.equals(itemtocheck)) {
                            errorMessage(player.getName(), "This arena already has an item assigned as its currency. Toggle the other item first!");
                            return true;
                        }
                    }
            }
        }
        return false;
    }
}
