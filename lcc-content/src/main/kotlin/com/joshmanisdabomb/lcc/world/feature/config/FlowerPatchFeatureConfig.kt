package com.joshmanisdabomb.lcc.world.feature.config

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.world.gen.feature.FeatureConfig
import net.minecraft.world.gen.stateprovider.BlockStateProvider

class FlowerPatchFeatureConfig(val size: Int, val flower: BlockStateProvider, val base: BlockStateProvider) : FeatureConfig {

    companion object {
        val codec = RecordCodecBuilder.create<FlowerPatchFeatureConfig> { instance -> instance.group(
            Codec.intRange(0, 64).fieldOf("size").forGetter { it.size },
            BlockStateProvider.TYPE_CODEC.fieldOf("flower").forGetter { it.flower },
            BlockStateProvider.TYPE_CODEC.fieldOf("base").forGetter { it.base },
        ).apply(instance, ::FlowerPatchFeatureConfig) }
    }

}
