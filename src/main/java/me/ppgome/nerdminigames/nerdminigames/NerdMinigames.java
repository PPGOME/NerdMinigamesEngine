package me.ppgome.nerdminigames.nerdminigames;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import me.ppgome.nerdminigames.nerdminigames.NPCs.Bank;
import me.ppgome.nerdminigames.nerdminigames.arenabuilder.CreationCommands;
import me.ppgome.nerdminigames.nerdminigames.guis.NerdGUI;
import me.ppgome.nerdminigames.nerdminigames.listeners.CreationListeners;
import me.ppgome.nerdminigames.nerdminigames.listeners.WorldGuardListeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public final class NerdMinigames extends JavaPlugin {
    private static NerdMinigames instance;

    public static NerdMinigames getPlugin() {
        return instance;
    }

    public static boolean jukeboxAPIIsLoaded() {
        return Bukkit.getServer().getPluginManager().getPlugin("JukeboxAPI") != null;
    }

    private static HashMap<UUID, NerdGUI> pendingInput = new HashMap<>();

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(true));
        CreationCommands.nerdGames();

    }

    /*
    Plugin Standards
    ----------------

    Permissions:
        - All permissions must follow nerdmg.*
        - There are 2 major permissions:
            - nerdmg.player grants permission to access games
            - nerdmg.mod grants permissions to create and edit arenas.
            - nerdmg.admin grants permissions to the entire plugin, including currency modification
     */

    @Override
    public void onEnable() {
        // Plugin startup logic

        System.out.println("AHAHAHAHAH");

        instance = this;

        saveDefaultConfig();

        // Check if arenas file exists. If no create it.
        File arenaconfigfile = new File(getDataFolder(), "arenas.yml");

        // Event listeners
        getServer().getPluginManager().registerEvents(new Bank(), this);
        getServer().getPluginManager().registerEvents(new CreationListeners(), this);
        getServer().getPluginManager().registerEvents(new WorldGuardListeners(), this);

        if(!arenaconfigfile.exists()) {
            arenaconfigfile.getParentFile().mkdirs();
            saveResource("arenas.yml", false);
        }

        File currencyconfigfile = new File(getDataFolder(), "currency.yml");
        if(!currencyconfigfile.exists()) {
            currencyconfigfile.getParentFile().mkdirs();
            saveResource("currency.yml", false);
        }

        ArenasConfig arenaconfig = new ArenasConfig(instance);

//        ItemStack test = new ItemStack(Material.STRING);
//        ItemMeta testmeta = test.getItemMeta();
//        testmeta.displayName(Component.text("Strong"));
//        testmeta.addEnchant(Enchantment.DURABILITY, 3, false);
//        test.setItemMeta(testmeta);
//
//        Arena arena = arenaconfig.getArena("TestArena");
//
//        ItemStack item = new ItemStack(Material.LIME_WOOL);
//        ItemMeta itemMeta = item.getItemMeta();
//        itemMeta.addEnchant(Enchantment.ARROW_INFINITE, 10, true);
//        itemMeta.displayName(Component.text("IamANitem"));
//        item.setItemMeta(itemMeta);
//
//        System.out.println(PlainTextComponentSerializer.plainText().serialize(item.displayName()));
//
//        Team team = arena.getTeamByName("abc");
//
//        Item itemdata = new Item(item, team.getTeamName(), 100, false);
//
//        List<Item> items = new ArrayList<>();
//        items.add(itemdata);
//        arena.setItems(items);
//
//        arenaconfig.editArena(arena);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static HashMap<UUID, NerdGUI> getPendingInput() {
        return pendingInput;
    }

    public static void setPendingInput(HashMap<UUID, NerdGUI> pendingInput) {
        NerdMinigames.pendingInput = pendingInput;
    }

    public static void addPendingInput(UUID uuid, NerdGUI gui) {
        pendingInput.put(uuid, gui);
    }
}
