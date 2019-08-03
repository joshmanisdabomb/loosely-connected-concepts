package com.joshmanisdabomb.lcc.block.model;

import com.joshmanisdabomb.lcc.block.AdvancedBlockRender;
import com.joshmanisdabomb.lcc.block.ConnectedTextureBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class ConnectedTextureBlockModel implements IBakedModel {

    private final Block block;
    private final IBakedModel defaultModel;

    private final HashMap<ConnectedTextureBlock.TextureType, TextureAtlasSprite> bakedTextures = new HashMap<>();

    public ConnectedTextureBlockModel(Block b, IBakedModel defaultModel) {
        this.block = b;
        this.defaultModel = defaultModel;

        for (Map.Entry<ConnectedTextureBlock.TextureType, ResourceLocation> e : ((ConnectedTextureBlock) b).getConnectedTextures().entrySet()) {
            bakedTextures.put(e.getKey(), ModelLoader.defaultTextureGetter().apply(e.getValue()));
        }
    }

    @Nonnull
    @Override
    public IModelData getModelData(@Nonnull IEnviromentBlockReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
        tileData = AdvancedBlockRender.DATA;
        ConnectedTextureBlock ct = (ConnectedTextureBlock) state.getBlock();
        for (Map.Entry<BlockPos, ModelProperty<Boolean>> e : ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.entrySet()) {
            tileData.setData(e.getValue(), ct.connectWith(state, world.getBlockState(pos.add(e.getKey()))));
        }
        return tileData;
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData tileData) {
        List<BakedQuad> quads = new ArrayList<>();

        //System.out.println(tileData.getData(ConnectedTextureBlock.UP));

        if (side != null) {
            quads.add(VertexUtility.create2DFace(side, 0.1, 0.1, 0.9, 0.9, 1, this.getTexture(ConnectedTextureBlock.TextureType.TOP_BASE), 0, 0, 16, 16));
        }
        //return defaultModel.getQuads(state, side, rand, extraData);
        return quads;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand) {
        return getQuads(state, side, rand, EmptyModelData.INSTANCE);
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return true;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return this.getTexture(ConnectedTextureBlock.TextureType.SIDE_BASE);
    }

    @Override
    public ItemOverrideList getOverrides() {
        return null;
    }

    private TextureAtlasSprite getTexture(ConnectedTextureBlock.TextureType tt) {
        return bakedTextures.get(tt);
    }

}