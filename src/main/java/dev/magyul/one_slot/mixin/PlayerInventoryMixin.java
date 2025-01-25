package dev.magyul.one_slot.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.magyul.one_slot.OneSlot;
import dev.magyul.one_slot.compat.cloth.ConfigIntegration;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {
    @Shadow @Final public DefaultedList<ItemStack> main;

    @Shadow public int selectedSlot;

    @Shadow @Final public PlayerEntity player;

    @ModifyReturnValue(method = "getMainHandStack", at = @At("RETURN"))
    private ItemStack getMainHandStack(ItemStack original) {
        if (ConfigIntegration.getConfig().sgm().check(OneSlot.getGameMode(player))) {
            selectedSlot = 0;
            return main.get(0);
        }
        return original;
    }

    @WrapOperation(method = "getEmptySlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/DefaultedList;size()I"))
    private int getEmptySlot(DefaultedList<ItemStack> instance, Operation<Integer> original) {
        if (ConfigIntegration.getConfig().sgm().check(OneSlot.getGameMode(player))) {
            return 1;
        }
        return original.call(instance);
    }

    @WrapOperation(method = "getSlotWithStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/DefaultedList;size()I"))
    private int getSlotWithStack(DefaultedList<ItemStack> instance, Operation<Integer> original) {
        if (ConfigIntegration.getConfig().sgm().check(OneSlot.getGameMode(player))) {
            return 1;
        }
        return original.call(instance);
    }

    @WrapOperation(method = "indexOf", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/DefaultedList;size()I"))
    private int indexOf(DefaultedList<ItemStack> instance, Operation<Integer> original) {
        if (ConfigIntegration.getConfig().sgm().check(OneSlot.getGameMode(player))) {
            return 1;
        }
        return original.call(instance);
    }

    @WrapOperation(method = "getOccupiedSlotWithRoomForStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/DefaultedList;size()I"))
    private int getOccupiedSlotWithRoomForStack(DefaultedList<ItemStack> instance, Operation<Integer> original) {
        if (ConfigIntegration.getConfig().sgm().check(OneSlot.getGameMode(player))) {
            return 1;
        }
        return original.call(instance);
    }
}
