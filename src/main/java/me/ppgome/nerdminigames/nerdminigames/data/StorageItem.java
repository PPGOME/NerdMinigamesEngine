package me.ppgome.nerdminigames.nerdminigames.data;

import org.bukkit.inventory.ItemStack;

public class StorageItem {

    private int ID;
    private int min;
    private int max;
    private int chance;

    public StorageItem(int ID, int min, int max, int chance) {
        this.ID = ID;
        this.min = min;
        this.max = max;
        this.chance = chance;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }
}
