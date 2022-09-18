package com.joshmanisdabomb.lcc.world.feature.config

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.block.BlockState
import net.minecraft.world.gen.feature.FeatureConfig

class FlowerPatchFeatureConfig(val size: Int, val flower: BlockState, val base: BlockState) : FeatureConfig {

    companion object {
        val codec = RecordCodecBuilder.create<FlowerPatchFeatureConfig> { instance -> instance.group(
            Codec.intRange(0, 64).fieldOf("size").forGetter { it.size },
            BlockState.CODEC.fieldOf("flower").forGetter { it.flower },
            BlockState.CODEC.fieldOf("base").forGetter { it.base },
        ).apply(instance, ::FlowerPatchFeatureConfig) }
    }

}
