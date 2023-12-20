package me.ppgome.nerdminigames.nerdminigames;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import me.ppgome.nerdminigames.nerdminigames.data.ArenasConfig;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class GUI {

    public static void arenaSelector(String player) {
        ChestGui arenaselector = new ChestGui(5, "Arena Selector");

        arenaselector.setOnGlobalClick(event -> event.setCancelled(true));

        OutlinePane background = new OutlinePane(0, 0, 9, 5, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        background.setRepeat(true);

        OutlinePane topbackground = new OutlinePane(0, 0, 9, 1, Pane.Priority.LOW);
        topbackground.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
        topbackground.setRepeat(true);

        ItemStack topicon = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta topiconmeta = topicon.getItemMeta();
        topiconmeta.displayName(Component.text("Pick an arena to edit below"));
        topicon.setItemMeta(topiconmeta);

        StaticPane topitem = new StaticPane(4, 0, 1, 1, Pane.Priority.HIGHEST);
        topitem.addItem(new GuiItem(topicon), 0, 0);

        OutlinePane body = new OutlinePane(0, 1, 9, 4);
        List<String> arenas = new ArrayList<String>();
        ArenasConfig config = new ArenasConfig(NerdMinigames.getPlugin(), "arenas.yml");
        arenas = config.getArenas();

        for(String arena : arenas) {
            ItemStack arenastack = new ItemStack(Material.REDSTONE);
            ItemMeta arenastackmeta = arenastack.getItemMeta();
            arenastackmeta.displayName(Component.text(arena));
            arenastack.setItemMeta(arenastackmeta);
            body.addItem(new GuiItem(arenastack));
        }

        arenaselector.addPane(background);
        arenaselector.addPane(topbackground);
        arenaselector.addPane(topitem);
        arenaselector.addPane(body);
        arenaselector.show(Bukkit.getPlayer(player));
    }
}
