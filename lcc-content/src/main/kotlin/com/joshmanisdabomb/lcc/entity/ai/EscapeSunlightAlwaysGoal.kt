package com.joshmanisdabomb.lcc.entity.ai

import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.ai.goal.EscapeSunlightGoal
import net.minecraft.entity.mob.PathAwareEntity

class EscapeSunlightAlwaysGoal(mob: PathAwareEntity, speed: Double) : EscapeSunlightGoal(mob, speed) {

    override fun canStart(): Boolean {
        return if (mob.target == null && mob.world.isDay && mob.world.isSkyVisible(mob.blockPos) && mob.getEquippedStack(EquipmentSlot.HEAD).isEmpty) targetShadedPos() else false
    }

}