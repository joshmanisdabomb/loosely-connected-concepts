package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;

public class SparklingGrassBlock extends FunctionalSnowlessGrassBlock implements TintedBlock {

    private final DyeColor color;

    public SparklingGrassBlock(DyeColor color, Properties properties) {
        super(state -> {
            if (state == null) return LCCBlocks.sparkling_dirt.getDefaultState();
            else if (state.getBlock() == LCCBlocks.sparkling_dirt) return LCCBlocks.sparkling_grass_block.get(color).getDefaultState();
            else return null;
        }, properties);
        this.color = color;
    }

    public int getColor() {
        int r = (color.getTextColor() & 0xFF0000) >> 16;
        int g = (color.getTextColor() & 0x00FF00) >> 8;
        int b = color.getTextColor() & 0x0000FF;
        return ((int)((r * 0.8F) + 22)) << 16 | ((int)((g * 0.8F) + 22)) << 8 | (int)((b * 0.8F) + 22);
    }

    @Override
    public int getBlockTintColor(BlockState state, ILightReader world, BlockPos pos, int tintIndex) {
        if (tintIndex != 1) return 0xFFFFFF;
        int color = this.getColor();
        int r = (color & 0xFF0000) >> 16;
        int g = (color & 0x00FF00) >> 8;
        int b = color & 0x0000FF;

        int adjacent = 0;
        int r2 = 0, b2 = 0, g2 = 0;
        for (int i = 0; i < 4; i++) {
            Direction d = Direction.byHorizontalIndex(i);
            BlockState state2 = world.getBlockState(pos.offset(d));
            if (state2.getBlock() instanceof SparklingGrassBlock && state2.getBlock() != this) {
                int color2 = ((SparklingGrassBlock)state2.getBlock()).getColor();
                r2 += (color2 & 0xFF0000) >> 16;
                g2 += (color2 & 0x00FF00) >> 8;
                b2 += color2 & 0x0000FF;
                adjacent++;
            }
        }

        if (adjacent > 0) {
            r = (r + r + (r2 / adjacent)) / 3;
            g = (g + g + (g2 / adjacent)) / 3;
            b = (b + b + (b2 / adjacent)) / 3;
        }

        return r << 16 | g << 8 | b;
    }

    @Override
    public int getItemTintColor(ItemStack stack, int tintIndex) {
        return this.getColor();
    }

}
