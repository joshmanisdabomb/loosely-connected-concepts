package com.joshmanisdabomb.lcc.gen.feature;

import com.joshmanisdabomb.lcc.block.CandyCaneBlock;
import com.joshmanisdabomb.lcc.gen.world.GenUtility;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class CandyCaneFeature extends Feature<NoFeatureConfig> {

    public static final CandyCaneBlock[] CANES = new CandyCaneBlock[]{LCCBlocks.candy_cane_red, LCCBlocks.candy_cane_green, LCCBlocks.candy_cane_blue};
    public static final CandyCaneBlock[] COATING = new CandyCaneBlock[]{LCCBlocks.candy_cane_coating_red, LCCBlocks.candy_cane_coating_green, LCCBlocks.candy_cane_coating_blue};

    private final int minHeight;
    private final int variation;

    public CandyCaneFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> config, int minHeight, int variation) {
        super(config);
        this.minHeight = minHeight;
        this.variation = variation;
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        BlockPos.Mutable bp = new BlockPos.Mutable(pos);

        int height = minHeight + rand.nextInt(variation + 1);
        Direction d = Direction.byHorizontalIndex(rand.nextInt(4));

        ChunkPos cp = new ChunkPos(pos);
        int type = Math.floorMod(cp.x + cp.z, 3);

        BlockState cane = CANES[type].getDefaultState().with(BlockStateProperties.AXIS, Direction.Axis.Y);
        BlockState coating = COATING[type].getDefaultState().with(BlockStateProperties.AXIS, Direction.Axis.Y);

        if (pos.getY() + height >= world.getMaxHeight()) return false;
        if (!GenUtility.allInAreaMatches(world, pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1, pos.getX() + 1, pos.getY() - 1, pos.getZ() + 1, 0, (w, p) -> w.getBlockState(p).getBlock() == LCCBlocks.sugar_grass_block)) return false;
        if (!GenUtility.allInAreaClear(world, pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + (d.getXOffset() * 2), pos.getY() + height, pos.getZ() + (d.getZOffset() * 2), 1)) return false;

        for (int i = 0; i <= height; i++) {
            if (i == height) {
                world.setBlockState(bp, CandyCaneBlock.getCaneState(coating.with(BlockStateProperties.AXIS, d.getAxis()), bp), 18);
            } else if (i == height - 1) {
                world.setBlockState(bp, CandyCaneBlock.getCaneState(coating, bp), 18);
                bp.move(d.getXOffset() * 2, 0, d.getZOffset() * 2);
                world.setBlockState(bp, CandyCaneBlock.getCaneState(coating, bp), 18);
                bp.move(-d.getXOffset(), 1, -d.getZOffset());
            } else {
                world.setBlockState(bp, CandyCaneBlock.getCaneState(cane, bp), 18);
                bp.move(0, 1, 0);
            }
        }
        return true;
    }

}