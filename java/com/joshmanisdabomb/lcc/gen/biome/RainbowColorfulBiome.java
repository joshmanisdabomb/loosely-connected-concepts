package com.joshmanisdabomb.lcc.gen.biome;

import com.joshmanisdabomb.lcc.registry.LCCBiomes;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.HeightWithChanceConfig;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class RainbowColorfulBiome extends RainbowBiome implements LCCBiomeHelper {

    public static final OctavesNoiseGenerator[] COLORS = new OctavesNoiseGenerator[16];
    static {
        for (int i = 0; i < COLORS.length; i++) {
            COLORS[i] = new OctavesNoiseGenerator(new SharedSeedRandom(3452108 + i), 4, 0);
        }
    }

    public static DyeColor getColor(int x, int y, int z) {
        int color = 0;
        double maxNoise = Math.abs(COLORS[0].getValue((double)x * 0.019D, y * 0.025D, (double)z * 0.019D, 1.0D, 0.0D, false));
        for (int i = 1; i < COLORS.length; i++) {
            double n = Math.abs(COLORS[i].getValue((double)x * 0.019D, y * 0.025D, (double)z * 0.019D, 1.0D, 0.0D, false));
            if (n > maxNoise) {
                color = i;
                maxNoise = n;
            }
        }
        return DyeColor.byId(color);
    }

    private static final BlockState GRASS = LCCBlocks.sparkling_grass_block.get(DyeColor.GREEN).getDefaultState();
    private static final BlockState DIRT = LCCBlocks.sparkling_dirt.getDefaultState();

    public RainbowColorfulBiome() {
        super(new Builder().surfaceBuilder(new ConfiguredSurfaceBuilder<>(LCCBiomes.colorful, new SurfaceBuilderConfig(GRASS, DIRT, DIRT))).precipitation(RainType.NONE).category(Category.EXTREME_HILLS).downfall(0.0F).depth(1.0F).scale(0.3F).temperature(1.0F).waterColor(0x0033FF).waterFogColor(0x0033FF).parent(null));
    }

    @Override
    public void lateGenerators() {
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, LCCFeatures.channelite.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(LCCFeatures.COUNT_CHANCE_TOPS.configure(new HeightWithChanceConfig(3, 0.1F))));
    }

    @Override
    public int getSkyColor() {
        return 0xFFFFFF;
    }

    @Override
    public int getGrassColor(double x, double z) {
        return 0x00FF11;
    }

    @Override
    public int getFoliageColor() {
        return 0x00FF11;
    }

    @Override
    public Biome getRiver() {
        return this;
    }

}
