package dev.magyul.one_slot.compat.cloth;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;

public class ConfigIntegration {

    /*
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
     */

    public static ConfigHolder<OneSlotConfig> getConfigHolder() {
        return AutoConfig.getConfigHolder(OneSlotConfig.class);
    }

    public static OneSlotConfig getConfig() {
        return getConfigHolder().getConfig();
    }
}
