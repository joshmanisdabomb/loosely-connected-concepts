package com.joshmanisdabomb.lcc.item;

import com.joshmanisdabomb.lcc.data.capability.CapabilityGauntlet;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;

public class ItemGauntlet extends Item {

    public ItemGauntlet(Properties p) {
        super(p);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            entity.getCapability(CapabilityGauntlet.CGauntletProvider.DEFAULT_CAPABILITY).ifPresent(gauntlet -> {
                if (gauntlet.canUppercut()) {
                    float f = entity.rotationYaw;
                    float f1 = entity.rotationPitch;
                    float f2 = -MathHelper.sin(f * ((float)Math.PI / 180F)) * MathHelper.cos(f1 * ((float)Math.PI / 180F));
                    float f4 = MathHelper.cos(f * ((float)Math.PI / 180F)) * MathHelper.cos(f1 * ((float)Math.PI / 180F));
                    float f5 = MathHelper.sqrt(f2 * f2 + f4 * f4);
                    float f6 = 3.0F * (1.0F / 4.0F);
                    f2 = f2 * (f6 / f5);
                    f4 = f4 * (f6 / f5);
                    entity.setVelocity((double)f2, 0, (double)f4);
                    entity.fallDistance = -15.0F;
                    entity.motionY = 1.66F;
                    entity.isAirBorne = true;
                    List<Entity> entities = entity.world.getEntitiesInAABBexcluding(entity, entity.getBoundingBox().grow(2.0F, 2.0F, 2.0F), (e) -> true);
                    if (entities.size() > 0) {
                        for (Entity other : entities) {
                            other.setVelocity((double)f2*1.8F, 1.6F, (double)f4*1.8F);
                            other.fallDistance = -3.0F;
                        }
                    }
                    gauntlet.uppercut();
                }
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
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        entityLiving.getCapability(CapabilityGauntlet.CGauntletProvider.DEFAULT_CAPABILITY).ifPresent(gauntlet -> {
            if (gauntlet.canPunch()) {
                float strength = Math.min(Math.max(this.getUseDuration(stack) - timeLeft, 4), 20) / 20F;
                float f = entityLiving.rotationYaw;
                float f1 = entityLiving.rotationPitch;
                float f2 = -MathHelper.sin(f * ((float)Math.PI / 180F)) * MathHelper.cos(f1 * ((float)Math.PI / 180F));
                float f4 = MathHelper.cos(f * ((float)Math.PI / 180F)) * MathHelper.cos(f1 * ((float)Math.PI / 180F));
                float f5 = MathHelper.sqrt(f2 * f2 + f4 * f4);
                float f6 = 3.0F * ((1.0F + ((float)strength) * 5F) / 4.0F);
                if (f5 == 0) f5 = 0.01F;
                f2 = f2 * (f6 / f5);
                f4 = f4 * (f6 / f5);
                entityLiving.motionY = 0.0F;
                entityLiving.addVelocity((double)f2, 0, (double)f4);
                entityLiving.isAirBorne = true;
                entityLiving.fallDistance = -10.0F;
                gauntlet.punch(strength, f2, f4);
            }
        });
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        LazyOptional<CapabilityGauntlet.CIGauntlet> cap = player.getCapability(CapabilityGauntlet.CGauntletProvider.DEFAULT_CAPABILITY);
        if (!cap.isPresent()) return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
        cap.ifPresent(gauntlet -> {
            if (gauntlet.canPunch()) {
                player.setActiveHand(hand);
            }
        });
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

}
