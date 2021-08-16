package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.abstracts.challenges.AltarChallenge
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class SapphireAltarBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.sapphire_altar, pos, state) {

    var challenge: AltarChallenge? = null
    var options: NbtCompound = NbtCompound()

    override fun readNbt(nbt: NbtCompound) {
        challenge = LCCRegistries.altar_challenges[Identifier(nbt.getString("Challenge"))]
        options = nbt.getCompound("ChallengeOptions")
    }

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        super.writeNbt(nbt)
        challenge?.also { nbt.putString("Challenge", it.id.toString()) }
        nbt.put("ChallengeOptions", options)
        return nbt
    }

    fun setChallenge(challenge: AltarChallenge, options: NbtCompound) {
        this.challenge = challenge
        this.options = options
    }

    companion object {
        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: SapphireAltarBlockEntity) {
            println("active")
        }
    }

}
