package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.model.ChanneliteModel;
import com.joshmanisdabomb.lcc.block.render.ConnectedTextureBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Collection;

public class ChanneliteBlock extends DirectionalBlock implements ConnectedTextureBlock {

    public static final BooleanProperty CHANNEL2 = BooleanProperty.create("channel2");
    public static final EnumProperty<ChanneliteColor> FLOW = EnumProperty.create("flow", ChanneliteColor.class);

    @OnlyIn(Dist.CLIENT)
    private ConnectedTextureMap connectedTextureMap;

    public ChanneliteBlock(Properties p) {
        super(p);
        this.setDefaultState(this.stateContainer.getBaseState().with(BlockStateProperties.FACING, Direction.NORTH).with(CHANNEL2, false).with(FLOW, ChanneliteColor.EMPTY));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING, FLOW, CHANNEL2);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (player.getHeldItem(hand).isEmpty()) {
            world.setBlockState(pos, state.with(CHANNEL2, !state.get(CHANNEL2)));
            return true;
        }
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    public boolean canRenderInLayer(BlockState state, BlockRenderLayer layer) {
        return state.get(FLOW) != ChanneliteColor.EMPTY ? (layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.SOLID) : super.canRenderInLayer(state, layer);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
        return adjacentBlockState.getBlock() == this || super.isSideInvisible(state, adjacentBlockState, side);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    public boolean causesSuffocation(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean connectWith(BlockState state, BlockState other) {
        if (other.getBlock().equals(state.getBlock())) {
            return !state.get(CHANNEL2) || other.get(CHANNEL2).equals(state.get(CHANNEL2));
        }
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int borderWidth(BlockState state, Direction side) {
        return 8;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public double blockGrowth(BlockState state) {
        return 1.00125;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ConnectedTextureMap getConnectedTextureMap() {
        if (connectedTextureMap == null) connectedTextureMap = new ConnectedTextureMap().useWhen(state -> true, "rainbow/channelite/casing_", false);
        return connectedTextureMap;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Collection<ResourceLocation> getTextures() {
        ArrayList<ResourceLocation> colors = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            colors.add(new ResourceLocation(LCC.MODID, "block/rainbow/channelite/flow_" + color.getName()));
        }
        Collection<ResourceLocation> textures = ConnectedTextureBlock.super.getTextures();
        textures.addAll(colors);
        return textures;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IBakedModel newModel(Block block) {
        return new ChanneliteModel(this);
    }

    public enum ChanneliteColor implements IStringSerializable {
        EMPTY,
        WHITE,
        ORANGE,
        MAGENTA,
        LIGHT_BLUE,
        YELLOW,
        LIME,
        PINK,
        GRAY,
        LIGHT_GRAY,
        CYAN,
        PURPLE,
        BLUE,
        BROWN,
        GREEN,
        RED,
        BLACK;

        @Override
        public String getName() {
            return this.name().toLowerCase();
        }
    }

}
