package com.joshmanisdabomb.lcc.gen.biome;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class RainbowTerreneBiome extends Biome implements LCCBiomeHelper {

    private static final BlockState GRASS = LCCBlocks.rainbow_grass_block.getDefaultState();
    private static final BlockState DIRT = LCCBlocks.sparkling_dirt.getDefaultState();

    public RainbowTerreneBiome() {
        super(new Builder().surfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(GRASS, DIRT, DIRT))).precipitation(RainType.NONE).category(Category.PLAINS).downfall(0.0F).depth(0.45F).scale(0.3F).temperature(1.0F).waterColor(0x432DBD).waterFogColor(0x4C4285).parent(null));
    }

    @Override
    public int getSkyColorByTemp(float p_76731_1_) {
        return 0xB1A8E3;
    }

    @Override
    public int getGrassColor(BlockPos p_180627_1_) {
        return 0x432DBD;
    }

    @Override
    public int getFoliageColor(BlockPos p_180625_1_) {
        return 0x432DBD;
    }

    @Override
    public Biome getRiver() {
        return this;
    }

}
