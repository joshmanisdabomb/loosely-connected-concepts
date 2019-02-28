package com.joshmanisdabomb.lcc.functionality;

import com.joshmanisdabomb.lcc.registry.LCCDamage;
import com.joshmanisdabomb.lcc.data.capability.CapabilityGauntlet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import java.util.List;
import java.util.function.Predicate;

public abstract class GauntletFunctionality {

    public static final Predicate<Entity> PUNCHABLES = (e) -> e instanceof EntityLivingBase || e instanceof EntityBoat || e instanceof EntityMinecart;

    public static final int UPPERCUT_COOLDOWN = 80;
    public static final int PUNCH_COOLDOWN = 60;
    public static final int PUNCH_MAX_DURATION = 9;
    public static final int PUNCH_EFFECT_MAX_DURATION = 13;
    public static final int STOMP_COOLDOWN = 200;
    public static final int STOMP_MAX_DURATION = 600;

    public static float getStrength(ItemStack stack, int timeLeft) {
        return Math.min(Math.max(stack.getItem().getUseDuration(stack) - timeLeft, 4), 20) / 20F;
    }

    public static void performUppercut(CapabilityGauntlet.CIGauntlet gauntlet, ItemStack stack, EntityLivingBase actor) {
        if (gauntlet.canUppercut() || (actor instanceof EntityPlayer && ((EntityPlayer)actor).isCreative())) {
            float f = actor.rotationYaw;
            float f1 = actor.rotationPitch;
            float f2 = -MathHelper.sin(f * ((float)Math.PI / 180F)) * MathHelper.cos(f1 * ((float)Math.PI / 180F));
            float f4 = MathHelper.cos(f * ((float)Math.PI / 180F)) * MathHelper.cos(f1 * ((float)Math.PI / 180F));
            float f5 = MathHelper.sqrt(f2 * f2 + f4 * f4);
            float f6 = 3.0F / 4.0F;
            f2 = f2 * (f6 / f5);
            f4 = f4 * (f6 / f5);
            actor.setVelocity((double)f2, 0, (double)f4);
            actor.fallDistance = -15.0F;
            actor.motionY = 1.66F;
            actor.isAirBorne = true;
            List<Entity> entities = actor.world.getEntitiesInAABBexcluding(actor, actor.getBoundingBox().grow(2.0F, 2.0F, 2.0F), PUNCHABLES);
            if (entities.size() > 0) {
                for (Entity other : entities) {
                    other.fallDistance = -3.0F;
                    other.attackEntityFrom(LCCDamage.causeGauntletPunchDamage(actor), 6.0F);
                    other.hurtResistantTime = 0;
                    other.setVelocity((double)f2*1.8F, 1.6F, (double)f4*1.8F);
                }
            }
            gauntlet.uppercut();
        }
    }

    public static void performPunch(CapabilityGauntlet.CIGauntlet gauntlet, ItemStack stack, EntityLivingBase actor, int timeLeft) {
        if (gauntlet.canPunch() || (actor instanceof EntityPlayer && ((EntityPlayer)actor).isCreative())) {
            float strength = getStrength(stack, timeLeft);
            float f = actor.rotationYaw;
            float f1 = actor.rotationPitch;
            float f2 = -MathHelper.sin(f * ((float)Math.PI / 180F)) * MathHelper.cos(f1 * ((float)Math.PI / 180F));
            float f4 = MathHelper.cos(f * ((float)Math.PI / 180F)) * MathHelper.cos(f1 * ((float)Math.PI / 180F));
            float f5 = MathHelper.sqrt(f2 * f2 + f4 * f4);
            float f6 = 3.0F * ((1.0F + ((float)strength) * 5F) / 4.0F);
            if (f5 == 0) f5 = 0.01F;
            f2 = f2 * (f6 / f5);
            f4 = f4 * (f6 / f5);
            actor.motionY = 0.0F;
            actor.addVelocity((double)f2, 0, (double)f4);
            actor.isAirBorne = true;
            actor.fallDistance = -10.0F;
            gauntlet.punch(strength, f2, f4);
        }
    }

    public static void tick(CapabilityGauntlet.CIGauntlet gauntlet, ItemStack stack, EntityLivingBase actor) {
        gauntlet.tick();
        if (gauntlet.isPunched()) {
            if (actor.collidedHorizontally || Math.max(Math.abs(actor.motionX), Math.abs(actor.motionZ)) < 0.8D) {
                gauntlet.stopPunched();
                actor.attackEntityFrom(LCCDamage.GAUNTLET_PUNCH_WALL, 14.0F);
                actor.motionX = 0.0F;
                actor.motionZ = 0.0F;
                actor.hurtResistantTime = 0;
            } else {
                actor.motionY = 0.0F;
                actor.isAirBorne = true;
            }
        } else if (gauntlet.isPunching()) {
            List<Entity> entities = actor.world.getEntitiesInAABBexcluding(actor, actor.getBoundingBox().grow(1.0F), PUNCHABLES);
            if (entities.size() > 0) {
                for (Entity other : entities) {
                    other.getCapability(CapabilityGauntlet.CGauntletProvider.DEFAULT_CAPABILITY).ifPresent(gauntlet2 -> {
                        other.fallDistance = 0.0F;
                        other.isAirBorne = true;
                        gauntlet2.punched(gauntlet.getPunchStrength());
                        other.attackEntityFrom(LCCDamage.causeGauntletUppercutDamage(actor), 11.0F * gauntlet.getPunchStrength());
                        other.addVelocity(gauntlet.getPunchVelocityX(), 0.3F, gauntlet.getPunchVelocityZ());
                        other.hurtResistantTime = 0;
                    });
                }
                actor.motionX = 0.0F;
                actor.motionY = 0.0F;
                actor.motionZ = 0.0F;
                actor.fallDistance = Math.min(actor.fallDistance, 0.0F);
                actor.isAirBorne = true;
            } else if (actor.collidedHorizontally || Math.max(Math.abs(actor.motionX), Math.abs(actor.motionZ)) < 0.8D) {
                gauntlet.stopPunch();
                actor.motionX = 0.0F;
                actor.motionZ = 0.0F;
            } else {
                actor.motionY = 0.0F;
                actor.isAirBorne = true;
            }
        }
    }
}
