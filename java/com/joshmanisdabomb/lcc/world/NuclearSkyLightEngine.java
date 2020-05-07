package com.joshmanisdabomb.lcc.world;

import com.joshmanisdabomb.lcc.capability.NuclearCapability;
import com.joshmanisdabomb.lcc.functionality.NuclearFunctionality;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkLightProvider;
import net.minecraft.world.lighting.SkyLightEngine;
import net.minecraftforge.common.util.LazyOptional;

public class NuclearSkyLightEngine extends SkyLightEngine {

    private final World world;

    public NuclearSkyLightEngine(IChunkLightProvider p_i51289_1_, World world) {
        super(p_i51289_1_);
        this.world = world;
    }

    @Override
    public int getLightFor(BlockPos worldPos) {
        LazyOptional<NuclearCapability> capability = world.getCapability(NuclearCapability.Provider.DEFAULT_CAPABILITY);
        if (capability.isPresent()) {
            NuclearCapability nuclear = capability.orElseThrow(RuntimeException::new);
            if (NuclearFunctionality.getWinterLevel(nuclear.score) > 0) {
                return Math.min(super.getLightFor(worldPos), NuclearFunctionality.getLightLevel(nuclear.score));
            }
        }
        return super.getLightFor(worldPos);
    }

}
