package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.gen.feature.OilGeyserFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.event.RegistryEvent;

import java.util.ArrayList;

public abstract class LCCFeatures {

    public static final ArrayList<Feature<?>> all = new ArrayList<>();

    public static OilGeyserFeature oil_geyser;

    public static void init(RegistryEvent.Register<Feature<?>> e) {
        all.add((oil_geyser = new OilGeyserFeature(NoFeatureConfig::deserialize, false, 3, 4)).setRegistryName(LCC.MODID, "oil_geyser"));
    }

}