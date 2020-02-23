package com.joshmanisdabomb.lcc.block.model;

import com.joshmanisdabomb.lcc.block.render.AdvancedBlockRender;
import com.joshmanisdabomb.lcc.block.render.ConnectedTextureBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ChanneliteModel extends ConnectedTextureBlockModel implements DefaultModelAcceptor {

    private final TextureAtlasSprite[] flowTextures;

    protected final HashMap<ModelResourceLocation, IBakedModel> models = new HashMap<>();

    public ChanneliteModel(Block block) {
        super(block);
        this.flowTextures = ((AdvancedBlockRender)block).getTextures().stream().filter(rl -> rl.getPath().startsWith("block/rainbow/channelite/flow_")).map(ModelLoader.defaultTextureGetter()).toArray(TextureAtlasSprite[]::new);
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData tileData) {
        switch (MinecraftForgeClient.getRenderLayer()) {
            case TRANSLUCENT:
                return super.getQuads(state, side, rand, tileData);
            default:
                if (side != null) return models.get(BlockModelShapes.getModelLocation(state)).getQuads(state, side, rand, tileData);
                return new ArrayList<>();
        }
    }

    @Override
    public void acceptModel(ModelResourceLocation mrl, IBakedModel model) {
        models.put(mrl, model);
    }

    @Override
    public TextureAtlasSprite getParticleTexture(@Nonnull IModelData data) {
        return this.bakedTextures.get(data.getData(AdvancedBlockRender.STATE), ConnectedTextureBlock.ConnectedTextureType.SIDE_CORNERS_OUTER);
    }

}