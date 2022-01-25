package com.joshmanisdabomb.lcc.mixin.hooks.common;

import com.joshmanisdabomb.lcc.facade.piston.LCCPiston;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
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

    @Redirect(method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;PISTON_HEAD:Lnet/minecraft/block/Block;"))
    private Block customHead() {
        if (this instanceof LCCPiston piston) {
            return piston.getHead();
        }
        return Blocks.PISTON_HEAD;
    }

    @Redirect(method = "onSyncedBlockEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/PistonExtensionBlock;createBlockEntityPiston(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;ZZ)Lnet/minecraft/block/entity/BlockEntity;"))
    private BlockEntity handlePistonCreation(BlockPos pos, BlockState extension, BlockState pushedBlock, Direction facing, boolean extending, boolean source, BlockState piston, World world) {
        if (this instanceof LCCPiston lpiston) {
            return lpiston.createExtension(world, pos, extension, pushedBlock, facing, extending, source);
        }
        return PistonExtensionBlock.createBlockEntityPiston(pos, extension, pushedBlock, facing, extending, source);
    }

    @Redirect(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/PistonExtensionBlock;createBlockEntityPiston(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;ZZ)Lnet/minecraft/block/entity/BlockEntity;"))
    private BlockEntity handlePistonCreation(BlockPos pos, BlockState extension, BlockState pushedBlock, Direction facing, boolean extending, boolean source, World world) {
        if (this instanceof LCCPiston lpiston) {
            return lpiston.createExtension(world, pos, extension, pushedBlock, facing, extending, source);
        }
        return PistonExtensionBlock.createBlockEntityPiston(pos, extension, pushedBlock, facing, extending, source);
    }

}
