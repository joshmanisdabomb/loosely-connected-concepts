package com.joshmanisdabomb.lcc.mixin.base.common;

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ImplServerInteractionManager {

    @Shadow
    @Final
    private ServerWorld world;

    @Shadow
    @Final
    private ServerPlayerEntity player;

    @Inject(method = "tryBreakBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void overrideBreak(BlockPos pos, CallbackInfoReturnable<Boolean> info, BlockState state, BlockEntity be, Block block) {
        if (block instanceof LCCExtendedBlock) {
            Boolean ret = ((LCCExtendedBlock)block).lcc_overrideBreak(this.world, pos, state, this.player);
            if (ret != null) {
                info.setReturnValue(ret);
                info.cancel();
            }
        }
    }

}