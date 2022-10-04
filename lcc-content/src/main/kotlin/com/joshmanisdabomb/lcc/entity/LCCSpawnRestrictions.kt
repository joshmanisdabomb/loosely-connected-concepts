package com.joshmanisdabomb.lcc.entity

import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnReason
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.mob.MobEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random
import net.minecraft.world.Difficulty
import net.minecraft.world.ServerWorldAccess

object LCCSpawnRestrictions {

    fun canSpawnInDarkOrSkylight(type: EntityType<out HostileEntity>, world: ServerWorldAccess, spawnReason: SpawnReason, pos: BlockPos, random: Random): Boolean {
        return world.difficulty != Difficulty.PEACEFUL && (world.isSkyVisible(pos) || HostileEntity.isSpawnDark(world, pos, random)) && MobEntity.canMobSpawn(type, world, spawnReason, pos, random)
    }

    fun canSpawnInSkylight(type: EntityType<out MobEntity>, world: ServerWorldAccess, spawnReason: SpawnReason, pos: BlockPos, random: Random): Boolean {
        return world.isSkyVisible(pos) && MobEntity.canMobSpawn(type, world, spawnReason, pos, random)
    }

}