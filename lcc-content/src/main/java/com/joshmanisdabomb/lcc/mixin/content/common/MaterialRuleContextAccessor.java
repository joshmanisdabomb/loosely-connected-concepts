package com.joshmanisdabomb.lcc.mixin.content.common;

import net.minecraft.world.gen.noise.NoiseConfig;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MaterialRules.MaterialRuleContext.class)
public interface MaterialRuleContextAccessor {

    @Accessor("noiseConfig")
    NoiseConfig getNoiseConfig();

    @Accessor("stoneDepthAbove")
    int getStoneDepthAbove();

}
