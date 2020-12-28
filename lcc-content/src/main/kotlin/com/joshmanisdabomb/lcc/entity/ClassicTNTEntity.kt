package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedEntity
import com.joshmanisdabomb.lcc.directory.LCCEntities
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.TntEntity
import net.minecraft.world.World

class ClassicTNTEntity(type: EntityType<out ClassicTNTEntity>, world: World) : TntEntity(type, world), LCCExtendedEntity {

    private var placer: LivingEntity? = null

    constructor(world: World, x: Double, y: Double, z: Double, igniter: LivingEntity?) : this(LCCEntities.classic_tnt, world) {
        updatePosition(x, y, z)
        val d = world.random.nextDouble() * 6.2831854820251465
        this.setVelocity(-Math.sin(d) * 0.02, 0.20000000298023224, -Math.cos(d) * 0.02)
        this.fuse = 80
        prevX = x
        prevY = y
        prevZ = z
        placer = igniter
    }

    override fun getCausingEntity() = placer

    override fun createSpawnPacket() = lcc_createSpawnPacket()

}