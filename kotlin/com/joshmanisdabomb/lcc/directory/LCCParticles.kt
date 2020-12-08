package com.joshmanisdabomb.lcc.directory

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes
import net.minecraft.particle.ParticleType
import net.minecraft.util.registry.Registry

object LCCParticles : RegistryDirectory<ParticleType<*>, Unit>() {

    override val _registry by lazy { Registry.PARTICLE_TYPE }

    val soaking_soul_sand_bubble by create { FabricParticleTypes.simple(false) }

}