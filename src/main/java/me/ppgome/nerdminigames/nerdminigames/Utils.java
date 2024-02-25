package me.ppgome.nerdminigames.nerdminigames;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class Utils {

    public static String removeBrackets(Component itemnamecomp) {
        String itemname = PlainTextComponentSerializer.plainText().serialize(itemnamecomp);
        if(itemname.substring(0, 1).equalsIgnoreCase("[") && itemname.substring(itemname.length() - 1).equalsIgnoreCase("]")) {
            itemname = itemname.substring(1, itemname.length() - 1);
        }
        return itemname;
    }

}
