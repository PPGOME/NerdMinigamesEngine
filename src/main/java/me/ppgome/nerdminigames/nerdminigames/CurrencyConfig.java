package me.ppgome.nerdminigames.nerdminigames;

import me.ppgome.nerdminigames.nerdminigames.data.ExternalCurrency;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static me.ppgome.nerdminigames.nerdminigames.Utils.removeBrackets;

public class CurrencyConfig {

    private final File file;
    private final FileConfiguration config;

    public CurrencyConfig(NerdMinigames plugin) {
        this(plugin.getDataFolder().getAbsolutePath() + "/currency.yml");
    }

    public CurrencyConfig(String path) {
        this.file = new File(path);
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public boolean save() {
        try {
            this.config.save(this.file);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void addCurrency(ItemStack item, int rate) {
        NamespacedKey ifuuid = new NamespacedKey(NerdMinigames.getPlugin(), "if-uuid");
        NamespacedKey arena = new NamespacedKey(NerdMinigames.getPlugin(), "arena");
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().remove(ifuuid);
        if(itemMeta.getPersistentDataContainer().has(arena)) {
            itemMeta.getPersistentDataContainer().remove(arena);
        }
        item.setItemMeta(itemMeta);
        String itemname = removeBrackets(item.displayName());
        getConfig().set(itemname + ".Item", item);
        getConfig().set(itemname + ".Rate", rate);
        save();
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

    public ExternalCurrency getCurrencyByName(String name) {
        for(ExternalCurrency currency : getCurrencies()) {
            if(removeBrackets(currency.getItem().displayName()).equalsIgnoreCase(name)) {
                return currency;
            }
        }
        return null;
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
