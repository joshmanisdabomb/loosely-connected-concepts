package com.joshmanisdabomb.lcc.gen.feature;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.CandyCaneBlock;
import com.joshmanisdabomb.lcc.gen.world.GenUtility;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import static com.joshmanisdabomb.lcc.gen.feature.CandyCaneFeature.CANES;
import static com.joshmanisdabomb.lcc.gen.feature.CandyCaneFeature.COATING;

public class BigVividTreeFeature extends HugeTreesFeature<HugeTreeFeatureConfig> {

    public static final ResourceLocation VIVID_TREE_TOP = new ResourceLocation(LCC.MODID, "vivid_tree");

    public BigVividTreeFeature(Function<Dynamic<?>, ? extends HugeTreeFeatureConfig> config) {
        super(config);
    }

    @Override
    protected boolean func_225557_a_(IWorldGenerationReader wg, Random random, BlockPos pos, Set<BlockPos> posSet, Set<BlockPos> posSet2, MutableBoundingBox bb, HugeTreeFeatureConfig config) {
        BlockPos.Mutable bp = new BlockPos.Mutable(pos);
        int i = config.baseHeight + random.nextInt(config.field_227275_a_ + 1) + random.nextInt(config.field_227276_b_ + 1);


        if (!(wg instanceof IWorld)) return false;

        Template vividTreeTop = ((ServerWorld)(((IWorld)wg).getWorld())).getStructureTemplateManager().getTemplate(VIVID_TREE_TOP);
        BlockPos size = vividTreeTop.getSize();

        if (pos.getY() + i + size.getY() >= wg.getMaxHeight()) return false;
        if (!GenUtility.allInAreaMatches((IWorld)wg, pos.getX(), pos.getY() - 1, pos.getZ(), pos.getX() + 1, pos.getY() - 1, pos.getZ() + 1, 0, (w, p) -> w.getBlockState(p).getBlock() == LCCBlocks.rainbow_grass_block || w.getBlockState(p).getBlock() == LCCBlocks.sparkling_dirt)) return false;
        if (!GenUtility.allInAreaClear((IWorld)wg, pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + i, pos.getZ() + 1, 0)) return false;
        if (!GenUtility.allInAreaClear((IWorld)wg, pos.getX() - (size.getX() / 2), pos.getY() + i, pos.getZ() - (size.getZ() / 2), pos.getX() + (size.getX() / 2), pos.getY() + i + size.getY(), pos.getZ() + (size.getZ() / 2), 0)) return false;

        this.func_227254_a_(wg, random, pos, i + 1, posSet, bb, config);

        BlockPos.Mutable origin = bp.setPos(pos).move(-size.getX() / 2 + 1, 0, -size.getZ() / 2 + 1).move(Direction.UP, i);

        PlacementSettings p = new PlacementSettings().setRandom(random).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);

        vividTreeTop.func_215381_a(origin, p, LCCBlocks.vivid_log).forEach(bi -> {
            wg.setBlockState(bi.pos, bi.state, 18);
        });
        vividTreeTop.func_215381_a(origin, p, LCCBlocks.vivid_leaves).forEach(bi -> {
            wg.setBlockState(bi.pos, bi.state.with(LeavesBlock.PERSISTENT, false), 18);
        });
        return true;
    }

}