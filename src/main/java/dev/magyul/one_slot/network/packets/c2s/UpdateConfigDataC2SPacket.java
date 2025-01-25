package dev.magyul.one_slot.network.packets.c2s;

import dev.magyul.one_slot.OneSlot;
import dev.magyul.one_slot.compat.cloth.ConfigIntegration;
import dev.magyul.one_slot.compat.cloth.OneSlotConfig;
import dev.magyul.one_slot.compat.cloth.SGameMode;
import dev.magyul.one_slot.network.NetworkHandler;
import dev.magyul.one_slot.network.packets.s2c.AsyncConfigDataS2CPacket;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class UpdateConfigDataC2SPacket implements FabricPacket {
    public static final PacketType<UpdateConfigDataC2SPacket> TYPE = PacketType.create(OneSlot.id("update_config_data"), UpdateConfigDataC2SPacket::new);
    public SGameMode sgm;

    public UpdateConfigDataC2SPacket(PacketByteBuf buf) {
        sgm = buf.readEnumConstant(SGameMode.class);
    }

    public UpdateConfigDataC2SPacket(OneSlotConfig config) {
        sgm = config.sgm;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeEnumConstant(sgm);
    }

    public void handle(ServerPlayerEntity player, PacketSender ignoredSender) {
        if (player == null) return;
        if (!player.hasPermissionLevel(4)) return;
        ConfigIntegration.getConfig().sgm = sgm;
        ConfigIntegration.getConfigHolder().save();
        NetworkHandler.sendToAllPlayers(new AsyncConfigDataS2CPacket(sgm));
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
