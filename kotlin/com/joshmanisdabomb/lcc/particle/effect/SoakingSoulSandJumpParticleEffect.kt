package com.joshmanisdabomb.lcc.particle.effect

import com.joshmanisdabomb.lcc.directory.LCCParticles
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.PacketByteBuf
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleType
import net.minecraft.util.math.Direction

class SoakingSoulSandJumpParticleEffect(val direction: Direction, val area: Float, val height: Float, val ring: Float) : ParticleEffect {

    override fun getType() = LCCParticles.soaking_soul_sand_jump

    override fun write(buf: PacketByteBuf) = buf.writeByte(direction.ordinal).writeFloat(area).writeFloat(height).writeFloat(ring).let {}

    override fun asString() = "${LCCParticles.getRegistryKey(LCCParticles.soaking_soul_sand_jump)} $direction $area $height $ring"

    companion object {
        val codec = RecordCodecBuilder.create { instance: RecordCodecBuilder.Instance<SoakingSoulSandJumpParticleEffect> ->
            instance.group(
                Codec.BYTE.fieldOf("direction").forGetter { p -> p.direction.ordinal.toByte() },
                Codec.FLOAT.fieldOf("area").forGetter { p -> p.area },
                Codec.FLOAT.fieldOf("height").forGetter { p -> p.height },
                Codec.FLOAT.fieldOf("ring").forGetter { p -> p.ring }
            ).apply(instance) { d, a, h, r -> SoakingSoulSandJumpParticleEffect(Direction.values()[d.toInt()], a, h, r) }
        }

        val factory = object : ParticleEffect.Factory<SoakingSoulSandJumpParticleEffect> {
            @Throws(CommandSyntaxException::class)
            override fun read(particleType: ParticleType<SoakingSoulSandJumpParticleEffect>, stringReader: StringReader): SoakingSoulSandJumpParticleEffect {
                stringReader.expect(' ')
                val dirInput = stringReader.readUnquotedString()
                val dir = Direction.values().firstOrNull { it.name.equals(dirInput, ignoreCase = true) } ?: throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().create()
                stringReader.expect(' ')
                val area = stringReader.readFloat()
                stringReader.expect(' ')
                val height = stringReader.readFloat()
                stringReader.expect(' ')
                val ring = stringReader.readFloat()
                return SoakingSoulSandJumpParticleEffect(dir, area, height, ring)
            }

            override fun read(particleType: ParticleType<SoakingSoulSandJumpParticleEffect>, packetByteBuf: PacketByteBuf): SoakingSoulSandJumpParticleEffect {
                return SoakingSoulSandJumpParticleEffect(Direction.values()[packetByteBuf.readByte().toInt()], packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readFloat())
            }
        }
    }

}