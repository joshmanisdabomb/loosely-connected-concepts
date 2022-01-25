package com.joshmanisdabomb.lcc.mixin.hooks.client;

import com.joshmanisdabomb.lcc.facade.piston.LCCPiston;
import com.joshmanisdabomb.lcc.facade.piston.LCCPistonHead;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.client.render.block.entity.PistonBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PistonBlockEntityRenderer.class)
public abstract class PistonBlockEntityRendererMixin {

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 0))
    private boolean extend(BlockState pushed, Block head) {
        Block block = pushed.getBlock();
        return pushed.isOf(head) || (block instanceof PistonHeadBlock && block instanceof LCCPistonHead);
    }

    @ModifyVariable(method = "render", at = @At(value = "STORE", ordinal = 1), ordinal = 1)
    private BlockState retractOverride(BlockState original, PistonBlockEntity piston) {
        Block block = piston.getPushedBlock().getBlock();
        if (block instanceof LCCPiston && original.getBlock() instanceof PistonHeadBlock) {
            return ((LCCPiston)block).getHead().getStateWithProperties(original);
        }
        return original;
    }

}