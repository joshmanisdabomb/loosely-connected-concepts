package com.joshmanisdabomb.lcc.mixin.hooks.client;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.render.BlockBreakingInfo;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WorldRenderer.class)
public interface WorldRendererAccessor {

    @Accessor("blockBreakingInfos")
    public Int2ObjectMap<BlockBreakingInfo> getBlockBreakingInfos();

}