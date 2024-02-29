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
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

import static me.ppgome.nerdminigames.nerdminigames.Utils.removeBrackets;
import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.createButton;
import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.isInteger;

public class BankCurrencyListGUI implements NerdGUI {

    private Player player;
    private NerdGUI backgui;
    private ChestGui gui;

    private List<Item> duplist;
    private List<ItemStack> itemlist;
    private DataInputGUI rateinput;
    private Arena arena;
    private ExternalCurrency externalCurrency;

    private final NamespacedKey arenaName = new NamespacedKey(NerdMinigames.getPlugin(), "arena");

    public BankCurrencyListGUI(Player player) {
        this.player = player;
    }

    public BankCurrencyListGUI(Player player, NerdGUI backgui) {
        this.player = player;
        this.backgui = backgui;
    }

    @Override
    public void displayGUI() {

        ArenasConfig arenaconfig = new ArenasConfig(NerdMinigames.getPlugin());
        CurrencyConfig currencyConfig = new CurrencyConfig(NerdMinigames.getPlugin());

        if (rateinput != null) {
            if (isInteger(rateinput.getInput()) && !rateinput.getInput().equalsIgnoreCase("")) {
                if (arena != null) {
                    arena.setCurrencyrate(Integer.parseInt(rateinput.getInput()));
                    for(Item item : arena.getItems()) {
                        ItemMeta removeTagMeta = item.getItem().getItemMeta();
                        if(removeTagMeta.getPersistentDataContainer().has(arenaName)) {
                            removeTagMeta.getPersistentDataContainer().remove(arenaName);
                            item.getItem().setItemMeta(removeTagMeta);
                        }
                    }
                    arenaconfig.editArena(arena);
                    arena = null;
                } else if (externalCurrency != null) {
                    currencyConfig.addCurrency(externalCurrency.getItem(), Integer.parseInt(rateinput.getInput()));
                }
            }
        }

        gui = new ChestGui(5, "Select a currency to modify");

        gui.setOnGlobalClick(e -> e.setCancelled(true));

        OutlinePane whitebars = new OutlinePane(0, 0, 9, 5, Pane.Priority.LOWEST);
        whitebars.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
        whitebars.setRepeat(true);

        PaginatedPane pages = new PaginatedPane(0, 1, 9, 3, Pane.Priority.HIGH);
        itemlist = new ArrayList<>();
        pages.clear();

        OutlinePane body = new OutlinePane(0, 1, 9, 3, Pane.Priority.NORMAL);
        body.addItem(new GuiItem(createButton(Material.BLACK_STAINED_GLASS_PANE, "", "#FFFFFF")));
        body.setRepeat(true);

        // Add engine currency
        for(String arenastr : arenaconfig.getArenas()) {
            for(Item checkcurrency : arenaconfig.getArenaByName(arenastr).getItems()) {
                if(checkcurrency.isCurrency()) {

                    ItemMeta currencyMeta = checkcurrency.getItem().getItemMeta();

                    currencyMeta.getPersistentDataContainer().set(arenaName, PersistentDataType.STRING, arenastr);
                    checkcurrency.getItem().setItemMeta(currencyMeta);
                    if(!itemlist.contains(checkcurrency.getItem())) {
                        itemlist.add(checkcurrency.getItem());
                    }
                }
            }
        }

        for(ExternalCurrency currency : currencyConfig.getCurrencies()) {
            ItemMeta currencyMeta = currency.getItem().getItemMeta();
            currencyMeta.getPersistentDataContainer().set(arenaName, PersistentDataType.STRING, "external");
            currency.getItem().setItemMeta(currencyMeta);
            itemlist.add(currency.getItem());
        }

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
            ItemStack clickeditem = clicc.getCurrentItem();
            String itemname = PlainTextComponentSerializer.plainText().serialize(clickeditem.displayName());
            if(!clickeditem.isSimilar(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)) && !itemname.equalsIgnoreCase("")) {
                String tag = clickeditem.getItemMeta().getPersistentDataContainer().get(arenaName, PersistentDataType.STRING);
                if(tag != null) {
                    if(!tag.equalsIgnoreCase("external")) {
                        arena = arenaconfig.getArenaByName(tag);
                        rateinput = new DataInputGUI(player, "Current rate: " + arena.getCurrencyrate(), this);
                        rateinput.displayGUI();
                    } else {
                        externalCurrency = currencyConfig.getCurrencyByName(removeBrackets(clicc.getCurrentItem().displayName()));
                        rateinput = new DataInputGUI(player, "Current rate: " + externalCurrency.getRate(), this);
                        rateinput.displayGUI();;
                    }
                }
            }
        });

        gui.addPane(whitebars);
        gui.addPane(body);
        gui.addPane(pages);
        gui.addPane(buttons);

        gui.show(player);
    }
}
