package com.joshmanisdabomb.lcc.gen;

import com.joshmanisdabomb.lcc.LCCBlocks;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.MinableConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BiomeBasedGenerator {

    public static final List<Biome> rubyBiomes = Biome.BIOMES.stream().filter((Biome b) -> b.getCategory() == Biome.Category.OCEAN).collect(Collectors.toList());
    public static final List<Biome> topazBiomes = Biome.BIOMES.stream().filter((Biome b) -> b.getCategory() == Biome.Category.RIVER).collect(Collectors.toList());
    public static final List<Biome> sapphireBiomes = Biome.BIOMES.stream().filter((Biome b) -> b.getCategory() == Biome.Category.DESERT).collect(Collectors.toList());
    public static final List<Biome> amethystBiomes = Biome.BIOMES.stream().filter((Biome b) -> b.getCategory() == Biome.Category.JUNGLE).collect(Collectors.toList());

    public static void init() {
        //Ruby Ore
        MinableConfig ruby_config = new MinableConfig(MinableConfig.IS_ROCK, LCCBlocks.ruby_ore.getDefaultState(), 6);
        DepthAverageConfig ruby_placement = new DepthAverageConfig(6, 40, 40);
        for (Biome b : rubyBiomes) {
            b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createCompositeFeature(Feature.MINABLE, ruby_config, Biome.DEPTH_AVERAGE, ruby_placement));
        }

        //Topaz Ore
        MinableConfig topaz_config = new MinableConfig(MinableConfig.IS_ROCK, LCCBlocks.topaz_ore.getDefaultState(), 6);
        DepthAverageConfig topaz_placement = new DepthAverageConfig(4, 58, 11);
        for (Biome b : topazBiomes) {
            b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createCompositeFeature(Feature.MINABLE, topaz_config, Biome.DEPTH_AVERAGE, topaz_placement));
        }

        //Sapphire Ore
        MinableConfig sapphire_config = new MinableConfig(MinableConfig.IS_ROCK, LCCBlocks.sapphire_ore.getDefaultState(), 6);
        DepthAverageConfig sapphire_placement = new DepthAverageConfig(4, 58, 11);
        for (Biome b : sapphireBiomes) {
            b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createCompositeFeature(Feature.MINABLE, sapphire_config, Biome.DEPTH_AVERAGE, sapphire_placement));
        }

        //Amethyst Ore
        MinableConfig amethyst_config = new MinableConfig(MinableConfig.IS_ROCK, LCCBlocks.amethyst_ore.getDefaultState(), 6);
        DepthAverageConfig amethyst_placement = new DepthAverageConfig(4, 58, 11);
        for (Biome b : amethystBiomes) {
            b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createCompositeFeature(Feature.MINABLE, amethyst_config, Biome.DEPTH_AVERAGE, amethyst_placement));
        }

        //Uranium Ore
        ReplaceBlockConfig uranium_config = new ReplaceBlockConfig(BlockMatcher.forBlock(Blocks.STONE), LCCBlocks.uranium_ore.getDefaultState());
        CountRangeConfig uranium_placement = new CountRangeConfig(2, 0, 0, 12);
        for (Biome b : Biome.BIOMES) {
            b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createCompositeFeature(Feature.REPLACE_BLOCK, uranium_config, Biome.COUNT_RANGE, uranium_placement));
        }
    }



}
