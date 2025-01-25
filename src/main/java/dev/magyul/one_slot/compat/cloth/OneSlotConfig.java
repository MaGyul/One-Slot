package dev.magyul.one_slot.compat.cloth;

import dev.magyul.one_slot.OneSlot;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = OneSlot.MOD_ID)
public class OneSlotConfig implements ConfigData {
    @Comment("Game mode to use One Slot")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public SGameMode sgm = SGameMode.ALL;

    public SGameMode sgm() {
        if (ServerData.server_sgm != null) {
            return ServerData.server_sgm;
        }
        return sgm;
    }

    public static class ServerData {
        public static SGameMode server_sgm = null;
    }
}
