package com.joshmanisdabomb.lcc.event.bus;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.ClassicSpongeBlock;
import com.joshmanisdabomb.lcc.block.IPottableBlock;
import com.joshmanisdabomb.lcc.block.IShearableBlock;
import com.joshmanisdabomb.lcc.data.capability.GauntletCapability;
import com.joshmanisdabomb.lcc.data.capability.HeartsCapability;
import com.joshmanisdabomb.lcc.functionality.GauntletFunctionality;
import com.joshmanisdabomb.lcc.functionality.HeartsFunctionality;
import com.joshmanisdabomb.lcc.item.GauntletItem;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCEffects;
import com.joshmanisdabomb.lcc.registry.LCCParticles;
import com.joshmanisdabomb.lcc.registry.LCCSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ShearsItem;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GeneralEvents {

    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed e) {
        if (e.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() instanceof GauntletItem) {
            e.setNewSpeed(-1);
        } else if (e.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() instanceof ShearsItem && e.getState().getBlock() instanceof IShearableBlock) {
            e.setNewSpeed(((IShearableBlock)e.getState().getBlock()).getBreakSpeed(e.getState(), e.getPlayer().getEntityWorld(), e.getPos(), e.getPlayer().getHeldItem(Hand.MAIN_HAND), e.getPlayer()));
        }
    }

    @SubscribeEvent
    public void onEntityTick(LivingEvent.LivingUpdateEvent e) {
        LivingEntity entity = e.getEntityLiving();
        entity.getCapability(HeartsCapability.Provider.DEFAULT_CAPABILITY).ifPresent(hearts -> {
            HeartsFunctionality.tick(hearts, entity);
        });
        entity.getCapability(GauntletCapability.Provider.DEFAULT_CAPABILITY).ifPresent(gauntlet -> {
            GauntletFunctionality.tick(gauntlet, entity.getHeldItem(Hand.MAIN_HAND), entity);
        });
        if (entity.isPotionActive(LCCEffects.stun)) {
            entity.onGround = false;
            if (entity instanceof MobEntity) {
                ((MobEntity)entity).goalSelector.getRunningGoals()
                    .filter(g -> g.getGoal() instanceof CreeperSwellGoal || g.getGoal() instanceof MeleeAttackGoal || g.getGoal() instanceof RangedAttackGoal || g.getGoal() instanceof RangedBowAttackGoal || g.getGoal() instanceof RangedCrossbowAttackGoal)
                    .forEach(PrioritizedGoal::resetTask);
            }
        }
    }

    @SubscribeEvent
    public void onItemUse(LivingEntityUseItemEvent e) {
        if (e.getEntityLiving().isPotionActive(LCCEffects.stun)) {
            if (e.isCancelable()) e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEntityAttacked(LivingAttackEvent e) {
        if (e.getSource().getClass().equals(EntityDamageSource.class) && e.getSource().getTrueSource() instanceof LivingEntity) {
            LivingEntity attacker = ((LivingEntity)e.getSource().getTrueSource());
            if (attacker.isPotionActive(LCCEffects.stun) || (attacker.getHeldItem(Hand.MAIN_HAND).getItem() instanceof GauntletItem && !e.getSource().getDamageType().startsWith("lcc.gauntlet_"))) {
                e.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent e) {
        PlayerEntity player = e.getPlayer();
        if (player.isPotionActive(LCCEffects.stun) && !player.isCreative()) {
            if (e.isCancelable()) {
                e.setCancellationResult(ActionResultType.FAIL);
                e.setCanceled(true);
            } else if (e.hasResult()) {
                e.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerInteractRightClick(PlayerInteractEvent.RightClickBlock e) {
        PlayerEntity player = e.getPlayer();
        BlockState state = e.getWorld().getBlockState(e.getPos());
        if (state.getBlock() == Blocks.FLOWER_POT) {
            if (e.getItemStack().getItem() instanceof IPottableBlock) {
                e.getWorld().setBlockState(e.getPos(), ((IPottableBlock)e.getItemStack().getItem()).getPottedState(), 3);
                e.setUseBlock(Event.Result.DENY);
                e.setUseItem(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent e) {
        LivingEntity entity = e.getEntityLiving();
        entity.getCapability(HeartsCapability.Provider.DEFAULT_CAPABILITY).ifPresent(hearts -> {
            HeartsFunctionality.hurt(hearts, entity, e);
        });
    }

    @SubscribeEvent
    public void onBlockPlace(BlockEvent.EntityPlaceEvent e) {
        if (e.getPlacedBlock() == Blocks.SOUL_SAND.getDefaultState()) {
            for (Direction side : Direction.values()) {
                if (side.getAxis() != Direction.Axis.Y && e.getWorld().getFluidState(e.getPos().offset(side)).isTagged(FluidTags.WATER)) {
                    e.getWorld().setBlockState(e.getPos(), LCCBlocks.hydrated_soul_sand.getDefaultState(), 3);
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public void onNeighborNotify(BlockEvent.NeighborNotifyEvent e) {
        if (e.getWorld().getFluidState(e.getPos()).isTagged(FluidTags.WATER)) {
            //Cancel when sponge is nearby.
            BlockPos.MutableBlockPos bp = new BlockPos.MutableBlockPos();
            for (int i = -2; i <= 2; i++) {
                for (int j = -2; j <= 2; j++) {
                    for (int k = -2; k <= 2; k++) {
                        BlockState state = e.getWorld().getBlockState(bp.setPos(e.getPos()).move(i,j,k));
                        if ((i != 0 || j != 0 || k != 0) && state.getBlock() == LCCBlocks.classic_sponge) {
                            ((ClassicSpongeBlock)state.getBlock()).absorb(state, e.getWorld(), bp, e.getPos(), false);
                            e.setCanceled(true);
                        }
                    }
                }
            }
            //Hydrate neighbour soul sand.
            for (Direction side : Direction.values()) {
                if (side.getAxis() != Direction.Axis.Y && e.getWorld().getBlockState(e.getPos().offset(side)) == Blocks.SOUL_SAND.getDefaultState()) {
                    e.getWorld().setBlockState(e.getPos().offset(side), LCCBlocks.hydrated_soul_sand.getDefaultState(), 3);
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityJump(LivingEvent.LivingJumpEvent e) {
        LivingEntity entity = e.getEntityLiving();
        AxisAlignedBB axisalignedbb = entity.getBoundingBox();

        try (
            BlockPos.PooledMutableBlockPos bp = BlockPos.PooledMutableBlockPos.retain(axisalignedbb.minX + 0.001D, axisalignedbb.minY + 0.001D, axisalignedbb.minZ + 0.001D);
            BlockPos.PooledMutableBlockPos bp1 = BlockPos.PooledMutableBlockPos.retain(axisalignedbb.maxX - 0.001D, axisalignedbb.maxY - 0.001D, axisalignedbb.maxZ - 0.001D);
            BlockPos.PooledMutableBlockPos bp2 = BlockPos.PooledMutableBlockPos.retain();
        ) {
            if (entity.world.isAreaLoaded(bp, bp1)) {
                for(int i = bp.getX(); i <= bp1.getX(); ++i) {
                    for(int j = bp.getY(); j <= bp1.getY(); ++j) {
                        for(int k = bp.getZ(); k <= bp1.getZ(); ++k) {
                            bp2.setPos(i, j, k);
                            BlockState state = entity.world.getBlockState(bp2);

                            if (state == LCCBlocks.hydrated_soul_sand.getDefaultState()) {
                                entity.setMotion(entity.getMotion().add(0, 0.4, 0));
                                entity.fallDistance = -1.0F;
                                LCC.proxy.addParticle(entity.world, entity, LCCParticles.hydrated_soul_sand_jump, false, i + 0.5, j + 1, k + 0.5, 1.0D, 1.0D, 1.0D);
                                entity.world.playSound(i + 0.5, j + 0.5, k + 0.5, LCCSounds.block_hydrated_soul_sand_jump, SoundCategory.BLOCKS, 0.4F, 1.3F, false);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

}
