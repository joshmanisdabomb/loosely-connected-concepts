package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.model.RainbowGateModel;
import com.joshmanisdabomb.lcc.block.render.AdvancedBlockRender;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import java.util.Arrays;
import java.util.Collection;

public class RainbowGateBlock extends Block implements AdvancedBlockRender {

    private static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);

    public RainbowGateBlock(Properties p) {
        super(p);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    @Override
    public IBakedModel newModel(Block block) {
        return new RainbowGateModel(block);
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return Arrays.asList(
            new ResourceLocation(LCC.MODID, "block/rainbow/gate/base"),
            new ResourceLocation(LCC.MODID, "block/rainbow/gate/redstone"),
            new ResourceLocation(LCC.MODID, "block/rainbow/gate/gold"),
            new ResourceLocation(LCC.MODID, "block/rainbow/gate/lapis"),
            new ResourceLocation(LCC.MODID, "block/rainbow/gate/uranium"),
            new ResourceLocation(LCC.MODID, "block/rainbow/gate/coal"),
            new ResourceLocation(LCC.MODID, "block/rainbow/gate/iron")
        );
    }

}
