package com.joshmanisdabomb.lcc.block.render;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public interface AdvancedBlockRender {

    @OnlyIn(Dist.CLIENT)
    IModelData DATA = new ModelDataMap.Builder().build();

    @OnlyIn(Dist.CLIENT)
    ModelProperty<BlockState> STATE = new ModelProperty<>();

    @OnlyIn(Dist.CLIENT)
    ModelProperty<BlockPos> POS = new ModelProperty<>();

    @OnlyIn(Dist.CLIENT)
    default Collection<ModelResourceLocation> getModelLocations() {
        ArrayList<ModelResourceLocation> mrl = new ArrayList<>();
        for (BlockState state : this.getStateContainer().getValidStates()) {
            mrl.add(BlockModelShapes.getModelLocation(state));
        }
        return mrl.stream().distinct().collect(Collectors.toList());
    }

    @OnlyIn(Dist.CLIENT)
    IBakedModel newModel(Block block);

    @OnlyIn(Dist.CLIENT)
    Collection<ResourceLocation> getTextures();

    StateContainer<Block, BlockState> getStateContainer();

}
