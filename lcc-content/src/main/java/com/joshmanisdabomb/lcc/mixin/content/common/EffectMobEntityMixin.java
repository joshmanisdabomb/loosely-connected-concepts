package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.directory.LCCEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class EffectMobEntityMixin extends LivingEntity {

    @Shadow
    protected MoveControl moveControl;

    protected EffectMobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tickNewAi", at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V", args = "ldc=goalSelector"), cancellable = true)
    private void stopAiWhenStun(CallbackInfo info) {
        if (hasStatusEffect(LCCEffects.INSTANCE.getStun())) {
            if (LCCEffects.INSTANCE.getStun().handleMobAi((MobEntity)(Object)this, moveControl)) info.cancel();
        }
    }

}
