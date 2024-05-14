package me.ppgome.nerdminigames.nerdminigames.listeners;

import me.ppgome.nerdminigames.nerdminigames.NerdMinigames;
import net.mcjukebox.plugin.bukkit.api.JukeboxAPI;
import net.mcjukebox.plugin.bukkit.api.ResourceType;
import net.mcjukebox.plugin.bukkit.api.models.Media;
import net.raidstone.wgevents.events.RegionEnteredEvent;
import net.raidstone.wgevents.events.RegionLeftEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WorldGuardListeners implements Listener {

    @EventHandler
    public void onRegionEnter(RegionEnteredEvent e) {
        Media music = null;
        if(e.getRegionName().equalsIgnoreCase("volcano")) {
            music = new Media(ResourceType.MUSIC, "https://cdn.discordapp.com/attachments/1157886179488829451/1218738017632522350/Volcano_Theme.mp3?ex=6608c13a&is=65f64c3a&hm=c0272219dd910b174baeffee87ee34709ac7bf6eec7f0bc55bc3fb9ffeac3b8e&");
        } else if(e.getRegionName().equalsIgnoreCase("normal")) {
            music = new Media(ResourceType.MUSIC, "https://cdn.discordapp.com/attachments/838283467883151440/1219021163724935178/Tarrey_Town_Zora_The_Legend_of_Zelda_Breath_of_the_Wild_OST.mp3?ex=6609c8ed&is=65f753ed&hm=1e343d177bc5a7c9460b486a6bb3ab458286bb45e44bfcd0cc6885c1cbbea4ed&");
        }
        if(music != null && e.getPlayer() != null) {
            music.setVolume(50);
            JukeboxAPI.play(e.getPlayer(), music);
        }

        System.out.println("Jukebox present: " + NerdMinigames.jukeboxAPIIsLoaded());
        System.out.println("Region entered: " + e.getRegionName());
    }

}
