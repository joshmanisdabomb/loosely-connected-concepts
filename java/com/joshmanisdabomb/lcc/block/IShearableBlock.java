package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public interface IShearableBlock extends IShearable {

    default float getBreakSpeed(BlockState state, World world, BlockPos pos, ItemStack shears, PlayerEntity entity) {
        return 1.0F;
    }

}