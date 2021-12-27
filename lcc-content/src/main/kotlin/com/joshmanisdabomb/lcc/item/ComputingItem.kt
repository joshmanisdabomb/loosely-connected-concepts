package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.abstracts.nuclear.NuclearUtil
import com.joshmanisdabomb.lcc.directory.LCCComponents
import com.joshmanisdabomb.lcc.extensions.transformInt
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.item.UnclampedModelPredicateProvider
import net.minecraft.item.Item
import kotlin.math.log2

open class ComputingItem(val initialLevel: Int, val maxLevel: Int, settings: Settings, val upgrader: ((space: Int) -> Int?)? = null) : Item(settings) {

    @Environment(EnvType.CLIENT)
    fun getDivPredicate() = UnclampedModelPredicateProvider { stack, _, _, _ ->
        val level = stack.getSubNbt("lcc-computing")?.getInt("level") ?: return@UnclampedModelPredicateProvider 0f
        return@UnclampedModelPredicateProvider level.toFloat().minus(initialLevel).div(maxLevel-initialLevel)
    }

    @Environment(EnvType.CLIENT)
    fun getLog2Predicate() = UnclampedModelPredicateProvider { stack, _, _, _ ->
        val level = stack.getSubNbt("lcc-computing")?.getInt("level") ?: return@UnclampedModelPredicateProvider 0f
        return@UnclampedModelPredicateProvider log2(level.toFloat().div(initialLevel)).div(log2(maxLevel.toFloat().div(initialLevel)))
    }

}