package com.joshmanisdabomb.lcc.adaptation.boat

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.vehicle.BoatEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.world.World

open class LCCBoatEntity(type: EntityType<out LCCBoatEntity>, override val lcc_boat: LCCBoatType, world: World) : BoatEntity(type, world), LCCBoat, LCCExtendedEntity {

    override fun getBoatType() = lcc_boat.base

    override fun asItem() = lcc_boat.item.asItem()

    override fun readCustomDataFromNbt(nbt: NbtCompound) = Unit

    override fun writeCustomDataToNbt(nbt: NbtCompound) = Unit

    override fun createSpawnPacket() = lcc_createSpawnPacket()

    fun placed(x: Double, y: Double, z: Double, yaw: Float) {
        setPosition(x, y, z)
        prevX = x
        prevY = y
        prevZ = z
        this.boatType = lcc_boat.base
        this.yaw = yaw
    }

}