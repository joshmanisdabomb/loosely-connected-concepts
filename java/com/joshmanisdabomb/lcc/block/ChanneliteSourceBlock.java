package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.item.DyeColor;

public class ChanneliteSourceBlock extends Block implements LCCBlockHelper {

    private final DyeColor color;

    public ChanneliteSourceBlock(DyeColor color, Properties p) {
        super(p);
        this.color = color;
    }

    @Override
    public RenderType getRenderLayer() {
        return RenderType.getCutoutMipped();
    }

    public DyeColor getColor() {
        return color;
    }

}
