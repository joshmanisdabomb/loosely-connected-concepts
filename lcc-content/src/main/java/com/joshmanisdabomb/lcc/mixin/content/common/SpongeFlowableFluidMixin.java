package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.block.ClassicSpongeBlock;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlowableFluid.class)
public abstract class SpongeFlowableFluidMixin extends Fluid {

    @Inject(method = "canFill", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;", ordinal = 0), cancellable = true)
    public void denyFillFromSponge(BlockView world, BlockPos pos, BlockState state, Fluid fluid, CallbackInfoReturnable<Boolean> info) {
        if (fluid.isIn(FluidTags.WATER) && ClassicSpongeBlock.Companion.cancelFill(world, pos, state)) {
            info.setReturnValue(false);
            info.cancel();
        }
    }

}
