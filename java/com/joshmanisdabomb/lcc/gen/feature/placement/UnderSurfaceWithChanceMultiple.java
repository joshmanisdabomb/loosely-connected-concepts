package com.joshmanisdabomb.lcc.gen.feature.placement;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.placement.HeightWithChanceConfig;
import net.minecraft.world.gen.placement.Placement;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class UnderSurfaceWithChanceMultiple extends Placement<HeightWithChanceConfig> {

    public UnderSurfaceWithChanceMultiple(Function<Dynamic<?>, ? extends HeightWithChanceConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generatorIn, Random random, HeightWithChanceConfig configIn, BlockPos pos) {
        return IntStream.range(0, configIn.count).filter((p_215043_2_) -> random.nextFloat() < configIn.chance).mapToObj((p_215042_3_) -> {
            ArrayList<BlockPos> list = new ArrayList<>();

            int i = random.nextInt(16);
            int j = random.nextInt(16);

            BlockPos p = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING, pos.add(i, 0, j));
            BlockPos.Mutable bp = new BlockPos.Mutable();

            for (int k = 0; k < p.getY() - 1; k++) {
                bp.setPos(p).setY(0);
                BlockState state = worldIn.getBlockState(bp.move(0, k, 0));
                BlockState state2 = worldIn.getBlockState(bp.move(0, 1, 0));
                if (Heightmap.Type.MOTION_BLOCKING.getHeightLimitPredicate().test(state) && !Heightmap.Type.MOTION_BLOCKING.getHeightLimitPredicate().test(state2)) {
                    list.add(bp.toImmutable());
                }
            }

            if (list.size() > 0) {
                return list.get(random.nextInt(list.size()));
            } else {
                return null;
            }
        }).filter(Objects::nonNull).distinct();
    }

}
