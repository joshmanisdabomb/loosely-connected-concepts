package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.gen.feature.BigCandyCaneFeature;
import com.joshmanisdabomb.lcc.gen.feature.CandyCaneFeature;
import com.joshmanisdabomb.lcc.gen.feature.ChanneliteFeature;
import com.joshmanisdabomb.lcc.gen.feature.OilGeyserFeature;
import com.joshmanisdabomb.lcc.gen.feature.placement.TopsWithChanceConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.HeightWithChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;

import java.util.ArrayList;

public abstract class LCCFeatures {

    public static final ArrayList<Feature<?>> all = new ArrayList<>();
    public static final ArrayList<Placement<?>> allPlacements = new ArrayList<>();

    public static OilGeyserFeature oil_geyser;

    public static CandyCaneFeature candy_cane;
    public static BigCandyCaneFeature big_candy_cane;

    public static ChanneliteFeature channelite;

    public static final TopsWithChanceConfig COUNT_CHANCE_TOPS = new TopsWithChanceConfig(HeightWithChanceConfig::deserialize);

    public static void init(RegistryEvent.Register<Feature<?>> e) {
        all.add((oil_geyser = new OilGeyserFeature(NoFeatureConfig::deserialize, 3, 4)).setRegistryName(LCC.MODID, "oil_geyser"));

        all.add((candy_cane = new CandyCaneFeature(NoFeatureConfig::deserialize, 5, 6)).setRegistryName(LCC.MODID, "candy_cane"));
        all.add((big_candy_cane = new BigCandyCaneFeature(NoFeatureConfig::deserialize, 15, 15)).setRegistryName(LCC.MODID, "big_candy_cane"));

        all.add((channelite = new ChanneliteFeature(NoFeatureConfig::deserialize, 2, 5)).setRegistryName(LCC.MODID, "channelite"));
    }

    public static void initPlacements(RegistryEvent.Register<Placement<?>> e) {
        allPlacements.add(COUNT_CHANCE_TOPS.setRegistryName(LCC.MODID, "count_chance_tops"));
    }

}