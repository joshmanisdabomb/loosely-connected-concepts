package com.joshmanisdabomb.lcc.entity

import net.minecraft.entity.damage.DamageSource

class LCCDamageSource(name: String, bypassArmor: Boolean = false, unblockable: Boolean = false, fire: Boolean = false) : DamageSource(name) {

    init {
        if (bypassArmor) setBypassesArmor()
        if (unblockable) setUnblockable()
        if (fire) setFire()
    }

    public override fun setBypassesArmor() = super.setBypassesArmor()
    public override fun setUnblockable() = super.setUnblockable()
    public override fun setFire() = super.setFire()

}