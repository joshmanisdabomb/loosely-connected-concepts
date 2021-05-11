package com.joshmanisdabomb.lcc.mixin.infra.common;

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedBlock;
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
public abstract class ImplPlantBlockMixin extends Block {

    public ImplPlantBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "canPlantOnTop", at = @At("HEAD"), cancellable = true)
    public void canPlantOn(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        Block block = floor.getBlock();
        if (block instanceof LCCExtendedBlock) {
            Boolean flag = ((LCCExtendedBlock)block).lcc_isPlantable(floor, world, pos, this);
            if (flag == null) return;
            info.setReturnValue(flag);
            info.cancel();
        }
    }

}