package com.joshmanisdabomb.lcc.item;

import com.joshmanisdabomb.lcc.data.capability.CapabilityHearts;
import com.joshmanisdabomb.lcc.functionality.HeartsFunctionality;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class ItemHeartContainer extends ItemHeart {

    public ItemHeartContainer(HeartsFunctionality.HeartType ht, float value, Properties p) {
        super(ht, value, p);
    }

    protected void onHeartEffect(CapabilityHearts.CIHearts hearts, EntityLivingBase entity) {
        this.ht.addMaxHealth(hearts, entity, this.value);
        this.ht.addHealth(hearts, entity, this.value, HeartsFunctionality.TEMPORARY_USUAL_LIMIT);
    }

    protected boolean canUse(CapabilityHearts.CIHearts hearts, EntityLivingBase entity) {
        return !this.ht.isFullMaxHealth(hearts, entity);
    }

}
