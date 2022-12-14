package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.particle.effect.DiscipleDustBlastParticleEffect
import com.joshmanisdabomb.lcc.particle.effect.SoakingSoulSandJumpParticleEffect
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes
import net.minecraft.particle.ParticleType
import net.minecraft.util.registry.Registry

object LCCParticles : BasicDirectory<ParticleType<*>, Unit>(), RegistryDirectory<ParticleType<*>, Unit, Unit> {

    override val registry by lazy { Registry.PARTICLE_TYPE }

    override fun regId(name: String) = LCC.id(name)

    val soaking_soul_sand_bubble by entry(::initialiser) { FabricParticleTypes.simple(false) }
    val soaking_soul_sand_jump by entry(::initialiser) { FabricParticleTypes.complex(true, SoakingSoulSandJumpParticleEffect.factory) }
    val steam by entry(::initialiser) { FabricParticleTypes.simple(false) }
    val nuclear by entry(::initialiser) { FabricParticleTypes.simple(false) }
    val uranium by entry(::initialiser) { FabricParticleTypes.simple(false) }
    val nuke by entry(::initialiser) { FabricParticleTypes.simple(false) }
    val disciple_dust by entry(::initialiser) { FabricParticleTypes.simple(false) }
    val disciple_dust_blast by entry(::initialiser) { FabricParticleTypes.complex(true, DiscipleDustBlastParticleEffect.factory) }

    override fun defaultProperties(name: String) = Unit

}