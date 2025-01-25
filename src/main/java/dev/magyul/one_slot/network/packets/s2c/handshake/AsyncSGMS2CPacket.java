package dev.magyul.one_slot.network.packets.s2c.handshake;

import dev.magyul.one_slot.OneSlot;
import dev.magyul.one_slot.compat.cloth.ConfigIntegration;
import dev.magyul.one_slot.compat.cloth.OneSlotConfig;
import dev.magyul.one_slot.compat.cloth.SGameMode;
import dev.magyul.one_slot.network.IHandshakeMessage;
import dev.magyul.one_slot.network.packets.c2s.handshake.AcknowledgeC2SPacket;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketByteBuf;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class AsyncSGMS2CPacket implements IHandshakeMessage {
    public static final PacketType<AsyncSGMS2CPacket> TYPE = PacketType.create(OneSlot.id("async_sgm"), AsyncSGMS2CPacket::new);
    public final SGameMode sgm;

    public AsyncSGMS2CPacket(PacketByteBuf buf) {
        sgm = buf.readEnumConstant(SGameMode.class);
    }

    public AsyncSGMS2CPacket() {
        this.sgm = ConfigIntegration.getConfig().sgm;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeEnumConstant(sgm);
    }

    @Override
    public @Nullable IResponsePacket handle(ClientConnection connection, Consumer<GenericFutureListener<? extends Future<? super Void>>> listenerAdder) {
        OneSlotConfig.ServerData.server_sgm = sgm;
        return new AcknowledgeC2SPacket();
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
