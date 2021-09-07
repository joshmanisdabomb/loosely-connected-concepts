package com.joshmanisdabomb.lcc.mixin.content.common;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.SpawnHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SpawnHelper.Info.class)
public interface NuclearSpawnHelperInfoAccessor {

    @Accessor("groupToCount")
    Object2IntOpenHashMap<SpawnGroup> getGroupToCountMap();

}
