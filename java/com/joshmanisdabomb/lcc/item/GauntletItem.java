package com.joshmanisdabomb.lcc.item;

import com.joshmanisdabomb.lcc.capability.GauntletCapability;
import com.joshmanisdabomb.lcc.functionality.GauntletFunctionality;
import com.joshmanisdabomb.lcc.registry.LCCEffects;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class GauntletItem extends Item {

    public GauntletItem(Properties p) {
        super(p);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (entity instanceof PlayerEntity && !entity.isPotionActive(LCCEffects.stun)) {
            entity.getCapability(GauntletCapability.Provider.DEFAULT_CAPABILITY).ifPresent(gauntlet -> {
                GauntletFunctionality.performUppercut(gauntlet, stack, entity);
            });
        }
        return true;
    }

    @Override
    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return false;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 30;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entity, int timeLeft) {
        entity.getCapability(GauntletCapability.Provider.DEFAULT_CAPABILITY).ifPresent(gauntlet -> {
            GauntletFunctionality.performPunch(gauntlet, stack, entity, timeLeft);
        });
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        LazyOptional<GauntletCapability> cap = player.getCapability(GauntletCapability.Provider.DEFAULT_CAPABILITY);
        if (!cap.isPresent()) return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
        cap.ifPresent(gauntlet -> {
            if (gauntlet.canPunch() || player.isCreative()) {
                player.setActiveHand(hand);
            }
        });
        return new ActionResult<>(ActionResultType.CONSUME, player.getHeldItem(hand));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity) {
        this.onPlayerStoppedUsing(stack, world, entity, 0);
        return stack;
    }
}
