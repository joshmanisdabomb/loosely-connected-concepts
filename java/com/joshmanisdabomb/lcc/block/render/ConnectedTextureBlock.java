package com.joshmanisdabomb.lcc.block.render;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.model.ConnectedTextureBlockModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
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
    default IBakedModel newModel(Block block) {
        return new ConnectedTextureBlockModel((Block)this);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    default Collection<ResourceLocation> getTextures() {
        return this.getConnectedTextureMap().all();
    }

    @OnlyIn(Dist.CLIENT)
    boolean connectWith(BlockState state, BlockState other);

    @OnlyIn(Dist.CLIENT)
    ConnectedTextureMap getConnectedTextureMap();

    @OnlyIn(Dist.CLIENT)
    default boolean hasInnerSeams(BlockState state) { return true; }

    @OnlyIn(Dist.CLIENT)
    default int borderWidth(BlockState state, Direction side) {
        return 4;
    }

    @OnlyIn(Dist.CLIENT)
    default int blockHeight(BlockState state) { return 16; }

    @OnlyIn(Dist.CLIENT)
    enum ConnectedTextureType {
        PARTICLE,
        TOP_BASE,
        TOP_CORNERS_OUTER,
        TOP_CORNERS_INNER,
        TOP_LINES_H,
        TOP_LINES_V,
        SIDE_BASE,
        SIDE_CORNERS_OUTER,
        SIDE_CORNERS_INNER,
        SIDE_LINES_H,
        SIDE_LINES_V,
        BOTTOM_BASE,
        BOTTOM_CORNERS_OUTER,
        BOTTOM_CORNERS_INNER,
        BOTTOM_LINES_H,
        BOTTOM_LINES_V;

        public static ConnectedTextureType base(Direction side) {
            switch(side) {
                case UP: return TOP_BASE;
                case DOWN: return BOTTOM_BASE;
                default: return SIDE_BASE;
            }
        }

        public static ConnectedTextureType corners_o(Direction side) {
            switch(side) {
                case UP: return TOP_CORNERS_OUTER;
                case DOWN: return BOTTOM_CORNERS_OUTER;
                default: return SIDE_CORNERS_OUTER;
            }
        }

        public static ConnectedTextureType corners_i(Direction side) {
            switch(side) {
                case UP: return TOP_CORNERS_INNER;
                case DOWN: return BOTTOM_CORNERS_INNER;
                default: return SIDE_CORNERS_INNER;
            }
        }

        public static ConnectedTextureType lines_h(Direction side) {
            switch(side) {
                case UP: return TOP_LINES_H;
                case DOWN: return BOTTOM_LINES_H;
                default: return SIDE_LINES_H;
            }
        }

        public static ConnectedTextureType lines_v(Direction side) {
            switch(side) {
                case UP: return TOP_LINES_V;
                case DOWN: return BOTTOM_LINES_V;
                default: return SIDE_LINES_V;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    class ConnectedTextureMap {

        private HashMap<ConnectedTextureType, ResourceLocation> last;
        private final HashMap<Predicate<BlockState>, HashMap<ConnectedTextureType, ResourceLocation>> map = new HashMap<>();

        public ConnectedTextureMap useWhen(Predicate<BlockState> state, String prefix, boolean threeSided) {
            return this.useWhen(state,
                new ResourceLocation(LCC.MODID, "block/" + prefix + (threeSided ? "side_" : "") + "base"),
                new ResourceLocation(LCC.MODID, "block/" + prefix + (threeSided ? "top_" : "") + "base"),
                new ResourceLocation(LCC.MODID, "block/" + prefix + (threeSided ? "top_" : "") + "corners_o"),
                new ResourceLocation(LCC.MODID, "block/" + prefix + (threeSided ? "top_" : "") + "corners_i"),
                new ResourceLocation(LCC.MODID, "block/" + prefix + (threeSided ? "top_" : "") + "lines_h"),
                new ResourceLocation(LCC.MODID, "block/" + prefix + (threeSided ? "top_" : "") + "lines_v"),
                new ResourceLocation(LCC.MODID, "block/" + prefix + (threeSided ? "side_" : "") + "base"),
                new ResourceLocation(LCC.MODID, "block/" + prefix + (threeSided ? "side_" : "") + "corners_o"),
                new ResourceLocation(LCC.MODID, "block/" + prefix + (threeSided ? "side_" : "") + "corners_i"),
                new ResourceLocation(LCC.MODID, "block/" + prefix + (threeSided ? "side_" : "") + "lines_h"),
                new ResourceLocation(LCC.MODID, "block/" + prefix + (threeSided ? "side_" : "") + "lines_v"),
                new ResourceLocation(LCC.MODID, "block/" + prefix + (threeSided ? "bottom_" : "") + "base"),
                new ResourceLocation(LCC.MODID, "block/" + prefix + (threeSided ? "bottom_" : "") + "corners_o"),
                new ResourceLocation(LCC.MODID, "block/" + prefix + (threeSided ? "bottom_" : "") + "corners_i"),
                new ResourceLocation(LCC.MODID, "block/" + prefix + (threeSided ? "bottom_" : "") + "lines_h"),
                new ResourceLocation(LCC.MODID, "block/" + prefix + (threeSided ? "bottom_" : "") + "lines_v")
            );
        }

        public ConnectedTextureMap useWhen(Predicate<BlockState> state, Consumer<HashMap<ConnectedTextureType, ResourceLocation>> copy) {
            last = (HashMap<ConnectedTextureType, ResourceLocation>)last.clone();
            copy.accept(last);
            map.put(state, last);
            return this;
        }

        public ConnectedTextureMap useWhen(Predicate<BlockState> state, ResourceLocation particle, ResourceLocation top_base, ResourceLocation top_corners_o, ResourceLocation top_corners_i, ResourceLocation top_lines_h, ResourceLocation top_lines_v, ResourceLocation side_base, ResourceLocation side_corners_o, ResourceLocation side_corners_i, ResourceLocation side_lines_h, ResourceLocation side_lines_v, ResourceLocation bottom_base, ResourceLocation bottom_corners_o, ResourceLocation bottom_corners_i, ResourceLocation bottom_lines_h, ResourceLocation bottom_lines_v) {
            last = new HashMap<>();
            last.put(ConnectedTextureType.PARTICLE, particle);
            last.put(ConnectedTextureType.TOP_BASE, top_base);
            last.put(ConnectedTextureType.TOP_CORNERS_OUTER, top_corners_o);
            last.put(ConnectedTextureType.TOP_CORNERS_INNER, top_corners_i);
            last.put(ConnectedTextureType.TOP_LINES_H, top_lines_h);
            last.put(ConnectedTextureType.TOP_LINES_V, top_lines_v);
            last.put(ConnectedTextureType.SIDE_BASE, side_base);
            last.put(ConnectedTextureType.SIDE_CORNERS_OUTER, side_corners_o);
            last.put(ConnectedTextureType.SIDE_CORNERS_INNER, side_corners_i);
            last.put(ConnectedTextureType.SIDE_LINES_H, side_lines_h);
            last.put(ConnectedTextureType.SIDE_LINES_V, side_lines_v);
            last.put(ConnectedTextureType.BOTTOM_BASE, bottom_base);
            last.put(ConnectedTextureType.BOTTOM_CORNERS_OUTER, bottom_corners_o);
            last.put(ConnectedTextureType.BOTTOM_CORNERS_INNER, bottom_corners_i);
            last.put(ConnectedTextureType.BOTTOM_LINES_H, bottom_lines_h);
            last.put(ConnectedTextureType.BOTTOM_LINES_V, bottom_lines_v);
            map.put(state, last);
            return this;
        }

        public HashMap<ConnectedTextureType, ResourceLocation> getTextureTypeMap(BlockState state) {
            if (state != null) {
                for (Map.Entry<Predicate<BlockState>, HashMap<ConnectedTextureType, ResourceLocation>> e : map.entrySet()) {
                    if (e.getKey().test(state)) return e.getValue();
                }
            }
            return map.values().stream().findFirst().get();
        }

        public ResourceLocation get(BlockState state, ConnectedTextureType tt) {
            return this.getTextureTypeMap(state).get(tt);
        }

        public Collection<ResourceLocation> all() {
            ArrayList<ResourceLocation> all = new ArrayList<>();
            for (HashMap<ConnectedTextureType, ResourceLocation> m : map.values()) {
                all.addAll(m.values());
            }
            return all.stream().distinct().collect(Collectors.toList());
        }

        public BakedConnectedTextureMap bake(Function<ResourceLocation, TextureAtlasSprite> textureGetter) {
            return new BakedConnectedTextureMap(textureGetter);
        }

        public class BakedConnectedTextureMap {

            private final HashMap<ResourceLocation, TextureAtlasSprite> baked = new HashMap<>();

            private BakedConnectedTextureMap(Function<ResourceLocation, TextureAtlasSprite> textureGetter) {
                for (ResourceLocation rl : ConnectedTextureMap.this.all()) {
                    baked.put(rl, textureGetter.apply(rl));
                }
            }

            public TextureAtlasSprite base(BlockState state, Direction side) {
                return baked.get(ConnectedTextureMap.this.get(state, ConnectedTextureType.base(side)));
            }

            public TextureAtlasSprite corners_o(BlockState state, Direction side) {
                return baked.get(ConnectedTextureMap.this.get(state, ConnectedTextureType.corners_o(side)));
            }

            public TextureAtlasSprite corners_i(BlockState state, Direction side) {
                return baked.get(ConnectedTextureMap.this.get(state, ConnectedTextureType.corners_i(side)));
            }

            public TextureAtlasSprite lines_h(BlockState state, Direction side) {
                return baked.get(ConnectedTextureMap.this.get(state, ConnectedTextureType.lines_h(side)));
            }

            public TextureAtlasSprite lines_v(BlockState state, Direction side) {
                return baked.get(ConnectedTextureMap.this.get(state, ConnectedTextureType.lines_v(side)));
            }

            public TextureAtlasSprite get(BlockState state, ConnectedTextureType tt) {
                return baked.get(ConnectedTextureMap.this.get(state, tt));
            }

            public TextureAtlasSprite side(BlockState state, boolean connection, boolean vertical, Direction side) {
                return connection ? base(state, side) : (vertical ? lines_v(state, side) : lines_h(state, side));
            }

            public TextureAtlasSprite corner(BlockState state, boolean connectionV, boolean connectionH, boolean connectionVH, Direction side) {
                if (connectionVH && connectionV && connectionH) {
                    return base(state, side);
                } else if (!connectionVH && connectionV && connectionH) {
                    return corners_i(state, side);
                } else if (connectionV) {
                    return lines_v(state, side);
                } else if (connectionH) {
                    return lines_h(state, side);
                } else {
                    return corners_o(state, side);
                }
            }
        }
    }
}