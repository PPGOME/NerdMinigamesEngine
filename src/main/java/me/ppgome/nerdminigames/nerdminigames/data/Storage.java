package me.ppgome.nerdminigames.nerdminigames.data;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Container;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the storage blocks in an arena
 *
 * @since 0.0.5
 * @author Keelan Delorme
 */
public class Storage {

    private Container container;
    private Location location;
    private List<StorageItem> items = new ArrayList<>();

    public Storage(Container container, Location location) {
        this.container = container;
        this.location = location;
    }

    public Storage(Location location, List<StorageItem> items) {
        this.location = location;
        this.items = items;
    }

    public Storage(){}

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<StorageItem> getItems() {
        return items;
    }

    public void setItems(List<StorageItem> items) {
        this.items = items;
    }
}
