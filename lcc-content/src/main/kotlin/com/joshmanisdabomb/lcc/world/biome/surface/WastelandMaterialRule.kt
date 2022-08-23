package com.joshmanisdabomb.lcc.world.biome.surface

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.mixin.content.common.MaterialRuleContextAccessor
import com.mojang.serialization.MapCodec
import net.minecraft.block.Blocks
import net.minecraft.util.dynamic.CodecHolder
import net.minecraft.world.gen.noise.NoiseParametersKeys
import net.minecraft.world.gen.surfacebuilder.MaterialRules

class WastelandMaterialRule : MaterialRules.MaterialRule {

	override fun apply(context: MaterialRules.MaterialRuleContext): MaterialRules.BlockStateRule {
		val sampler = (context as MaterialRuleContextAccessor).noiseConfig.getOrCreateSampler(NoiseParametersKeys.NETHERRACK)
		return MaterialRules.BlockStateRule { x, y, z ->
			val sample = sampler.sample(x.toDouble(), 0.0, z.toDouble())
			val depth = (context as MaterialRuleContextAccessor).stoneDepthAbove
			var length = (sample.toString().last().digitToIntOrNull() ?: 0).mod(4)
			for (i in 2 downTo 0) {
				if (y < 80.plus(i.times(6))) length = length.mod(i.plus(1))
			}
			if (depth >= length.plus(1)) {
				return@BlockStateRule LCCBlocks.cracked_mud.defaultState
			}
			return@BlockStateRule Blocks.AIR.defaultState
		}
	}

	override fun codec(): CodecHolder<out MaterialRules.MaterialRule> = codec

	companion object {
		val codec = CodecHolder.of(MapCodec.unit(::WastelandMaterialRule))
	}

}
