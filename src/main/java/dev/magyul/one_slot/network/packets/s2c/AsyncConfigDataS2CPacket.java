package dev.magyul.one_slot.network.packets.s2c;

import dev.magyul.one_slot.OneSlot;
import dev.magyul.one_slot.compat.cloth.OneSlotConfig;
import dev.magyul.one_slot.compat.cloth.SGameMode;
import dev.magyul.one_slot.util.EnvironmentUtil;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;

public class AsyncConfigDataS2CPacket implements FabricPacket {
    public static final PacketType<AsyncConfigDataS2CPacket> TYPE = PacketType.create(OneSlot.id("async_config_data"), AsyncConfigDataS2CPacket::new);
    public SGameMode sgm;

    public AsyncConfigDataS2CPacket(PacketByteBuf buf) {
        sgm = buf.readEnumConstant(SGameMode.class);
    }

    public AsyncConfigDataS2CPacket(SGameMode sgm) {
        this.sgm = sgm;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeEnumConstant(sgm);
    }

    public void handle(PlayerEntity ignoredPlayer, PacketSender ignoredSender) {
        if (EnvironmentUtil.isClient()) {
            OneSlotConfig.ServerData.server_sgm = sgm;
        }
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
