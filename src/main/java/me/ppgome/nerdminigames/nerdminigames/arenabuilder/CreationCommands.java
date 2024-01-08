package me.ppgome.nerdminigames.nerdminigames.arenabuilder;

import dev.jorel.commandapi.CommandAPICommand;
import me.ppgome.nerdminigames.nerdminigames.GUI;
import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import me.ppgome.nerdminigames.nerdminigames.data.ArenasConfig;
import me.ppgome.nerdminigames.nerdminigames.data.Minigame;

public class CreationCommands {

    Minigame minigame;

    public static void nerdGames() {
        new CommandAPICommand("nerdmg")
                .withSubcommand(new CommandAPICommand("edit"))
                    .executes((sender, args) -> {
                        GUI.arenaSelector(sender.getName());
                    }).register();

        new CommandAPICommand("addteam")
                .executes(((sender, args) -> {
                    ArenasConfig config = new ArenasConfig(NerdMinigames.getPlugin(), "arenas.yml");
                    config.addTeam("HOE", "bum");
                })).register();
    }
}
