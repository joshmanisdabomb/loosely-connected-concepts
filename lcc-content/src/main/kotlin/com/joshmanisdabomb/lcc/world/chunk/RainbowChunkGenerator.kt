package com.joshmanisdabomb.lcc.world.chunk

import com.mojang.datafixers.util.Function4
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.structure.StructureSet
import net.minecraft.util.dynamic.RegistryOps
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryEntry
import net.minecraft.world.biome.source.BiomeSource
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings
import net.minecraft.world.gen.chunk.NoiseChunkGenerator


class RainbowChunkGenerator(structures: Registry<StructureSet>, val noise: Registry<DoublePerlinNoiseSampler.NoiseParameters>, source: BiomeSource, settings: RegistryEntry<ChunkGeneratorSettings>) : NoiseChunkGenerator(structures, noise, source, settings) {

    init {

    }

    override fun getCodec() = Companion.codec

    companion object {
        val codec = RecordCodecBuilder.create { instance: RecordCodecBuilder.Instance<RainbowChunkGenerator> ->
            createStructureSetRegistryGetter(instance).and(
                instance.group(
                    RegistryOps.createRegistryCodec(Registry.NOISE_KEY).forGetter { g: RainbowChunkGenerator -> g.noise },
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter { g: RainbowChunkGenerator -> g.biomeSource },
                    ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings").forGetter { g: RainbowChunkGenerator -> g.settings },
                )
            )
            .apply(instance, instance.stable(Function4(::RainbowChunkGenerator)))
        }
    }

}