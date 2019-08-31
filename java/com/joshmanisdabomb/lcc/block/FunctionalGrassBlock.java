package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.lighting.LightEngine;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import java.util.Random;
import java.util.function.BiFunction;

public class FunctionalGrassBlock extends GrassBlock {

    private final BiFunction<BlockState, Boolean, BlockState> change;

    public FunctionalGrassBlock(BiFunction<BlockState, Boolean, BlockState> change, Properties properties) {
        super(properties);
        this.change = change;
}

    public boolean survives(BlockState state, IWorldReader world, BlockPos pos, boolean water) {
        BlockPos pos2 = pos.up();
        BlockState state2 = world.getBlockState(pos2);
        if (water && world.getFluidState(pos2).isTagged(FluidTags.WATER)) {
            return false;
        } else if (state2.getBlock() == Blocks.SNOW && state2.get(SnowBlock.LAYERS) == 1) {
            return true;
        } else {
            int i = LightEngine.func_215613_a(world, state, pos, state2, pos2, Direction.UP, state2.getOpacity(world, pos2));
            return i < world.getMaxLightLevel();
        }
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random random) {
        if (!world.isRemote) {
            if (!world.isAreaLoaded(pos, 3)) return;
            if (!survives(state, world, pos, false)) {
                world.setBlockState(pos, this.change.apply(null, null));
            } else {
                if (world.getLight(pos.up()) >= 9) {
                    for(int i = 0; i < 4; ++i) {
                        BlockPos pos2 = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                        if (survives(state, world, pos2, true)) {
                            BlockState param = world.getBlockState(pos2);
                            if (param.getBlock() != this) {
                                BlockState set = this.change.apply(param, world.getBlockState(pos2.up()).getBlock() == Blocks.SNOW);
                                if (set != null && set != param) {
                                    world.setBlockState(pos2, set);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.SOLID;
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction side, IPlantable plantable) {
        net.minecraftforge.common.PlantType type = plantable.getPlantType(world, pos.offset(side));

        if (plantable instanceof BushBlock || type == PlantType.Plains) return true;
        return type == PlantType.Beach && (world.getBlockState(pos.east()).getMaterial() == Material.WATER || world.getBlockState(pos.west()).getMaterial() == Material.WATER || world.getBlockState(pos.north()).getMaterial() == Material.WATER || world.getBlockState(pos.south()).getMaterial() == Material.WATER);
    }

}
