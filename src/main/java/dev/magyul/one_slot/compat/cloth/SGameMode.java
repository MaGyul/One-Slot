package dev.magyul.one_slot.compat.cloth;

import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import org.jetbrains.annotations.NotNull;

public enum SGameMode implements SelectionListEntry.Translatable {
    ADVENTURE,
    CREATIVE,
    SURVIVAL,
    SPECTATOR,
    ALL;

    public boolean check(GameMode gameMode) {
        var gm = toGameMode();
        if (gm == null) {
            return true;
        }
        return gameMode == gm;
    }

    public GameMode toGameMode() {
        return switch (this) {
            case ADVENTURE -> GameMode.ADVENTURE;
            case CREATIVE -> GameMode.CREATIVE;
            case SURVIVAL -> GameMode.SURVIVAL;
            case SPECTATOR -> GameMode.SPECTATOR;
            case ALL -> null;
        };
    }

    public static Text getTranslationName(Enum<?> enumValue) {
        var gameMode = ((SGameMode) enumValue).toGameMode();
        if (gameMode == null) {
            return Text.translatable("option.setting.gamemode.all");
        }
        return gameMode.getTranslatableName();
    }

    @Override
    public @NotNull String getKey() {
        var gameMode = this.toGameMode();
        if (gameMode == null) {
            return "option.setting.gamemode.all";
        }
        return "gameMode." + gameMode.getName();
    }
}
