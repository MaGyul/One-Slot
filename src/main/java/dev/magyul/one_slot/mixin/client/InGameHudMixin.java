package dev.magyul.one_slot.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.magyul.one_slot.OneSlot;
import dev.magyul.one_slot.compat.cloth.ConfigIntegration;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.gui.widget.ClickableWidget.WIDGETS_TEXTURE;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow
    protected abstract PlayerEntity getCameraPlayer();

    @Shadow
    protected abstract void renderHotbarItem(DrawContext context, int x, int y, float f, PlayerEntity player, ItemStack stack, int seed);

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    @Final
    private static Identifier ICONS;

    @Unique
    private final int ONE_SLOT_OFFSET = 20;

    @WrapOperation(method = "renderMountJumpBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"))
    private void renderMountJumpBar(DrawContext instance, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
        var player = getCameraPlayer();
        if (player != null && ConfigIntegration.getConfig().sgm().check(OneSlot.getGameMode(player))) {
            y += ONE_SLOT_OFFSET;
        }
        original.call(instance, texture, x, y, u, v, width, height);
    }

    @WrapOperation(method = "renderExperienceBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"))
    private void renderExperienceBar(DrawContext instance, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
        var player = getCameraPlayer();
        if (player != null && ConfigIntegration.getConfig().sgm().check(OneSlot.getGameMode(player))) {
            y += ONE_SLOT_OFFSET;
        }
        original.call(instance, texture, x, y, u, v, width, height);
    }

    @WrapOperation(method = "renderExperienceBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;IIIZ)I"))
    private int renderExperienceBar(DrawContext instance, TextRenderer textRenderer, String text, int x, int y, int color, boolean shadow, Operation<Integer> original) {
        var player = getCameraPlayer();
        if (player != null && ConfigIntegration.getConfig().sgm().check(OneSlot.getGameMode(player))) {
            y += ONE_SLOT_OFFSET;
        }
        return original.call(instance, textRenderer, text, x, y, color, shadow);
    }

    @WrapOperation(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"))
    private void renderStatusBars(DrawContext instance, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
        var player = getCameraPlayer();
        if (player != null && ConfigIntegration.getConfig().sgm().check(OneSlot.getGameMode(player))) {
            y += ONE_SLOT_OFFSET;
        }
        original.call(instance, texture, x, y, u, v, width, height);
    }

    @WrapOperation(method = "drawHeart", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"))
    private void drawHeart(DrawContext instance, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
        var player = getCameraPlayer();
        if (player != null && ConfigIntegration.getConfig().sgm().check(OneSlot.getGameMode(player))) {
            y += ONE_SLOT_OFFSET;
        }
        original.call(instance, texture, x, y, u, v, width, height);
    }

    @WrapOperation(method = "renderMountHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"))
    private void renderMountHealth(DrawContext instance, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
        var player = getCameraPlayer();
        if (player != null && ConfigIntegration.getConfig().sgm().check(OneSlot.getGameMode(player))) {
            y += ONE_SLOT_OFFSET;
        }
        original.call(instance, texture, x, y, u, v, width, height);
    }

    @Inject(method = "renderHotbar", at = @At("HEAD"), cancellable = true)
    private void renderHotbar(float tickDelta, DrawContext context, CallbackInfo ci) {
        var player = getCameraPlayer();
        if (player == null) {
            return;
        }

        if (ConfigIntegration.getConfig().sgm().check(OneSlot.getGameMode(player))) {
            ci.cancel();
            var halfWidth = context.getScaledWindowWidth() / 2;
            var offHand = player.getMainArm().getOpposite();
            var offHandStack = player.getOffHandStack();
            var mainHand = player.getMainArm();
            var mainHandStack = player.getMainHandStack();
            RenderSystem.enableBlend();
            context.getMatrices().push();
            context.getMatrices().translate(.0f, .0f, -90.0f);
            drawSlot(context, halfWidth, offHand, offHandStack);
            drawSlot(context, halfWidth, mainHand, mainHandStack);
            context.getMatrices().pop();
            RenderSystem.disableBlend();
            int l = 1;
            int y = halfWidth - 90 + 2;
            l = renderHotbarItem(tickDelta, context, player, halfWidth, offHand, offHandStack, l);
            renderHotbarItem(tickDelta, context, player, halfWidth, mainHand, mainHandStack, l);
            if (client.options.getAttackIndicator().getValue() == AttackIndicator.HOTBAR) {
                RenderSystem.enableBlend();
                if (client.player != null) {
                    var f = client.player.getAttackCooldownProgress(.0f);
                    if (f < 1.0f) {
                        var p = (int) (f * 19.0f);
                        context.drawTexture(ICONS, halfWidth - 9, y, 0, 94, 18, 18);
                        context.drawTexture(ICONS, halfWidth - 9, y + 18 - p, 18, 112 - p, 18, p);
                    }
                }
                RenderSystem.disableBlend();
            }
        }
    }

    @Unique
    private void drawSlot(DrawContext context, int halfWidth, Arm arm, ItemStack stack) {
        if (!stack.isEmpty()) {
            if (arm == Arm.LEFT) {
                context.drawTexture(WIDGETS_TEXTURE, halfWidth - 91 - 29, context.getScaledWindowHeight() - 23, 24, 22, 29, 24);
            } else {
                context.drawTexture(WIDGETS_TEXTURE, halfWidth + 91, context.getScaledWindowHeight() - 23, 53, 22, 29, 24);
            }
        }
    }

    @Unique
    private int renderHotbarItem(float tickDelta, DrawContext context, PlayerEntity player, int halfWidth, Arm arm, ItemStack stack, int l) {
        if (!stack.isEmpty()) {
            var y = context.getScaledWindowHeight() - 16 - 3;
            if (arm == Arm.LEFT) {
                renderHotbarItem(context, halfWidth - 91 - 26, y, tickDelta, player, stack, l++);
            } else {
                renderHotbarItem(context, halfWidth + 91 + 10, y, tickDelta, player, stack, l++);
            }
        }
        return l;
    }
}
