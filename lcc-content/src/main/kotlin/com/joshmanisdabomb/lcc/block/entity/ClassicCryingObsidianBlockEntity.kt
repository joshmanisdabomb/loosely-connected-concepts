package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.extensions.build
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import java.util.*

class ClassicCryingObsidianBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.classic_crying_obsidian, pos, state), BlockEntityClientSerializable {

    private val spawns = mutableMapOf<UUID, Vec3d>()

    fun register(player: PlayerEntity, location: Vec3d) {
        spawns[player.uuid] = location
        markDirty()
    }

    fun deregister(player: PlayerEntity) {
        spawns.remove(player.uuid)
        markDirty()
    }

    override fun readNbt(tag: CompoundTag) {
        fromClientTag(tag)
        super.readNbt(tag)
    }

    override fun writeNbt(tag: CompoundTag): CompoundTag {
        return super.writeNbt(toClientTag(tag))
    }

    fun getLocation(player: ServerPlayerEntity) = spawns[player.uuid]

    fun isActive(player: PlayerEntity): Boolean {
        return spawns.contains(player.uuid)
    }

    override fun fromClientTag(tag: CompoundTag) {
        with (tag.getCompound("Spawns")) {
            keys.forEach {
                with (this.getCompound(it)) {
                    spawns[UUID.fromString(it)] = Vec3d(this.getDouble("SpawnX"), this.getDouble("SpawnY"), this.getDouble("SpawnZ"))
                }
            }
        }
    }

    override fun toClientTag(tag: CompoundTag): CompoundTag {
        tag.build("Spawns") {
            spawns.forEach { (k, v) ->
                this.build(k.toString().toLowerCase()) {
                    this.putDouble("SpawnX", v.x)
                    this.putDouble("SpawnY", v.y)
                    this.putDouble("SpawnZ", v.z)
                }
            }
        }
        return tag
    }

}
