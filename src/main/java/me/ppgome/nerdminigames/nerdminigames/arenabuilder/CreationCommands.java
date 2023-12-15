package me.ppgome.nerdminigames.nerdminigames.arenabuilder;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.StringArgument;
import me.ppgome.nerdminigames.nerdminigames.data.Minigame;
import me.ppgome.nerdminigames.nerdminigames.db.DAO;
import me.ppgome.nerdminigames.nerdminigames.db.MinigameDAO;
import me.ppgome.nerdminigames.nerdminigames.db.MinigameDAOImpl;

import java.sql.SQLException;

public class CreationCommands {

    Minigame minigame;

    public static void nerdGames() {
        new CommandAPICommand("nerdmg")
                .withSubcommand(new CommandAPICommand("arena"))
                    .withSubcommand(new CommandAPICommand("create"))
                        .withArguments(new StringArgument("arenaname"))
                        .executes((sender, args) -> {
                            sender.sendMessage("Test");
                        });
    }

}
