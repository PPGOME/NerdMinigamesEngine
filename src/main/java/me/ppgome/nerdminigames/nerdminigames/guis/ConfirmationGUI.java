package me.ppgome.nerdminigames.nerdminigames.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.DispenserGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import static me.ppgome.nerdminigames.nerdminigames.guis.GUIUtils.createButton;

public class ConfirmationGUI implements NerdGUI {

    private boolean input;

    private final Player player;
    private final String label;
    private final NerdGUI backgui;

    public ConfirmationGUI(Player player, String label, NerdGUI backgui) {
        this.player = player;
        this.label = label;
        this.backgui = backgui;
    }

    @Override
    public void displayGUI() {

        DispenserGui gui = new DispenserGui(label);

        gui.setOnGlobalClick(e -> e.setCancelled(true));

        OutlinePane background = new OutlinePane(0 ,0, 3, 3, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(createButton(Material.BLACK_STAINED_GLASS_PANE, "", "#FFFFFF")));
        background.setRepeat(true);

        StaticPane buttons = new StaticPane(0, 1, 3, 1);
        buttons.addItem(new GuiItem(createButton(Material.RED_STAINED_GLASS_PANE, "Cancel", "#ff5151"),
                inventoryClickEvent -> {
                    this.input = false;
                    backgui.displayGUI();
                }), Slot.fromIndex(0));
        buttons.addItem(new GuiItem(createButton(Material.LIME_STAINED_GLASS_PANE, "Confirm", "#b5ff20"),
                inventoryClickEvent -> {
                    this.input = true;
                    backgui.displayGUI();
                }), Slot.fromIndex(2));

        gui.getContentsComponent().addPane(background);
        gui.getContentsComponent().addPane(buttons);

        gui.show(player);

    }

    public boolean getInput() {
        return input;
    }

}
