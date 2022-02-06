package com.joshmanisdabomb.lcc.mixin.hooks.common;

import com.joshmanisdabomb.lcc.trait.LCCEffectTrait;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity {

    protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyVariable(method = "spawnEffectsCloud", at = @At(value = "STORE"), ordinal = 0)
    private Collection<StatusEffectInstance> blockEffect(Collection<StatusEffectInstance> original) {
        CreeperEntity me = (CreeperEntity)(Object)this;
        return original.stream().filter(e -> ((LCCEffectTrait)e.getEffectType()).lcc_canIncludeInExplosion(e, me)).collect(Collectors.toCollection(ArrayList::new));
    }

}
