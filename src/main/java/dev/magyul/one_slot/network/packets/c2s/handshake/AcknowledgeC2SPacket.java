package dev.magyul.one_slot.network.packets.c2s.handshake;


import dev.magyul.one_slot.OneSlot;
import dev.magyul.one_slot.network.IHandshakeMessage;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class AcknowledgeC2SPacket implements IHandshakeMessage.IResponsePacket {
    public static final Marker ACKNOWLEDGE = MarkerFactory.getMarker("HANDSHAKE_ACKNOWLEDGE");
    public static final Identifier ID = OneSlot.id("acknowledge");

    public AcknowledgeC2SPacket() {
    }

    @Override
    public void write(PacketByteBuf buf) {
    }

    @Override
    public void read(PacketByteBuf buf) {
    }

    @Override
    public void handle(String info, PacketSender sender) {
        OneSlot.LOGGER.debug(ACKNOWLEDGE, "{} Received acknowledgement from client", info);
    }

    @Override
    public Identifier getId() {
        return ID;
    }
}