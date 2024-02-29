package me.ppgome.nerdminigames.nerdminigames.NPCs;

import me.ppgome.nerdminigames.nerdminigames.ArenasConfig;
import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import me.ppgome.nerdminigames.nerdminigames.data.Arena;
import me.ppgome.nerdminigames.nerdminigames.data.Item;
import me.ppgome.nerdminigames.nerdminigames.guis.BankCurrencyListGUI;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static me.ppgome.nerdminigames.nerdminigames.Utils.removeBrackets;

public class Bank implements Listener {

    @EventHandler
    public void onTellerLeftClick(NPCLeftClickEvent e) {
        Player p = e.getClicker();
        List<Arena> arenas = new ArrayList<>();
        ArenasConfig config = new ArenasConfig(NerdMinigames.getPlugin());
        ItemStack handitem = p.getInventory().getItemInMainHand();

        for(String strarena : config.getArenas()) {
            arenas.add(config.getArenaByName(strarena));
        }

        for(Arena arena : arenas) {
            for(Item item : arena.getItems()) {
                if(item.isCurrency()) {
                    p.getInventory().addItem(item.getItem());
                    if(item.getItem().isSimilar(handitem)) {

                        System.out.println(handitem);
                        System.out.println(item.getItem());

                        int rate = (int) Math.floor(handitem.getAmount() / arena.getCurrencyrate());

                        if(rate > 0) {
                            p.sendMessage(Component.text("[")
                                    .color(TextColor.fromHexString("#5555ff"))
                                    .append(Component.text("Teller")
                                            .color(TextColor.fromHexString("#ff9d3b"))
                                            .append(Component.text("]")
                                                    .color(TextColor.fromHexString("#5555ff"))
                                                    .append(Component.text(" You exchanged " + rate * arena.getCurrencyrate() + " "
                                                            + removeBrackets(handitem.displayName())
                                                            + " for " + rate + " CCoins!").color(TextColor.fromHexString("#feb137"))))));
                            p.getInventory().getItemInMainHand().setAmount(handitem.getAmount() - (rate * arena.getCurrencyrate()));

                            //REMOVE
                            ItemStack ccoin = new ItemStack(Material.SUNFLOWER);
                            ItemMeta ccoinmeta = ccoin.getItemMeta();
                            ccoinmeta.displayName(Component.text("CCoin")
                                    .color(TextColor.fromHexString("#ebd210"))
                                    .decorate(TextDecoration.BOLD));
                            ccoinmeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
                            ccoin.setItemMeta(ccoinmeta);

                            ccoin.setAmount(rate);
                            //REMOVE

                            p.getInventory().addItem(ccoin);
                        }
                    }
                }
            }
        }

    }

    @EventHandler
    public void onTellerRightClick(NPCRightClickEvent e) {
        Player p = e.getClicker();
        if(p.isSneaking()) {
            if(p.hasPermission("nerdmg.admin")) {
                new BankCurrencyListGUI(p.getPlayer()).displayGUI();
            }
        }
    }

}
