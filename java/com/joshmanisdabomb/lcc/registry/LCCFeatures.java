package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.gen.feature.*;
import com.joshmanisdabomb.lcc.gen.feature.placement.UnderSurfaceWithChanceMultiple;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.HugeTreeFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.HeightWithChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;

import java.util.ArrayList;

public abstract class LCCFeatures {

    public static final ArrayList<Feature<?>> all = new ArrayList<>();
    public static final ArrayList<Placement<?>> allPlacements = new ArrayList<>();

    public static OilGeyserFeature oil_geyser;

    public static VividTreeFeature vivid_tree;
    public static BigVividTreeFeature big_vivid_tree;

    public static CandyCaneFeature candy_cane;
    public static BigCandyCaneFeature big_candy_cane;

    public static ChanneliteFeature channelite;

    public static final TreeFeatureConfig CLASSIC_TREE_CONFIG = new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()), new SimpleBlockStateProvider(LCCBlocks.classic_leaves.getDefaultState()), new BlobFoliagePlacer(2, 0)).baseHeight(4).heightRandA(2).foliageHeight(3).ignoreVines().setSapling(LCCBlocks.classic_sapling).build();
    public static final TreeFeatureConfig VIVID_TREE_CONFIG = new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(LCCBlocks.vivid_log.getDefaultState()), new SimpleBlockStateProvider(LCCBlocks.vivid_leaves.getDefaultState()), new BlobFoliagePlacer(2, 0)).baseHeight(7).heightRandA(4).ignoreVines().setSapling(LCCBlocks.vivid_sapling).maxWaterDepth(256).build();
    public static final HugeTreeFeatureConfig BIG_VIVID_TREE_CONFIG = new HugeTreeFeatureConfig.Builder(new SimpleBlockStateProvider(LCCBlocks.vivid_log.getDefaultState()), new SimpleBlockStateProvider(LCCBlocks.vivid_leaves.getDefaultState())).baseHeight(10).func_227283_b_(15).func_227284_c_(3).setSapling(LCCBlocks.vivid_sapling).build();

    public static final UnderSurfaceWithChanceMultiple COUNT_CHANCE_UNDER_SURFACE = new UnderSurfaceWithChanceMultiple(HeightWithChanceConfig::deserialize);

    public static void init(RegistryEvent.Register<Feature<?>> e) {
        all.add((oil_geyser = new OilGeyserFeature(NoFeatureConfig::deserialize, 3, 4)).setRegistryName(LCC.MODID, "oil_geyser"));

        all.add((vivid_tree = new VividTreeFeature(TreeFeatureConfig::func_227338_a_)).setRegistryName(LCC.MODID, "vivid_tree"));
        all.add((big_vivid_tree = new BigVividTreeFeature(HugeTreeFeatureConfig::deserializeSpruce)).setRegistryName(LCC.MODID, "big_vivid_tree"));

        all.add((candy_cane = new CandyCaneFeature(NoFeatureConfig::deserialize, 5, 6)).setRegistryName(LCC.MODID, "candy_cane"));
        all.add((big_candy_cane = new BigCandyCaneFeature(NoFeatureConfig::deserialize, 15, 15)).setRegistryName(LCC.MODID, "big_candy_cane"));

        all.add((channelite = new ChanneliteFeature(NoFeatureConfig::deserialize, 2, 5)).setRegistryName(LCC.MODID, "channelite"));
    }

    public static void initPlacements(RegistryEvent.Register<Placement<?>> e) {
        allPlacements.add(COUNT_CHANCE_UNDER_SURFACE.setRegistryName(LCC.MODID, "count_chance_tops"));
    }

}