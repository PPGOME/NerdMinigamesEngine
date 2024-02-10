package me.ppgome.nerdminigames.nerdminigames.data;

import org.bukkit.inventory.ItemStack;

public class Item {

    private ItemStack item;
    private String team;
    private int chance;
    private boolean isCurrency;

    public Item(ItemStack item, String team, int chance, boolean isCurrency) {
        this.item = item;
        this.team = team;
        this.chance = chance;
        this.isCurrency = isCurrency;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public boolean isCurrency() {
        return isCurrency;
    }

    public void setCurrency(boolean currency) {
        isCurrency = currency;
    }
}
