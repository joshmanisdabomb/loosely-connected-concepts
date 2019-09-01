package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.misc.AdaptedFromSource;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TNTBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FunctionalTNTBlock extends TNTBlock {

    private final TNTFactory factory;

    public FunctionalTNTBlock(TNTFactory factory, Properties properties) {
        super(properties);
        this.factory = factory;
    }

    public FunctionalTNTBlock(TNTFactory factory, boolean unstable, Properties properties) {
        this(factory, properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(UNSTABLE, unstable));
    }

    @Override
    @AdaptedFromSource
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (oldState.getBlock() != state.getBlock()) {
            if (worldIn.isBlockPowered(pos)) {
                this.explode(worldIn, pos, null);
                worldIn.removeBlock(pos, false);
            }
        }
    }

    @Override
    @AdaptedFromSource
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (worldIn.isBlockPowered(pos)) {
            this.explode(worldIn, pos, null);
            worldIn.removeBlock(pos, false);
        }

    }

    /**
     * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually collect
     * this block
     */
    @Override
    @AdaptedFromSource
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isRemote() && !player.isCreative() && state.get(UNSTABLE)) {
            this.explode(worldIn, pos, null);
        }
        worldIn.playEvent(player, 2001, pos, getStateId(state));
    }

    /**
     * Called when this Block is destroyed by an Explosion
     */
    @Override
    @AdaptedFromSource
    public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
        if (!worldIn.isRemote) {
            TNTEntity tntentity = factory.create(worldIn, (double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), explosionIn.getExplosivePlacedBy());
            tntentity.setFuse((short)(worldIn.rand.nextInt(tntentity.getFuse() / 4) + tntentity.getFuse() / 8));
            worldIn.addEntity(tntentity);
        }
    }

    public void explode(World world, BlockPos pos, @Nullable LivingEntity entity) {
        if (!world.isRemote) {
            TNTEntity tntentity = factory.create(world, (double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), entity);
            world.addEntity(tntentity);
            world.playSound(null, tntentity.posX, tntentity.posY, tntentity.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);

        }
    }

    @Override
    @AdaptedFromSource
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemstack = player.getHeldItem(handIn);
        Item item = itemstack.getItem();
        if (item != Items.FLINT_AND_STEEL && item != Items.FIRE_CHARGE) {
            return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
        } else {
            this.explode(worldIn, pos, player);
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
            if (item == Items.FLINT_AND_STEEL) {
                itemstack.damageItem(1, player, (p_220287_1_) -> {
                    p_220287_1_.sendBreakAnimation(handIn);
                });
            } else {
                itemstack.shrink(1);
            }

            return true;
        }
    }

    @Override
    @AdaptedFromSource
    public void onProjectileCollision(World worldIn, BlockState state, BlockRayTraceResult hit, Entity projectile) {
        if (!worldIn.isRemote && projectile instanceof AbstractArrowEntity) {
            AbstractArrowEntity abstractarrowentity = (AbstractArrowEntity)projectile;
            Entity entity = abstractarrowentity.getShooter();
            if (abstractarrowentity.isBurning()) {
                BlockPos blockpos = hit.getPos();
                this.explode(worldIn, blockpos, entity instanceof LivingEntity ? (LivingEntity)entity : null);
                worldIn.removeBlock(blockpos, false);
            }
        }
    }

    public interface TNTFactory {
        TNTEntity create(World world, double x, double y, double z, LivingEntity placer);
    }

}
