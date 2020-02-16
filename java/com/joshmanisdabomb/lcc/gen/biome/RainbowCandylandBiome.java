package com.joshmanisdabomb.lcc.gen.biome;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class RainbowCandylandBiome extends Biome implements LCCBiomeHelper {

    private static final BlockState GRASS = LCCBlocks.sugar_grass_block.getDefaultState();
    private static final BlockState DIRT = LCCBlocks.sparkling_dirt.getDefaultState();

    public RainbowCandylandBiome() {
        super(new Biome.Builder().surfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(GRASS, DIRT, DIRT))).precipitation(RainType.NONE).category(Category.FOREST).downfall(0.0F).depth(0.25F).scale(0.12F).temperature(0.9F).waterColor(0xE6C7EB).waterFogColor(0xE6C7EB).parent(null));
    }

    @Override
    public void lateGenerators() {
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.RANDOM_SELECTOR, new MultipleRandomFeatureConfig(new Feature[]{LCCFeatures.big_candy_cane}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.1F}, LCCFeatures.candy_cane, IFeatureConfig.NO_FEATURE_CONFIG), Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(1, 0.1F, 1)));
    }

    @Override
    public int getSkyColorByTemp(float p_76731_1_) {
        return 0xE6C7EB;
    }

    @Override
    public int getGrassColor(BlockPos p_180627_1_) {
        return 0xE6C7EB;
    }

    @Override
    public int getFoliageColor(BlockPos p_180625_1_) {
        return 0xE6C7EB;
    }

    @Override
    public Biome getRiver() {
        return this;
    }

}
