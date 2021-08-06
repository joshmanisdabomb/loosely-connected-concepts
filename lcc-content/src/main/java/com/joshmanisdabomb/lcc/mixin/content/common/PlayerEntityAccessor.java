package com.joshmanisdabomb.lcc.mixin.content.common;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerEntity.class)
public interface PlayerEntityAccessor {

    @Accessor("lcc_lastHitCritical")
    boolean getLastHitCritical();

}
