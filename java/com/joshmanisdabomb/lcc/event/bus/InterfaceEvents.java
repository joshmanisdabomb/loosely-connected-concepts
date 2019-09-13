package com.joshmanisdabomb.lcc.event.bus;

import com.joshmanisdabomb.lcc.block.IPottableBlock;
import com.joshmanisdabomb.lcc.block.IShearableBlock;
import com.joshmanisdabomb.lcc.block.MultipartBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
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
            PlayerEntity player = e.getPlayer();
            double reach = player.getAttribute(PlayerEntity.REACH_DISTANCE).getValue() - (player.isCreative() ? 0 : 0.5F);
            Vec3d start = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
            Vec3d look = player.getLookVec();
            Vec3d end = start.add(look.x * reach, look.y * reach, look.z * reach);
            BlockRayTraceResult target = e.getWorld().rayTraceBlocks(new RayTraceContext(start, end, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));

            if (target.getType() == RayTraceResult.Type.BLOCK) {
                for (VoxelShape s : ((MultipartBlock)e.getState().getBlock()).getParts(e.getState(), e.getWorld(), e.getPos())) {
                    if (s.toBoundingBoxList().stream().anyMatch(box -> box.grow(0.001F).contains(target.getHitVec().subtract(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ())))) {
                        boolean cancel = ((MultipartBlock)e.getState().getBlock()).onShapeHarvested(e.getState(), e.getWorld(), e.getPos(), e.getPlayer(), s);
                        e.setCanceled(cancel);
                        return;
                    }
                }
            }
        }
    }

}