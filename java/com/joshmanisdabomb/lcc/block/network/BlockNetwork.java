package com.joshmanisdabomb.lcc.block.network;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BlockNetwork<T> {

    private final int distance;

    public BlockNetwork(int distance) {
        this.distance = distance;
    }

    public NetworkResult discover(World world, T start) {
        List<T> traversables = new ArrayList<>(Collections.singletonList(start));
        List<BlockPos> nodes = new ArrayList<>();
        BlockPos startPos = this.toPosition(start);
        for(int i = 0; i < traversables.size(); i++) {
            T traversable = traversables.get(i);
            for (T otherTraversable : this.traverse(world, traversable, nodes)) {
                if (startPos.withinDistance(this.toPosition(otherTraversable), this.distance) && !traversables.contains(otherTraversable)) {
                    traversables.add(otherTraversable);
                }
            }
        }
        return new NetworkResult(traversables.stream().map(this::toPosition).distinct().collect(Collectors.toList()), nodes.stream().distinct().collect(Collectors.toList()));
    }

    protected abstract List<T> traverse(World world, T current, List<BlockPos> nodes);

    protected abstract BlockPos toPosition(T traversable);

    public static final class NetworkResult {

        private final List<BlockPos> traversables;
        private final List<BlockPos> nodes;

        public NetworkResult(List<BlockPos> traversables, List<BlockPos> nodes) {
            this.traversables = traversables;
            this.nodes = nodes;
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + ": " + this.traversables.size() + " traversables, " + this.nodes.size() + " nodes";
        }

        public List<BlockPos> getTraversables() {
            return new ArrayList<>(this.traversables);
        }

        public List<BlockPos> getNodes() {
            return new ArrayList<>(this.nodes);
        }

    }

}
