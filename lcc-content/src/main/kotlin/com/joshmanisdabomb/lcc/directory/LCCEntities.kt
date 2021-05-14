package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.entity.*
import com.joshmanisdabomb.lcc.entity.render.*
import com.joshmanisdabomb.lcc.facade.boat.LCCBoatEntity
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry
import net.minecraft.client.render.entity.BoatEntityRenderer
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.util.registry.Registry

object LCCEntities : AdvancedDirectory<FabricEntityTypeBuilder<out Entity>, EntityType<out Entity>, Unit, Unit>(), RegistryDirectory<EntityType<out Entity>, Unit, Unit> {

    override val registry by lazy { Registry.ENTITY_TYPE }

    override fun regId(path: String) = LCC.id(path)

    val pocket_zombie_pigman by entry(::typeInitialiser) { FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ::PocketZombiePigmanEntity).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).fireImmune().trackRangeChunks(5).trackedUpdateRate(3).forceTrackedVelocityUpdates(true) }
        .addInitListener { context, params -> FabricDefaultAttributeRegistry.register(context.entry, PocketZombiePigmanEntity.createAttributes()) }

    val classic_tnt by entry(::typeInitialiser) { FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::ClassicTNTEntity).dimensions(EntityDimensions.fixed(0.98f, 0.98f)).fireImmune().trackRangeChunks(10).trackedUpdateRate(10).forceTrackedVelocityUpdates(true) }

    val atomic_bomb by entry(::typeInitialiser) { FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::AtomicBombEntity).dimensions(EntityDimensions.fixed(0.98f, 0.98f)).fireImmune().trackRangeChunks(100).trackedUpdateRate(2).forceTrackedVelocityUpdates(true) }
    val nuclear_explosion by entry(::typeInitialiser) { FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::NuclearExplosionEntity).dimensions(EntityDimensions.fixed(0.1f, 0.1f)).fireImmune().trackRangeChunks(100).trackedUpdateRate(1).forceTrackedVelocityUpdates(false) }

    val salt by entry(::typeInitialiser) { FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::SaltEntity).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeChunks(4).trackedUpdateRate(10).forceTrackedVelocityUpdates(true) }

    val wasp by entry(::typeInitialiser) { FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ::WaspEntity).dimensions(EntityDimensions.fixed(1.1f, 0.675f)).trackRangeChunks(5).trackedUpdateRate(3).forceTrackedVelocityUpdates(true) }
        .addInitListener { context, params -> FabricDefaultAttributeRegistry.register(context.entry, WaspEntity.createAttributes()) }

    val rubber_boat: EntityType<LCCBoatEntity> get() = LCCBoatTypes.rubber.entityType

    private fun <E : Entity> typeInitialiser(input: FabricEntityTypeBuilder<E>, context: DirectoryContext<Unit>, parameters: Unit): EntityType<E> {
        return initialiser(input.build(), context, parameters)
    }

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = Unit

    @Environment(EnvType.CLIENT)
    fun initRenderers() {
        EntityRendererRegistry.INSTANCE.register(pocket_zombie_pigman, ::PocketZombiePigmanEntityRenderer)
        EntityRendererRegistry.INSTANCE.register(wasp, ::WaspEntityRenderer)

        EntityRendererRegistry.INSTANCE.register(atomic_bomb, ::AtomicBombEntityRenderer)
        EntityRendererRegistry.INSTANCE.register(nuclear_explosion, ::NuclearExplosionEntityRenderer)
        EntityRendererRegistry.INSTANCE.register(salt, ::SaltEntityRenderer)

        EntityRendererRegistry.INSTANCE.register(classic_tnt) { dispatcher -> StateBasedTNTEntityRenderer(LCCBlocks.classic_tnt.defaultState, dispatcher) }

        EntityRendererRegistry.INSTANCE.register(rubber_boat, ::BoatEntityRenderer)
    }

}