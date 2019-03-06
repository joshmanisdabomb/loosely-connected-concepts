package com.joshmanisdabomb.lcc.item;

import com.joshmanisdabomb.lcc.data.capability.CapabilityGauntlet;
import com.joshmanisdabomb.lcc.data.capability.CapabilityHearts;
import com.joshmanisdabomb.lcc.functionality.GauntletFunctionality;
import com.joshmanisdabomb.lcc.functionality.HeartsFunctionality;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class ItemHeart extends Item {

    protected final HeartsFunctionality.HeartType ht;
    protected final float value;

    public ItemHeart(HeartsFunctionality.HeartType ht, float value, Properties p) {
        super(p);
        this.ht = ht;
        this.value = value;
    }

    protected void onHeartEffect(CapabilityHearts.CIHearts hearts, EntityLivingBase entity) {
        this.ht.addHealth(hearts, entity, this.value, HeartsFunctionality.TEMPORARY_USUAL_LIMIT);
    }

    protected boolean canUse(CapabilityHearts.CIHearts hearts, EntityLivingBase entity) {
        return !this.ht.isFullHealth(hearts, entity);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase entity, int ticks) {
        ticks = this.getUseDuration(stack) - ticks;
        if (ticks >= 30 && ticks % 10 == 0) {
            entity.getCapability(CapabilityHearts.CHeartsProvider.DEFAULT_CAPABILITY).ifPresent(hearts -> {
                this.onHeartEffect(hearts, entity);
                if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isCreative()) stack.shrink(1);
                if (stack.isEmpty() || !this.canUse(hearts, entity)) entity.stopActiveHand();
            });
        }
    }

    @Override
    public EnumAction getUseAction(ItemStack p_77661_1_) {
        return EnumAction.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        LazyOptional<CapabilityHearts.CIHearts> cap = player.getCapability(CapabilityHearts.CHeartsProvider.DEFAULT_CAPABILITY);
        if (!cap.isPresent()) return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
        cap.ifPresent(hearts -> {
            if (this.canUse(hearts, player)) {
                player.setActiveHand(hand);
            }
        });
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

}
