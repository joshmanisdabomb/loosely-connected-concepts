package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.entity.*
import com.joshmanisdabomb.lcc.entity.render.*
import com.joshmanisdabomb.lcc.facade.boat.LCCBoatEntity
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.minecraft.client.render.entity.BoatEntityRenderer
import net.minecraft.client.render.entity.EmptyEntityRenderer
import net.minecraft.client.render.entity.SkeletonEntityRenderer
import net.minecraft.entity.*
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.mob.MobEntity
import net.minecraft.util.registry.Registry
import net.minecraft.world.Heightmap

object LCCEntities : AdvancedDirectory<FabricEntityTypeBuilder<out Entity>, EntityType<out Entity>, Unit, Unit>(), RegistryDirectory<EntityType<out Entity>, Unit, Unit> {

    override val registry by lazy { Registry.ENTITY_TYPE }

    override fun regId(path: String) = LCC.id(path)

    val pocket_zombie_pigman by entry(::typeInitialiser) { FabricEntityTypeBuilder.createMob<PocketZombiePigmanEntity>().spawnGroup(SpawnGroup.MONSTER).entityFactory(::PocketZombiePigmanEntity).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).fireImmune().trackRangeChunks(5).trackedUpdateRate(3).forceTrackedVelocityUpdates(true) }
        .addInitListener { context, params -> FabricDefaultAttributeRegistry.register(context.entry, PocketZombiePigmanEntity.createAttributes()) }

    val classic_tnt by entry(::typeInitialiser) { FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::ClassicTNTEntity).dimensions(EntityDimensions.fixed(0.98f, 0.98f)).fireImmune().trackRangeChunks(10).trackedUpdateRate(10).forceTrackedVelocityUpdates(true) }

    val atomic_bomb by entry(::typeInitialiser) { FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::AtomicBombEntity).dimensions(EntityDimensions.fixed(0.98f, 0.98f)).fireImmune().trackRangeChunks(100).trackedUpdateRate(2).forceTrackedVelocityUpdates(true) }
    val nuclear_explosion by entry(::typeInitialiser) { FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::NuclearExplosionEntity).dimensions(EntityDimensions.fixed(0.1f, 0.1f)).fireImmune().trackRangeChunks(100).trackedUpdateRate(1).forceTrackedVelocityUpdates(false) }

    val salt by entry(::typeInitialiser) { FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::SaltEntity).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeChunks(4).trackedUpdateRate(10).forceTrackedVelocityUpdates(true) }
    val consumer_tongue by entry(::typeInitialiser) { FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::ConsumerTongueEntity).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeChunks(4).trackedUpdateRate(10).forceTrackedVelocityUpdates(true) }
    val disciple_dust by entry(::typeInitialiser) { FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::DiscipleDustEntity).dimensions(EntityDimensions.fixed(0.6f, 0.6f)).trackRangeChunks(4).trackedUpdateRate(10).forceTrackedVelocityUpdates(true) }

    val traveller by entry(::typeInitialiser) { FabricEntityTypeBuilder.createMob<TravellerEntity>().spawnGroup(SpawnGroup.CREATURE).entityFactory(::TravellerEntity).dimensions(EntityDimensions.changing(0.6f, 1.95f)).trackRangeChunks(10).trackedUpdateRate(3).forceTrackedVelocityUpdates(true) }
        .addInitListener { context, params -> FabricDefaultAttributeRegistry.register(context.entry, MobEntity.createMobAttributes()) }

    val consumer by entry(::typeInitialiser) { FabricEntityTypeBuilder.createMob<ConsumerEntity>().spawnGroup(SpawnGroup.MONSTER).entityFactory(::ConsumerEntity).dimensions(EntityDimensions.changing(0.8f, 1.1f)).trackRangeChunks(8).trackedUpdateRate(3).forceTrackedVelocityUpdates(true).spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LCCSpawnRestrictions::canSpawnInDarkOrSkylight) }
        .addInitListener { context, params -> FabricDefaultAttributeRegistry.register(context.entry, ConsumerEntity.createAttributes()) }
    val wasp by entry(::typeInitialiser) { FabricEntityTypeBuilder.createMob<WaspEntity>().spawnGroup(SpawnGroup.MONSTER).entityFactory(::WaspEntity).dimensions(EntityDimensions.changing(0.99f, 0.675f)).trackRangeChunks(5).trackedUpdateRate(3).forceTrackedVelocityUpdates(true).spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LCCSpawnRestrictions::canSpawnInSkylight) }
        .addInitListener { context, params -> FabricDefaultAttributeRegistry.register(context.entry, WaspEntity.createAttributes()) }
    val baby_skeleton by entry(::typeInitialiser) { FabricEntityTypeBuilder.createMob<BabySkeletonEntity>().spawnGroup(SpawnGroup.MONSTER).entityFactory(::BabySkeletonEntity).dimensions(EntityDimensions.changing(0.6f, 1.99f)).trackRangeChunks(8).trackedUpdateRate(3).forceTrackedVelocityUpdates(true).spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark) }
        .addInitListener { context, params -> FabricDefaultAttributeRegistry.register(context.entry, BabySkeletonEntity.createAttributes()) }
    val disciple by entry(::typeInitialiser) { FabricEntityTypeBuilder.createMob<DiscipleEntity>().spawnGroup(SpawnGroup.MONSTER).entityFactory(::DiscipleEntity).dimensions(EntityDimensions.changing(0.8f, 2.15f)).trackRangeChunks(8).trackedUpdateRate(3).forceTrackedVelocityUpdates(true).spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LCCSpawnRestrictions::canSpawnInDarkOrSkylight) }
        .addInitListener { context, params -> FabricDefaultAttributeRegistry.register(context.entry, DiscipleEntity.createAttributes()) }
    val psycho_pig by entry(::typeInitialiser) { FabricEntityTypeBuilder.createMob<PsychoPigEntity>().spawnGroup(SpawnGroup.MONSTER).entityFactory(::PsychoPigEntity).dimensions(EntityDimensions.changing(0.9f, 0.9f)).trackRangeChunks(10).trackedUpdateRate(3).forceTrackedVelocityUpdates(true).spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LCCSpawnRestrictions::canSpawnInDarkOrSkylight) }
        .addInitListener { context, params -> FabricDefaultAttributeRegistry.register(context.entry, PsychoPigEntity.createAttributes()) }
    val rotwitch by entry(::typeInitialiser) { FabricEntityTypeBuilder.createMob<RotwitchEntity>().spawnGroup(SpawnGroup.MONSTER).entityFactory(::RotwitchEntity).dimensions(EntityDimensions.changing(1.2f, 0.8f)).trackRangeChunks(8).trackedUpdateRate(3).forceTrackedVelocityUpdates(true).spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LCCSpawnRestrictions::canSpawnInDarkOrSkylight) }
        .addInitListener { context, params -> FabricDefaultAttributeRegistry.register(context.entry, RotwitchEntity.createAttributes()) }
    val fly by entry(::typeInitialiser) { FabricEntityTypeBuilder.createMob<FlyEntity>().spawnGroup(SpawnGroup.MONSTER).entityFactory(::FlyEntity).dimensions(EntityDimensions.changing(0.3f, 0.3f)).trackRangeChunks(5).trackedUpdateRate(3).forceTrackedVelocityUpdates(true) }
        .addInitListener { context, params -> FabricDefaultAttributeRegistry.register(context.entry, FlyEntity.createAttributes()) }
    val woodlouse by entry(::typeInitialiser) { FabricEntityTypeBuilder.createMob<WoodlouseEntity>().spawnGroup(SpawnGroup.CREATURE).entityFactory(::WoodlouseEntity).dimensions(EntityDimensions.changing(0.8f, 0.625f)).trackRangeChunks(10).trackedUpdateRate(3).forceTrackedVelocityUpdates(true).spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LCCSpawnRestrictions::canSpawnInSkylight) }
        .addInitListener { context, params -> FabricDefaultAttributeRegistry.register(context.entry, WoodlouseEntity.createAttributes()) }

    val rubber_boat: EntityType<LCCBoatEntity> get() = LCCBoatTypes.rubber.entityType
    val deadwood_boat: EntityType<LCCBoatEntity> get() = LCCBoatTypes.deadwood.entityType

    private fun <E : Entity> typeInitialiser(input: FabricEntityTypeBuilder<E>, context: DirectoryContext<Unit>, parameters: Unit): EntityType<E> {
        return initialiser(input.build(), context, parameters)
    }

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = Unit

    @Environment(EnvType.CLIENT)
    fun initRenderers() {
        EntityRendererRegistry.register(pocket_zombie_pigman, ::PocketZombiePigmanEntityRenderer)

        EntityRendererRegistry.register(traveller, ::TravellerEntityRenderer)

        EntityRendererRegistry.register(consumer, ::ConsumerEntityRenderer)
        EntityRendererRegistry.register(wasp, ::WaspEntityRenderer)
        EntityRendererRegistry.register(baby_skeleton, ::SkeletonEntityRenderer)
        EntityRendererRegistry.register(disciple, ::DiscipleEntityRenderer)
        EntityRendererRegistry.register(psycho_pig, ::PsychoPigEntityRenderer)
        EntityRendererRegistry.register(rotwitch, ::RotwitchEntityRenderer)
        EntityRendererRegistry.register(fly, ::FlyEntityRenderer)
        EntityRendererRegistry.register(woodlouse, ::WoodlouseEntityRenderer)

        EntityRendererRegistry.register(atomic_bomb, ::AtomicBombEntityRenderer)
        EntityRendererRegistry.register(nuclear_explosion, ::NuclearExplosionEntityRenderer)
        EntityRendererRegistry.register(salt, ::SaltEntityRenderer)
        EntityRendererRegistry.register(consumer_tongue, ::ConsumerTongueEntityRenderer)
        EntityRendererRegistry.register(disciple_dust, ::EmptyEntityRenderer)

        EntityRendererRegistry.register(classic_tnt) { dispatcher -> StateBasedTNTEntityRenderer(LCCBlocks.classic_tnt.defaultState, dispatcher) }

        EntityRendererRegistry.register(rubber_boat) { dispatcher -> BoatEntityRenderer(dispatcher, false) }
        EntityRendererRegistry.register(deadwood_boat) { dispatcher -> BoatEntityRenderer(dispatcher, false) }
    }

}