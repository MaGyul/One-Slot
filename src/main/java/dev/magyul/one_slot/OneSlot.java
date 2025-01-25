package dev.magyul.one_slot;

import dev.magyul.one_slot.compat.cloth.OneSlotConfig;
import dev.magyul.one_slot.network.HandshakeNetworking;
import dev.magyul.one_slot.network.NetworkHandler;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OneSlot implements ModInitializer {
    public static final String MOD_ID = "one_slot";
    public static final Logger LOGGER = LoggerFactory.getLogger(OneSlot.class);

    public static MinecraftServer currentServer;

    @Override
    public void onInitialize() {
        if (FabricLoader.getInstance().isModLoaded("cloth-config")) {
            AutoConfig.register(OneSlotConfig.class, GsonConfigSerializer::new);
        }

        HandshakeNetworking.init();
        NetworkHandler.init();

        ServerLifecycleEvents.SERVER_STARTING.register(server -> currentServer = server);
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> currentServer = null);
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    public static GameMode getGameMode(PlayerEntity player) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT && player instanceof AbstractClientPlayerEntity c) {
            var entry = c.getPlayerListEntry();
            if (entry != null) {
                return entry.getGameMode();
            }
        }
        if (!(player instanceof ServerPlayerEntity s)) {
            return GameMode.DEFAULT;
        }
        return s.interactionManager.getGameMode();
    }
}
