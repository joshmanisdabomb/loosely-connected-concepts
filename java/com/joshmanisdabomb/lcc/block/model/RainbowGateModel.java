package com.joshmanisdabomb.lcc.block.model;

import com.joshmanisdabomb.lcc.block.render.AdvancedBlockRender;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ILightReader;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.joshmanisdabomb.lcc.block.RainbowGateBlock.Y;

public class RainbowGateModel implements IBakedModel {

    ModelProperty<Long> RAND = new ModelProperty<>();

    private final Block block;
    private final TextureAtlasSprite[] textures;

    public RainbowGateModel(Block block) {
        this.block = block;
        this.textures = ((AdvancedBlockRender)block).getTextures().stream().map(AdvancedBlockRender.blockTextureGetter).toArray(TextureAtlasSprite[]::new);
    }

    @Nonnull
    @Override
    public IModelData getModelData(@Nonnull ILightReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
        tileData = AdvancedBlockRender.DATA;
        tileData.setData(RAND, MathHelper.getPositionRandom(new BlockPos(pos.getX(), state.get(Y), pos.getZ())));
        return tileData;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData data) {
        final List<BakedQuad> quads = new ArrayList<>();

        long r = data != EmptyModelData.INSTANCE ? data.getData(RAND) : 0L;
        if (side != null) {
            switch (side) {
                case DOWN:
                case UP:
                    quads.add(VertexUtility.create2DFace(side, 5/16D, 5/16D, 11/16D, 11/16D, 1, textures[0], 5, 5, 11, 11));
                    break;
                default:
                    break;
            }
        } else {
            for (int i = 0; i < 4; i++) {
                Direction d = Direction.byHorizontalIndex(i);
                quads.add(VertexUtility.create2DFace(d, 5/16D, 0, 11/16D, 1, 0.375, textures[(int)(Math.abs(r / 179) % 6) + 1], 5, 0, 11, 16));
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
    public boolean func_230044_c_() {
        return false;
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
