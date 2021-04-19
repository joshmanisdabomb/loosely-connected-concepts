package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.abstracts.nuclear.NuclearUtil
import com.joshmanisdabomb.lcc.directory.LCCComponents
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyContext
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyStorage
import com.joshmanisdabomb.lcc.extensions.transformInt
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.item.ModelPredicateProvider
import net.minecraft.item.Item

class RadiationDetectorItem(val energy: Float, settings: Settings) : Item(settings), StackEnergyStorage {

    override fun getRawEnergyMaximum(context: StackEnergyContext) = energy

    @Environment(EnvType.CLIENT)
    fun getWinterPredicate() = ModelPredicateProvider { stack, world, entity, _ ->
        val w = world ?: entity?.world ?: MinecraftClient.getInstance().world ?: return@ModelPredicateProvider 0f
        val winter = LCCComponents.nuclear.getNullable(w)?.winter
        NuclearUtil.getWinterLevel(winter?.minus((w.random.nextFloat() > 0.2f.plus(winter.rem(1f).times(0.8f))).transformInt())?.coerceAtLeast(0f) ?: 0f).toFloat()
    }

}