package dev.magyul.one_slot.network;

import dev.magyul.one_slot.mixin.client.network.ClientLoginNetworkHandlerAccessor;
import dev.magyul.one_slot.network.packets.s2c.AsyncConfigDataS2CPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketByteBuf;

import java.util.concurrent.CompletableFuture;

@Environment(EnvType.CLIENT)
public class NetworkClientInitializer {

    @Environment(EnvType.CLIENT)
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(AsyncConfigDataS2CPacket.TYPE, AsyncConfigDataS2CPacket::handle);
    }

    @Environment(EnvType.CLIENT)
    static <T extends IHandshakeMessage> void registerHandshake(PacketType<T> type) {
        ClientLoginNetworking.registerGlobalReceiver(type.getId(), (client, handler, buf, listenerAdder) -> {
            T packet = type.read(buf);
            ClientConnection connection = ((ClientLoginNetworkHandlerAccessor) handler).getConnection();
            IHandshakeMessage.IResponsePacket responsePacket = packet.handle(connection, listenerAdder);
            PacketByteBuf response = PacketByteBufs.create();
            if (responsePacket != null) {
                response.writeIdentifier(responsePacket.getId());
                responsePacket.write(response);
            }
            return CompletableFuture.completedFuture(response);
        });
    }
}