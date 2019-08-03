package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.model.ConnectedTextureBlockModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.data.ModelProperty;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

public interface ConnectedTextureBlock extends AdvancedBlockRender {

    public static final ModelProperty<Boolean> UP = new ModelProperty<Boolean>();
    public static final ModelProperty<Boolean> DOWN = new ModelProperty<Boolean>();
    public static final ModelProperty<Boolean> NORTH = new ModelProperty<Boolean>();
    public static final ModelProperty<Boolean> EAST = new ModelProperty<Boolean>();
    public static final ModelProperty<Boolean> SOUTH = new ModelProperty<Boolean>();
    public static final ModelProperty<Boolean> WEST = new ModelProperty<Boolean>();
    public static final ModelProperty<Boolean> NORTH_EAST = new ModelProperty<Boolean>();
    public static final ModelProperty<Boolean> SOUTH_EAST = new ModelProperty<Boolean>();
    public static final ModelProperty<Boolean> NORTH_WEST = new ModelProperty<Boolean>();
    public static final ModelProperty<Boolean> SOUTH_WEST = new ModelProperty<Boolean>();
    public static final ModelProperty<Boolean> NORTH_UP = new ModelProperty<Boolean>();
    public static final ModelProperty<Boolean> EAST_UP = new ModelProperty<Boolean>();
    public static final ModelProperty<Boolean> SOUTH_UP = new ModelProperty<Boolean>();
    public static final ModelProperty<Boolean> WEST_UP = new ModelProperty<Boolean>();
    public static final ModelProperty<Boolean> NORTH_DOWN = new ModelProperty<Boolean>();
    public static final ModelProperty<Boolean> EAST_DOWN = new ModelProperty<Boolean>();
    public static final ModelProperty<Boolean> SOUTH_DOWN = new ModelProperty<Boolean>();
    public static final ModelProperty<Boolean> WEST_DOWN = new ModelProperty<Boolean>();
    public static final HashMap<BlockPos, ModelProperty<Boolean>> OFFSET_TO_PROPERTY_MAP = Util.make(new HashMap<>(), (map) -> {
        map.put(BlockPos.ZERO.offset(Direction.UP), UP);
        map.put(BlockPos.ZERO.offset(Direction.DOWN), DOWN);
        map.put(BlockPos.ZERO.offset(Direction.NORTH), NORTH);
        map.put(BlockPos.ZERO.offset(Direction.EAST), EAST);
        map.put(BlockPos.ZERO.offset(Direction.SOUTH), SOUTH);
        map.put(BlockPos.ZERO.offset(Direction.WEST), WEST);
        map.put(BlockPos.ZERO.offset(Direction.NORTH).offset(Direction.EAST), NORTH_EAST);
        map.put(BlockPos.ZERO.offset(Direction.SOUTH).offset(Direction.EAST), SOUTH_EAST);
        map.put(BlockPos.ZERO.offset(Direction.NORTH).offset(Direction.WEST), NORTH_WEST);
        map.put(BlockPos.ZERO.offset(Direction.SOUTH).offset(Direction.WEST), SOUTH_WEST);
        map.put(BlockPos.ZERO.offset(Direction.NORTH).offset(Direction.UP), NORTH_UP);
        map.put(BlockPos.ZERO.offset(Direction.EAST).offset(Direction.UP), EAST_UP);
        map.put(BlockPos.ZERO.offset(Direction.SOUTH).offset(Direction.UP), SOUTH_UP);
        map.put(BlockPos.ZERO.offset(Direction.WEST).offset(Direction.UP), WEST_UP);
        map.put(BlockPos.ZERO.offset(Direction.NORTH).offset(Direction.DOWN), NORTH_DOWN);
        map.put(BlockPos.ZERO.offset(Direction.EAST).offset(Direction.DOWN), EAST_DOWN);
        map.put(BlockPos.ZERO.offset(Direction.SOUTH).offset(Direction.DOWN), SOUTH_DOWN);
        map.put(BlockPos.ZERO.offset(Direction.WEST).offset(Direction.DOWN), WEST_DOWN);
    });

    boolean connectWith(BlockState state, BlockState other);

    @Override
    default Collection<ResourceLocation> getTextures() {
        return this.getConnectedTextures().values().stream().distinct().collect(Collectors.toList());
    }

    HashMap<TextureType, ResourceLocation> getConnectedTextures();

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

    boolean hasInnerSeams();

    @Override
    default IBakedModel newModel(Block block, IBakedModel defaultModel) {
        return new ConnectedTextureBlockModel((Block)this, defaultModel);
    }

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
    }

}