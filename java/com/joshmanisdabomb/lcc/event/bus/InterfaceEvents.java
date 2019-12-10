package com.joshmanisdabomb.lcc.event.bus;

import com.joshmanisdabomb.lcc.block.IPottableBlock;
import com.joshmanisdabomb.lcc.block.IShearableBlock;
import com.joshmanisdabomb.lcc.block.MultipartBlock;
import com.joshmanisdabomb.lcc.data.capability.HeartsCapability;
import com.joshmanisdabomb.lcc.functionality.HeartsFunctionality;
import com.joshmanisdabomb.lcc.potion.HurtResistanceEffect;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class InterfaceEvents {

    @SubscribeEvent
    public void onPlayerInteractRightClick(PlayerInteractEvent.RightClickBlock e) {
        BlockState state = e.getWorld().getBlockState(e.getPos());
        if (state.getBlock() == Blocks.FLOWER_POT) {
            PlayerEntity player = e.getPlayer();
            ItemStack is = e.getItemStack();
            if (is.getItem() instanceof IPottableBlock) {
                e.getWorld().setBlockState(e.getPos(), ((IPottableBlock)is.getItem()).getPottedState(), 3);
                e.setUseBlock(Event.Result.DENY);
                e.setUseItem(Event.Result.DENY);
                player.addStat(Stats.POT_FLOWER);
                if (!player.abilities.isCreativeMode) is.shrink(1);
            }
        }
    }

    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed e) {
        if (e.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() instanceof ShearsItem && e.getState().getBlock() instanceof IShearableBlock) {
            e.setNewSpeed(((IShearableBlock)e.getState().getBlock()).getBreakSpeed(e.getState(), e.getPlayer().getEntityWorld(), e.getPos(), e.getPlayer().getHeldItem(Hand.MAIN_HAND), e.getPlayer()));
        }
    }

    @SubscribeEvent
    public void onBreakBlock(BlockEvent.BreakEvent e) {
        if (e.getState().getBlock() instanceof MultipartBlock) {
            VoxelShape s = ((MultipartBlock)e.getState().getBlock()).getPartFromTrace(e.getPlayer(), e.getState(), e.getWorld(), e.getPos());
            if (s != null) {
                boolean cancel = ((MultipartBlock)e.getState().getBlock()).onShapeHarvested(e.getState(), e.getWorld(), e.getPos(), e.getPlayer(), s);
                e.setCanceled(cancel);
            }
        }
    }

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent e) {
        LivingEntity entity = e.getEntityLiving();
        if (entity != null) entity.getActivePotionEffects().stream().filter(ei -> ei.getPotion() instanceof HurtResistanceEffect).forEach(ei -> {
            double mod = ((HurtResistanceEffect)ei.getPotion()).getResistanceMultiplier(e.getSource(), ei.getAmplifier());
            entity.hurtResistantTime = (int)Math.floor(entity.hurtResistantTime * mod);
            entity.hurtTime = (int)Math.floor(entity.hurtTime * mod);
        });
    }

}