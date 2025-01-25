package dev.magyul.one_slot.mixin;

import dev.magyul.one_slot.OneSlot;
import dev.magyul.one_slot.compat.cloth.ConfigIntegration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Shadow @Final private PlayerInventory inventory;

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (ConfigIntegration.getConfig().sgm().check(OneSlot.getGameMode((PlayerEntity) (Object) this))) {
            inventory.selectedSlot = 0;
        }
    }
}
