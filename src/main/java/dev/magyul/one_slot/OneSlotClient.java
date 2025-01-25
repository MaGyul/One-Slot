package dev.magyul.one_slot;

import dev.magyul.one_slot.compat.cloth.ConfigIntegration;
import dev.magyul.one_slot.compat.cloth.OneSlotConfig;
import dev.magyul.one_slot.network.NetworkHandler;
import dev.magyul.one_slot.network.packets.c2s.UpdateConfigDataC2SPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ActionResult;

public class OneSlotClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ConfigIntegration.getConfigHolder().registerSaveListener((holder, config) -> {
            if (MinecraftClient.getInstance().getNetworkHandler() != null)
                NetworkHandler.sendToServer(new UpdateConfigDataC2SPacket(config));
            return ActionResult.SUCCESS;
        });
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) ->
                OneSlotConfig.ServerData.server_sgm = null);
    }
}
