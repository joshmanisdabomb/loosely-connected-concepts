package com.joshmanisdabomb.lcc.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.SpawnGroup
import net.minecraft.entity.SpawnReason
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.mob.MobEntity
import net.minecraft.predicate.entity.EntityPredicates
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.random.Random
import net.minecraft.world.Difficulty

class SpawningPitBlock(settings: Settings) : Block(settings) {

    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        if (world.difficulty == Difficulty.PEACEFUL) return super.randomTick(state, world, pos, random)
        if (random.nextInt(10) == 0) return super.randomTick(state, world, pos, random)
        val origin = Vec3d.ofBottomCenter(pos.up())
        if (world.getEntitiesByClass(HostileEntity::class.java, Box.of(origin, 8.0, 8.0, 8.0), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR).isNotEmpty()) return super.randomTick(state, world, pos, random)
        val biome = world.getBiome(pos)
        val spawns = world.chunkManager.chunkGenerator.getEntitySpawnList(biome, world.structureAccessor, SpawnGroup.MONSTER, pos)
        val spawn = spawns.getOrEmpty(random).orElse(null) ?: return super.randomTick(state, world, pos, random)
        if (!world.isSpaceEmpty(spawn.type.createSimpleBoundingBox(origin.x, origin.y, origin.z))) return super.randomTick(state, world, pos, random)
        val entity = spawn.type.create(world) as? MobEntity ?: return super.randomTick(state, world, pos, random)
        entity.refreshPositionAndAngles(origin.x, origin.y, origin.z, world.random.nextFloat() * 360.0f, 0.0f)
        if (!entity.canSpawn(world)) return super.randomTick(state, world, pos, random)
        entity.initialize(world, world.getLocalDifficulty(pos), SpawnReason.SPAWNER, null, null)
        world.spawnEntityAndPassengers(entity)
        return super.randomTick(state, world, pos, random)
    }

}
