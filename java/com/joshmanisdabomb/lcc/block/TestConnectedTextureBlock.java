package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.block.render.ConnectedTextureBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TestConnectedTextureBlock extends Block implements LCCBlockHelper, ConnectedTextureBlock {

    @OnlyIn(Dist.CLIENT)
    private ConnectedTextureMap connectedTextureMap;

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
    public ConnectedTextureMap getConnectedTextureMap() {
        if (connectedTextureMap == null) connectedTextureMap = new ConnectedTextureMap().useWhen(state -> true, "test/5", false);
        return connectedTextureMap;
    }

}
