package com.joshmanisdabomb.lcc.world.placement

import com.joshmanisdabomb.lcc.directory.LCCPlacementModifierTypes
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.util.math.BlockPos
import net.minecraft.world.gen.feature.FeaturePlacementContext
import net.minecraft.world.gen.heightprovider.HeightProvider
import net.minecraft.world.gen.placementmodifier.AbstractConditionalPlacementModifier

class HeightThreshold(val provider: HeightProvider, val reverse: Boolean = false) : AbstractConditionalPlacementModifier() {

    override fun getType() = LCCPlacementModifierTypes.height_threshold

    override fun shouldPlace(context: FeaturePlacementContext, random: net.minecraft.util.math.random.Random, pos: BlockPos): Boolean {
        return if (reverse) pos.y <= provider.get(random, context) else pos.y >= provider.get(random, context)
    }

    companion object {
        val codec = RecordCodecBuilder.create { instance: RecordCodecBuilder.Instance<HeightThreshold> ->
            instance.group(
                HeightProvider.CODEC.fieldOf("provider").forGetter { it.provider },
                Codec.BOOL.fieldOf("reverse").forGetter { it.reverse },
            ).apply(instance, ::HeightThreshold)
        }
    }

}