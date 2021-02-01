package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.entity.AtomicBombEntity
import com.joshmanisdabomb.lcc.entity.ClassicTNTEntity
import com.joshmanisdabomb.lcc.entity.NuclearExplosionEntity
import com.joshmanisdabomb.lcc.entity.PocketZombiePigmanEntity
import com.joshmanisdabomb.lcc.entity.render.AtomicBombEntityRenderer
import com.joshmanisdabomb.lcc.entity.render.NuclearExplosionEntityRenderer
import com.joshmanisdabomb.lcc.entity.render.PocketZombiePigmanEntityRenderer
import com.joshmanisdabomb.lcc.entity.render.StateBasedTNTEntityRenderer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry
import net.minecraft.entity.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.util.registry.Registry

object LCCEntities : RegistryDirectory<EntityType<out Entity>, () -> DefaultAttributeContainer.Builder?>() {

    override val _registry by lazy { Registry.ENTITY_TYPE }

    override fun id(path: String) = LCC.id(path)

    val pocket_zombie_pigman by create(PocketZombiePigmanEntity::createAttributes) { FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ::PocketZombiePigmanEntity).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).fireImmune().trackRangeChunks(5).trackedUpdateRate(3).forceTrackedVelocityUpdates(true).build() }

    val classic_tnt by create { FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::ClassicTNTEntity).dimensions(EntityDimensions.fixed(0.98f, 0.98f)).fireImmune().trackRangeChunks(10).trackedUpdateRate(10).forceTrackedVelocityUpdates(true).build() }

    val atomic_bomb by create { FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::AtomicBombEntity).dimensions(EntityDimensions.fixed(0.98f, 0.98f)).fireImmune().trackRangeChunks(100).trackedUpdateRate(2).forceTrackedVelocityUpdates(true).build() }
    val nuclear_explosion by create { FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::NuclearExplosionEntity).dimensions(EntityDimensions.fixed(0.1f, 0.1f)).fireImmune().trackRangeChunks(100).trackedUpdateRate(1).forceTrackedVelocityUpdates(false).build() }

    override fun getDefaultProperty(): (() -> DefaultAttributeContainer.Builder?) = { null }

    override fun registerAll(things: Map<String, EntityType<out Entity>>, properties: Map<String, () -> DefaultAttributeContainer.Builder?>) {
        super.registerAll(things, properties)
        allProperties.filterValues { it() != null }.forEach { (k, v) -> FabricDefaultAttributeRegistry.register(all[k] as EntityType<out LivingEntity>, v()) }
    }

    @Environment(EnvType.CLIENT)
    fun initRenderers() {
        EntityRendererRegistry.INSTANCE.register(classic_tnt) { dispatcher -> StateBasedTNTEntityRenderer(LCCBlocks.classic_tnt.defaultState, dispatcher) }
        EntityRendererRegistry.INSTANCE.register(pocket_zombie_pigman, ::PocketZombiePigmanEntityRenderer)
        EntityRendererRegistry.INSTANCE.register(atomic_bomb, ::AtomicBombEntityRenderer)
        EntityRendererRegistry.INSTANCE.register(nuclear_explosion, ::NuclearExplosionEntityRenderer)
    }

}