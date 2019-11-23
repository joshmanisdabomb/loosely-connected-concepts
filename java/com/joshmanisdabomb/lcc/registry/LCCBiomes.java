package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.gen.biome.WastelandBiome;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;

import java.util.ArrayList;

public abstract class LCCBiomes {

    public static final ArrayList<Biome> all = new ArrayList<>();

    public static Biome wasteland;

    public static void init(RegistryEvent.Register<Biome> e) {
        all.add(wasteland = new WastelandBiome().setRegistryName(LCC.MODID, "wasteland"));
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

}
