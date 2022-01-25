package com.joshmanisdabomb.lcc.component

import com.joshmanisdabomb.lcc.facade.piston.LCCPiston
import com.joshmanisdabomb.lcc.facade.piston.LCCPistonHead
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import net.minecraft.block.PistonBlock
import net.minecraft.block.PistonHeadBlock
import net.minecraft.block.entity.PistonBlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class PistonCauseComponent(private val piston: PistonBlockEntity) : ComponentV3, AutoSyncedComponent {

    var head: LCCPistonHead? = null
    val headBlock get() = head as? PistonHeadBlock
    var base: LCCPiston? = null
    val baseBlock get() = base as? PistonBlock

    override fun readFromNbt(tag: NbtCompound) {
        head = Registry.BLOCK.get(Identifier(tag.getString("lcc-pistonhead"))) as? LCCPistonHead
        base = Registry.BLOCK.get(Identifier(tag.getString("lcc-pistonbase"))) as? LCCPiston
    }

    override fun writeToNbt(tag: NbtCompound) {
        headBlock?.also { tag.putString("lcc-pistonhead", Registry.BLOCK.getId(it).toString()) }
        baseBlock?.also { tag.putString("lcc-pistonbase", Registry.BLOCK.getId(it).toString()) }
    }

}
