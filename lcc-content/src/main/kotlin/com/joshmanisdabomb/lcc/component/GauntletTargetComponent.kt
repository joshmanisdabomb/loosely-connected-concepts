package com.joshmanisdabomb.lcc.component

import com.joshmanisdabomb.lcc.abstracts.gauntlet.GauntletAction
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent
import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.CompoundTag

class GauntletTargetComponent(val entity: LivingEntity) : ComponentV3, AutoSyncedComponent, CommonTickingComponent {

    protected val action = mutableMapOf<GauntletAction, CompoundTag>()

    override fun readFromNbt(tag: CompoundTag) {

    }

    override fun writeToNbt(tag: CompoundTag) {
        
    }

    override fun tick() {

    }

}
