package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.extensions.modifyCompound
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import java.util.*

class ClassicCryingObsidianBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.classic_crying_obsidian, pos, state) {

    private val spawns = mutableMapOf<UUID, Vec3d>()

    fun register(player: PlayerEntity, location: Vec3d) {
        spawns[player.uuid] = location
        markDirty()
    }

    fun deregister(player: PlayerEntity) {
        spawns.remove(player.uuid)
        markDirty()
    }

    override fun readNbt(tag: NbtCompound) {
        with (tag.getCompound("Spawns")) {
            keys.forEach {
                with (this.getCompound(it)) {
                    spawns[UUID.fromString(it)] = Vec3d(this.getDouble("SpawnX"), this.getDouble("SpawnY"), this.getDouble("SpawnZ"))
                }
            }
        }
        super.readNbt(tag)
    }

    override fun writeNbt(tag: NbtCompound) {
        tag.modifyCompound("Spawns") {
            spawns.forEach { (k, v) ->
                this.modifyCompound(k.toString().toLowerCase()) {
                    this.putDouble("SpawnX", v.x)
                    this.putDouble("SpawnY", v.y)
                    this.putDouble("SpawnZ", v.z)
                }
            }
        }
        return super.writeNbt(tag)
    }

    override fun toUpdatePacket() = BlockEntityUpdateS2CPacket.create(this)

    override fun toInitialChunkDataNbt() = createNbt()

    fun getLocation(player: ServerPlayerEntity) = spawns[player.uuid]

    fun isActive(player: PlayerEntity): Boolean {
        return spawns.contains(player.uuid)
    }

}
