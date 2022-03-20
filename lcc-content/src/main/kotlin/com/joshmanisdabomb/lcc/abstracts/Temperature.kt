package com.joshmanisdabomb.lcc.abstracts

import com.joshmanisdabomb.lcc.directory.tags.LCCBlockTags
import com.joshmanisdabomb.lcc.directory.tags.LCCEntityTags
import com.joshmanisdabomb.lcc.trait.LCCContentBlockTrait
import com.joshmanisdabomb.lcc.trait.LCCContentEntityTrait
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.predicate.entity.EntityPredicates
import net.minecraft.tag.TagKey
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.world.BlockView
import net.minecraft.world.EntityView

enum class Temperature(val energy: Float, val tag: TagKey<Block>, val soulTag: TagKey<Block>? = null, val entityTag: TagKey<EntityType<*>>? = null) {

    LUKEWARM(0.001f, LCCBlockTags.temperature_lukewarm),
    WARM(0.0015f, LCCBlockTags.temperature_warm),
    HOT(0.01f, LCCBlockTags.temperature_hot),
    SCALDING(0.05f, LCCBlockTags.temperature_scalding, soulTag = LCCBlockTags.temperature_soul_scalding, entityTag = LCCEntityTags.temperature_scalding),
    BURNING(0.01f, LCCBlockTags.temperature_burning, soulTag = LCCBlockTags.temperature_soul_burning),
    SCORCHING(0.15f, LCCBlockTags.temperature_scorching, soulTag = LCCBlockTags.temperature_soul_scorching),
    RED_HOT(0.2f, LCCBlockTags.temperature_red_hot, entityTag = LCCEntityTags.temperature_red_hot),
    NUCLEAR(5f, LCCBlockTags.temperature_nuclear);

    val entityEnergy = energy.times(200)

    companion object {
        fun getBlockEnergy(world: BlockView, pos: BlockPos, source: BlockState = world.getBlockState(pos), block: Block = source.block) = (block as? LCCContentBlockTrait)?.run { lcc_content_getTemperature(world, source, pos)?.let { lcc_content_getTemperatureEnergy(world, source, pos, it) } }
        fun getEntityEnergy(entity: Entity) = (entity as? LCCContentEntityTrait)?.run { lcc_content_getTemperature()?.let { lcc_content_getTemperatureEnergy(it) } }

        fun getEnergyFromPos(world: BlockView, pos: BlockPos, source: BlockState, block: Block): Float? {
            getBlockEnergy(world, pos, source, block)?.also { return it }
            if (world is EntityView) {
                val energy = world.getEntitiesByClass(Entity::class.java, Box(pos), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR).maxOfOrNull { e ->
                    val pos2 = e.blockPos
                    if (pos2.x != pos.x || pos2.z != pos.z) return@maxOfOrNull 0f
                    getEntityEnergy(e) ?: 0f
                }
                return energy?.let { if (it <= 0f) null else it }
            }
            return null
        }
    }

}