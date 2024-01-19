package me.ppgome.nerdminigames.nerdminigames.guis;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface NerdGUI {

    void displayGUI();

    ItemStack createButton(Material material, String name, String color);

}
