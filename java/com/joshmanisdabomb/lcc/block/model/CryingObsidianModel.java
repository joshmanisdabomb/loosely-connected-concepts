package com.joshmanisdabomb.lcc.block.model;

import com.joshmanisdabomb.lcc.block.render.AdvancedBlockRender;
import com.joshmanisdabomb.lcc.data.capability.CryingObsidianCapability;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
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
import java.util.Random;

public class CryingObsidianModel implements IBakedModel {

    ModelProperty<Boolean> ACTIVE = new ModelProperty<>();

    private final Block block;
    private final TextureAtlasSprite[] textures;

    public CryingObsidianModel(Block block) {
        this.block = block;
        this.textures = ((AdvancedBlockRender)block).getTextures().stream().map(ModelLoader.defaultTextureGetter()).toArray(TextureAtlasSprite[]::new);
    }

    @Nonnull
    @Override
    public IModelData getModelData(@Nonnull IEnviromentBlockReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
        tileData = AdvancedBlockRender.DATA;
        CryingObsidianCapability co = Minecraft.getInstance().player.getCapability(CryingObsidianCapability.Provider.DEFAULT_CAPABILITY).orElseThrow(RuntimeException::new);
        tileData.setData(ACTIVE, co != null && !co.isEmpty() && Minecraft.getInstance().world.getDimension().getType() == co.dimension && pos.equals(co.pos));
        return tileData;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData data) {
        final List<BakedQuad> quads = new ArrayList<>();

        if (side != null) quads.add(VertexUtility.create2DFace(side, 0, 0, 1, 1, 1, textures[data != EmptyModelData.INSTANCE && data.getData(ACTIVE) ? 1 : 0], 0, 0, 16, 16));

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
        return this.textures[data != EmptyModelData.INSTANCE && data.getData(ACTIVE) ? 1 : 0];
    }

    @Override
    public ItemOverrideList getOverrides() {
        return null;
    }
}
