package dev.magyul.one_slot.network.packets.c2s;

import dev.magyul.one_slot.OneSlot;
import dev.magyul.one_slot.compat.cloth.OneSlotConfig;
import dev.magyul.one_slot.compat.cloth.SGameMode;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class UpdateConfigDataPacket implements FabricPacket {
    public static final PacketType<UpdateConfigDataPacket> TYPE = PacketType.create(OneSlot.id("update_config_data"), UpdateConfigDataPacket::new);
    public SGameMode sgm;

    public UpdateConfigDataPacket(PacketByteBuf buf) {
        sgm = buf.readEnumConstant(SGameMode.class);
    }

    public UpdateConfigDataPacket(OneSlotConfig config) {
        sgm = config.sgm;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeEnumConstant(sgm);
    }

    public void handle(ServerPlayerEntity player, PacketSender ignoredSender) {
        if (player == null) return;
        if (!player.hasPermissionLevel(4)) return;
        OneSlotConfig.getConfig().sgm = sgm;
        OneSlotConfig.getConfigHolder().save();
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
