package com.joshmanisdabomb.lcc.world.feature.config

import com.joshmanisdabomb.lcc.block.BuddingCrystalBlock
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.world.gen.feature.FeatureConfig

class SmallGeodeFeatureConfig(val size: Int, val gem: Block, val bud: BuddingCrystalBlock, val inner: BlockState, val outer: BlockState) : FeatureConfig {

    constructor(size: Int, gem: Identifier, bud: Identifier, inner: BlockState, outer: BlockState) : this(size, Registry.BLOCK[gem], Registry.BLOCK[bud] as BuddingCrystalBlock, inner, outer)

    companion object {
        val codec = RecordCodecBuilder.create<SmallGeodeFeatureConfig> { instance -> instance.group(
            Codec.intRange(0, 64).fieldOf("size").forGetter { it.size },
            Identifier.CODEC.fieldOf("gem").forGetter { Registry.BLOCK.getId(it.gem) },
            Identifier.CODEC.fieldOf("bud").forGetter { Registry.BLOCK.getId(it.bud) },
            BlockState.CODEC.fieldOf("inner").forGetter { it.inner },
            BlockState.CODEC.fieldOf("outer").forGetter { it.outer }
        ).apply(instance, ::SmallGeodeFeatureConfig) }
    }

}
