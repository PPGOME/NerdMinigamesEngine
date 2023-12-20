package me.ppgome.nerdminigames.nerdminigames.arenabuilder;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.StringArgument;
import me.ppgome.nerdminigames.nerdminigames.GUI;
import me.ppgome.nerdminigames.nerdminigames.data.Minigame;

public class CreationCommands {

    Minigame minigame;

    public static void nerdGames() {
        new CommandAPICommand("nerdmg")
                .withSubcommand(new CommandAPICommand("edit"))
                    .executes((sender, args) -> {
                        GUI.arenaSelector(sender.getName());
                    }).register();
    }
}
