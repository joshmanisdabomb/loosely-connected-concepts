package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.abstracts.challenges.AltarChallenge
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.extensions.NBT_INT_ARRAY
import com.joshmanisdabomb.lcc.extensions.NBT_LIST
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtHelper
import net.minecraft.nbt.NbtList
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class SapphireAltarBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.sapphire_altar, pos, state) {

    var challenge: AltarChallenge? = null
    var data: NbtCompound = NbtCompound()

    var challengers: MutableList<UUID>? = null

    var ticks = 0

    override fun readNbt(nbt: NbtCompound) {
        challenge = LCCRegistries.altar_challenges[Identifier(nbt.getString("Challenge"))]
        data = nbt.getCompound("ChallengeData")

        challengers?.clear()
        if (nbt.contains("Challengers", NBT_LIST)) {
            challengers = nbt.getList("Challengers", NBT_INT_ARRAY).map { NbtHelper.toUuid(it) }.toMutableList()
        }
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        challenge?.also { nbt.putString("Challenge", it.id.toString()) }
        nbt.put("ChallengeData", data)

        if (challengers != null) {
            nbt.put("Challengers", NbtList().apply {
                challengers?.forEach { this.add(NbtHelper.fromUuid(it)) }
            })
        }
    }

    fun setChallenge(challenge: AltarChallenge, data: NbtCompound) {
        this.challenge = challenge
        this.data = data
    }

    companion object {
        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: SapphireAltarBlockEntity) {
            val challenge = entity.challenge ?: return
            var verify = challenge.verifyTick(world, state, pos, entity)
            if (verify == AltarChallenge.ChallengeState.ACTIVE && entity.ticks++ >= 20) {
                verify = challenge.verify(world, state, pos, entity)
                entity.ticks = 0
            }
            challenge.handleState(verify, world as ServerWorld, pos, state, entity)
        }
    }

}
