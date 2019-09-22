package com.joshmanisdabomb.lcc.block.model;

import com.joshmanisdabomb.lcc.block.render.AdvancedBlockRender;
import com.joshmanisdabomb.lcc.block.render.ConnectedTextureBlock;
import com.joshmanisdabomb.lcc.misc.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ConnectedTextureBlockModel implements IBakedModel {

    private final Block block;

    private final ConnectedTextureBlock.ConnectedTextureMap.BakedConnectedTextureMap bakedTextures;

    public ConnectedTextureBlockModel(Block b) {
        this.block = b;

        this.bakedTextures = ((ConnectedTextureBlock)b).getConnectedTextureMap().bake(ModelLoader.defaultTextureGetter());
    }

    @Nonnull
    @Override
    public IModelData getModelData(@Nonnull IEnviromentBlockReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
        tileData = AdvancedBlockRender.DATA;
        tileData.setData(AdvancedBlockRender.STATE, world.getBlockState(pos));
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
            final boolean innerSeams = ((ConnectedTextureBlock)this.block).hasInnerSeams(state);

            final Direction[] perpendiculars = Util.PERPENDICULARS.get(side); //up, right, down, left on 2d plane
            final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

            final boolean cUpDirect = tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.setPos(0,0,0).move(perpendiculars[0])));
            final boolean cUp = cUpDirect && (!innerSeams || !tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.move(side))));
            final boolean cRight = tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.setPos(0,0,0).move(perpendiculars[1]))) && (!innerSeams || !tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.move(side))));
            final boolean cDown = tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.setPos(0,0,0).move(perpendiculars[2]))) && (!innerSeams || !tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.move(side))));
            final boolean cLeft = tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.setPos(0,0,0).move(perpendiculars[3]))) && (!innerSeams || !tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.move(side))));
            final boolean cUpLeft = tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.setPos(0,0,0).move(perpendiculars[0]).move(perpendiculars[3]))) && (!innerSeams || !tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.move(side))));
            final boolean cUpRight = tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.setPos(0,0,0).move(perpendiculars[1]).move(perpendiculars[0]))) && (!innerSeams || !tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.move(side))));
            final boolean cDownRight = tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.setPos(0,0,0).move(perpendiculars[2]).move(perpendiculars[1]))) && (!innerSeams || !tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.move(side))));
            final boolean cDownLeft = tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.setPos(0,0,0).move(perpendiculars[3]).move(perpendiculars[2]))) && (!innerSeams || !tileData.getData(ConnectedTextureBlock.OFFSET_TO_PROPERTY_MAP.get(pos.move(side))));

            final int uvOffset = ((ConnectedTextureBlock)this.block).borderWidth(state, side);
            final double vertexOffset = uvOffset / 16D;

            final int blockHeight = ((ConnectedTextureBlock)this.block).blockHeight(state);

            final int yUVDiff = perpendiculars[0] == Direction.UP ? 16 - blockHeight : 0;
            final double yVertexDiff = yUVDiff / 16D;
            final double z = side == Direction.UP ? ((blockHeight - 8) / 8D) : 1;

            //middle
            quads.add(VertexUtility.create2DFace(side, vertexOffset, vertexOffset + yVertexDiff, 1-vertexOffset, 1-vertexOffset, z, bakedTextures.base(state, side), uvOffset, uvOffset + yUVDiff, 16-uvOffset, 16-uvOffset));

            //top
            quads.add(VertexUtility.create2DFace(side, vertexOffset, yVertexDiff, 1-vertexOffset, vertexOffset + yVertexDiff, z, bakedTextures.side(state, cUp, false, side), uvOffset, cUp ? yUVDiff : 0, 16-uvOffset, uvOffset + (cUp ? yUVDiff : 0)));
            //right
            quads.add(VertexUtility.create2DFace(side, 1-vertexOffset, vertexOffset, 1, 1-vertexOffset, z, bakedTextures.side(state, cRight, true, side), 16-uvOffset, uvOffset, 16, 16-uvOffset));
            //bottom
            quads.add(VertexUtility.create2DFace(side, vertexOffset, 1-vertexOffset, 1-vertexOffset, 1, z, bakedTextures.side(state, cDown, false, side), uvOffset, 16-uvOffset, 16-uvOffset, 16));
            //left
            quads.add(VertexUtility.create2DFace(side, 0, vertexOffset, vertexOffset, 1-vertexOffset, z, bakedTextures.side(state, cLeft, true, side), 0, uvOffset, uvOffset, 16-uvOffset));

            //top left
            quads.add(VertexUtility.create2DFace(side, 0, yVertexDiff, vertexOffset, vertexOffset + yVertexDiff, z, bakedTextures.corner(state, cUp, cLeft, cUpLeft, side), 0, cUp && cLeft && cUpLeft ? yUVDiff : 0, uvOffset, uvOffset + (cUp && cLeft && cUpLeft ? yUVDiff : 0)));
            //top right
            quads.add(VertexUtility.create2DFace(side, 1-vertexOffset, yVertexDiff, 1, vertexOffset + yVertexDiff, z, bakedTextures.corner(state, cUp, cRight, cUpRight, side), 16-uvOffset, cUp && cRight && cUpRight ? yUVDiff : 0, 16, uvOffset + (cUp && cRight && cUpRight ? yUVDiff : 0)));
            //bottom right
            quads.add(VertexUtility.create2DFace(side, 1-vertexOffset, 1-vertexOffset, 1, 1, z, bakedTextures.corner(state, cDown, cRight, cDownRight, side), 16-uvOffset, 16-uvOffset, 16, 16));
            //bottom left
            quads.add(VertexUtility.create2DFace(side, 0, 1-vertexOffset, vertexOffset, 1, z, bakedTextures.corner(state, cDown, cLeft, cDownLeft, side), 0, 16-uvOffset, uvOffset, 16));
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
        return this.getParticleTexture(EmptyModelData.INSTANCE);
    }

    @Override
    public TextureAtlasSprite getParticleTexture(@Nonnull IModelData data) {
        return this.bakedTextures.get(data.getData(AdvancedBlockRender.STATE), ConnectedTextureBlock.ConnectedTextureType.PARTICLE);
    }

    @Override
    public ItemOverrideList getOverrides() {
        return null;
    }

}