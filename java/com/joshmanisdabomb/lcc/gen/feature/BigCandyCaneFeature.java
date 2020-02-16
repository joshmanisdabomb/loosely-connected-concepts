package com.joshmanisdabomb.lcc.gen.feature;

import com.google.common.collect.ImmutableList;
import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.CandyCaneBlock;
import com.joshmanisdabomb.lcc.gen.world.GenUtility;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.*;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Function;

import static com.joshmanisdabomb.lcc.gen.feature.CandyCaneFeature.CANES;
import static com.joshmanisdabomb.lcc.gen.feature.CandyCaneFeature.COATING;

public class BigCandyCaneFeature extends Feature<NoFeatureConfig> {

    public static final ResourceLocation CANDY_CANE_TOP = new ResourceLocation(LCC.MODID, "candy_cane");

    private final int minHeight;
    private final int variation;

    public BigCandyCaneFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> config, boolean notify, int minHeight, int variation) {
        super(config, notify);
        this.minHeight = minHeight;
        this.variation = variation;
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        BlockPos.MutableBlockPos bp = new BlockPos.MutableBlockPos(pos);

        if (!world.isRemote()) {
            Template candyCaneTop = ((ServerWorld)world.getWorld()).getStructureTemplateManager().getTemplate(CANDY_CANE_TOP);
            BlockPos size = candyCaneTop.getSize();

            int height = minHeight + rand.nextInt(variation + 1);
            Rotation r = Rotation.randomRotation(rand);

            int type = rand.nextInt(3);

            BlockState cane = CANES[type].getDefaultState().with(BlockStateProperties.AXIS, Direction.Axis.Y);
            BlockState coating = COATING[type].getDefaultState().with(BlockStateProperties.AXIS, Direction.Axis.Y);

            if (pos.getY() + height + size.getY() >= world.getMaxHeight()) return false;
            if (!GenUtility.allInAreaMatches(world, pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1, pos.getX() + 4, pos.getY() - 1, pos.getZ() + 4, 0, (w, p) -> w.getBlockState(p).getBlock() == LCCBlocks.sugar_grass_block)) return false;
            if (!GenUtility.allInAreaClear(world, pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 3, pos.getY() + height, pos.getZ() + 3, 1)) return false;

            //lazy check for all directions
            if (!GenUtility.allInAreaClear(world, pos.getX() - 7, pos.getY() + 1 + height, pos.getZ() - 7, pos.getX() + 10, pos.getY() + height, pos.getZ() + 10, 1)) return false;

            for (int i = 0; i < height; i++) {
                for (int x = 0; x < 4; x++) {
                    for (int z = 0; z < 4; z++) {
                        if ((x == 0 || x == 3) && (z == 0 || z == 3)) continue;
                        world.setBlockState(bp.setPos(pos).move(x, i, z), CandyCaneBlock.getCaneState(cane, bp), 18);
                    }
                }
            }

            BlockPos.MutableBlockPos origin = bp.setPos(pos).move(Direction.UP, height);

            PlacementSettings p = new PlacementSettings().setRandom(rand).setRotation(r).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
            switch (r) {
                case CLOCKWISE_90:
                    origin.move(3, 0, 0);
                    break;
                case CLOCKWISE_180:
                    origin.move(3, 0, 3);
                    break;
                case COUNTERCLOCKWISE_90:
                    origin.move(0, 0, 3);
                    break;
                default:
                    break;
            }

            candyCaneTop.func_215381_a(origin, p, LCCBlocks.candy_cane_red).forEach(bi -> {
                world.setBlockState(bi.pos, CandyCaneBlock.getCaneState(cane, bi.pos), 18);
            });
            candyCaneTop.func_215381_a(origin, p, LCCBlocks.candy_cane_coating_red).forEach(bi -> {
                world.setBlockState(bi.pos, CandyCaneBlock.getCaneState(coating, bi.pos), 18);
            });
            return true;
        }
        return false;
    }

}