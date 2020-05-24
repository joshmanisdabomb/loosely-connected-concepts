package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.gen.biome.*;
import com.joshmanisdabomb.lcc.gen.surface.ColorfulSurfaceBuilder;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;

import java.util.ArrayList;

public abstract class LCCBiomes {

    public static final ArrayList<Biome> all = new ArrayList<>();
    public static final ArrayList<SurfaceBuilder<?>> allSurfaceBuilders = new ArrayList<>();

    //Overworld
    public static WastelandBiome wasteland;

    //Rainbow
    public static RainbowCandylandBiome rainbow_candyland;
    public static RainbowColorfulBiome rainbow_colorful;
    public static RainbowStarlightBiome rainbow_starlight;
    public static RainbowTerreneBiome rainbow_terrene;

    public static ColorfulSurfaceBuilder colorful = new ColorfulSurfaceBuilder(SurfaceBuilderConfig::deserialize);

    public static void init(RegistryEvent.Register<Biome> e) {
        all.add((wasteland = new WastelandBiome()).setRegistryName(LCC.MODID, "wasteland"));
        all.add((rainbow_candyland = new RainbowCandylandBiome()).setRegistryName(LCC.MODID, "rainbow_candyland"));
        all.add((rainbow_colorful = new RainbowColorfulBiome()).setRegistryName(LCC.MODID, "rainbow_colorful"));
        all.add((rainbow_starlight = new RainbowStarlightBiome()).setRegistryName(LCC.MODID, "rainbow_starlight"));
        all.add((rainbow_terrene = new RainbowTerreneBiome()).setRegistryName(LCC.MODID, "rainbow_terrene"));
    }

    public static void initEntries() {
        entry(wasteland, false, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.DRY, BiomeDictionary.Type.SAVANNA, BiomeDictionary.Type.SPOOKY, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.RARE, BiomeDictionary.Type.WASTELAND);
    }

    private static void entry(Biome b, boolean spawnable, BiomeDictionary.Type... types) {
        BiomeDictionary.addTypes(b, types);
        //TODO: 4
        BiomeManager.addBiome(BiomeManager.BiomeType.DESERT, new BiomeManager.BiomeEntry(b, 200));
        if (spawnable) BiomeManager.addSpawnBiome(b);
    }

    public static void initSurfaceBuilders(RegistryEvent.Register<SurfaceBuilder<?>> e) {
        allSurfaceBuilders.add(colorful.setRegistryName(LCC.MODID, "colorful"));
    }

}
