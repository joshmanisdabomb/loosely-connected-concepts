package com.joshmanisdabomb.lcc.mixin.base.client;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.render.BlockBreakingInfo;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientPlayerInteractionManager.class)
public interface ClientPlayerInteractionAccessor {

    @Accessor("breakingBlock")
    public boolean isBreakingBlock();

    @Accessor("currentBreakingPos")
    public BlockPos getCurrentBreakingPos();

    @Accessor("currentBreakingProgress")
    public float getCurrentBreakingProgress();

}