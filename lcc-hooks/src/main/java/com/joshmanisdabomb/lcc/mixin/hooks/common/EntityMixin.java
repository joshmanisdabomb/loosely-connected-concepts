package com.joshmanisdabomb.lcc.mixin.hooks.common;

import com.joshmanisdabomb.lcc.trait.LCCBlockTrait;
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

import java.util.*;

@Mixin(Entity.class)
public abstract class EntityMixin {

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
                    Set<Map.Entry<BlockPos, BlockState>> collides = lcc_onEntitySingleCollision_positions.entrySet();
                    //Group by Block
                    Map<Block, List<Map.Entry<BlockPos, BlockState>>> group1 = new HashMap<>();
                    //Group by Block class
                    Map<Class<? extends Block>, List<Map.Entry<BlockPos, BlockState>>> group2 = new HashMap<>();

                    for (Map.Entry<BlockPos, BlockState> collide : collides) {
                        group1.computeIfAbsent(collide.getValue().getBlock(), k -> new ArrayList<>()).add(collide);
                        group2.computeIfAbsent(collide.getValue().getBlock().getClass(), k -> new ArrayList<>()).add(collide);
                    }

                    for (Map.Entry<Block, List<Map.Entry<BlockPos, BlockState>>> entry : group1.entrySet()) {
                        Block block = entry.getKey();
                        List<Map.Entry<BlockPos, BlockState>> entries = entry.getValue();

                        if (block instanceof LCCBlockTrait) {
                            List<BlockPos> bpList = new ArrayList<>();
                            for (Map.Entry<BlockPos, BlockState> e : entries) {
                                bpList.add(e.getKey());
                            }
                            BlockPos[] positions = bpList.toArray(new BlockPos[0]);
                            List<BlockState> bsList = new ArrayList<>();
                            for (Map.Entry<BlockPos, BlockState> e : entries) {
                                bsList.add(e.getValue());
                            }
                            BlockState[] states = bsList.toArray(new BlockState[0]);

                            ((LCCBlockTrait)block).lcc_onEntityCollisionGroupedByBlock(world, positions, states, (Entity)(Object)this);
                        }
                    }

                    for (Map.Entry<Class<? extends Block>, List<Map.Entry<BlockPos, BlockState>>> entry : group2.entrySet()) {
                        Block block = null;
                        for (Block b : group1.keySet()) {
                            if (b.getClass().equals(entry.getKey())) {
                                block = b;
                            }
                        }
                        if (block == null) continue;
                        List<Map.Entry<BlockPos, BlockState>> entries = entry.getValue();

                        List<BlockPos> bpList = new ArrayList<>();
                        for (Map.Entry<BlockPos, BlockState> e : entries) {
                            bpList.add(e.getKey());
                        }
                        BlockPos[] positions = bpList.toArray(new BlockPos[0]);
                        List<BlockState> bsList = new ArrayList<>();
                        for (Map.Entry<BlockPos, BlockState> e : entries) {
                            bsList.add(e.getValue());
                        }
                        BlockState[] states = bsList.toArray(new BlockState[0]);

                        ((LCCBlockTrait)block).lcc_onEntityCollisionGroupedByClass(world, positions, states, (Entity)(Object)this);
                    }
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
                        if (block instanceof LCCBlockTrait) {
                            Vec3d vec = ((Entity)(Object)this).getPos();
                            ((LCCBlockTrait)block).lcc_onEntityNearby(world, state, bp, (Entity)(Object)this, bp.getSquaredDistanceFromCenter(vec.x, vec.y, vec.z));
                        }
                    }
                }
            }
        }
    }

}
