package com.joshmanisdabomb.lcc.entity

import net.minecraft.entity.damage.DamageSource

class LCCDamageSource(name: String, bypassArmor: Boolean = false, unblockable: Boolean = false) : DamageSource(name) {

    init {
        if (bypassArmor) setBypassesArmor()
        if (unblockable) setUnblockable()
    }

    public override fun setBypassesArmor(): DamageSource {
        return super.setBypassesArmor()
    }

    public override fun setUnblockable(): DamageSource {
        return super.setUnblockable()
    }

}