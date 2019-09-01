package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ShearableBlock extends Block implements IShearableBlock {

    public final float speed;

    public ShearableBlock(float shearSpeed, Properties properties) {
        super(properties);
        this.speed = shearSpeed;
    }

    @Override
    public float getBreakSpeed(BlockState state, World world, BlockPos pos, ItemStack shears, PlayerEntity entity) {
        return speed;
    }
}
