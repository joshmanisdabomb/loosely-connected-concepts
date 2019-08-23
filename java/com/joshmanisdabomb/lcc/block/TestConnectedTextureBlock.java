package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.block.render.ConnectedTextureBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;

public class TestConnectedTextureBlock extends Block implements LCCBlockHelper, ConnectedTextureBlock {

    public TestConnectedTextureBlock(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean connectWith(BlockState state, BlockState other) {
        return other.equals(state);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public HashMap<TextureType, ResourceLocation> getConnectedTextures() {
        return traitGetTexturesForNonSided("test/5");
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean hasInnerSeams() {
        return true;
    }

}
