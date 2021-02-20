package com.joshmanisdabomb.lcc.mixin.content.common;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Entity.class)
public abstract class GauntletEntityMixin {

    @ModifyVariable(method = "adjustMovementForCollisions", at = @At(value = "STORE"), ordinal = 3)
    public boolean modifyStepGroundFlag(boolean flag) {
        /*if (!((Object)this instanceof PlayerEntity)) return flag;
        for (GauntletAction g : GauntletAction.values()) {
            if (g.isActing((PlayerEntity)(Object)this)) {
                if (g.forceStep((PlayerEntity)(Object)this, g.getActorManager().fromTracker((PlayerEntity)(Object)this))) return true;
            }
        }
        return flag;*/
        return flag;
    }

}
