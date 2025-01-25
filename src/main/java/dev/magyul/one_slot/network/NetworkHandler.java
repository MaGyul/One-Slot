package dev.magyul.one_slot.network;

import dev.magyul.one_slot.OneSlot;
import dev.magyul.one_slot.network.packets.c2s.UpdateConfigDataC2SPacket;
import dev.magyul.one_slot.network.packets.c2s.handshake.AcknowledgeC2SPacket;
import dev.magyul.one_slot.network.packets.s2c.handshake.AsyncSGMS2CPacket;
import dev.magyul.one_slot.util.EnvironmentUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.world.World;

import java.util.Objects;

public class NetworkHandler {

    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(UpdateConfigDataC2SPacket.TYPE, UpdateConfigDataC2SPacket::handle);

        HandshakeNetworking.register(AcknowledgeC2SPacket.ID, AcknowledgeC2SPacket.class);
        HandshakeNetworking.register(AsyncSGMS2CPacket.TYPE, AsyncSGMS2CPacket.class);

        if (EnvironmentUtil.isClient()) {
            NetworkClientInitializer.init();
        }
    }

    @Environment(EnvType.CLIENT)
    public static <T extends FabricPacket> void sendToServer(T message) {
        ClientPlayNetworking.send(message);
    }

    public static <T extends FabricPacket> void sendToClientPlayer(T message, PlayerEntity player) {
        ServerPlayNetworking.send((ServerPlayerEntity) player, message);
    }

    /**
     * Sent to all players listening to this entity
     */
    public static <T extends FabricPacket> void sendToTrackingEntityAndSelf(Entity centerEntity, T message) {
        ((ServerChunkManager)centerEntity.getEntityWorld().getChunkManager())
                .sendToNearbyPlayers(centerEntity, toVanillaPacket(message));
    }

    public static <T extends FabricPacket> void sendToAllPlayers(T message) {
        OneSlot.currentServer.getPlayerManager().sendToAll(toVanillaPacket(message));
    }

    public static <T extends FabricPacket> void sendToTrackingEntity(T message, final Entity centerEntity) {
        ((ServerChunkManager)centerEntity.getEntityWorld().getChunkManager())
                .sendToOtherNearbyPlayers(centerEntity, toVanillaPacket(message));
    }

    public static <T extends FabricPacket> void sendToDimension(T message, final Entity centerEntity) {
        RegistryKey<World> dimension = centerEntity.getWorld().getRegistryKey();
        var server = centerEntity.getServer();
        if (server != null) {
            server.getPlayerManager().sendToDimension(toVanillaPacket(message), dimension);
        }
    }

    public static <T extends FabricPacket> Packet<ClientPlayPacketListener> toVanillaPacket(T packet) {
        Objects.requireNonNull(packet, "Packet cannot be null");
        Objects.requireNonNull(packet.getType(), "Packet#getType cannot return null");

        PacketByteBuf buf = PacketByteBufs.create();
        packet.write(buf);
        return ServerPlayNetworking.createS2CPacket(packet.getType().getId(), buf);
    }
}