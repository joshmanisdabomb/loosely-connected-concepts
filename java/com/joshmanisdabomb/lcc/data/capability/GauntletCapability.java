package com.joshmanisdabomb.lcc.data.capability;

import com.joshmanisdabomb.lcc.functionality.GauntletFunctionality;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class GauntletCapability {

    private int uppercutCooldown = -4;
    private int punchCooldown = -4;
    private float punchStrength = 0.0F;
    private int punchDuration = 0;
    private int punchEffectDuration = 0;
    private double punchVelocityX = 0.0D;
    private double punchVelocityZ = 0.0D;
    private int punchGemID = 0;
    private int stompCooldown = -4;
    private int stompDuration = 0;

    public boolean canUppercut() {
        return uppercutCooldown <= 0 && punchDuration <= 0 && stompDuration <= 0;
    }

    public void uppercut(float cooldown) {
        uppercutCooldown = (int)Math.ceil(cooldown);
    }

    public boolean canPunch() {
        return punchCooldown <= 0 && punchDuration <= 0 && stompDuration <= 0;
    }

    public void punch(float cooldown, float strength, double velocityX, double velocityZ, int gemID) {
        punchCooldown = (int)Math.ceil(cooldown);
        punchStrength = strength;
        punchDuration = (int)Math.ceil(GauntletFunctionality.PUNCH_MAX_DURATION * strength);
        punchVelocityX = velocityX;
        punchVelocityZ = velocityZ;
        punchGemID = gemID;
    }

    public boolean isPunching() {
        return punchDuration > 0;
    }

    public void stopPunch() {
        punchDuration = 0;
    }

    public void punched(float strength) {
        punchEffectDuration = (int)Math.ceil(GauntletFunctionality.PUNCH_EFFECT_MAX_DURATION * strength);
    }

    public float getPunchStrength() {
        return punchStrength;
    }

    public double getPunchVelocityX() {
        return punchVelocityX;
    }

    public double getPunchVelocityZ() {
        return punchVelocityZ;
    }

    public int getPunchGemID() { return punchGemID; }

    public boolean isPunched() {
        return punchEffectDuration > 0;
    }

    public void stopPunched() {
        punchEffectDuration = 0;
    }

    public boolean canStomp() {
        return stompCooldown <= 0 && punchDuration <= 0 && stompDuration <= 0;
    }

    public void stomp() {
        stompCooldown = GauntletFunctionality.STOMP_COOLDOWN;
        stompDuration = GauntletFunctionality.STOMP_MAX_DURATION;
    }

    public boolean isStomping() {
        return stompDuration > 0;
    }

    public void stopStomp() {
        stompDuration = 0;
    }

    public void tick() {
        if (uppercutCooldown > -4) uppercutCooldown--;
        if (punchCooldown > -4) punchCooldown--;
        if (punchDuration > 0) punchDuration--;
        if (punchEffectDuration > 0) punchEffectDuration--;
        if (stompCooldown > -4) stompCooldown--;
        if (stompDuration > 0) stompDuration--;
    }

    public int getUppercutCooldownRaw() {
        return uppercutCooldown;
    }

    public int getPunchCooldownRaw() {
        return punchCooldown;
    }

    public int getPunchDurationRaw() {
        return punchDuration;
    }

    public int getStompCooldownRaw() {
        return stompCooldown;
    }

    public int getStompDurationRaw() {
        return stompDuration;
    }
    
    public static class Storage implements Capability.IStorage<GauntletCapability> {

        @Override
        public INBT writeNBT(Capability<GauntletCapability> capability, GauntletCapability instance, Direction side) {
            return null;
        }

        @Override
        public void readNBT(Capability<GauntletCapability> capability, GauntletCapability instance, Direction side, INBT nbt) {
        }

    }

    public static class Provider implements ICapabilityProvider {

        @CapabilityInject(GauntletCapability.class)
        public static final Capability<GauntletCapability> DEFAULT_CAPABILITY = null;

        private GauntletCapability instance = DEFAULT_CAPABILITY.getDefaultInstance();

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
            return capability == DEFAULT_CAPABILITY ? LazyOptional.of(() -> (T) instance) : LazyOptional.empty();
        }

    }

}

