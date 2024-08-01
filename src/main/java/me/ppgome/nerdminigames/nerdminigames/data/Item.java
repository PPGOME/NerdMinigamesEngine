package me.ppgome.nerdminigames.nerdminigames.data;

import org.bukkit.inventory.ItemStack;

/**
 * The object representing an item for use with teams, storage, and currency in an arena
 *
 * @since 0.0.3
 * @author Keelan Delorme
 */
public class Item {

    private int ID;
    private ItemStack item;
    private Team team;
    private Integer chance;
    private boolean isCurrency;

    public Item(ItemStack item, Team team, int chance, boolean isCurrency) {
        this.item = item;
        this.team = team;
        this.chance = chance;
        this.isCurrency = isCurrency;
    }

    public Item(Integer ID, ItemStack item, Team team, int chance, boolean isCurrency) {
        this.ID = ID;
        this.item = item;
        this.team = team;
        this.chance = chance;
        this.isCurrency = isCurrency;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
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
