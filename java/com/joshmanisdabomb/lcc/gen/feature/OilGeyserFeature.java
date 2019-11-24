package com.joshmanisdabomb.lcc.gen.feature;

import com.joshmanisdabomb.lcc.gen.world.GenUtility;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.mojang.datafixers.Dynamic;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class OilGeyserFeature extends Feature<NoFeatureConfig> {

    private final int minHeight;
    private final int variation;

    public OilGeyserFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> config, boolean notify, int minHeight, int variation) {
        super(config, notify);
        this.minHeight = minHeight;
        this.variation = variation;
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int height = minHeight + rand.nextInt(variation + 1);
        BlockPos.MutableBlockPos bp = new BlockPos.MutableBlockPos(pos).move(0, height, 0);
        if (bp.getY() >= world.getMaxHeight()) return false;
        if (!GenUtility.allInAreaClear(world, pos.getX() - 2, pos.getY(), pos.getZ() - 2, pos.getX() + 2, bp.getY(), pos.getZ() + 2)) return false;
        if (!GenUtility.allInAreaMatches(world, pos.getX() - 2, pos.getY() - 1, pos.getZ() - 2, pos.getX() + 2, pos.getY() - 1, pos.getZ() + 2, (w, p) -> w.getBlockState(p).getBlock() == LCCBlocks.cracked_mud)) return false;
        for (int i = 0; i <= height; i++) {
            world.setBlockState(bp, LCCBlocks.oil.getDefaultState().with(BlockStateProperties.LEVEL_0_15, i > 0 ? 8 : 0), 18);
            for (int j = 0; j < 4; j++) {
                Direction d = Direction.byHorizontalIndex(j);
                world.setBlockState(bp.offset(d), LCCBlocks.oil.getDefaultState().with(BlockStateProperties.LEVEL_0_15, i > 0 ? 8 : 7), 18);
                if (i == height) {
                    world.setBlockState(bp.offset(d, 2), LCCBlocks.oil.getDefaultState().with(BlockStateProperties.LEVEL_0_15, 7), 18);
                    world.setBlockState(bp.add(j % 2 == 0 ? 1 : -1, 0, j / 2 == 0 ? 1 : -1), LCCBlocks.oil.getDefaultState().with(BlockStateProperties.LEVEL_0_15, 7), 18);
                }
            }
            bp.move(0, -1, 0);
        }
        return true;
    }

}