package com.joshmanisdabomb.lcc.abstracts.challenges

import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.world.feature.SapphireAltarStructureFeature
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.math.BlockBox
import net.minecraft.world.StructureWorldAccess
import java.lang.RuntimeException
import java.util.*
import kotlin.Int

abstract class AltarChallenge {

    val id get() = LCCRegistries.altar_challenges.getKey(this).orElseThrow(::RuntimeException).value

    abstract fun generateOptions(random: Random, nbt: NbtCompound = NbtCompound()): NbtCompound

    abstract fun generate(world: StructureWorldAccess, piece: SapphireAltarStructureFeature.Piece, yOffset: Int, boundingBox: BlockBox, options: NbtCompound, random: Random)

    open fun getAltarWidth(options: NbtCompound): Int? = options.getInt("Width") + 2

    open fun getAltarDepth(options: NbtCompound): Int? = options.getInt("Depth") + 2

}