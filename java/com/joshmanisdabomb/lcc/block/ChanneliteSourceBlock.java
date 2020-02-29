package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.Block;
import net.minecraft.item.DyeColor;
import net.minecraft.util.BlockRenderLayer;

public class ChanneliteSourceBlock extends Block {

    private final DyeColor color;

    public ChanneliteSourceBlock(DyeColor color, Properties p) {
        super(p);
        this.color = color;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    public DyeColor getColor() {
        return color;
    }

}
