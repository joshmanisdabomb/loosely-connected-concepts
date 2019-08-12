package com.joshmanisdabomb.lcc.item;

import com.joshmanisdabomb.lcc.data.capability.HeartsCapability;
import com.joshmanisdabomb.lcc.functionality.HeartsFunctionality;
import com.joshmanisdabomb.lcc.network.HeartsUpdatePacket;
import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

public class HeartItem extends Item {

    protected final HeartsFunctionality.HeartType ht;
    protected final float value;

    public HeartItem(HeartsFunctionality.HeartType ht, float value, Properties p) {
        super(p);
        this.ht = ht;
        this.value = value;
    }

    protected void onHeartEffect(HeartsCapability hearts, LivingEntity entity) {
        this.ht.addHealth(hearts, entity, this.value, HeartsFunctionality.TEMPORARY_USUAL_LIMIT);
    }

    protected boolean canUse(HeartsCapability hearts, LivingEntity entity) {
        return this.ht == HeartsFunctionality.HeartType.TEMPORARY ? this.ht.getHealth(hearts, entity) < HeartsFunctionality.TEMPORARY_USUAL_LIMIT : !this.ht.isFullHealth(hearts, entity);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity entity, int ticks) {
        ticks = this.getUseDuration(stack) - ticks;
        if (ticks >= 30 && ticks % 10 == 0) {
            entity.getCapability(HeartsCapability.Provider.DEFAULT_CAPABILITY).ifPresent(hearts -> {
                this.onHeartEffect(hearts, entity);
                if (!(entity instanceof PlayerEntity) || !((PlayerEntity)entity).isCreative()) {
                    stack.shrink(1);
                }
                if (stack.isEmpty() || !this.canUse(hearts, entity)) {
                    entity.stopActiveHand();
                }
                if (entity instanceof ServerPlayerEntity) {
                    LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)entity), new HeartsUpdatePacket(hearts));
                }
            });
        }
    }

    @Override
    public UseAction getUseAction(ItemStack p_77661_1_) {
        return UseAction.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        LazyOptional<HeartsCapability> cap = player.getCapability(HeartsCapability.Provider.DEFAULT_CAPABILITY);
        if (!cap.isPresent()) return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
        cap.ifPresent(hearts -> {
            if (this.canUse(hearts, player)) {
                player.setActiveHand(hand);
            }
        });
        return new ActionResult<>(ActionResultType.SUCCESS, player.getHeldItem(hand));
    }

}
