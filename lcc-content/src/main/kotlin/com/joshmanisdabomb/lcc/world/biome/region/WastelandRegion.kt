package com.joshmanisdabomb.lcc.world.biome.region

import com.mojang.datafixers.util.Pair
import net.minecraft.util.Identifier
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.source.util.MultiNoiseUtil.NoiseHypercube
import net.minecraft.world.biome.source.util.MultiNoiseUtil.ParameterRange
import terrablender.api.ParameterUtils.*
import terrablender.api.Region
import terrablender.api.RegionType
import java.util.function.Consumer


class WastelandRegion(name: Identifier, val biome: Identifier, type: RegionType, weight: Int) : Region(name, type, weight) {

    override fun addBiomes(registry: Registry<Biome>, mapper: Consumer<Pair<NoiseHypercube, RegistryKey<Biome>>>) {
        this.addModifiedVanillaOverworldBiomes(mapper) {}
        this.addBiome(mapper,
            ParameterRange.of(0.2f, 1.0f),
            ParameterRange.of(-1.0f, -0.2f),
            Continentalness.span(Continentalness.NEAR_INLAND, Continentalness.FAR_INLAND),
            Erosion.span(Erosion.EROSION_5, Erosion.EROSION_6),
            Weirdness.FULL_RANGE.parameter(),
            Depth.SURFACE.parameter(),
            0.0f,
            RegistryKey.of(BuiltinRegistries.BIOME.key, biome)
        )
    }

}