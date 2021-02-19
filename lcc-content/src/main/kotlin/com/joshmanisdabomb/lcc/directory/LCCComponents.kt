package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.component.AdvancementRaceTracker
import com.joshmanisdabomb.lcc.component.NuclearTracker
import com.joshmanisdabomb.lcc.component.RadiationComponent
import dev.onyxstudios.cca.api.v3.component.ComponentFactory
import dev.onyxstudios.cca.api.v3.component.ComponentKey
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer
import dev.onyxstudios.cca.api.v3.level.LevelComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.level.LevelComponentInitializer
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer
import net.minecraft.entity.Entity
import net.minecraft.world.World
import net.minecraft.world.WorldProperties

object LCCComponents : AdvancedDirectory<Class<out ComponentV3>, ComponentKey<out ComponentV3>, Unit, Any>(), WorldComponentInitializer, LevelComponentInitializer, EntityComponentInitializer {

    val nuclear by entry({ i, c, p -> worldInitialiser(i, c, p, ::NuclearTracker) }) { NuclearTracker::class.java }
        .addTags("world")
    val radiation by entry({ i, c, p -> entityInitialiser(i, c, p, ::RadiationComponent) }) { RadiationComponent::class.java }
        .addTags("entity")

    val advancement_race by entry({ i, c, p -> levelInitialiser(i, c, p, ::AdvancementRaceTracker) }) { AdvancementRaceTracker::class.java }
        .addTags("level")

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

    inline fun <C : ComponentV3, reified E : Entity> entityInitialiser(input: Class<C>, context: DirectoryContext<Unit>, parameters: Any, factory: ComponentFactory<E, C>): ComponentKey<C> {
        if (parameters !is EntityComponentFactoryRegistry) error("Incorrect registry type given as parameters for entity initialiser.")
        val key = ComponentRegistryV3.INSTANCE.getOrCreate(LCC.id(context.name), input)
        parameters.registerFor(E::class.java, key, factory)
        return key
    }

    override fun registerEntityComponentFactories(registry: EntityComponentFactoryRegistry) {
        init(registry) { it.tags.contains("entity") }
    }

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = error("No default context available. Please specify a component registry.")

}