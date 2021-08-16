package com.joshmanisdabomb.lcc.mixin.content.client;

import com.joshmanisdabomb.lcc.trait.LCCContentBlockTrait;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(DebugHud.class)
public abstract class DebugHudMixin {

    @Shadow
    private HitResult blockHit;

    @Shadow
    private MinecraftClient client;

    @Redirect(method = "getRightText", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 3))
    private boolean debugStateHide(List<Object> list, Object string) {
        if (!(string instanceof String)) return list.add(string);
        BlockHitResult blockHit = (BlockHitResult)this.blockHit;
        BlockState state = client.world.getBlockState(blockHit.getBlockPos());
        Block block = state.getBlock();
        if (!(block instanceof LCCContentBlockTrait)) return list.add(string);
        String message = ((LCCContentBlockTrait)block).lcc_content_hideStateFromDebug(state, client.player, blockHit);
        if (message == null) return list.add(string);
        if (!list.get(list.size() - 1).equals(message)) list.add(message);
        return false;
    }

}
