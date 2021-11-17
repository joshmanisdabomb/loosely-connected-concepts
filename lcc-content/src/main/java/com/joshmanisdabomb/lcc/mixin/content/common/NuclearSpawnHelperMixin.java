package com.joshmanisdabomb.lcc.mixin.content.common;

import net.minecraft.world.SpawnHelper;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SpawnHelper.class)
public abstract class NuclearSpawnHelperMixin {

    private static final int CHUNK_AREA = (int)Math.pow(17.0D, 2.0D);

    /*@Redirect(method = "spawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/SpawnHelper$Info;method_27829(Lnet/minecraft/world/SpawnHelper$Info;Lnet/minecraft/entity/SpawnGroup;)Z"))
    private static boolean belowCapOverride(SpawnHelper.Info info, SpawnGroup group, ServerWorld world) {
        int w = NuclearUtil.INSTANCE.getWinterLevel(LCCComponents.INSTANCE.getNuclear().maybeGet(world).map(NuclearComponent::getWinter).orElse(0f));
        int i = MathHelper.floor(group.getCapacity() * NuclearUtil.INSTANCE.getMobCapIncrease(w, group)) * info.getSpawningChunkCount() / CHUNK_AREA;
        return ((NuclearSpawnHelperInfoAccessor)info).getGroupToCountMap().getInt(group) < i;
    }*/

}