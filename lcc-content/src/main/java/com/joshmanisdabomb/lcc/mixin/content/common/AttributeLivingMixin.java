package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.directory.LCCAttributes;
import kotlin.Unit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class AttributeLivingMixin {

    @Inject(method = "createLivingAttributes", at = @At("RETURN"))
    private static void addAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cinfo) {
        //TODO directory system 3.0, query initialised objects, late initialisation?
        try {
            LCCAttributes.INSTANCE.getWasteland_damage();
        } catch (Exception e) {
            LCCAttributes.INSTANCE.init(Unit.INSTANCE, d -> true);
        }
        for (EntityAttribute a : LCCAttributes.INSTANCE.getAll().values()) {
            cinfo.getReturnValue().add(a);
        }
    }

}
