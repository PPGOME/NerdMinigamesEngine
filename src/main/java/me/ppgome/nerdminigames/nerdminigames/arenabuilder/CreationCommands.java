package me.ppgome.nerdminigames.nerdminigames.arenabuilder;

import dev.jorel.commandapi.CommandAPICommand;
import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import me.ppgome.nerdminigames.nerdminigames.ArenasConfig;
import me.ppgome.nerdminigames.nerdminigames.data.Minigame;
import me.ppgome.nerdminigames.nerdminigames.guis.ArenaListGUI;
import org.bukkit.Bukkit;

public class CreationCommands {

    Minigame minigame;

    public static void nerdGames() {
        new CommandAPICommand("nerdmg")
                .withSubcommand(new CommandAPICommand("edit"))
                    .executes((sender, args) -> {
                        new ArenaListGUI(Bukkit.getPlayer(sender.getName()), null).displayGUI();
                    }).register();

        new CommandAPICommand("addteam")
                .executes(((sender, args) -> {
                    ArenasConfig config = new ArenasConfig(NerdMinigames.getPlugin());
                    config.addTeam("HOE", "bum");
                })).register();
    }
}
