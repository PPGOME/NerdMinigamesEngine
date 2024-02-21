package me.ppgome.nerdminigames.nerdminigames;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import me.ppgome.nerdminigames.nerdminigames.NPCs.Bank;
import me.ppgome.nerdminigames.nerdminigames.arenabuilder.CreationCommands;
import me.ppgome.nerdminigames.nerdminigames.data.Arena;
import me.ppgome.nerdminigames.nerdminigames.data.Item;
import me.ppgome.nerdminigames.nerdminigames.data.Team;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

        System.out.println("AHAHAHAHAH");

        instance = this;

        saveDefaultConfig();

        // Check if arenas file exists. If no create it.
        File arenaconfigfile = new File(getDataFolder(), "arenas.yml");

        getServer().getPluginManager().registerEvents(new Bank(), this);

        if(!arenaconfigfile.exists()) {
            arenaconfigfile.getParentFile().mkdirs();
            saveResource("arenas.yml", false);
        }

        ArenasConfig arenaconfig = new ArenasConfig(instance);

        ItemStack test = new ItemStack(Material.STRING);
        ItemMeta testmeta = test.getItemMeta();
        testmeta.displayName(Component.text("Strong"));
        testmeta.addEnchant(Enchantment.DURABILITY, 3, false);
        test.setItemMeta(testmeta);

        Arena arena = arenaconfig.getArena("TestArena");

        ItemStack item = new ItemStack(Material.LIME_WOOL);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addEnchant(Enchantment.ARROW_INFINITE, 10, true);
        itemMeta.displayName(Component.text("IamANitem"));
        item.setItemMeta(itemMeta);

        System.out.println(PlainTextComponentSerializer.plainText().serialize(item.displayName()));

        Team team = arena.getTeamByName("abc");

        Item itemdata = new Item(item, team.getTeamName(), 100, false);

        List<Item> items = new ArrayList<>();
        items.add(itemdata);
        arena.setItems(items);

        arenaconfig.editArena(arena);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
