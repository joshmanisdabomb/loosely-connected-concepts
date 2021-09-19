package com.joshmanisdabomb.lcc.mixin.hooks.common;

import com.joshmanisdabomb.lcc.trait.LCCBlockTrait;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlantBlock.class)
public abstract class PlantBlockMixin extends Block {

    public PlantBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "canPlantOnTop", at = @At("HEAD"), cancellable = true)
    public void canPlantOn(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        Block block = floor.getBlock();
        if (block instanceof LCCBlockTrait) {
            Boolean flag = ((LCCBlockTrait)block).lcc_isPlantable(floor, world, pos, this);
            if (flag == null) return;
            info.setReturnValue(flag);
            info.cancel();
        }
    }

}