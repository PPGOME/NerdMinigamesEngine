package me.ppgome.nerdminigames.nerdminigames;

import me.ppgome.nerdminigames.nerdminigames.data.ExternalCurrency;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CurrencyConfig {

    private final File file;
    private final FileConfiguration config;

    public CurrencyConfig(NerdMinigames plugin) {
        this(plugin.getDataFolder().getAbsolutePath() + "/arenas.yml");
    }

    public CurrencyConfig(String path) {
        this.file = new File(path);
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void addCurrency(ItemStack item, int rate) {
        String currencyname = PlainTextComponentSerializer.plainText().serialize(item.displayName());
        if(currencyname.substring(0, 1).equalsIgnoreCase("[") && currencyname.substring(currencyname.length() - 1).equalsIgnoreCase("]")) {
            currencyname = currencyname.substring(1, currencyname.length() - 1);
        }
        getConfig().set(currencyname + ".Item", item);
        getConfig().set(currencyname + ".Rate", rate);
    }

    public List<ExternalCurrency> getCurrencies() {
        List<ExternalCurrency> items = new ArrayList<>();
        ConfigurationSection configsec = getConfig().getConfigurationSection("");
        if(configsec != null) {
            for(String key : configsec.getKeys(false)) {
                items.add(new ExternalCurrency(getConfig().getItemStack(key + ".Item"), getConfig().getInt(key + ".Rate")));
            }
        }
        return items;
    }

    public void deleteCurrency(ItemStack item) {
        String currencyname = PlainTextComponentSerializer.plainText().serialize(item.displayName());
        if(currencyname.substring(0, 1).equalsIgnoreCase("[") && currencyname.substring(currencyname.length() - 1).equalsIgnoreCase("]")) {
            currencyname = currencyname.substring(1, currencyname.length() - 1);
        }
        getConfig().set(currencyname, "");
    }

    public File getFile() {
        return this.file;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

}
