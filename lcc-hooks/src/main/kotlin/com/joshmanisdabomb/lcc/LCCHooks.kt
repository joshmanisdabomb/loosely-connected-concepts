package com.joshmanisdabomb.lcc

import com.joshmanisdabomb.lcc.component.PistonCauseComponent
import dev.onyxstudios.cca.api.v3.block.BlockComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.block.BlockComponentInitializer
import dev.onyxstudios.cca.api.v3.component.ComponentKey
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3
import net.fabricmc.api.ModInitializer
import net.minecraft.block.entity.PistonBlockEntity
import net.minecraft.util.Identifier

object LCCHooks : ModInitializer, BlockComponentInitializer {

    internal lateinit var piston_cause_component_key: ComponentKey<PistonCauseComponent>

    override fun onInitialize() {
        piston_cause_component_key = ComponentRegistryV3.INSTANCE.getOrCreate(Identifier("lcc-hooks", "piston_cause"), PistonCauseComponent::class.java)
    }

    override fun registerBlockComponentFactories(registry: BlockComponentFactoryRegistry) {
        registry.registerFor(PistonBlockEntity::class.java, piston_cause_component_key, ::PistonCauseComponent)
    }

}