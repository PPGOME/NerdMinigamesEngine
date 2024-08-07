package me.ppgome.nerdminigames.nerdminigames.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.*;
import me.ppgome.nerdminigames.nerdminigames.CurrencyConfig;
import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import me.ppgome.nerdminigames.nerdminigames.ArenasConfig;
import me.ppgome.nerdminigames.nerdminigames.data.Minigame;
import me.ppgome.nerdminigames.nerdminigames.guis.ArenaListGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
                        new CurrencyConfig(NerdMinigames.PLUGIN).addCurrency(item, rate);
                    }
                }).register();
        new CommandAPICommand("game")
                .withSubcommand(new CommandAPICommand("load")
                        .withArguments(new TextArgument("Arena"))
                        .executes((sender, args) -> {
                            ArenasConfig arenasConfig = new ArenasConfig(NerdMinigames.PLUGIN);
                            for(String arena : arenasConfig.getArenas()) {
                                if(arena.equalsIgnoreCase((String) args.get("Arena"))) {
                                    Minigame minigame = new Minigame(arenasConfig.getArenaByName(arena));
                                    NerdMinigames.addActiveGame(minigame);
                                    minigame.queue();
                                }
                            }
                        })
                )
                .withSubcommand(new CommandAPICommand("start")
                        .withArguments(new IntegerArgument("Game ID"))
                        .executes((sender, args) -> {
                            NerdMinigames.getActiveGame((Integer) args.get("Game ID")).queue();
                            NerdMinigames.getActiveGame((Integer) args.get("Game ID")).addPlayer(Bukkit.getPlayer(sender.getName()));
                        })
                )
                .withSubcommand(new CommandAPICommand("end")
                        .withArguments(new IntegerArgument("Game ID"))
                        .executes((sender, args) -> {

                        })
                )
                .withSubcommand(new CommandAPICommand("addplayer")
                        .withArguments(new IntegerArgument("Game ID"))
                        .withArguments(new PlayerArgument("Player"))
                        .executes(((sender, args) -> {
                            NerdMinigames.getActiveGame((Integer) args.get("Game ID")).addPlayer((Player) args.get("Player"));
                        }))
        ).register();
    }
}
