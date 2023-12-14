package me.ppgome.nerdminigames.nerdminigames.arenabuilder;

import dev.jorel.commandapi.CommandAPICommand;

public class CreationCommands {

    public static void nerdGames() {
        new CommandAPICommand("test")
                .executes(((sender, args) -> {
                    sender.sendMessage("Test!");
                }))
                .register();
    }

}
