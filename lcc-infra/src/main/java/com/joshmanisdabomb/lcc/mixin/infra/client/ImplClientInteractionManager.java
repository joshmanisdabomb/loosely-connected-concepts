package com.joshmanisdabomb.lcc.mixin.infra.client;

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ImplClientInteractionManager {

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "breakBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getFluidState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/fluid/FluidState;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void overrideBreak(BlockPos pos, CallbackInfoReturnable<Boolean> info, World world, BlockState state, Block block) {
        if (block instanceof LCCExtendedBlock) {
            Boolean ret = ((LCCExtendedBlock)block).lcc_overrideBreak(world, pos, state, this.client.player);
            if (ret != null) {
                info.setReturnValue(ret);
                info.cancel();
            }
        }
    }

}