package me.ppgome.nerdminigames.nerdminigames.data;

import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

/**
 * The object representing an item for use with teams, storage, and currency in an arena
 */
public class Item implements Serializable {

    private int ID;
    private ItemStack item;
    private Team team;
    private Integer chance;
    private boolean isCurrency;

    /**
     * Creates a temporary item before being stored in the config file
     * @param item The ItemStack
     * @param team The team
     * @param chance The chance of spawning with item
     * @param isCurrency If the item is this arena's currency
     */
    public Item(ItemStack item, Team team, int chance, boolean isCurrency) {
        this.item = item;
        this.team = team;
        this.chance = chance;
        this.isCurrency = isCurrency;
    }

    /**
     * The item object for items loaded from the config
     * @param ID The ID of the item
     * @param item The ItemStack
     * @param team The team
     * @param chance The chance of spawning with item
     * @param isCurrency If the item is this arena's currency
     */
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
