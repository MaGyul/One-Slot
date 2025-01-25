package dev.magyul.one_slot.compat.cloth;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

@Config(name = "one_slot")
public class MenuIntegration implements ConfigData {
    @ConfigEntry.Gui.EnumHandler
    public SGameMode sgm = SGameMode.ALL;


    public static ConfigBuilder getConfigBuilder() {
        var config = ConfigBuilder.create();
        config.setTitle(Text.translatable("title.one_slot.config"));

        var entryBuilder = config.entryBuilder();

        var category = config.getOrCreateCategory(Text.translatable("category.one_slot.general"));

        category.addEntry(entryBuilder.startEnumSelector(Text.translatable("option.setting.gamemode"), SGameMode.class, SGameMode.ALL)
                .setDefaultValue(SGameMode.ALL)
                .setTooltip(Text.translatable("option.setting.gamemode.tooltip"))
                .setEnumNameProvider(SGameMode::getTranslationName)
                .setSaveConsumer((value) -> {
                }).build());


        return config;
    }

    public static Screen getConfigScreen(@Nullable Screen parent) {
        return MenuIntegration.getConfigBuilder().setParentScreen(parent).build();
    }
}
