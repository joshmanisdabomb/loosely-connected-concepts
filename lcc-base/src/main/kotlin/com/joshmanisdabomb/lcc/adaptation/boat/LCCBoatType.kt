package com.joshmanisdabomb.lcc.adaptation.boat

import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.entity.vehicle.BoatEntity
import net.minecraft.item.ItemConvertible
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

open class LCCBoatType(val item: ItemConvertible, private val entity: Identifier, val texture: Identifier, val base: BoatEntity.Type = BoatEntity.Type.OAK, val drops: BoatEntity.() -> Unit) {

    constructor(item: ItemConvertible, entity: Identifier, texture: Identifier, planks: ItemConvertible, stick: ItemConvertible, base: BoatEntity.Type = BoatEntity.Type.OAK) : this(item, entity, texture, base, {
        repeat(3) { dropItem(planks) }
        repeat(2) { dropItem(stick) }
    })

    private var _entityType: EntityType<LCCBoatEntity>? = null
    val entityType get() = _entityType ?: error("This boat entity has not yet been initialised.")

    open fun registerEntity(): EntityType<LCCBoatEntity> {
        return Registry.register(Registry.ENTITY_TYPE, entity,
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, EntityType.EntityFactory<LCCBoatEntity> { e, w -> LCCBoatEntity(e, this, w) })
                .dimensions(EntityDimensions.fixed(1.375f, 0.5625f))
                .trackRangeChunks(10)
                .build().apply { _entityType = this }
        )
    }

}