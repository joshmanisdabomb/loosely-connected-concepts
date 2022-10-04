package com.joshmanisdabomb.lcc.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public interface RandomTickCallback {

    Event<RandomTickCallback> EVENT = EventFactory.createArrayBacked(RandomTickCallback.class,
        (listeners) -> (world, state, pos, random) -> {
            for (RandomTickCallback listener : listeners) {
                boolean result = listener.randomTick(world, state, pos, random);
                if (result) return true;
            }
            return false;
        });

    //true to block other listeners, false to continue
    boolean randomTick(ServerWorld world, BlockState state, BlockPos pos, Random random);

}
