package me.ppgome.nerdminigames.nerdminigames.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import me.ppgome.nerdminigames.nerdminigames.ArenasConfig;
import me.ppgome.nerdminigames.nerdminigames.CurrencyConfig;
import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import me.ppgome.nerdminigames.nerdminigames.data.Arena;
import me.ppgome.nerdminigames.nerdminigames.data.ExternalCurrency;
import me.ppgome.nerdminigames.nerdminigames.data.Item;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static me.ppgome.nerdminigames.nerdminigames.Utils.removeBrackets;
import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.createButton;

public class BankCurrencyListGUI implements NerdGUI {

    Player player;
    NerdGUI backgui;
    ChestGui gui;

    List<ItemStack> itemlist;
    DataInputGUI rateinput;
    ExternalCurrency externalCurrency;

    public BankCurrencyListGUI(Player player) {
        this.player = player;
    }

    public BankCurrencyListGUI(Player player, NerdGUI backgui) {
        this.player = player;
        this.backgui = backgui;
    }

    @Override
    public void displayGUI() {
        gui = new ChestGui(5, "Select a currency to modify");

        gui.setOnGlobalClick(e -> e.setCancelled(true));
        ArenasConfig arenaconfig = new ArenasConfig(NerdMinigames.getPlugin());
        CurrencyConfig currencyConfig = new CurrencyConfig(NerdMinigames.getPlugin());

        OutlinePane whitebars = new OutlinePane(0, 0, 9, 5, Pane.Priority.LOWEST);
        whitebars.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
        whitebars.setRepeat(true);

        PaginatedPane pages = new PaginatedPane(0, 1, 9, 3, Pane.Priority.HIGH);
        itemlist = new ArrayList<>();
        pages.clear();

        OutlinePane body = new OutlinePane(0, 1, 9, 3, Pane.Priority.NORMAL);
        body.addItem(new GuiItem(createButton(Material.BLACK_STAINED_GLASS_PANE, "", "#FFFFFF")));
        body.setRepeat(true);

        for(String checkarenastr : arenaconfig.getArenas()) {
            for(Item checkcurrency : arenaconfig.getArenaByName(checkarenastr).getItems()) {
                if(checkcurrency.isCurrency()) {
                    System.out.println(checkcurrency.getTeam() + "i");
                    itemlist.add(checkcurrency.getItem());
                }
            }
        }

        for(ExternalCurrency externalCurrency : currencyConfig.getCurrencies()) {
            System.out.println("a");
            itemlist.add(externalCurrency.getItem());
        }

        System.out.println(itemlist);

        pages.populateWithItemStacks(itemlist);

        StaticPane buttons = new StaticPane(0, 0, 9, 5, Pane.Priority.HIGH);
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

        pages.setOnClick(clicc -> {
            String itemname = PlainTextComponentSerializer.plainText().serialize(clicc.getCurrentItem().displayName());
            if(!clicc.getCurrentItem().isSimilar(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)) &&
                    !itemname.equalsIgnoreCase("")) {
                for(ExternalCurrency currency : currencyConfig.getCurrencies()) {
                    System.out.println(removeBrackets(currency.getItem().displayName()));
                    System.out.println(itemname);
                    if(removeBrackets(currency.getItem().displayName()).equalsIgnoreCase(itemname)) {
                        rateinput = new DataInputGUI(player, "Change " + itemname + "'s exchange rate", backgui);
                        rateinput.displayGUI();
                    }
                }
                for(String arena : arenaconfig.getArenas()) {
                    for(Item item : arenaconfig.getArenaByName(arena).getItems()) {
                        if(item.isCurrency() && removeBrackets(item.getItem().displayName()).equalsIgnoreCase(itemname)) {
                            rateinput = new DataInputGUI(player, "Change " + itemname + "'s exchange rate", backgui);
                            rateinput.displayGUI();
                        }
                    }
                }
//                player.getInventory().addItem(currencyConfig.getCurrencyByName(itemname).getItem());
                // TODO :)
            }
        });

        gui.addPane(whitebars);
        gui.addPane(body);
        gui.addPane(pages);
        gui.addPane(buttons);

        gui.show(player);
    }
}
