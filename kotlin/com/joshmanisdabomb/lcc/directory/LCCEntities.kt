package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.entity.ClassicTNTEntity
import com.joshmanisdabomb.lcc.entity.render.StateBasedTNTRenderer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.util.registry.Registry

object LCCEntities : RegistryDirectory<EntityType<out Entity>, Unit>() {

    override val _registry by lazy { Registry.ENTITY_TYPE }

    val classic_tnt by create { FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::ClassicTNTEntity).dimensions(EntityDimensions.fixed(0.98f, 0.98f)).fireImmune().trackRangeChunks(10).trackedUpdateRate(10).forceTrackedVelocityUpdates(true).build() }

    @Environment(EnvType.CLIENT)
    fun initRenderers() {
        EntityRendererRegistry.INSTANCE.register(classic_tnt) { dispatcher -> StateBasedTNTRenderer(LCCBlocks.classic_tnt.defaultState, dispatcher) }
    }

}