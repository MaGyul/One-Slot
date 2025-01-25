package dev.magyul.one_slot.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.magyul.one_slot.OneSlot;
import dev.magyul.one_slot.compat.cloth.ConfigIntegration;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Slot.class)
public class SlotMixin {

    @Shadow @Final public Inventory inventory;

    @Shadow @Final private int index;

    @ModifyReturnValue(method = "isEnabled", at = @At("RETURN"))
    private boolean isEnabled(boolean original) {
        return checkSingleSlot(original);
    }

    @ModifyReturnValue(method = "canInsert", at = @At("RETURN"))
    private boolean canInsert(boolean original) {
        return checkSingleSlot(original);
    }

    @Unique
    private boolean checkSingleSlot(boolean original) {
        if (inventory instanceof PlayerInventory playerInventory) {
            if (ConfigIntegration.getConfig().sgm().check(OneSlot.getGameMode(playerInventory.player))) {
                if (isNotSingleSlot(playerInventory)) {
                    return false;
                }
            }
        }
        return original;
    }

    @Unique
    private boolean isNotSingleSlot(PlayerInventory inventory) {
        return index > 0 && index < (inventory.size() - 5);
    }
}
