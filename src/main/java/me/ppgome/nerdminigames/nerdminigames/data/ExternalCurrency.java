package me.ppgome.nerdminigames.nerdminigames.data;

import org.bukkit.inventory.ItemStack;

public class ExternalCurrency {

    private ItemStack item;
    private int rate;

    public ExternalCurrency(ItemStack item, int rate) {
        this.item = item;
        this.rate = rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getRate() {
        return rate;
    }
}
