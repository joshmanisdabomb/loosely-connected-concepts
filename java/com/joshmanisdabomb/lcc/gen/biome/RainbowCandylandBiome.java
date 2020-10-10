package com.joshmanisdabomb.lcc.gen.biome;

import com.google.common.collect.ImmutableList;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.HeightWithChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class RainbowCandylandBiome extends RainbowBiome implements LCCBiomeHelper {

    private static final BlockState GRASS = LCCBlocks.sugar_grass_block.getDefaultState();
    private static final BlockState DIRT = LCCBlocks.sparkling_dirt.getDefaultState();

    public RainbowCandylandBiome() {
        super(new Biome.Builder().surfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(GRASS, DIRT, DIRT))).precipitation(RainType.NONE).category(Category.FOREST).downfall(0.0F).depth(0.25F).scale(0.12F).temperature(0.9F).waterColor(0xE6C7EB).waterFogColor(0xE6C7EB).parent(null));
    }

    @Override
    public void lateGenerators() {
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(
            LCCFeatures.big_candy_cane.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withChance(0.1F)
        ), LCCFeatures.candy_cane.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG))).withPlacement(Placement.COUNT_CHANCE_HEIGHTMAP.configure(new HeightWithChanceConfig(2, 0.5F))));
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(
            LCCFeatures.big_candy_cane.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withChance(0.1F)
        ), LCCFeatures.candy_cane.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG))).withPlacement(LCCFeatures.COUNT_CHANCE_UNDER_SURFACE.configure(new HeightWithChanceConfig(4, 0.9F))));
        this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, LCCFeatures.chocolate_mound.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, 0.08F, 2))));
    }

    @Override
    public int getSkyColor() {
        return 0xE6C7EB;
    }

    @Override
    public int getGrassColor(double x, double z) {
        return 0xE6C7EB;
    }

    @Override
    public int getFoliageColor() {
        return 0xE6C7EB;
    }

    @Override
    public Biome getRiver() {
        return this;
    }

}
