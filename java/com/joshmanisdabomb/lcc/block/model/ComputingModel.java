package com.joshmanisdabomb.lcc.block.model;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.render.AdvancedBlockRender;
import com.joshmanisdabomb.lcc.computing.ComputingModule;
import com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
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
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ComputingModel implements IBakedModel {

    private static final ModelProperty<ComputingTileEntity> TE = new ModelProperty<>();
    private final TextureAtlasSprite particle;

    private final Block block;

    public ComputingModel(Block block) {
        this.block = block;
        this.particle = AdvancedBlockRender.blockTextureGetter.apply(((AdvancedBlockRender)block).getTextures().iterator().next());
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData data) {
        final List<BakedQuad> quads = new ArrayList<>();

        ComputingTileEntity te = data.getData(TE);
        if (te == null) return Collections.emptyList();

        for (ComputingModule m : te.getInstalledModules()) {
            boolean above = te.isModuleConnectedAbove(m.location);
            boolean below = te.isModuleConnectedBelow(m.location);
            String suffix = (above || below ? ("_" + (above ? "u" : "") + (below ? "d" : "")) : "");
            IBakedModel model = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(LCC.MODID, "block/" + m.type.name().toLowerCase() + (m.location == SlabType.TOP ? "_top" : "") + suffix));
            quads.addAll(VertexUtility.rotateQuads(model.getQuads(state, side, rand, data), m.direction));
        }
        return quads;
    }

    @Nonnull
    @Override
    public IModelData getModelData(@Nonnull ILightReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
        tileData = AdvancedBlockRender.DATA;
        TileEntity te = world.getTileEntity(pos);
        tileData.setData(TE, te instanceof ComputingTileEntity ? (ComputingTileEntity)te : null);
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
        return this.particle;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return null;
    }



}
