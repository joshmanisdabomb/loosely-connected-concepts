package com.joshmanisdabomb.lcc.gen.biome;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;

public abstract class RainbowBiome extends Biome {

    public RainbowBiome(Builder biomeBuilder) {
        super(biomeBuilder);
        this.rainbowInit();
    }

    protected void rainbowInit() {
        OreFeatureConfig.FillerBlockType twilight_stone = OreFeatureConfig.FillerBlockType.create("LCC_TWILIGHT_STONE", "lcc:twilight_stone", new BlockMatcher(LCCBlocks.twilight_stone));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(twilight_stone, LCCBlocks.neon_ore.getDefaultState(), 6)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 128))));
        //TODO: change to netherite placement
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(twilight_stone, LCCBlocks.chancite_ore.getDefaultState(), 10)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 128))));
    }

}
