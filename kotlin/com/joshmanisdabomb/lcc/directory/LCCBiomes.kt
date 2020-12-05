package com.joshmanisdabomb.lcc.directory

import net.fabricmc.fabric.api.biome.v1.OverworldBiomes
import net.fabricmc.fabric.api.biome.v1.OverworldClimate
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.sound.BiomeMoodSound
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.BiomeEffects
import net.minecraft.world.biome.GenerationSettings
import net.minecraft.world.biome.SpawnSettings
import net.minecraft.world.biome.SpawnSettings.SpawnEntry
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.feature.DefaultBiomeFeatures

object LCCBiomes : RegistryDirectory<Biome, Function1<RegistryKey<Biome>, Unit>>() {

    override val _registry by lazy { BuiltinRegistries.BIOME }

    val wasteland by create({ OverworldBiomes.addContinentalBiome(it, OverworldClimate.DRY, 0.3); OverworldBiomes.setRiverBiome(it, it) }) {
        Biome.Builder()
            .precipitation(Biome.Precipitation.NONE)
            .category(Biome.Category.DESERT)
            .depth(0.1f)
            .scale(-0.1f)
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
                .spawn(SpawnGroup.MONSTER, SpawnEntry(EntityType.SPIDER, 10, 2, 4))
                .spawn(SpawnGroup.MONSTER, SpawnEntry(EntityType.CAVE_SPIDER, 10, 4, 8))
                .spawnCost(EntityType.SPIDER, 0.1, 0.12)
                .spawnCost(EntityType.CAVE_SPIDER, 0.1, 0.12)
                .build())
            .generationSettings(GenerationSettings.Builder()
                .surfaceBuilder(LCCConfiguredSurfaceBuilders.wasteland)
                .carver(GenerationStep.Carver.AIR, LCCConfiguredCarvers.wasteland_cave)
                .carver(GenerationStep.Carver.AIR, LCCConfiguredCarvers.wasteland_ravine)
                .feature(GenerationStep.Feature.UNDERGROUND_ORES, LCCConfiguredFeatures.abundant_coal)
                .feature(GenerationStep.Feature.UNDERGROUND_ORES, LCCConfiguredFeatures.abundant_iron)
                .feature(GenerationStep.Feature.UNDERGROUND_ORES, LCCConfiguredFeatures.abundant_copper)
                .feature(GenerationStep.Feature.UNDERGROUND_ORES, LCCConfiguredFeatures.uranium_wasteland)
                .feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, LCCConfiguredFeatures.oil_geyser)
                .feature(GenerationStep.Feature.UNDERGROUND_ORES, LCCConfiguredFeatures.oil_hidden)
                .apply {
                    DefaultBiomeFeatures.addMineables(this)
                    DefaultBiomeFeatures.addDefaultDisks(this)
                    DefaultBiomeFeatures.addInfestedStone(this)
                    DefaultBiomeFeatures.addFossils(this)
                }.build()).build()
    }

    override fun register(key: String, thing: Biome, properties: (RegistryKey<Biome>) -> Unit) = super.register(key, thing, properties).apply { properties(this) }

}
