package me.ppgome.nerdminigames.nerdminigames.messages;

import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class PlayerMessager {
    private static ConfigurationSection config = NerdMinigames.getPlugin().getConfig();

    public static void errorMessage(String player, String errortype) {
        Player p = Bukkit.getPlayer(player);
        if(p != null && p.isOnline() && config.getString("messages.error.isempty") != null &&
                config.getString("messages.error.color.bracket") != null &&
                config.getString("messages.prefix.color") != null && config.getString("messages.prefix.text")
                != null) {
            if(errortype.equalsIgnoreCase("isempty")) {
                p.sendMessage(Component.text().content("[")
                                .color(TextColor.fromHexString(config.getString("messages.error.color.bracket")))
                        .append(Component.text(config.getString("messages.prefix.text"))
                                .color(TextColor.fromHexString(config.getString("messages.prefix.color")))
                        .append(Component.text("] "))
                                .color(TextColor.fromHexString(config.getString("messages.error.color.bracket")))
                        .append(Component.text(config.getString("messages.error.isempty"))
                                .color(TextColor.fromHexString(config.getString("messages.error.color.message"))))));
            }
        }
    }
}
