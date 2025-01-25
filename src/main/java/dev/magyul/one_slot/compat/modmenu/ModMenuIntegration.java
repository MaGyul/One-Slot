package dev.magyul.one_slot.compat.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.magyul.one_slot.compat.cloth.OneSlotConfig;
import dev.magyul.one_slot.compat.cloth.gui.ClothConfigScreen;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.loader.api.FabricLoader;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (parent) -> {
            if (FabricLoader.getInstance().isModLoaded("cloth-config")) {
                return AutoConfig.getConfigScreen(OneSlotConfig.class, parent).get();
            } else {
                return new ClothConfigScreen(parent);
            }
        };
    }
}
