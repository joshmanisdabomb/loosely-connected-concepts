package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.abstracts.nuclear.NuclearUtil;
import com.joshmanisdabomb.lcc.component.NuclearComponent;
import com.joshmanisdabomb.lcc.directory.LCCComponents;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.SpawnHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnHelper.Info.class)
public interface NuclearSpawnHelperInfoAccessor {

    @Accessor("groupToCount")
    Object2IntOpenHashMap<SpawnGroup> getGroupToCountMap();

}
