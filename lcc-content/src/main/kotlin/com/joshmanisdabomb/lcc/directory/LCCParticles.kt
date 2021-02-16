package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.particle.effect.SoakingSoulSandJumpParticleEffect
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes
import net.minecraft.particle.ParticleType
import net.minecraft.util.registry.Registry

object LCCParticles : RegistryDirectory<ParticleType<*>, Unit>() {

    override val _registry by lazy { Registry.PARTICLE_TYPE }

    override fun id(path: String) = LCC.id(path)

    val soaking_soul_sand_bubble by create { FabricParticleTypes.simple(false) }
    val soaking_soul_sand_jump by create { FabricParticleTypes.complex(true, SoakingSoulSandJumpParticleEffect.factory) }
    val steam by create { FabricParticleTypes.simple(false) }
    val nuclear by create { FabricParticleTypes.simple(false) }

}