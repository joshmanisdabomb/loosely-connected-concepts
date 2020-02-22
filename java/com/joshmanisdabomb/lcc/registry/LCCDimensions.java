package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.gen.dimension.LCCDimensionHelper;
import com.joshmanisdabomb.lcc.gen.dimension.RainbowDimensionRegistry;
import com.joshmanisdabomb.lcc.gen.dimension.generator.AmplifiedEndChunkGenerator;
import com.joshmanisdabomb.lcc.gen.dimension.provider.MultiBiomeProvider;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.EndGenerationSettings;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;

import java.util.ArrayList;

public abstract class LCCDimensions {

    public static final ArrayList<ModDimension> all = new ArrayList<>();
    public static final ArrayList<BiomeProviderType<?, ?>> allBiomeProviders = new ArrayList<>();
    public static final ArrayList<ChunkGeneratorType<?, ?>> allChunkGenerators = new ArrayList<>();

    public static RainbowDimensionRegistry rainbow;

    public static BiomeProviderType<MultiBiomeProvider.MultiBiomeProviderSettings, MultiBiomeProvider> multiple_biomes;

    public static ChunkGeneratorType<EndGenerationSettings, AmplifiedEndChunkGenerator> floating_islands_amplified;

    public static void init(RegistryEvent.Register<ModDimension> e) {
        all.add((rainbow = new RainbowDimensionRegistry()).setRegistryName(LCC.MODID, "rainbow"));
    }

    public static void initBiomeProviders(RegistryEvent.Register<BiomeProviderType<?, ?>> e) {
        allBiomeProviders.add((multiple_biomes = new BiomeProviderType<>(MultiBiomeProvider::new, MultiBiomeProvider.MultiBiomeProviderSettings::new)).setRegistryName(LCC.MODID, "multiple_biomes"));
    }

    public static void initChunkGenerators(RegistryEvent.Register<ChunkGeneratorType<?, ?>> e) {
        allChunkGenerators.add((floating_islands_amplified = new ChunkGeneratorType<>(AmplifiedEndChunkGenerator::new, false, EndGenerationSettings::new)).setRegistryName(LCC.MODID, "floating_islands_amplified"));
    }

    public static void initManagerRegistry() {
        for (ModDimension d : all) {
            ((LCCDimensionHelper)d).getType();
        }
    }

}
