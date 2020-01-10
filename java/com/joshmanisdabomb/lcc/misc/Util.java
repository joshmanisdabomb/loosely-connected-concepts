package com.joshmanisdabomb.lcc.misc;

import net.minecraft.util.Direction;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;

public abstract class Util {

    public static final HashMap<Direction, Direction[]> PERPENDICULARS = net.minecraft.util.Util.make(new HashMap<>(), map -> {
        map.put(Direction.UP, new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST});
        map.put(Direction.DOWN, new Direction[]{Direction.SOUTH, Direction.EAST, Direction.NORTH, Direction.WEST});
        map.put(Direction.NORTH, new Direction[]{Direction.UP, Direction.WEST, Direction.DOWN, Direction.EAST});
        map.put(Direction.EAST, new Direction[]{Direction.UP, Direction.NORTH, Direction.DOWN, Direction.SOUTH});
        map.put(Direction.SOUTH, new Direction[]{Direction.UP, Direction.EAST, Direction.DOWN, Direction.WEST});
        map.put(Direction.WEST, new Direction[]{Direction.UP, Direction.SOUTH, Direction.DOWN, Direction.NORTH});
    });

    public static Pair<Integer, Integer> get2DSpiralOffset(int traversal) {
        int x = 0, y = 0, l = 0, ml = 1;
        boolean addMl = false;
        Direction d = Direction.NORTH;
        for (int i = 0; i < traversal; i++) {
            x += d.getXOffset();
            y += d.getZOffset();
            if (++l >= ml) {
                l = 0;
                d = d.rotateY();
                if (!(addMl = !addMl)) ml++;
            }
        }
        return new ImmutablePair<>(x,y);
    }

}
