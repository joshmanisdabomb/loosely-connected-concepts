package com.joshmanisdabomb.lcc.component

import com.joshmanisdabomb.lcc.abstracts.gauntlet.GauntletAction
import com.joshmanisdabomb.lcc.abstracts.gauntlet.GauntletActorInstance
import com.joshmanisdabomb.lcc.abstracts.gauntlet.GauntletDirectory
import com.joshmanisdabomb.lcc.extensions.build
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundTag

class GauntletActorComponent(val entity: PlayerEntity) : ComponentV3, AutoSyncedComponent, CommonTickingComponent {

    protected val _instances = mutableMapOf<GauntletAction<*>, GauntletActorInstance>()
    var fallHandler: GauntletAction<*>? = null

    override fun readFromNbt(tag: CompoundTag) {
        _instances.clear()
        tag.getCompound("Instances").apply {
            keys.forEach {
                val instance = GauntletDirectory[it].newActorInstance(entity)
                instance.read(tag.getCompound(it))
                _instances[instance.action] = instance
            }
        }
        tag.getString("FallHandler").also { if (it.isNotBlank()) fallHandler = GauntletDirectory[it] }
    }

    override fun writeToNbt(tag: CompoundTag) {
        tag.build("Instances", CompoundTag()) {
            _instances.forEach { (k, v) ->
                put(GauntletDirectory[k].name, CompoundTag().also { v.write(it) })
            }
        }
        fallHandler?.also { tag.putString("FallHandler", GauntletDirectory[it].name) }
    }

    override fun tick() {
        val keys = _instances.filter { (k, v) -> v.baseTick() }.keys
        keys.forEach { _instances.remove(it) }
    }

    operator fun <A : GauntletActorInstance> get(action: GauntletAction<A>) = _instances.get(action) as? A
    operator fun <A : GauntletActorInstance> set(action: GauntletAction<A>, info: A) = _instances.put(action, info)

}
