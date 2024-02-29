package me.ppgome.nerdminigames.nerdminigames.arenabuilder;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.IntegerArgument;
import me.ppgome.nerdminigames.nerdminigames.CurrencyConfig;
import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import me.ppgome.nerdminigames.nerdminigames.ArenasConfig;
import me.ppgome.nerdminigames.nerdminigames.data.Minigame;
import me.ppgome.nerdminigames.nerdminigames.guis.ArenaListGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.isInteger;

public class CreationCommands {

    Minigame minigame;

    public static void nerdGames() {
        new CommandAPICommand("nerdmg")
                .withSubcommand(new CommandAPICommand("edit"))
                    .executes((sender, args) -> {
                        new ArenaListGUI(Bukkit.getPlayer(sender.getName()), null).displayGUI();
                    }).register();

        new CommandAPICommand("addcurrency")
                .withArguments(new IntegerArgument("rate"))
                .executes((commandSender, commandArguments) -> {
                    Player p = Bukkit.getPlayer(commandSender.getName());
                    ItemStack item = p.getInventory().getItemInMainHand();
                    if(!item.isSimilar(new ItemStack(Material.AIR))) {
                        int rate = (int) commandArguments.get("rate");
                        new CurrencyConfig(NerdMinigames.getPlugin()).addCurrency(item, rate);
                    }
                }).register();
    }
}
