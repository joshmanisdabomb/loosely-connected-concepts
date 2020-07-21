package com.joshmanisdabomb.lcc.block.model;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.ChocolateBlock;
import com.joshmanisdabomb.lcc.block.render.AdvancedBlockRender;
import com.joshmanisdabomb.lcc.block.render.ConnectedTextureBlock;
import com.joshmanisdabomb.lcc.capability.CryingObsidianCapability;
import com.joshmanisdabomb.lcc.computing.ComputingModule;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.joshmanisdabomb.lcc.block.ChocolateBlock.BOTTOM;
import static com.joshmanisdabomb.lcc.block.ChocolateBlock.TOP;

public class ChocolateModel implements IBakedModel {

    private final Block block;
    private final TextureAtlasSprite[] textures;

    public ChocolateModel(Block b) {
        this.block = b;
        this.textures = ((AdvancedBlockRender)block).getTextures().stream().map(AdvancedBlockRender.blockTextureGetter).toArray(TextureAtlasSprite[]::new);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData data) {
        final List<BakedQuad> quads = new ArrayList<>();

        ChocolateBlock.Type top = state.get(TOP);
        ChocolateBlock.Type bottom = state.get(BOTTOM);
        if (top != ChocolateBlock.Type.NONE) {
            IBakedModel model = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(LCC.MODID, "block/chocolate_" + top.name().toLowerCase() + "_top"));
            quads.addAll(model.getQuads(state, side, rand, data));
        }
        if (bottom != ChocolateBlock.Type.NONE) {
            IBakedModel model = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(LCC.MODID, "block/chocolate_" + bottom.name().toLowerCase() + "_bottom"));
            quads.addAll(model.getQuads(state, side, rand, data));
        }

        return quads;
    }

    @Nonnull
    @Override
    public IModelData getModelData(@Nonnull ILightReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
        tileData = AdvancedBlockRender.DATA;
        tileData.setData(AdvancedBlockRender.STATE, world.getBlockState(pos));
        return tileData;
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
        BlockState state = data.getData(AdvancedBlockRender.STATE);
        if (state == null) return this.textures[1];
        if (state.get(BOTTOM) == ChocolateBlock.Type.NONE) return this.textures[state.get(TOP).ordinal() - 1];
        return this.textures[state.get(BOTTOM).ordinal() - 1];
    }

    @Override
    public ItemOverrideList getOverrides() {
        return null;
    }

}
