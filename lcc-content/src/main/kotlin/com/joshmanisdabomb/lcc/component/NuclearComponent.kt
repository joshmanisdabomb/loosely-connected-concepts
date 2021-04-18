package com.joshmanisdabomb.lcc.component

import com.joshmanisdabomb.lcc.abstracts.nuclear.NuclearUtil
import com.joshmanisdabomb.lcc.directory.LCCComponents
import com.joshmanisdabomb.lcc.entity.NuclearExplosionEntity
import com.joshmanisdabomb.lcc.extensions.NBT_COMPOUND
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent
import net.minecraft.client.MinecraftClient
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtHelper
import net.minecraft.nbt.NbtList
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class NuclearComponent(private val world: World) : ComponentV3, ServerTickingComponent, CommonTickingComponent, AutoSyncedComponent {

    val strikes = mutableListOf<NuclearStrike>()

    var winter = 0.0f

    fun strike(radius: Short, time: Long, pos: BlockPos) {
        this.strikes += NuclearStrike(pos, radius, time)
        winter = winter.plus(NuclearUtil.getWinterIncreaseFromRadius(radius.toInt())).coerceIn(0f, 6f)
        LCCComponents.nuclear.sync(world)
    }

    fun strike(entity: NuclearExplosionEntity) = strike(entity.radius.toShort(), world.time, entity.blockPos)

    override fun readFromNbt(tag: NbtCompound) {
        winter = tag.getFloat("WinterLevel")
        strikes.clear()
        tag.getList("Strikes", NBT_COMPOUND).forEach {
            (it as? NbtCompound)?.also {
                this.strikes += NuclearStrike(NbtHelper.toBlockPos(it.getCompound("Position")), it.getShort("Radius"), it.getLong("Time"))
            }
        }
    }

    override fun writeToNbt(tag: NbtCompound) {
        tag.putFloat("WinterLevel", winter)
        tag.put("Strikes", NbtList().also {
            strikes.forEach { (p, r, t) ->
                it.add(NbtCompound().also {
                    it.putShort("Radius", r)
                    it.putLong("Time", t)
                    it.put("Position", NbtHelper.fromBlockPos(p))
                })
            }
        })
    }

    override fun shouldSyncWith(player: ServerPlayerEntity) = player.world == this.world

    override fun writeSyncPacket(buf: PacketByteBuf, recipient: ServerPlayerEntity) {
        buf.writeBoolean(false)
        super.writeSyncPacket(buf, recipient)
    }

    override fun applySyncPacket(buf: PacketByteBuf) {
        val w = NuclearUtil.getWinterLevel(winter)
        val force = buf.readBoolean()
        super.applySyncPacket(buf)
        if (force || NuclearUtil.getLightModifierFromWinter(w) != NuclearUtil.getLightModifierFromWinter(NuclearUtil.getWinterLevel(winter))) {
            MinecraftClient.getInstance().worldRenderer.reload()
        }
    }

    override fun tick() {
        winter = winter.minus(0.000001f.times(6f.minus(winter).plus(1f))).coerceIn(0f, 6f)
    }

    override fun serverTick() {
        val w = NuclearUtil.getWinterLevel(winter)
        super.serverTick()
        val w2 = NuclearUtil.getWinterLevel(winter)
        if (w2 > 0) NuclearUtil.tick(world as ServerWorld, w2)
        if (NuclearUtil.getLightModifierFromWinter(w) != NuclearUtil.getLightModifierFromWinter(w2)) {
            LCCComponents.nuclear.sync(world) { buf, player -> buf.writeBoolean(true); super.writeSyncPacket(buf, player) }
        }
    }

    data class NuclearStrike(val pos: BlockPos, val radius: Short, val time: Long)

}
