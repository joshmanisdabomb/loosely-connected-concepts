package com.joshmanisdabomb.lcc.mixin.infra.common;

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.HashMap;
import java.util.Map;

@Mixin(Entity.class)
public abstract class ImplEntityMixin {

    private final HashMap<BlockPos, BlockState> lcc_onEntitySingleCollision_positions = new HashMap<>();

    @Shadow
    private World world;

    @Inject(method = "Lnet/minecraft/entity/Entity;checkBlockCollision()V", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void singleBlockCollisions(CallbackInfo info, Box box, BlockPos pos1, BlockPos pos2) {
        BlockPos.Mutable bp = new BlockPos.Mutable();
        if (this.world.isRegionLoaded(pos1, pos2)) {
            lcc_onEntitySingleCollision_positions.clear();
            for(int i = pos1.getX(); i <= pos2.getX(); ++i) {
                for(int j = pos1.getY(); j <= pos2.getY(); ++j) {
                    for(int k = pos1.getZ(); k <= pos2.getZ(); ++k) {
                        bp.set(i, j, k);
                        lcc_onEntitySingleCollision_positions.put(bp.toImmutable(), this.world.getBlockState(bp));
                    }
                }
            }

            if (!lcc_onEntitySingleCollision_positions.isEmpty()) {
                try {
                    lcc_onEntitySingleCollision_positions.values().stream().map(AbstractBlock.AbstractBlockState::getBlock).distinct().forEach(block -> {
                        if (block instanceof LCCExtendedBlock) ((LCCExtendedBlock)block).lcc_onEntitySingleCollision(world, lcc_onEntitySingleCollision_positions.entrySet().stream().filter(e -> e.getValue().isOf(block)).map(Map.Entry::getKey).toArray(BlockPos[]::new), lcc_onEntitySingleCollision_positions.values().stream().filter(state -> state.isOf(block)).toArray(BlockState[]::new), (Entity)(Object)this);
                    });
                } catch (Throwable var12) {
                    CrashReport crashReport = CrashReport.create(var12, "LCC: Single colliding entity with blocks");
                    throw new CrashException(crashReport);
                }
            }
        }
    }

    @Inject(method = "Lnet/minecraft/entity/Entity;baseTick()V", at = @At("TAIL"))
    public void nearbyBlockTicks(CallbackInfo info) {
        BlockPos.Mutable bp = new BlockPos.Mutable();
        BlockPos pos = ((Entity)(Object)this).getBlockPos();
        if (this.world.isRegionLoaded(pos, pos)) {
            for(int i = pos.getX() - 5; i <= pos.getX() + 5; ++i) {
                for (int j = pos.getY() - 5; j <= pos.getY() + 5; ++j) {
                    for (int k = pos.getZ() - 5; k <= pos.getZ() + 5; ++k) {
                        bp.set(i, j, k);
                        BlockState state = world.getBlockState(bp);
                        Block block = state.getBlock();
                        if (block instanceof LCCExtendedBlock) {
                            Vec3d vec = ((Entity)(Object)this).getPos();
                            ((LCCExtendedBlock)block).lcc_onEntityNearby(world, state, bp.toImmutable(), (Entity)(Object)this, bp.getSquaredDistance(vec.x, vec.y, vec.z, true));
                        }
                    }
                }
            }
        }
    }

}
