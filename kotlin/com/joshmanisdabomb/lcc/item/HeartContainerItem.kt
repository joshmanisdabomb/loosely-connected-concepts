package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.concepts.hearts.HeartType
import net.minecraft.entity.LivingEntity

class HeartContainerItem(heart: HeartType, value: Float, settings: Settings) : HeartItem(heart, value, settings) {

    override fun canUseHeart(user: LivingEntity) = heart.getMaxHealth(user) < heart.getDefaultMaxLimit(user)

    override fun useHeart(user: LivingEntity) = heart.addMaxHealth(user, value)

}