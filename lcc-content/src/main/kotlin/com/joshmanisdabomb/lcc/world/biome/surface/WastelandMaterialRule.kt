package com.joshmanisdabomb.lcc.world.biome.surface

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.util.dynamic.CodecHolder
import net.minecraft.world.gen.surfacebuilder.MaterialRules
import kotlin.random.Random


data class WastelandMaterialRule(val breakage: Float, val then: MaterialRules.MaterialRule?, val `else`: MaterialRules.MaterialRule) : MaterialRules.MaterialRule {

	override fun apply(context: MaterialRules.MaterialRuleContext): MaterialRules.BlockStateRule {
		return MaterialRules.BlockStateRule { x, y, z ->
			if (rand.nextFloat() >= breakage) {
				return@BlockStateRule then?.apply(context)?.tryApply(x, y, z) ?: LCCBlocks.cracked_mud.defaultState
			} else {
				return@BlockStateRule `else`.apply(context).tryApply(x, y, z)
			}
		}
	}

	override fun codec(): CodecHolder<out MaterialRules.MaterialRule> = codec

	companion object {
		val codec = CodecHolder.of(RecordCodecBuilder.create { instance: RecordCodecBuilder.Instance<WastelandMaterialRule> ->
			instance.group(
				Codec.FLOAT.fieldOf("breakage").forGetter(WastelandMaterialRule::breakage),
				MaterialRules.MaterialRule.CODEC.fieldOf("then_run").forGetter(WastelandMaterialRule::then),
				MaterialRules.MaterialRule.CODEC.fieldOf("else_run").forGetter(WastelandMaterialRule::`else`)
			).apply(instance, ::WastelandMaterialRule)
		})

		val rand = Random(8942353564345645465)
	}

}
