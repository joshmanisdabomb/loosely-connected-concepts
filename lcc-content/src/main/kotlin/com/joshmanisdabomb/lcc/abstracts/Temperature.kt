package com.joshmanisdabomb.lcc.abstracts

import com.joshmanisdabomb.lcc.trait.LCCContentBlockTrait
import com.joshmanisdabomb.lcc.trait.LCCContentEntityTrait
import com.joshmanisdabomb.lcc.directory.LCCTags
import net.minecraft.block.*
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.predicate.entity.EntityPredicates
import net.minecraft.tag.Tag
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.world.BlockView
import net.minecraft.world.EntityView

enum class Temperature(val energy: Float, val tag: Tag<Block>, val soulTag: Tag<Block>? = null, val entityTag: Tag<EntityType<*>>? = null) {

    LUKEWARM(0.001f, LCCTags.temperature_lukewarm),
    WARM(0.0015f, LCCTags.temperature_warm),
    HOT(0.01f, LCCTags.temperature_hot),
    SCALDING(0.05f, LCCTags.temperature_scalding, soulTag = LCCTags.temperature_soul_scalding, entityTag = LCCTags.temperature_scalding_e),
    BURNING(0.01f, LCCTags.temperature_burning, soulTag = LCCTags.temperature_soul_burning),
    SCORCHING(0.15f, LCCTags.temperature_scorching, soulTag = LCCTags.temperature_soul_scorching),
    RED_HOT(0.2f, LCCTags.temperature_red_hot, entityTag = LCCTags.temperature_red_hot_e),
    NUCLEAR(5f, LCCTags.temperature_nuclear);

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