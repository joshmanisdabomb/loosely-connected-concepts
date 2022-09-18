package com.joshmanisdabomb.lcc.mixin.content.common;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.*;
import net.minecraft.util.registry.Registry;
import org.checkerframework.common.reflection.qual.Invoke;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @Redirect(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I"))
    public int getMaxLevel(Enchantment enchantment) {
        ItemStack stack = this.input.getStack(0);
        ItemStack stack2 = this.input.getStack(1);
        if (stack == null || stack2 == null) return enchantment.getMaxLevel();
        NbtCompound nbt = stack.getSubNbt("lcc-overlevel");
        NbtCompound nbt2 = stack2.getSubNbt("lcc-overlevel");
        if (nbt == null && nbt2 == null) return enchantment.getMaxLevel();
        String id = Registry.ENCHANTMENT.getId(enchantment).toString();
        return Math.max(Math.max(nbt != null ? nbt.getByte(id) : 0, nbt2 != null ? nbt2.getByte(id) : 0), enchantment.getMaxLevel());
    }

    @Inject(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/AnvilScreenHandler;sendContentUpdates()V"))
    public void modifyResult(CallbackInfo cinfo) {
        ItemStack stack = this.input.getStack(0);
        NbtCompound nbt = stack != null ? stack.getSubNbt("lcc-overlevel") : null;
        ItemStack stack2 = this.input.getStack(1);
        NbtCompound nbt2 = stack2 != null ? stack2.getSubNbt("lcc-overlevel") : null;
        if (nbt == null && nbt2 == null) return;
        ItemStack output = this.output.getStack(0);
        if (output == null) return;
        NbtCompound ret = output.getOrCreateSubNbt("lcc-overlevel");
        if (nbt != null) {
            for (String key : nbt.getKeys()) {
                ret.putByte(key, (byte) Math.max(ret.getByte(key), nbt.getByte(key)));
            }
        }
        if (nbt2 != null) {
            for (String key : nbt2.getKeys()) {
                ret.putByte(key, (byte) Math.max(ret.getByte(key), nbt2.getByte(key)));
            }
        }
    }

}
