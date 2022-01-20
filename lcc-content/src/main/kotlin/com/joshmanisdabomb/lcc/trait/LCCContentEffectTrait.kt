package com.joshmanisdabomb.lcc.trait

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.input.Input
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.entity.ai.control.MoveControl
import net.minecraft.entity.mob.MobEntity

interface LCCContentEffectTrait {

    @JvmDefault
    fun lcc_content_handleMobAi(entity: MobEntity, directMovement: MoveControl) = false

    @Environment(EnvType.CLIENT)
    @JvmDefault
    fun lcc_content_modifyLookSpeed(player: ClientPlayerEntity, deltaX: Double, deltaY: Double): Array<Double>? = null

    @Environment(EnvType.CLIENT)
    @JvmDefault
    fun lcc_content_modifyPlayerInput(player: ClientPlayerEntity, input: Input) = Unit

}
