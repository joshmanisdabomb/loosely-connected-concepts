package com.joshmanisdabomb.lcc.component

import com.joshmanisdabomb.lcc.abstracts.gauntlet.GauntletAction
import com.joshmanisdabomb.lcc.abstracts.gauntlet.GauntletDirectory
import com.joshmanisdabomb.lcc.abstracts.gauntlet.GauntletTargetInstance
import com.joshmanisdabomb.lcc.abstracts.gauntlet.TargetableGauntletAction
import com.joshmanisdabomb.lcc.extensions.NBT_COMPOUND
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent
import net.minecraft.entity.Entity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList

class GauntletTargetComponent(private val entity: Entity) : ComponentV3, AutoSyncedComponent, CommonTickingComponent {

    protected val _instances = mutableListOf<GauntletTargetInstance<*>>()
    val instances get() = _instances.toList()
    var fallHandler: GauntletAction<*>? = null

    override fun readFromNbt(tag: NbtCompound) {
        _instances.clear()
        tag.getList("Instances", NBT_COMPOUND).forEach {
            (it as? NbtCompound)?.also {
                val instance = (GauntletDirectory[it.getString("Type")] as? TargetableGauntletAction<*, *>)?.newTargetInstance(entity) ?: return@forEach
                instance.read(it)
                _instances.add(instance)
            }
        }
        tag.getString("FallHandler").also { if (it.isNotBlank()) fallHandler = GauntletDirectory[it] }
    }

    override fun writeToNbt(tag: NbtCompound) {
        tag.put("Instances", NbtList().also {
            _instances.forEach { i ->
                it.add(NbtCompound().also {
                    it.putString("Type", GauntletDirectory[i.action].name)
                    i.write(it)
                })
            }
        })
        fallHandler?.also { tag.putString("FallHandler", GauntletDirectory[it].name) }
    }

    override fun tick() {
        _instances.removeAll { it.baseTick() }
    }

    fun add(instance: GauntletTargetInstance<*>) = _instances.add(instance)

}
