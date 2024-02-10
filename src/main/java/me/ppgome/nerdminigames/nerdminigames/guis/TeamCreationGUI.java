package me.ppgome.nerdminigames.nerdminigames.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import me.ppgome.nerdminigames.nerdminigames.ArenasConfig;
import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import me.ppgome.nerdminigames.nerdminigames.data.Arena;
import me.ppgome.nerdminigames.nerdminigames.data.Team;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.Locale;

import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.createButton;
import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.isInteger;

public class TeamCreationGUI implements NerdGUI {

    private Team team;
    private Player player;
    private Arena arena;
    private NerdGUI backgui;
    private DataInputGUI teamName;
    private DataInputGUI maxPlayers;
    private DataInputGUI minPlayers;
    private ConfirmationGUI deleteTeam;

    public TeamCreationGUI(Player player, Arena arena, NerdGUI backgui) {
        this.player = player;
        this.arena = arena;
        this.backgui = backgui;
    }

    public TeamCreationGUI(Team team, Player player, Arena arena, NerdGUI backgui) {
        this.team = team;
        this.player = player;
        this.arena = arena;
        this.backgui = backgui;
    }

    @Override
    public void displayGUI() {

        ChestGui gui = new ChestGui(5, "Creating a new team...");

        gui.setOnGlobalClick(e -> e.setCancelled(true));

        OutlinePane whitebars = new OutlinePane(0, 0, 9, 5, Pane.Priority.LOWEST);
        whitebars.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
        whitebars.setRepeat(true);

        OutlinePane body = new OutlinePane(0, 1, 9, 3, Pane.Priority.LOW);
        body.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        body.setRepeat(true);

        StaticPane buttons = new StaticPane(2, 1, 5, 3, Pane.Priority.NORMAL);

        // Cancel
        buttons.addItem(new GuiItem(createButton(Material.RED_STAINED_GLASS_PANE, "Cancel", "#ff5151"), inventoryClickEvent -> {
            backgui.displayGUI();
        }), Slot.fromIndex(0));

        // Confirm
        buttons.addItem(new GuiItem(createButton(Material.LIME_STAINED_GLASS_PANE, "Confirm", "#b5ff20"), inventoryClickEvent -> {

            if(team == null) {
                if(teamName != null && maxPlayers != null && minPlayers != null) {
                    if(!teamName.getInput().equalsIgnoreCase("") && !maxPlayers.getInput().equalsIgnoreCase("")
                            && isInteger(maxPlayers.getInput()) && !minPlayers.getInput().equalsIgnoreCase("")
                            && isInteger(minPlayers.getInput())) {
                        team = new Team(teamName.getInput(), Integer.parseInt(minPlayers.getInput()), Integer.parseInt(maxPlayers.getInput()));
                        arena.editTeam(team, arena);
                        backgui.displayGUI();
                    }
                }
            } else {
                if(teamName != null) {
                    team.setTeamName(teamName.getInput());
                }
                if(minPlayers != null && isInteger(minPlayers.getInput())) {
                    team.setMinPlayers(Integer.parseInt(minPlayers.getInput()));
                }
                if(maxPlayers != null && isInteger(maxPlayers.getInput())) {
                    team.setMaxPlayers(Integer.parseInt(maxPlayers.getInput()));
                }
                arena.editTeam(team, arena);
                backgui.displayGUI();
            }

        }), Slot.fromIndex(4));

        // Team Name
        String label;
        if(teamName != null) {
            label = "Team name: " + teamName.getInput();
        } else if(team != null) {
            label = "Team name: " + team.getTeamName();
        } else {
            label = "Click to name team";
        }
        buttons.addItem(new GuiItem(createButton(Material.NAME_TAG, label, "#3366ff"), inventoryClickEvent -> {
            teamName = new DataInputGUI(player, "Setting team name...", this);
            teamName.displayGUI();
        }), Slot.fromIndex(2));

        // Max Players
        if(maxPlayers != null) {
            label = "Maximum players: " + maxPlayers.getInput();
        } else if(team != null) {
            label = "Maximum players: " + team.getMaxPlayers();
        } else {
            label = "Click to set maximum players";
        }
        buttons.addItem(new GuiItem(createButton(Material.BLUE_STAINED_GLASS, label, "#3366ff"), inventoryClickEvent -> {
            maxPlayers = new DataInputGUI(player, "Setting maximum players...", this);
            maxPlayers.displayGUI();
        }), Slot.fromIndex(12));

        // Min players
        if(minPlayers != null) {
            label = "Minimum players: " + minPlayers.getInput();
        } else if(team != null) {
            label = "Minimum players: " + team.getMinPlayers();
        } else {
            label = "Click to set minimum players";
        }
        buttons.addItem(new GuiItem(createButton(Material.LIGHT_BLUE_STAINED_GLASS, label, "#3366ff"), inventoryClickEvent -> {
            minPlayers = new DataInputGUI(player, "Setting minimum players...", this);
            minPlayers.displayGUI();
        }), Slot.fromIndex(10));

        // Delete team
        if(team != null) {
            buttons.addItem(new GuiItem(createButton(Material.BARRIER, "Delete Team", "#ff5151"), inventoryClickEvent -> {
                deleteTeam = new ConfirmationGUI(player, "Delete team " + team.getTeamName() + "?", this);
                deleteTeam.displayGUI();
            }), Slot.fromIndex(14));
        }

        gui.addPane(whitebars);
        gui.addPane(body);
        gui.addPane(buttons);

        gui.show(player);

        if(deleteTeam != null && deleteTeam.getInput()) {
            arena.deleteTeam(team);
            new ArenasConfig(NerdMinigames.getPlugin()).editArena(arena);
            backgui.displayGUI();
            backgui.displayGUI();
        }

    }
}
