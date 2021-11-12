package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.abstracts.heart.HeartType
import com.joshmanisdabomb.lcc.directory.LCCCriteria
import net.minecraft.entity.LivingEntity
import net.minecraft.server.network.ServerPlayerEntity

class HeartContainerItem(heart: HeartType, value: Float, settings: Settings) : HeartItem(heart, value, settings) {

    override fun canUseHeart(user: LivingEntity) = heart.getMaxHealth(user) < heart.getDefaultMaxLimit(user)

    override fun useHeart(user: LivingEntity) {
        heart.addMaxHealth(user, value)
        if (user is ServerPlayerEntity) LCCCriteria.heart_container.trigger(user, heart, heart.getMaxHealth(user).toDouble())
    }

}