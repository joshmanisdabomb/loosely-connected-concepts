package com.joshmanisdabomb.lcc.gen.biome;

import com.joshmanisdabomb.lcc.registry.LCCBiomes;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class RainbowColorfulBiome extends Biome implements LCCBiomeHelper {

    private static final BlockState GRASS = LCCBlocks.sparkling_grass_block.get(DyeColor.GREEN).getDefaultState();
    private static final BlockState DIRT = LCCBlocks.sparkling_dirt.getDefaultState();

    public RainbowColorfulBiome() {
        super(new Builder().surfaceBuilder(new ConfiguredSurfaceBuilder<>(LCCBiomes.colorful, new SurfaceBuilderConfig(GRASS, DIRT, DIRT))).precipitation(RainType.NONE).category(Category.EXTREME_HILLS).downfall(0.0F).depth(1.0F).scale(0.3F).temperature(1.0F).waterColor(0x0033FF).waterFogColor(0x0033FF).parent(null));
    }

    @Override
    public int getSkyColorByTemp(float p_76731_1_) {
        return 0xFFFFFF;
    }

    @Override
    public int getGrassColor(BlockPos p_180627_1_) {
        return 0x00FF11;
    }

    @Override
    public int getFoliageColor(BlockPos p_180625_1_) {
        return 0x00FF11;
    }

    @Override
    public Biome getRiver() {
        return this;
    }

}
