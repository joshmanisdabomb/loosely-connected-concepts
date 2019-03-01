package com.joshmanisdabomb.lcc.item;

import com.joshmanisdabomb.lcc.data.capability.CapabilityGauntlet;
import com.joshmanisdabomb.lcc.functionality.GauntletFunctionality;
import com.joshmanisdabomb.lcc.registry.LCCPotions;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class ItemGauntlet extends Item {

    public ItemGauntlet(Properties p) {
        super(p);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, EntityLivingBase entity) {
        if (entity instanceof EntityPlayer && !entity.isPotionActive(LCCPotions.stun)) {
            entity.getCapability(CapabilityGauntlet.CGauntletProvider.DEFAULT_CAPABILITY).ifPresent(gauntlet -> {
                GauntletFunctionality.performUppercut(gauntlet, stack, entity);
            });
        }
        return true;
    }

    @Override
    public boolean canPlayerBreakBlockWhileHolding(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player) {
        return false;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public EnumAction getUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entity, int timeLeft) {
        entity.getCapability(CapabilityGauntlet.CGauntletProvider.DEFAULT_CAPABILITY).ifPresent(gauntlet -> {
            GauntletFunctionality.performPunch(gauntlet, stack, entity, timeLeft);
        });
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        LazyOptional<CapabilityGauntlet.CIGauntlet> cap = player.getCapability(CapabilityGauntlet.CGauntletProvider.DEFAULT_CAPABILITY);
        if (!cap.isPresent()) return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
        cap.ifPresent(gauntlet -> {
            if (gauntlet.canPunch() || player.isCreative()) {
                player.setActiveHand(hand);
            }
        });
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

}
