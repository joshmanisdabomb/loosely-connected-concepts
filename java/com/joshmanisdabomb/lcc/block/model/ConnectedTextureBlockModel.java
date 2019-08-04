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
        for (Map.Entry<BlockPos, ModelProperty<Boolean>> e : ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.entrySet()) {
            tileData.setData(e.getValue(), ((ConnectedTextureBlock)this.block).connectWith(state, world.getBlockState(pos.add(e.getKey()))));
        }
        return tileData;
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData tileData) {
        List<BakedQuad> quads = new ArrayList<>();

        int uvOffset = ((ConnectedTextureBlock)this.block).borderWidth();
        double vertexOffset = uvOffset / 16D;
        if (side != null) {
            quads.add(VertexUtility.create2DFace(side, vertexOffset, vertexOffset, 1-vertexOffset, 1-vertexOffset, 1, this.getTexture(ConnectedTextureBlock.TextureType.base(side)), uvOffset, uvOffset, 16-uvOffset, 16-uvOffset));

            quads.add(VertexUtility.create2DFace(side, 0, vertexOffset, vertexOffset, 1-vertexOffset, 1, this.getTexture(ConnectedTextureBlock.TextureType.lines_v(side)), 0, uvOffset, uvOffset, 16-uvOffset));
            quads.add(VertexUtility.create2DFace(side, 1-vertexOffset, vertexOffset, 1, 1-vertexOffset, 1, this.getTexture(ConnectedTextureBlock.TextureType.lines_v(side)), 16-uvOffset, uvOffset, 16, 16-uvOffset));
            quads.add(VertexUtility.create2DFace(side, vertexOffset, 0, 1-vertexOffset, vertexOffset, 1, this.getTexture(ConnectedTextureBlock.TextureType.lines_h(side)), uvOffset, 0, 16-uvOffset, uvOffset));
            quads.add(VertexUtility.create2DFace(side, vertexOffset, 1-vertexOffset, 1-vertexOffset, 1, 1, this.getTexture(ConnectedTextureBlock.TextureType.lines_h(side)), uvOffset, 16-uvOffset, 16-uvOffset, 16));
        }

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