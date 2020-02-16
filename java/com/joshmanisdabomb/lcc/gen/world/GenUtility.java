package com.joshmanisdabomb.lcc.gen.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import java.util.function.BiPredicate;

public abstract class GenUtility {

    public static boolean allInAreaMatches(IWorld world, int x1, int y1, int z1, int x2, int y2, int z2, int expand, BiPredicate<IWorld, BlockPos> match) {
        int xMin = Math.min(x1, x2) - expand;
        int xMax = Math.max(x1, x2) + expand;
        int yMin = Math.min(y1, y2) - expand;
        int yMax = Math.max(y1, y2) + expand;
        int zMin = Math.min(z1, z2) - expand;
        int zMax = Math.max(z1, z2) + expand;
        try (BlockPos.PooledMutableBlockPos pos = BlockPos.PooledMutableBlockPos.retain()) {
            for (int x = xMin; x <= xMax; x++) {
                for (int y = yMin; y <= yMax; y++) {
                    for (int z = zMin; z <= zMax; z++) {
                        pos.setPos(x, y, z);
                        if (!match.test(world, pos)) return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean allInAreaMatches(IWorld world, BlockPos pos1, BlockPos pos2, BiPredicate<IWorld, BlockPos> match) {
        return GenUtility.allInAreaMatches(world, pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ(), 0, match);
    }

    public static boolean allInAreaClear(IWorld world, int x1, int y1, int z1, int x2, int y2, int z2, int expand) {
        return GenUtility.allInAreaMatches(world, x1, y1, z1, x2, y2, z2, expand, (w, bp) -> world.isAirBlock(bp));
    }

    public static boolean allInAreaClear(IWorld world, BlockPos pos1, BlockPos pos2) {
        return GenUtility.allInAreaMatches(world, pos1, pos2, (w, bp) -> world.isAirBlock(bp));
    }

}
