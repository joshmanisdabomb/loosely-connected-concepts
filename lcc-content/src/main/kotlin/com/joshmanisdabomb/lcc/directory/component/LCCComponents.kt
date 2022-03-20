package com.joshmanisdabomb.lcc.directory.component

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.component.*
import com.joshmanisdabomb.lcc.directory.AdvancedDirectory
import dev.onyxstudios.cca.api.v3.component.*
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy
import dev.onyxstudios.cca.api.v3.level.LevelComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.level.LevelComponentInitializer
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.World
import net.minecraft.world.WorldProperties

//TODO split
object LCCComponents : AdvancedDirectory<Class<out ComponentV3>, ComponentKey<out ComponentV3>, Unit, Any>(), WorldComponentInitializer, LevelComponentInitializer, EntityComponentInitializer {

    val gauntlet_actor by entry({ i, c, p -> playerInitialiser(i, c, p, ::GauntletActorComponent) }) { GauntletActorComponent::class.java }
        .addTags("entity")
    val gauntlet_target by entry({ i, c, p -> entityInitialiser(i, c, p, ::GauntletTargetComponent) }) { GauntletTargetComponent::class.java }
        .addTags("entity")

    val hearts by entry({ i, c, p -> entityInitialiser(i, c, p, ::HeartsComponent, null) }) { HeartsComponent::class.java }
        .addTags("entity")

    val nuclear by entry({ i, c, p -> worldInitialiser(i, c, p, ::NuclearComponent) }) { NuclearComponent::class.java }
        .addTags("world")
    val radiation by entry({ i, c, p -> entityInitialiser(i, c, p, ::RadiationComponent) }) { RadiationComponent::class.java }
        .addTags("entity")

    val advancement_race by entry({ i, c, p -> levelInitialiser(i, c, p, ::AdvancementRaceComponent) }) { AdvancementRaceComponent::class.java }
        .addTags("level")

    val computing_storage by entry({ i, c, p -> levelInitialiser(i, c, p, ::ComputingStorageComponent) }) { ComputingStorageComponent::class.java }
        .addTags("level")
    val computing_sessions by entry({ i, c, p -> levelInitialiser(i, c, p, ::ComputingSessionComponent) }) { ComputingSessionComponent::class.java }
        .addTags("level")

    val targeted_effects by entry({ i, c, p -> entityInitialiser(i, c, p, ::TargetedEffectComponent, null) }) { TargetedEffectComponent::class.java }
        .addTags("entity")

    fun <C : ComponentV3> worldInitialiser(input: Class<C>, context: DirectoryContext<Unit>, parameters: Any, factory: ComponentFactory<World, C>): ComponentKey<C> {
        if (parameters !is WorldComponentFactoryRegistry) error("Incorrect registry type given as parameters for world initialiser.")
        val key = ComponentRegistryV3.INSTANCE.getOrCreate(LCC.id(context.name), input)
        parameters.register(key, factory)
        return key
    }

    override fun registerWorldComponentFactories(registry: WorldComponentFactoryRegistry) {
        init(registry) { it.tags.contains("world") }
    }

    fun <C : ComponentV3> levelInitialiser(input: Class<C>, context: DirectoryContext<Unit>, parameters: Any, factory: ComponentFactory<WorldProperties, C>): ComponentKey<C> {
        if (parameters !is LevelComponentFactoryRegistry) error("Incorrect registry type given as parameters for level initialiser.")
        val key = ComponentRegistryV3.INSTANCE.getOrCreate(LCC.id(context.name), input)
        parameters.register(key, factory)
        return key
    }

    override fun registerLevelComponentFactories(registry: LevelComponentFactoryRegistry) {
        init(registry) { it.tags.contains("level") }
    }

    inline fun <C : ComponentV3, reified E : Entity> entityInitialiser(input: Class<C>, context: DirectoryContext<Unit>, parameters: Any, factory: ComponentFactory<E, C>, strategy: RespawnCopyStrategy<Component>? = RespawnCopyStrategy.INVENTORY): ComponentKey<C> {
        if (parameters !is EntityComponentFactoryRegistry) error("Incorrect registry type given as parameters for entity initialiser.")
        val key = ComponentRegistryV3.INSTANCE.getOrCreate(LCC.id(context.name), input)
        parameters.beginRegistration(E::class.java, key)
            .apply { if (strategy != null) this.respawnStrategy(strategy) }
            .end(factory)
        return key
    }

    fun <C : ComponentV3> playerInitialiser(input: Class<C>, context: DirectoryContext<Unit>, parameters: Any, factory: ComponentFactory<PlayerEntity, C>, strategy: RespawnCopyStrategy<Component> = RespawnCopyStrategy.INVENTORY): ComponentKey<C> {
        if (parameters !is EntityComponentFactoryRegistry) error("Incorrect registry type given as parameters for entity initialiser.")
        val key = ComponentRegistryV3.INSTANCE.getOrCreate(LCC.id(context.name), input)
        parameters.registerForPlayers(key, factory, strategy)
        return key
    }

    //might need fixing if used
    fun <C : ComponentV3> playerInitialiser(input: Class<PlayerComponent<C>>, context: DirectoryContext<Unit>, parameters: Any, factory: ComponentFactory<PlayerEntity, out PlayerComponent<C>>): ComponentKey<PlayerComponent<C>> {
        if (parameters !is EntityComponentFactoryRegistry) error("Incorrect registry type given as parameters for entity initialiser.")
        val key = ComponentRegistryV3.INSTANCE.getOrCreate(LCC.id(context.name), input)
        parameters.registerForPlayers(key, factory)
        return key
    }

    override fun registerEntityComponentFactories(registry: EntityComponentFactoryRegistry) {
        init(registry) { it.tags.contains("entity") }
    }

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = error("No default context available. Please specify a component registry.")

}