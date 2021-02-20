package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.abstracts.nuclear.NuclearUtil;
import com.joshmanisdabomb.lcc.component.NuclearComponent;
import com.joshmanisdabomb.lcc.directory.LCCComponents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.ChunkProvider;
import net.minecraft.world.chunk.light.ChunkLightProvider;
import net.minecraft.world.chunk.light.ChunkSkyLightProvider;
import net.minecraft.world.chunk.light.LightStorage;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChunkSkyLightProvider.class)
public abstract class NuclearSkylightProviderMixin extends ChunkLightProvider {

    public NuclearSkylightProviderMixin(ChunkProvider chunkProvider, LightType type, LightStorage lightStorage) {
        super(chunkProvider, type, lightStorage);
    }

    @Override
    public int getLightLevel(BlockPos pos) {
        int light = super.getLightLevel(pos);
        BlockView world = chunkProvider.getWorld();
        return MathHelper.floor(light * NuclearUtil.INSTANCE.getLightModifierFromWinter(LCCComponents.INSTANCE.getNuclear().maybeGet(world).map(NuclearComponent::getWinter).map(NuclearUtil.INSTANCE::getWinterLevel).orElse(1)));
    }

}