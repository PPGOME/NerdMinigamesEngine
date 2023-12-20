package me.ppgome.nerdminigames.nerdminigames.data;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

// https://www.spigotmc.org/threads/tutorial-bukkit-custom-serialization.148781/

@SerializableAs("Arena")
public class Arena implements ConfigurationSerializable {



    @Override
    public @NotNull Map<String, Object> serialize() {
        return null;
    }

}
