package com.joshmanisdabomb.lcc.component

import com.joshmanisdabomb.lcc.abstracts.nuclear.NuclearUtil
import com.joshmanisdabomb.lcc.directory.LCCComponents
import com.joshmanisdabomb.lcc.entity.NuclearExplosionEntity
import com.joshmanisdabomb.lcc.extensions.NBT_COMPOUND
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import dev.onyxstudios.cca.api.v3.component.tick.ClientTickingComponent
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent
import net.minecraft.client.MinecraftClient
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.NbtHelper
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class NuclearTracker(private val world: World) : ComponentV3, ServerTickingComponent, ClientTickingComponent, AutoSyncedComponent {

    val strikes = mutableListOf<NuclearStrike>()

    private var _winter = 0.0f
    val winter get() = _winter

    fun strike(radius: Short, time: Long, pos: BlockPos) {
        this.strikes += NuclearStrike(pos, radius, time)
        _winter = _winter.plus(NuclearUtil.getWinterIncreaseFromRadius(radius.toInt())).coerceIn(0f, 6f)
        LCCComponents.nuclear.sync(world)
    }

    fun strike(entity: NuclearExplosionEntity) = strike(entity.radius.toShort(), world.time, entity.blockPos)

    override fun readFromNbt(tag: CompoundTag) {
        _winter = tag.getFloat("WinterLevel")
        strikes.clear()
        tag.getList("Strikes", NBT_COMPOUND).forEach {
            (it as? CompoundTag)?.also {
                this.strikes += NuclearStrike(NbtHelper.toBlockPos(it.getCompound("Position")), it.getShort("Radius"), it.getLong("Time"))
            }
        }
    }

    override fun writeToNbt(tag: CompoundTag) {
        tag.putFloat("WinterLevel", _winter)
        tag.put("Strikes", ListTag().also {
            strikes.forEach { (p, r, t) ->
                it.add(CompoundTag().also {
                    it.putShort("Radius", r)
                    it.putLong("Time", t)
                    it.put("Position", NbtHelper.fromBlockPos(p))
                })
            }
        })
    }

    override fun shouldSyncWith(player: ServerPlayerEntity) = player.world == this.world

    override fun applySyncPacket(buf: PacketByteBuf) {
        super.applySyncPacket(buf)
        MinecraftClient.getInstance().worldRenderer.reload()
    }

    override fun clientTick() = serverTick()

    override fun serverTick() {
        val winter = NuclearUtil.getWinterLevel(_winter)
        _winter = _winter.minus(0.000001f.times(6f.minus(_winter).plus(1f))).coerceIn(0f, 6f)
        if (NuclearUtil.getLightModifierFromWinter(winter) != NuclearUtil.getLightModifierFromWinter(NuclearUtil.getWinterLevel(_winter))) {
            LCCComponents.nuclear.sync(world)
        }
    }

    data class NuclearStrike(val pos: BlockPos, val radius: Short, val time: Long)

}
