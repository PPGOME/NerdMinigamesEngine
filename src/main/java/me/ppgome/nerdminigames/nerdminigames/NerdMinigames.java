package me.ppgome.nerdminigames.nerdminigames;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import me.ppgome.nerdminigames.nerdminigames.arenabuilder.CreationCommands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
public final class NerdMinigames extends JavaPlugin {
    private static NerdMinigames instance;

    public static NerdMinigames getPlugin() {
        return instance;
    }

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(true));
        CreationCommands.nerdGames();

    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;

        saveDefaultConfig();

        // Check if arenas file exists. If no create it.
        File arenaconfigfile = new File(getDataFolder(), "arenas.yml");

        if(!arenaconfigfile.exists()) {
            arenaconfigfile.getParentFile().mkdirs();
            saveResource("arenas.yml", false);
        }

        ArenasConfig arenaconfig = new ArenasConfig(instance);

        System.out.println(arenaconfig.getArena("A").getBoundaries());

//        ItemStack test = new ItemStack(Material.STRING);
//        ItemMeta testmeta = test.getItemMeta();
//        testmeta.displayName(Component.text("Strong"));
//        testmeta.addEnchant(Enchantment.DURABILITY, 3, false);
//        test.setItemMeta(testmeta);
//
//        arenaconfig.getConfig().set("item", test);
//        arenaconfig.save();
//        System.out.println(arenaconfig.getConfig().getItemStack("item"));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
