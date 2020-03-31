package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.tileentity.NetherReactorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class NetherReactorBlock extends Block {

    public static final EnumProperty<ReactorState> STATE = EnumProperty.create("state", ReactorState.class);

    public static final BlockState SHELL = Blocks.COBBLESTONE.getDefaultState();
    public static final BlockState BASE = Blocks.GOLD_BLOCK.getDefaultState();

    public NetherReactorBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(STATE, ReactorState.READY));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STATE);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new NetherReactorTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public MaterialColor getMaterialColor(BlockState state, IBlockReader world, BlockPos pos) {
        switch (state.get(STATE)) {
            case ACTIVE:
                return MaterialColor.TNT;
            case USED:
                return MaterialColor.BLACK;
            default:
                return MaterialColor.CYAN;
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (state.get(STATE) == ReactorState.READY) {
            if (!world.isRemote) {
                if (pos.getY() < 4 || pos.getY() > 221) {
                    player.sendMessage(new TranslationTextComponent("block.lcc.nether_reactor.y"));
                } else if (!this.checkStructure(world, pos)) {
                    player.sendMessage(new TranslationTextComponent("block.lcc.nether_reactor.incorrect"));
                } else if (!player.isCreative() && !this.checkForPlayers(world, pos)) {
                    player.sendMessage(new TranslationTextComponent("block.lcc.nether_reactor.players"));
                } else {
                    ((NetherReactorTileEntity)world.getTileEntity(pos)).activate();
                    world.setBlockState(pos, state.with(STATE, ReactorState.ACTIVE), 3);
                    player.sendMessage(new TranslationTextComponent("block.lcc.nether_reactor.active"));
                }
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    protected boolean checkStructure(World world, BlockPos pos) {
        BlockPos.Mutable bp = new BlockPos.Mutable();
        for (int i = -1; i <= 1; i++) {
            for (int k = -1; k <= 1; k++) {
                BlockState bottom = world.getBlockState(bp.setPos(pos).move(i, -1, k));
                BlockState middle = world.getBlockState(bp.move(Direction.UP));
                BlockState top = world.getBlockState(bp.move(Direction.UP));
                if (i != 0 && k != 0 && (bottom != BASE || middle != SHELL || !top.isAir(world, pos))) return false;
                if (((i != 0 && k == 0) || (i == 0 && k != 0)) && (bottom != SHELL || !middle.isAir(world, pos) || top != SHELL)) return false;
                if (i == 0 && k == 0 && (bottom != SHELL || top != SHELL)) return false;
            }
        }
        return true;
    }

    protected boolean checkForPlayers(World world, BlockPos pos) {
        AxisAlignedBB range = new AxisAlignedBB(pos.down(), pos.up(2)).grow(9, 0, 9);

        //TODO: config option, big servers won't feasibly get everyone together
        return world.getServer().getPlayerList().getPlayers().stream().allMatch(player -> player.dimension == world.dimension.getType() && range.intersects(player.getBoundingBox()));
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof NetherReactorTileEntity) {
                ((NetherReactorTileEntity)tileentity).setCustomName(stack.getDisplayName());
            }
        }
    }

    public enum ReactorState implements IStringSerializable {
        READY,
        ACTIVE,
        USED;

        @Override
        public String getName() {
            return this.name().toLowerCase();
        }

        @Override
        public String toString() {
            return this.getName();
        }
    }
}
