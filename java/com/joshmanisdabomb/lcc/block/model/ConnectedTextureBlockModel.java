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
import net.minecraft.util.math.MathHelper;
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
        final List<BakedQuad> quads = new ArrayList<>();

        if (side != null) {
            final boolean innerSeams = ((ConnectedTextureBlock)this.block).hasInnerSeams();
            final int uvOffset = ((ConnectedTextureBlock)this.block).borderWidth();
            final double vertexOffset = uvOffset / 16D;

            final Direction[] perpendiculars = VertexUtility.PERPENDICULARS.get(side); //up, right, down, left on 2d plane
            final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

            boolean cUp = tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.setPos(0,0,0).move(perpendiculars[0]))) && (!innerSeams || !tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.move(side))));
            boolean cRight = tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.setPos(0,0,0).move(perpendiculars[1]))) && (!innerSeams || !tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.move(side))));
            boolean cDown = tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.setPos(0,0,0).move(perpendiculars[2]))) && (!innerSeams || !tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.move(side))));
            boolean cLeft = tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.setPos(0,0,0).move(perpendiculars[3]))) && (!innerSeams || !tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.move(side))));
            boolean cUpLeft = tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.setPos(0,0,0).move(perpendiculars[0]).move(perpendiculars[3]))) && (!innerSeams || !tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.move(side))));
            boolean cUpRight = tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.setPos(0,0,0).move(perpendiculars[1]).move(perpendiculars[0]))) && (!innerSeams || !tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.move(side))));
            boolean cDownRight = tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.setPos(0,0,0).move(perpendiculars[2]).move(perpendiculars[1]))) && (!innerSeams || !tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.move(side))));
            boolean cDownLeft = tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.setPos(0,0,0).move(perpendiculars[3]).move(perpendiculars[2]))) && (!innerSeams || !tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.move(side))));

            quads.add(VertexUtility.create2DFace(side, vertexOffset, vertexOffset, 1-vertexOffset, 1-vertexOffset, 1, getTexture(ConnectedTextureBlock.TextureType.base(side)), uvOffset, uvOffset, 16-uvOffset, 16-uvOffset));

            //top
            quads.add(VertexUtility.create2DFace(side, vertexOffset, 0, 1-vertexOffset, vertexOffset, 1, getSideTexture(cUp, false, side), uvOffset, 0, 16-uvOffset, uvOffset));
            //right
            quads.add(VertexUtility.create2DFace(side, 1-vertexOffset, vertexOffset, 1, 1-vertexOffset, 1, getSideTexture(cRight, true, side), 16-uvOffset, uvOffset, 16, 16-uvOffset));
            //bottom
            quads.add(VertexUtility.create2DFace(side, vertexOffset, 1-vertexOffset, 1-vertexOffset, 1, 1, getSideTexture(cDown, false, side), uvOffset, 16-uvOffset, 16-uvOffset, 16));
            //left
            quads.add(VertexUtility.create2DFace(side, 0, vertexOffset, vertexOffset, 1-vertexOffset, 1, getSideTexture(cLeft, true, side), 0, uvOffset, uvOffset, 16-uvOffset));

            //top left
            quads.add(VertexUtility.create2DFace(side, 0, 0, vertexOffset, vertexOffset, 1, getCornerTexture(cUp, cLeft, cUpLeft, side), getCornerU(0, uvOffset, cUp, cLeft, cUpLeft), 0, getCornerU(uvOffset, uvOffset, cUp, cLeft, cUpLeft), uvOffset));
            //top right
            quads.add(VertexUtility.create2DFace(side, 1-vertexOffset, 0, 1, vertexOffset, 1, getCornerTexture(cUp, cRight, cUpRight, side), getCornerU(16-uvOffset, uvOffset, cUp, cRight, cUpRight), 0, getCornerU(16, uvOffset, cUp, cRight, cUpRight), uvOffset));
            //bottom right
            quads.add(VertexUtility.create2DFace(side, 1-vertexOffset, 1-vertexOffset, 1, 1, 1, getCornerTexture(cDown, cRight, cDownRight, side), getCornerU(16-uvOffset, uvOffset, cDown, cRight, cDownRight), 16-uvOffset, getCornerU(16, uvOffset, cDown, cRight, cDownRight), 16));
            //bottom left
            quads.add(VertexUtility.create2DFace(side, 0, 1-vertexOffset, vertexOffset, 1, 1, getCornerTexture(cDown, cLeft, cDownLeft, side), getCornerU(0, uvOffset, cDown, cLeft, cDownLeft), 16-uvOffset, getCornerU(uvOffset, uvOffset, cDown, cLeft, cDownLeft), 16));
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

    private TextureAtlasSprite getSideTexture(boolean connection, boolean vertical, Direction side) {
        return this.getTexture(connection ? ConnectedTextureBlock.TextureType.base(side) : (vertical ? ConnectedTextureBlock.TextureType.lines_v(side) : ConnectedTextureBlock.TextureType.lines_h(side)));
    }

    private TextureAtlasSprite getCornerTexture(boolean connectionV, boolean connectionH, boolean connectionVH, Direction side) {
        if (connectionVH && connectionV && connectionH) {
            return this.getTexture(ConnectedTextureBlock.TextureType.base(side));
        } else if (!connectionVH && connectionV && connectionH) {
            return this.getTexture(ConnectedTextureBlock.TextureType.corners(side));
        } else if (connectionV) {
            return this.getTexture(ConnectedTextureBlock.TextureType.lines_v(side));
        } else if (connectionH) {
            return this.getTexture(ConnectedTextureBlock.TextureType.lines_h(side));
        } else {
            return this.getTexture(ConnectedTextureBlock.TextureType.corners(side));
        }
    }

    private int getCornerU(int u, int borderWidth, boolean connectionV, boolean connectionH, boolean connectionVH) {
        if (!connectionVH && connectionV && connectionH) {
            return MathHelper.signum(u - 8) > 0 ? u - borderWidth : u + borderWidth;
        } else {
            return u;
        }
    }

}