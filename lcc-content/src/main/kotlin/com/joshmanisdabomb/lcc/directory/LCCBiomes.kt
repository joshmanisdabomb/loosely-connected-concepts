package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import net.minecraft.entity.SpawnGroup
import net.minecraft.sound.BiomeMoodSound
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.BiomeEffects
import net.minecraft.world.biome.GenerationSettings
import net.minecraft.world.biome.SpawnSettings
import net.minecraft.world.biome.SpawnSettings.SpawnEntry
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.feature.DefaultBiomeFeatures
import net.minecraft.world.gen.feature.OrePlacedFeatures

object LCCBiomes : AdvancedDirectory<Biome.Builder, Biome, Unit, Unit>(), RegistryDirectory<Biome, Unit, Unit> {

    override val registry by lazy { BuiltinRegistries.BIOME }

    override fun regId(name: String) = LCC.id(name)

    val wasteland by entry(::biomeInitialiser) {
        Biome.Builder()
            .precipitation(Biome.Precipitation.NONE)
            .temperature(1.23F)
            .downfall(0.0f)
            .effects(BiomeEffects.Builder()
                .grassColor(0x8f8865)
                .foliageColor(0x8f8865)
                .waterColor(0x3f535c)
                .waterFogColor(0x172226)
                .fogColor(0xccc8b4)
                .skyColor(0xb0d4d6)
                .moodSound(BiomeMoodSound.CAVE)
                .build())
            .spawnSettings(SpawnSettings.Builder()
                .spawn(SpawnGroup.MONSTER, SpawnEntry(LCCEntities.baby_skeleton, 10, 1, 3))
                .spawn(SpawnGroup.MONSTER, SpawnEntry(LCCEntities.consumer, 5, 1, 1))
                .spawn(SpawnGroup.MONSTER, SpawnEntry(LCCEntities.rotwitch, 2, 1, 1))
                .spawn(SpawnGroup.MONSTER, SpawnEntry(LCCEntities.disciple, 2, 1, 1))
                .spawn(SpawnGroup.MONSTER, SpawnEntry(LCCEntities.wasp, 1, 1, 1))
                .spawn(SpawnGroup.MONSTER, SpawnEntry(LCCEntities.psycho_pig, 1, 1, 3))
                .spawn(SpawnGroup.CREATURE, SpawnEntry(LCCEntities.woodlouse, 10, 2, 4))
                .build())
            .generationSettings(GenerationSettings.Builder()
                //.carver(GenerationStep.Carver.AIR, LCCConfiguredCarvers.wasteland_cave)
                //.carver(GenerationStep.Carver.AIR, LCCConfiguredCarvers.wasteland_ravine)
                .apply {
                    DefaultBiomeFeatures.addFossils(this)
                    DefaultBiomeFeatures.addMineables(this)
                }
                .feature(GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_COAL_UPPER)
                .feature(GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_COAL_LOWER)
                .feature(GenerationStep.Feature.UNDERGROUND_ORES, LCCPlacedFeatures.abundant_coal)
                .feature(GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_IRON_UPPER)
                .feature(GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_IRON_MIDDLE)
                .feature(GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_IRON_SMALL)
                .feature(GenerationStep.Feature.UNDERGROUND_ORES, LCCPlacedFeatures.abundant_iron)
                .feature(GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_COPPER)
                .feature(GenerationStep.Feature.UNDERGROUND_ORES, LCCPlacedFeatures.abundant_copper)
                .feature(GenerationStep.Feature.UNDERGROUND_ORES, LCCPlacedFeatures.tungsten)
                .apply {
                    DefaultBiomeFeatures.addDefaultDisks(this)
                    DefaultBiomeFeatures.addInfestedStone(this)
                }
                .feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, LCCPlacedFeatures.fortstone_patches)
                .feature(GenerationStep.Feature.SURFACE_STRUCTURES, LCCPlacedFeatures.wasteland_spikes)
                .feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, LCCPlacedFeatures.oil_geyser)
                .feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, LCCPlacedFeatures.oil_pockets)
                //.feature(GenerationStep.Feature.VEGETAL_DECORATION, LCCPlacedFeatures.deposits)
                .feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, LCCPlacedFeatures.landmines)
                .feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, LCCPlacedFeatures.spike_trap)
                .feature(GenerationStep.Feature.VEGETAL_DECORATION, LCCPlacedFeatures.deadwood_logs)
                .feature(GenerationStep.Feature.VEGETAL_DECORATION, LCCPlacedFeatures.wasp_hive)
                .feature(GenerationStep.Feature.VEGETAL_DECORATION, LCCPlacedFeatures.clover_patch)
                .feature(GenerationStep.Feature.VEGETAL_DECORATION, LCCPlacedFeatures.forget_me_not_patch)
                .feature(GenerationStep.Feature.UNDERGROUND_ORES, LCCPlacedFeatures.uranium)
                .feature(GenerationStep.Feature.UNDERGROUND_ORES, LCCPlacedFeatures.uranium_wasteland)
                .build())
    }

    fun biomeInitialiser(input: Biome.Builder, context: DirectoryContext<Unit>, parameters: Unit): Biome {
        return initialiser(input.build(), context, parameters)
    }

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = Unit

}
