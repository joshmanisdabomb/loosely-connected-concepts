package com.joshmanisdabomb.lcc.gen.world;

import com.joshmanisdabomb.lcc.gen.biome.LCCBiomeHelper;
import com.joshmanisdabomb.lcc.registry.LCCBiomes;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BiomeBasedGenerator {

    public static final List<Biome> rubyBiomes = Biome.BIOMES.stream().filter((Biome b) -> b.getCategory() == Biome.Category.OCEAN).collect(Collectors.toList());
    public static final List<Biome> topazBiomes = Biome.BIOMES.stream().filter((Biome b) -> b.getCategory() == Biome.Category.RIVER).collect(Collectors.toList());
    public static final List<Biome> sapphireBiomes = Biome.BIOMES.stream().filter((Biome b) -> b.getCategory() == Biome.Category.DESERT).collect(Collectors.toList());
    public static final List<Biome> amethystBiomes = Biome.BIOMES.stream().filter((Biome b) -> b.getCategory() == Biome.Category.JUNGLE).collect(Collectors.toList());

    public static void init() {
        //Ruby Ore
        OreFeatureConfig ruby_config = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, LCCBlocks.ruby_ore.getDefaultState(), 6);
        DepthAverageConfig ruby_placement = new DepthAverageConfig(6, 40, 40);
        for (Biome b : rubyBiomes) {
            b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(ruby_config).withPlacement(Placement.COUNT_DEPTH_AVERAGE.configure(ruby_placement)));
        }

        //Topaz Ore
        OreFeatureConfig topaz_config = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, LCCBlocks.topaz_ore.getDefaultState(), 6);
        DepthAverageConfig topaz_placement = new DepthAverageConfig(4, 58, 11);
        for (Biome b : topazBiomes) {
            b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(topaz_config).withPlacement(Placement.COUNT_DEPTH_AVERAGE.configure(topaz_placement)));
        }

        //Sapphire Ore
        OreFeatureConfig sapphire_config = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, LCCBlocks.sapphire_ore.getDefaultState(), 6);
        DepthAverageConfig sapphire_placement = new DepthAverageConfig(4, 58, 11);
        for (Biome b : sapphireBiomes) {
            b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(sapphire_config).withPlacement(Placement.COUNT_DEPTH_AVERAGE.configure(sapphire_placement)));
        }

        //Amethyst Ore
        OreFeatureConfig amethyst_config = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, LCCBlocks.amethyst_ore.getDefaultState(), 6);
        DepthAverageConfig amethyst_placement = new DepthAverageConfig(4, 58, 11);
        for (Biome b : amethystBiomes) {
            b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(amethyst_config).withPlacement(Placement.COUNT_DEPTH_AVERAGE.configure(amethyst_placement)));
        }

        //Uranium Ore
        ReplaceBlockConfig uranium_config = new ReplaceBlockConfig(Blocks.STONE.getDefaultState(), LCCBlocks.uranium_ore.getDefaultState());
        CountRangeConfig uranium_placement = new CountRangeConfig(2, 0, 0, 12);
        for (Biome b : Biome.BIOMES) {
            b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.EMERALD_ORE.withConfiguration(uranium_config).withPlacement(Placement.COUNT_RANGE.configure(uranium_placement)));
        }

        //Late Generators
        LCCBiomes.all.stream().filter(biome -> biome instanceof LCCBiomeHelper).forEach(biome -> ((LCCBiomeHelper)biome).lateGenerators());
    }

}
