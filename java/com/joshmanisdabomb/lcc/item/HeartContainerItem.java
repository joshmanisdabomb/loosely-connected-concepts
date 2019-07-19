package com.joshmanisdabomb.lcc.item;

import com.joshmanisdabomb.lcc.data.capability.HeartsCapability;
import com.joshmanisdabomb.lcc.functionality.HeartsFunctionality;
import net.minecraft.entity.LivingEntity;

public class HeartContainerItem extends HeartItem {

    public HeartContainerItem(HeartsFunctionality.HeartType ht, float value, Properties p) {
        super(ht, value, p);
    }

    protected void onHeartEffect(HeartsCapability.CIHearts hearts, LivingEntity entity) {
        this.ht.addMaxHealth(hearts, entity, this.value);
        this.ht.addHealth(hearts, entity, this.value, HeartsFunctionality.TEMPORARY_USUAL_LIMIT);
    }

    protected boolean canUse(HeartsCapability.CIHearts hearts, LivingEntity entity) {
        return !this.ht.isFullMaxHealth(hearts, entity);
    }

}
