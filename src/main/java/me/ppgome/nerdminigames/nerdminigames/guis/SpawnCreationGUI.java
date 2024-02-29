package me.ppgome.nerdminigames.nerdminigames.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.ppgome.nerdminigames.nerdminigames.ArenasConfig;
import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import me.ppgome.nerdminigames.nerdminigames.data.Arena;
import me.ppgome.nerdminigames.nerdminigames.data.Spawn;
import me.ppgome.nerdminigames.nerdminigames.data.Team;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.KeybindComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.*;
import static me.ppgome.nerdminigames.nerdminigames.messages.PlayerMessager.infoMessage;

public class SpawnCreationGUI implements NerdGUI {

    private Player player;
    private Arena arena;
    private Spawn spawn;
    private NerdGUI backgui;
    private Location location;

    DataInputGUI nameInput;
    DataInputGUI teamInput;
    ConfirmationGUI confirmdelete;

    ChestGui gui;

    public SpawnCreationGUI(Player player, Arena arena, NerdGUI backgui) {
        this.player = player;
        this.arena = arena;
        this.backgui = backgui;
    }

    public SpawnCreationGUI(Player player, Arena arena, Spawn spawn, NerdGUI backgui) {
        this.player = player;
        this.arena = arena;
        this.spawn = spawn;
        this.backgui = backgui;
    }

    public void setLocation() {
        location = player.getLocation();
        displayGUI();
    }

    @Override
    public void displayGUI() {

        if(location != null) {
            System.out.println(location);
        }

        gui = new ChestGui(5, "Adding spawn...");
        gui.setOnGlobalClick(e -> e.setCancelled(true));
        addBackground(gui);

        StaticPane buttons = new StaticPane(0, 1, 9, 3, Pane.Priority.HIGHEST);

        // Cancel
        buttons.addItem(new GuiItem(createButton(Material.RED_STAINED_GLASS_PANE, "Cancel", "#ff5151"), inventoryClickEvent -> {
            backgui.displayGUI();
        }), Slot.fromIndex(2));

        // Confirm
        buttons.addItem(new GuiItem(createButton(Material.LIME_STAINED_GLASS_PANE, "Confirm", "#b5ff20"), inventoryClickEvent -> {
            if(location != null) {
                if(nameInput != null) {
                    if(!nameInput.getInput().equals("")) {
                        if(teamInput != null) {
                            for(Team team : arena.getTeams()) {
                                if(team.getTeamName().equalsIgnoreCase(teamInput.getInput())) {
                                    arena.addSpawn(new Spawn(nameInput.getInput(), location, team));
                                    new ArenasConfig(NerdMinigames.getPlugin()).editArena(arena);
                                    backgui.displayGUI();
                                }
                            }
                        }
                    }
                }
            }
        }), Slot.fromIndex(6));

        // Location

        if(spawn != null) {
            location = spawn.getLocation();
        }

        buttons.addItem(new GuiItem(createLocationButton(Material.COMPASS, location), clicc -> {
            NerdMinigames.addPendingInput(player.getUniqueId(), this);
            player.sendMessage(Component.text("[").color(TextColor.fromHexString("#5555FF"))
                    .append(Component.text("NerdMG").color(TextColor.fromHexString("#ffc820")))
                    .append(Component.text("]")).color(TextColor.fromHexString("#5555FF"))
                    .append(Component.text(" Go to where you want the spawn to be and press '").color(TextColor.fromHexString("#ffc820")))
                    .append(Component.keybind().keybind("key.swapOffhand").color(TextColor.fromHexString("#ffc820")).decoration(TextDecoration.BOLD, true).build())
                    .append(Component.text("' to save the location.").color(TextColor.fromHexString("#ffc820"))));
            player.closeInventory();

            // Check if within arena?

        }), Slot.fromIndex(4));

        // Nudge!
        int nudge = 0;
        if (spawn == null) nudge += 1;

        System.out.println("nudge: " + nudge);

        // Team name

        String buttonname = "Something broke!";

        if(spawn != null) {
            buttonname = "Team name: " + spawn.getName();
        } else if (teamInput != null) {
            if(!teamInput.getInput().equals("")) {
                buttonname = "Team name: " + teamInput.getInput();
            }
        } else {
            buttonname = "Click to Set Team";
        }

        buttons.addItem(new GuiItem(createButton(Material.LEATHER_CHESTPLATE, buttonname, "#FFFFFF"), clicc -> {
            teamInput = new DataInputGUI(player, "Assigning a team...", this);
            teamInput.displayGUI();
        }), Slot.fromIndex(20 + nudge));

        // Spawn name

        if(spawn != null) {
            buttonname = "Spawn name: " + spawn.getName();
        } else if (nameInput != null) {
            if(!nameInput.getInput().equals("")) {
                buttonname = "Spawn name: " + nameInput.getInput();
            }
        } else {
            buttonname = "Click to Set Spawn Name";
        }

        buttons.addItem(new GuiItem(createButton(Material.NAME_TAG, buttonname, "#FFFFFF"), clicc -> {
            nameInput = new DataInputGUI(player, "Setting spawn name...", this);
            nameInput.displayGUI();
        }), Slot.fromIndex(22 + nudge));

        // Delete

        if(spawn != null) {
            buttons.addItem(new GuiItem(createButton(Material.BARRIER, "Delete", "#ff5151"), clicc -> {
                confirmdelete = new ConfirmationGUI(player, "Delete " + spawn.getName() + "?", this);
                confirmdelete.displayGUI();
            }), Slot.fromIndex(24));
        }

        gui.addPane(buttons);
        gui.show(player);

        if(confirmdelete != null) {
            if(confirmdelete.getInput()) {
                arena.deleteSpawn(spawn);
                new ArenasConfig(NerdMinigames.getPlugin()).editArena(arena);
                backgui.displayGUI();
            }
        }
    }
}
