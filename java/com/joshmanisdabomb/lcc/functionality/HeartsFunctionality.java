package com.joshmanisdabomb.lcc.functionality;

import com.joshmanisdabomb.lcc.data.capability.HeartsCapability;
import com.joshmanisdabomb.lcc.network.HeartsUpdatePacket;
import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public abstract class HeartsFunctionality {

    public static final float RED_LIMIT = 20.0F;
    public static final float IRON_LIMIT = 20.0F;
    public static final float CRYSTAL_LIMIT = 20.0F;
    public static final float TEMPORARY_USUAL_LIMIT = 20.0F;

    public static final HeartType[] HURT_TYPES = {HeartType.TEMPORARY, HeartType.CRYSTAL, HeartType.IRON};

    public static void tick(HeartsCapability.CIHearts hearts, LivingEntity actor) {
        if (hearts.getTemporaryHealth() > 0) {
            hearts.addTemporaryHealth(-0.019F, Float.MAX_VALUE);
        }
    }

    public static void capabilityClone(HeartsCapability.CIHearts heartsOriginal, HeartsCapability.CIHearts heartsNew, PlayerEntity playerOriginal, PlayerEntity playerNew, PlayerEvent.Clone event) {
        if (!event.isWasDeath()) {
            heartsNew.setIronMaxHealth(heartsOriginal.getIronMaxHealth());
            heartsNew.setIronHealth(heartsOriginal.getIronHealth());
            heartsNew.setCrystalMaxHealth(heartsOriginal.getCrystalMaxHealth());
            heartsNew.setCrystalHealth(heartsOriginal.getCrystalHealth());
            heartsNew.setTemporaryHealth(heartsOriginal.getTemporaryHealth(), Float.MAX_VALUE);
        }
    }

    public static void hurt(HeartsCapability.CIHearts hearts, LivingEntity entity, LivingHurtEvent e) {
        float damage = e.getAmount();
        for (HeartType ht : HURT_TYPES) {
            if (damage <= 0.0F) break;
            float absorption = ht.getHealth(hearts, entity);
            if (absorption > 0.0F) {
                ht.addHealth(hearts, entity, -damage, Float.MAX_VALUE);
                damage -= absorption;
            }
        }
        if (entity instanceof ServerPlayerEntity) {
            LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)entity), new HeartsUpdatePacket(hearts));
        }
        e.setAmount(Math.max(damage, 0.0F));
    }

    public enum HeartType {
        RED,
        IRON,
        CRYSTAL,
        TEMPORARY;

        public float getHealth(HeartsCapability.CIHearts hearts, LivingEntity entity) {
            switch (this) {
                case RED:
                    return entity.getHealth();
                case IRON:
                    return hearts.getIronHealth();
                case CRYSTAL:
                    return hearts.getCrystalHealth();
                case TEMPORARY:
                    return hearts.getTemporaryHealth();
                default:
                    return 0.0F;
            }
        }

        public float getMaxHealth(HeartsCapability.CIHearts hearts, LivingEntity entity) {
            switch (this) {
                case RED:
                    return entity.getMaxHealth();
                case IRON:
                    return hearts.getIronMaxHealth();
                case CRYSTAL:
                    return hearts.getCrystalMaxHealth();
                case TEMPORARY:
                    return Float.MAX_VALUE;
                default:
                    return 0.0F;
            }
        }

        public void setHealth(HeartsCapability.CIHearts hearts, LivingEntity entity, float value, float temporaryLimit) {
            switch (this) {
                case RED:
                    entity.setHealth(value);
                    break;
                case IRON:
                    hearts.setIronHealth(value);
                    break;
                case CRYSTAL:
                    hearts.setCrystalHealth(value);
                    break;
                case TEMPORARY:
                    hearts.setTemporaryHealth(value, temporaryLimit);
                    break;
                default:
                    break;
            }
        }

        public void addHealth(HeartsCapability.CIHearts hearts, LivingEntity entity, float value, float temporaryLimit) {
            switch (this) {
                case RED:
                    entity.setHealth(entity.getHealth() + value);
                    break;
                case IRON:
                    hearts.addIronHealth(value);
                    break;
                case CRYSTAL:
                    hearts.addCrystalHealth(value);
                    break;
                case TEMPORARY:
                    hearts.addTemporaryHealth(value, temporaryLimit);
                    break;
                default:
                    break;
            }
        }

        public void setMaxHealth(HeartsCapability.CIHearts hearts, LivingEntity entity, float value) {
            switch (this) {
                case RED:
                    hearts.setRedMaxHealth(value);
                    break;
                case IRON:
                    hearts.setIronMaxHealth(value);
                    break;
                case CRYSTAL:
                    hearts.setCrystalMaxHealth(value);
                    break;
                case TEMPORARY:
                default:
                    break;
            }
        }

        public void addMaxHealth(HeartsCapability.CIHearts hearts, LivingEntity entity, float value) {
            switch (this) {
                case RED:
                    hearts.addRedMaxHealth(value);
                    break;
                case IRON:
                    hearts.addIronMaxHealth(value);
                    break;
                case CRYSTAL:
                    hearts.addCrystalMaxHealth(value);
                    break;
                case TEMPORARY:
                default:
                    break;
            }
        }

        public boolean isFullHealth(HeartsCapability.CIHearts hearts, LivingEntity entity) {
            return this.getHealth(hearts, entity) >= this.getMaxHealth(hearts, entity);
        }

        public boolean isFullMaxHealth(HeartsCapability.CIHearts hearts, LivingEntity entity) {
            switch (this) {
                case RED:
                    return hearts.getRedMaxHealth() >= HeartsFunctionality.RED_LIMIT;
                case IRON:
                    return hearts.getIronMaxHealth() >= HeartsFunctionality.IRON_LIMIT;
                case CRYSTAL:
                    return hearts.getCrystalMaxHealth() >= HeartsFunctionality.CRYSTAL_LIMIT;
                case TEMPORARY:
                    return false;
                default:
                    return true;
            }
        }

    }

}
