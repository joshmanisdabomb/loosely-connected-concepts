package com.joshmanisdabomb.lcc.block.render;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.model.ConnectedTextureBlockModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelProperty;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

public interface ConnectedTextureBlock extends AdvancedBlockRender {

    @OnlyIn(Dist.CLIENT)
    HashMap<BlockPos, ModelProperty<Boolean>> OFFSET_TO_PROPERTY_MAP = Util.make(new HashMap<>(), map -> {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    if (i != 0 || j != 0 || k != 0) {
                        map.put(new BlockPos(i, j, k), new ModelProperty<>());
                    }
                }
            }
        }
    });

    @Override
    @OnlyIn(Dist.CLIENT)
    default IBakedModel newModel(Block block, IBakedModel defaultModel) {
        return new ConnectedTextureBlockModel((Block)this, defaultModel);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    default Collection<ResourceLocation> getTextures() {
        return this.getConnectedTextures().values().stream().distinct().collect(Collectors.toList());
    }

    @OnlyIn(Dist.CLIENT)
    boolean connectWith(BlockState state, BlockState other);

    @OnlyIn(Dist.CLIENT)
    HashMap<TextureType, ResourceLocation> getConnectedTextures();

    @OnlyIn(Dist.CLIENT)
    boolean hasInnerSeams();

    @OnlyIn(Dist.CLIENT)
    default int borderWidth() {
        return 4;
    }

    @OnlyIn(Dist.CLIENT)
    default HashMap<TextureType, ResourceLocation> traitGetTexturesForNonSided(String folder) {
        HashMap<TextureType, ResourceLocation> map = new HashMap<>();

        ResourceLocation rl = new ResourceLocation(LCC.MODID, "block/" + folder + "/base");
        map.put(TextureType.TOP_BASE, rl);
        map.put(TextureType.SIDE_BASE, rl);
        map.put(TextureType.BOTTOM_BASE, rl);

        rl = new ResourceLocation(LCC.MODID, "block/" + folder + "/corners");
        map.put(TextureType.TOP_CORNERS, rl);
        map.put(TextureType.SIDE_CORNERS, rl);
        map.put(TextureType.BOTTOM_CORNERS, rl);

        rl = new ResourceLocation(LCC.MODID, "block/" + folder + "/lines_h");
        map.put(TextureType.TOP_LINES_H, rl);
        map.put(TextureType.SIDE_LINES_H, rl);
        map.put(TextureType.BOTTOM_LINES_H, rl);

        rl = new ResourceLocation(LCC.MODID, "block/" + folder + "/lines_v");
        map.put(TextureType.TOP_LINES_V, rl);
        map.put(TextureType.SIDE_LINES_V, rl);
        map.put(TextureType.BOTTOM_LINES_V, rl);

        return map;
    }

    @OnlyIn(Dist.CLIENT)
    enum TextureType {
        TOP_BASE,
        TOP_CORNERS,
        TOP_LINES_H,
        TOP_LINES_V,
        SIDE_BASE,
        SIDE_CORNERS,
        SIDE_LINES_H,
        SIDE_LINES_V,
        BOTTOM_BASE,
        BOTTOM_CORNERS,
        BOTTOM_LINES_H,
        BOTTOM_LINES_V;

        public static TextureType base(Direction side) {
            switch(side) {
                case UP: return TOP_BASE;
                case DOWN: return BOTTOM_BASE;
                default: return SIDE_BASE;
            }
        }

        public static TextureType corners(Direction side) {
            switch(side) {
                case UP: return TOP_CORNERS;
                case DOWN: return BOTTOM_CORNERS;
                default: return SIDE_CORNERS;
            }
        }

        public static TextureType lines_h(Direction side) {
            switch(side) {
                case UP: return TOP_LINES_H;
                case DOWN: return BOTTOM_LINES_H;
                default: return SIDE_LINES_H;
            }
        }

        public static TextureType lines_v(Direction side) {
            switch(side) {
                case UP: return TOP_LINES_V;
                case DOWN: return BOTTOM_LINES_V;
                default: return SIDE_LINES_V;
            }
        }
    }

}