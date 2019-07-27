package com.joshmanisdabomb.lcc.functionality;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.data.capability.GauntletCapability;
import com.joshmanisdabomb.lcc.data.capability.HeartsCapability;
import com.joshmanisdabomb.lcc.registry.LCCDamage;
import com.joshmanisdabomb.lcc.registry.LCCEffects;
import com.joshmanisdabomb.lcc.registry.LCCItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.minecart.MinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public abstract class GauntletFunctionality {

    public static final Predicate<Entity> PUNCHABLES = (e) -> ((e instanceof LivingEntity && ((LivingEntity)e).deathTime <= 0) || e instanceof BoatEntity || e instanceof MinecartEntity);

    public static final int UPPERCUT_COOLDOWN = 92;
    public static final int PUNCH_COOLDOWN = 78;
    public static final int STOMP_COOLDOWN = 200;

    public static final int PUNCH_MAX_DURATION = 9;
    public static final int PUNCH_EFFECT_MAX_DURATION = 13;
    public static final int STOMP_MAX_DURATION = 600;
    public static final int STUN_MAX_DURATION = 13;

    public static final float UPPERCUT_DAMAGE = 6.0F;
    public static final float PUNCH_DAMAGE = 11.0F;
    public static final float PUNCH_WALL_DAMAGE = 14.0F;

    public static final float UPPERCUT_SPEED_VERTICAL = 1.66F;
    public static final float UPPERCUT_SPEED_HORIZONTAL = 1/2F;

    public static final float FALL_UPPERCUT_COMPENSATION = -16.0F;
    public static final float FALL_UPPERCUTEE_COMPENSATION = -5.0F;
    public static final float FALL_PUNCH_COMPENSATION = -8.0F;
    public static final float FALL_PUNCHED_COMPENSATION = 0.0F;

    public static final float TEMPHEALTH_UPPERCUT = 1.0F;
    public static final float TEMPHEALTH_PUNCH = 2.0F;

    public static final Vec3d UPPERCUT_BB = new Vec3d(2.0D, 2.0D, 2.0D);
    public static final Vec3d PUNCH_BB = new Vec3d(1.35D, 1.15D, 1.35D);

    public static float getStrength(ItemStack stack, int timeLeft) {
        return Math.min(Math.max(stack.getItem().getUseDuration(stack) - timeLeft, 4), 20) / 20F;
    }

    public static void performUppercut(GauntletCapability.CIGauntlet gauntlet, ItemStack stack, LivingEntity actor) {
        GemModifier gm = GemModifier.getGem(LCCItems.ruby);
        if (gauntlet.canUppercut() || (actor instanceof PlayerEntity && ((PlayerEntity)actor).isCreative())) {
            float f = actor.rotationYaw;
            float f1 = actor.rotationPitch;
            float f2 = -MathHelper.sin(f * ((float)Math.PI / 180F)) * MathHelper.cos(f1 * ((float)Math.PI / 180F));
            float f4 = MathHelper.cos(f * ((float)Math.PI / 180F)) * MathHelper.cos(f1 * ((float)Math.PI / 180F));
            float f5 = MathHelper.sqrt(f2 * f2 + f4 * f4);
            if (f5 == 0) f5 = 1F;
            f2 = f2 * (UPPERCUT_SPEED_HORIZONTAL / f5);
            f4 = f4 * (UPPERCUT_SPEED_HORIZONTAL / f5);
            actor.setMotion((double)f2, UPPERCUT_SPEED_VERTICAL*gm.speed, (double)f4);
            actor.fallDistance = Math.min(FALL_UPPERCUT_COMPENSATION*gm.speed, actor.fallDistance);
            actor.isAirBorne = true;
            LCC.proxy.addParticle(actor.world, ParticleTypes.EXPLOSION_EMITTER, actor.posX, actor.posY, actor.posZ, 1.0D, 0.0D, 0.0D);
            List<Entity> entities = actor.world.getEntitiesInAABBexcluding(actor, actor.getBoundingBox().grow(UPPERCUT_BB.x, UPPERCUT_BB.y, UPPERCUT_BB.z), PUNCHABLES);
            if (entities.size() > 0) {
                actor.getCapability(HeartsCapability.CHeartsProvider.DEFAULT_CAPABILITY).ifPresent(hearts -> {
                    hearts.addTemporaryHealth(TEMPHEALTH_UPPERCUT*gm.health, HeartsFunctionality.TEMPORARY_USUAL_LIMIT);
                });
                for (Entity other : entities) {
                    other.fallDistance = FALL_UPPERCUTEE_COMPENSATION;
                    other.hurtResistantTime = 0;
                    if (actor.isServerWorld()) other.attackEntityFrom(LCCDamage.causeGauntletUppercutDamage(actor), UPPERCUT_DAMAGE*gm.damage);
                    other.setMotion((double)f2*1.9F*gm.knockback, (UPPERCUT_SPEED_VERTICAL*gm.speed)-0.03F, (double)f4*1.9F*gm.knockback);
                }
            }
            gauntlet.uppercut(UPPERCUT_COOLDOWN*gm.cooldown);
        }
    }

    public static void performPunch(GauntletCapability.CIGauntlet gauntlet, ItemStack stack, LivingEntity actor, int timeLeft) {
        GemModifier gm = GemModifier.getGem(LCCItems.ruby);
        if (gauntlet.canPunch() || (actor instanceof PlayerEntity && ((PlayerEntity)actor).isCreative())) {
            float strength = getStrength(stack, timeLeft);
            float f = actor.rotationYaw;
            float f1 = actor.rotationPitch;
            float f2 = -MathHelper.sin(f * ((float)Math.PI / 180F)) * MathHelper.cos(f1 * ((float)Math.PI / 180F));
            float f4 = MathHelper.cos(f * ((float)Math.PI / 180F)) * MathHelper.cos(f1 * ((float)Math.PI / 180F));
            float f5 = MathHelper.sqrt(f2 * f2 + f4 * f4);
            float f6 = 3.0F * ((1.0F + (((float)strength) * 5F)) / 4.0F);
            if (f5 == 0) f5 = 1F;
            f2 = f2 * (f6 / f5);
            f4 = f4 * (f6 / f5);
            actor.setMotion(actor.getMotion().x, 0F, actor.getMotion().z);
            actor.addVelocity((double)f2 * gm.speed, 0, (double)f4 * gm.speed);
            actor.isAirBorne = true;
            actor.fallDistance = Math.min(FALL_PUNCH_COMPENSATION, actor.fallDistance);
            gauntlet.punch(PUNCH_COOLDOWN*gm.cooldown, strength, f2, f4, gm.ordinal());
        }
    }

    public static void tick(GauntletCapability.CIGauntlet gauntlet, ItemStack stack, LivingEntity actor) {
        gauntlet.tick();
        if (gauntlet.isPunched()) {
            if (actor.isInWaterOrBubbleColumn() || actor.isInLava()) {
                gauntlet.stopPunched();
                actor.isAirBorne = true;
            } else if (actor.collidedHorizontally || Math.max(Math.abs(actor.getMotion().x), Math.abs(actor.getMotion().z)) < 0.8D) {
                gauntlet.stopPunched();
                actor.hurtResistantTime = 0;
                if (actor.isServerWorld()) actor.attackEntityFrom(LCCDamage.GAUNTLET_PUNCH_WALL, PUNCH_WALL_DAMAGE);
                actor.hurtResistantTime = 15;
                actor.setMotion(0F, actor.getMotion().y, 0F);
            } else {
                actor.setMotion(actor.getMotion().x, 0F, actor.getMotion().z);
                actor.isAirBorne = true;
            }
        } else if (gauntlet.isPunching()) {
            GemModifier gm = GemModifier.values()[0];
            List<Entity> entities = actor.world.getEntitiesInAABBexcluding(actor, actor.getBoundingBox().grow(PUNCH_BB.x, PUNCH_BB.y, PUNCH_BB.z), PUNCHABLES);
            if (entities.size() > 0) {
                actor.getCapability(HeartsCapability.CHeartsProvider.DEFAULT_CAPABILITY).ifPresent(hearts -> {
                    hearts.addTemporaryHealth(TEMPHEALTH_PUNCH*gm.health, HeartsFunctionality.TEMPORARY_USUAL_LIMIT);
                });
                for (Entity other : entities) {
                    other.getCapability(GauntletCapability.CGauntletProvider.DEFAULT_CAPABILITY).ifPresent(gauntlet2 -> {
                        if (other instanceof LivingEntity) {
                            ((LivingEntity)other).addPotionEffect(new EffectInstance(LCCEffects.stun, (int)Math.ceil(STUN_MAX_DURATION * gauntlet.getPunchStrength() * gm.stun), 0, true, false, true));
                        }
                        other.fallDistance = FALL_PUNCHED_COMPENSATION;
                        other.isAirBorne = true;
                        gauntlet2.punched(gauntlet.getPunchStrength());
                        if (actor.isServerWorld()) other.attackEntityFrom(LCCDamage.causeGauntletPunchDamage(actor), PUNCH_DAMAGE * gauntlet.getPunchStrength() * gm.damage);
                        other.addVelocity(gauntlet.getPunchVelocityX() * gm.knockback, 0.08F, gauntlet.getPunchVelocityZ() * gm.knockback);
                        other.hurtResistantTime = 15;
                    });
                }
                gauntlet.stopPunched();
                actor.setMotion((-gauntlet.getPunchVelocityX() * gm.speed) / Math.max(Math.abs(gauntlet.getPunchVelocityX()), Math.abs(gauntlet.getPunchVelocityZ())), 0.08F, (-gauntlet.getPunchVelocityZ() * gm.speed) / Math.max(Math.abs(gauntlet.getPunchVelocityX()), Math.abs(gauntlet.getPunchVelocityZ())));
                actor.fallDistance = Math.min(actor.fallDistance, 0.0F);
                actor.isAirBorne = true;
            } else if (actor.collidedHorizontally || Math.max(Math.abs(actor.getMotion().x), Math.abs(actor.getMotion().z)) < 0.8D) {
                gauntlet.stopPunch();
                actor.setMotion(0F, actor.getMotion().y, 0F);
            } else {
                actor.setMotion(actor.getMotion().x, 0F, actor.getMotion().z);
                actor.isAirBorne = true;
            }
        }
    }

    public enum GemModifier {
        RUBY(LCCItems.ruby, 1.5F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F),
        TOPAZ(LCCItems.topaz, 1.0F, 2/3.0F, 1.0F, 1.0F, 1.0F, 1.0F),
        EMERALD(Items.EMERALD, 1.0F, 1.0F, 1.25F, 1.0F, 1.0F, 1.0F),
        DIAMOND(Items.DIAMOND, 1.0F, 1.0F, 1.0F, 1.5F, 1.0F, 1.0F),
        SAPPHIRE(LCCItems.sapphire, 1.0F, 1.0F, 1.0F, 1.0F, 1.5F, 1.0F),
        AMETHYST(LCCItems.amethyst, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 2.0F);

        public final Item gem;
        public final float damage;
        public final float cooldown;
        public final float speed;
        public final float stun;
        public final float health;
        public final float knockback;

        GemModifier(Item gem, float damage, float cooldown, float speed, float stun, float health, float knockback) {
            this.gem = gem;
            this.damage = damage;
            this.cooldown = cooldown;
            this.speed = speed;
            this.stun = stun;
            this.health = health;
            this.knockback = knockback;
        }

        public static GemModifier getGem(Item i) {
            return Arrays.stream(GemModifier.values()).filter(gm -> gm.gem == i).findFirst().get();
        }
    }

}
