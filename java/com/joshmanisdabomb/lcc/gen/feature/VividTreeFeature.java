package com.joshmanisdabomb.lcc.gen.feature;

import com.joshmanisdabomb.lcc.gen.world.GenUtility;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractSmallTreeFeature;
import net.minecraft.world.gen.feature.HugeTreeFeatureConfig;
import net.minecraft.world.gen.feature.HugeTreesFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class VividTreeFeature extends AbstractSmallTreeFeature<TreeFeatureConfig> {

    public VividTreeFeature(Function<Dynamic<?>, ? extends TreeFeatureConfig> config) {
        super(config);
    }

    @Override
    protected boolean func_225557_a_(IWorldGenerationReader wg, Random random, BlockPos pos, Set<BlockPos> posSet, Set<BlockPos> posSet2, MutableBoundingBox bb, TreeFeatureConfig config) {
        BlockPos.Mutable bp = new BlockPos.Mutable(pos);
        int i = config.baseHeight + random.nextInt(config.heightRandA + 1) + random.nextInt(config.heightRandB + 1);

        if (!(wg instanceof IWorld)) return false;
        if (pos.getY() + i >= wg.getMaxHeight()) return false;
        if (!GenUtility.allInAreaMatches((IWorld)wg, pos.getX(), pos.getY() - 1, pos.getZ(), pos.getX(), pos.getY() - 1, pos.getZ(), 0, (w, p) -> w.getBlockState(p).getBlock() == LCCBlocks.rainbow_grass_block || w.getBlockState(p).getBlock() == LCCBlocks.sparkling_dirt)) return false;
        if (!GenUtility.allInAreaClear((IWorld)wg, pos.getX() - 2, pos.getY(), pos.getZ() - 2, pos.getX() + 2, pos.getY() + i, pos.getZ() + 2, 0)) return false;

        this.func_227213_a_(wg, random, i, pos, config.trunkTopOffset + random.nextInt(config.trunkTopOffsetRandom + 1), posSet, bb, config);
        wg.setBlockState(bp.move(Direction.UP, i), config.leavesProvider.getBlockState(random, bp).with(LeavesBlock.DISTANCE, 1), 18);
        bp.move(0, -1, 0);
        for (int a = 0; a < 4; a++) {
            Direction d = Direction.byHorizontalIndex(a);
            wg.setBlockState(bp.move(0, -2, 0).move(d), config.leavesProvider.getBlockState(random, bp).with(LeavesBlock.DISTANCE, 1), 18);
            wg.setBlockState(bp.move(0, 1, 0), config.trunkProvider.getBlockState(random, bp).with(BlockStateProperties.AXIS, d.getAxis()), 18);
            wg.setBlockState(bp.move(d), config.leavesProvider.getBlockState(random, bp).with(LeavesBlock.DISTANCE, 1), 18);
            wg.setBlockState(bp.move(d.getOpposite()).move(0, 1, 0), config.leavesProvider.getBlockState(random, bp).with(LeavesBlock.DISTANCE, 1), 18);
            bp.move(d.getOpposite());
        }
        for (int x = -1; x <= 1; x += 2) {
            for (int z = -1; z <= 1; z += 2) {
                wg.setBlockState(bp.setPos(pos).move(x, i-2, z), config.leavesProvider.getBlockState(random, bp).with(LeavesBlock.DISTANCE, 1), 18);
            }
        }
        return true;
    }

}
