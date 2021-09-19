package com.joshmanisdabomb.lcc.mixin.hooks.common;

import com.joshmanisdabomb.lcc.facade.piston.LCCPiston;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PistonBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PistonBlock.class)
public abstract class PistonBlockMixin extends Block {

    public PistonBlockMixin(Settings settings) {
        super(settings);
    }

    @Redirect(method = "isMovable", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 3))
    private static boolean isPiston(BlockState state, Block original) {
        return state.isOf(original) || state.getBlock() instanceof LCCPiston;
    }

}
