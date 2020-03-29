package com.joshmanisdabomb.lcc.gen.biome;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class RainbowStarlightBiome extends Biome implements LCCBiomeHelper {

    private static final BlockState GRASS = LCCBlocks.star_plating.getDefaultState();
    private static final BlockState DIRT = LCCBlocks.sparkling_dirt.getDefaultState();

    public RainbowStarlightBiome() {
        super(new Builder().surfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(GRASS, DIRT, DIRT))).precipitation(RainType.NONE).category(Category.ICY).downfall(0.0F).depth(0.1F).scale(-0.1F).temperature(0.55F).waterColor(0xA0F2F0).waterFogColor(0xB6D6D6).parent(null));
    }

    @Override
    public int getSkyColor() {
        return 0xD9FFFE;
    }

    @Override
    public int getGrassColor(double x, double z) {
        return 0xD9FFFE;
    }

    @Override
    public int getFoliageColor() {
        return 0xD9FFFE;
    }

    @Override
    public Biome getRiver() {
        return this;
    }

}
