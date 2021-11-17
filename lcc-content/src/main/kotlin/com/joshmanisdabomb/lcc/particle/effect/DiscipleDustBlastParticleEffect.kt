package com.joshmanisdabomb.lcc.particle.effect

import com.joshmanisdabomb.lcc.directory.LCCParticles
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.PacketByteBuf
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleType

class DiscipleDustBlastParticleEffect(val size: Float) : ParticleEffect {

    override fun getType() = LCCParticles.disciple_dust_blast

    override fun write(buf: PacketByteBuf) = buf.writeFloat(size).let {}

    override fun asString() = "${LCCParticles.getRegistryKey(LCCParticles.soaking_soul_sand_jump)} $size"

    companion object {
        val codec = RecordCodecBuilder.create { instance: RecordCodecBuilder.Instance<DiscipleDustBlastParticleEffect> ->
            instance.group(
                Codec.FLOAT.fieldOf("area").forGetter { p -> p.size }
            ).apply(instance) { s -> DiscipleDustBlastParticleEffect(s) }
        }

        val factory = object : ParticleEffect.Factory<DiscipleDustBlastParticleEffect> {
            @Throws(CommandSyntaxException::class)
            override fun read(particleType: ParticleType<DiscipleDustBlastParticleEffect>, stringReader: StringReader): DiscipleDustBlastParticleEffect {
                stringReader.expect(' ')
                val size = stringReader.readFloat()
                return DiscipleDustBlastParticleEffect(size)
            }

            override fun read(particleType: ParticleType<DiscipleDustBlastParticleEffect>, packetByteBuf: PacketByteBuf): DiscipleDustBlastParticleEffect {
                return DiscipleDustBlastParticleEffect(packetByteBuf.readFloat())
            }
        }
    }

}