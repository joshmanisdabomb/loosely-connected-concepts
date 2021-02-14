package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.component.NuclearTracker
import com.joshmanisdabomb.lcc.component.RadiationComponent
import dev.onyxstudios.cca.api.v3.component.ComponentKey
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer
import dev.onyxstudios.cca.api.v3.level.LevelComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.level.LevelComponentInitializer
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer
import net.minecraft.entity.LivingEntity

object LCCComponents : ThingDirectory<ComponentKey<out ComponentV3>, Unit>(), WorldComponentInitializer, LevelComponentInitializer, EntityComponentInitializer {

    val nuclear by createWithName { ComponentRegistryV3.INSTANCE.getOrCreate(LCC.id(it), NuclearTracker::class.java) }
    val radiation by createWithName { ComponentRegistryV3.INSTANCE.getOrCreate(LCC.id(it), RadiationComponent::class.java) }

    override fun registerWorldComponentFactories(registry: WorldComponentFactoryRegistry) {
        registry.register(nuclear, ::NuclearTracker)
    }

    override fun registerLevelComponentFactories(registry: LevelComponentFactoryRegistry) {

    }

    override fun registerEntityComponentFactories(registry: EntityComponentFactoryRegistry) {
        registry.registerFor(LivingEntity::class.java, radiation, ::RadiationComponent)
    }

}