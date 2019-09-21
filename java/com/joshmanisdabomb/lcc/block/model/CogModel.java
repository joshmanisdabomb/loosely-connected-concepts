package com.joshmanisdabomb.lcc.block.model;

import com.joshmanisdabomb.lcc.block.CogBlock;
import com.joshmanisdabomb.lcc.block.render.AdvancedBlockRender;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.joshmanisdabomb.lcc.block.CogBlock.FACING_TO_PROPERTIES;

public class CogModel implements IBakedModel {

    private final Block block;
    private final TextureAtlasSprite[] textures;

    public CogModel(Block block) {
        this.block = block;
        this.textures = ((AdvancedBlockRender)block).getTextures().stream().map(ModelLoader.defaultTextureGetter()).toArray(TextureAtlasSprite[]::new);
    }

    @Nonnull
    @Override
    public IModelData getModelData(@Nonnull IEnviromentBlockReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
        tileData = AdvancedBlockRender.DATA;
        tileData.setData(AdvancedBlockRender.POS, pos);
        return tileData;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData data) {
        final List<BakedQuad> quads = new ArrayList<>();

        for (Map.Entry<Direction, EnumProperty<CogBlock.CogState>> e : FACING_TO_PROPERTIES.entrySet()) {
            CogBlock.CogState cogState = state.get(e.getValue());
            if (cogState != CogBlock.CogState.NONE) {
                quads.add(VertexUtility.create2DFace(e.getKey(), -0.0625, -0.0625, 1.0625, 1.0625, 31/32F, textures[cogState == CogBlock.CogState.INACTIVE ? 0 : 1], 0, 0, 16, 16));
                quads.add(VertexUtility.create2DFace(e.getKey().getOpposite(), -0.0625, -0.0625, 1.0625, 1.0625, -31/32F, textures[cogState == CogBlock.CogState.INACTIVE ? 0 : 1], 0, 0, 16, 16));
            }
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
        return this.textures[0];
    }

    @Override
    public ItemOverrideList getOverrides() {
        return null;
    }

}
