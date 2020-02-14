package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.gen.dimension.LCCDimensionHelper;
import com.joshmanisdabomb.lcc.gen.dimension.MultiBiomeProvider;
import com.joshmanisdabomb.lcc.gen.dimension.RainbowDimensionRegistry;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class LCCDimensions {

    public static final ArrayList<ModDimension> all = new ArrayList<>();
    public static final ArrayList<BiomeProviderType<?, ?>> allBiomeProviders = new ArrayList<>();

    public static RainbowDimensionRegistry rainbow;

    public static BiomeProviderType<MultiBiomeProvider.MultiBiomeProviderSettings, MultiBiomeProvider> multiple_biomes;

    public static void init(RegistryEvent.Register<ModDimension> e) {
        all.add((rainbow = new RainbowDimensionRegistry()).setRegistryName(LCC.MODID, "rainbow"));
    }

    public static void initBiomeProviders(RegistryEvent.Register<BiomeProviderType<?, ?>> e) {
        allBiomeProviders.add((multiple_biomes = new BiomeProviderType<>(MultiBiomeProvider::new, MultiBiomeProvider.MultiBiomeProviderSettings::new)).setRegistryName(LCC.MODID, "mutliple_biomes"));
    }

    public static void initManagerRegistry() {
        for (ModDimension d : all) {
            ((LCCDimensionHelper)d).getType();
        }
    }

}
