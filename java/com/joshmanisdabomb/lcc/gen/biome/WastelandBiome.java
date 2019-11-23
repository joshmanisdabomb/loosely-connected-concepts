package com.joshmanisdabomb.lcc.gen.biome;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class WastelandBiome extends Biome {

    private static final BlockState CRACKED_MUD = LCCBlocks.cracked_mud.getDefaultState();

    public WastelandBiome() {
        super(new Biome.Builder().surfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(CRACKED_MUD, CRACKED_MUD, CRACKED_MUD))).precipitation(RainType.NONE).category(Category.DESERT).downfall(0.0F).depth(0.1F).scale(-0.1F).temperature(1.23F).waterColor(0x3f535c).waterFogColor(0x172226).parent(null));
        this.addStructure(Feature.MINESHAFT, new MineshaftConfig(0.004D, MineshaftStructure.Type.NORMAL));
        this.addStructure(Feature.STRONGHOLD, IFeatureConfig.NO_FEATURE_CONFIG);
        DefaultBiomeFeatures.addCarvers(this);
        DefaultBiomeFeatures.addStructures(this);
        DefaultBiomeFeatures.addMonsterRooms(this);
        DefaultBiomeFeatures.addStoneVariants(this);
        DefaultBiomeFeatures.addFossils(this);
    }

    @Override
    public int getSkyColorByTemp(float p_76731_1_) {
        return super.getSkyColorByTemp(p_76731_1_);
    }

    @Override
    public int getGrassColor(BlockPos p_180627_1_) {
        return 0x8f8865;
    }

    @Override
    public int getFoliageColor(BlockPos p_180625_1_) {
        return 0x8f8865;
    }

    @Override
    public Biome getRiver() {
        return this;
    }



}