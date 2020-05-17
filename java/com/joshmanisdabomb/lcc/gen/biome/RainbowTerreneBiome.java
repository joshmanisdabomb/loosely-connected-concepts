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
import net.minecraft.world.gen.placement.HeightWithChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class RainbowTerreneBiome extends RainbowBiome implements LCCBiomeHelper {

    private static final BlockState GRASS = LCCBlocks.rainbow_grass_block.getDefaultState();
    private static final BlockState DIRT = LCCBlocks.sparkling_dirt.getDefaultState();

    public RainbowTerreneBiome() {
        super(new Builder().surfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(GRASS, DIRT, DIRT))).precipitation(RainType.NONE).category(Category.PLAINS).downfall(0.0F).depth(0.45F).scale(0.3F).temperature(1.0F).waterColor(0x432DBD).waterFogColor(0x4C4285).parent(null));
    }

    @Override
    public void lateGenerators() {
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(
            LCCFeatures.big_vivid_tree.withConfiguration(LCCFeatures.BIG_VIVID_TREE_CONFIG).func_227227_a_(0.01F)
        ), LCCFeatures.vivid_tree.withConfiguration(LCCFeatures.VIVID_TREE_CONFIG))).withPlacement(Placement.COUNT_CHANCE_HEIGHTMAP.configure(new HeightWithChanceConfig(3, 0.9F))));
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(
            LCCFeatures.big_vivid_tree.withConfiguration(LCCFeatures.BIG_VIVID_TREE_CONFIG).func_227227_a_(0.1F)
        ), LCCFeatures.vivid_tree.withConfiguration(LCCFeatures.VIVID_TREE_CONFIG))).withPlacement(LCCFeatures.COUNT_CHANCE_UNDER_SURFACE.configure(new HeightWithChanceConfig(10, 0.95F))));
    }

    @Override
    public int getSkyColor() {
        return 0xB1A8E3;
    }

    @Override
    public int getGrassColor(double x, double z) {
        return 0x432DBD;
    }

    @Override
    public int getFoliageColor() {
        return 0x432DBD;
    }

    @Override
    public Biome getRiver() {
        return this;
    }

}
