package com.joshmanisdabomb.lcc.capability;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.functionality.GauntletFunctionality;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class GauntletCapability implements LCCCapabilityHelper {

    public static final ResourceLocation LOCATION = new ResourceLocation(LCC.MODID, "gauntlet");

    public int uppercutCooldown = -4;
    public int uppercutDuration = 0;
    public int uppercutGem = 0;
    public int uppercutEffectDuration = 0;
    public int uppercutEffectGem = 0;

    public int punchCooldown = -4;
    public float punchStrength = 0.0F;
    public int punchDuration = 0;
    public int punchGem = 0;
    public int punchStepAssist = 0;
    public int punchEffectDuration = 0;
    public int punchEffectGem = 0;
    public int punchEffectStepAssist = 0;
    public double punchVelocityX = 0.0D;
    public double punchVelocityZ = 0.0D;

    public int stompCooldown = -4;
    public boolean stompActive = false;
    public int stompGem = 0;
    public int stompEffectDuration = 0;
    public int stompEffectGem = 0;

    @Override
    public ResourceLocation getLocation() {
        return LOCATION;
    }

    public boolean canUppercut() {
        return uppercutCooldown <= 0 && punchDuration <= 0 && !stompActive;
    }

    public void uppercut(float cooldown, int gemID) {
        uppercutCooldown = (int)Math.ceil(cooldown);
        uppercutGem = gemID;
        uppercutDuration = GauntletFunctionality.UPPERCUT_MAX_DURATION;
        uppercutEffectDuration = 0;
    }

    public boolean canPunch() {
        return punchCooldown <= 0 && punchDuration <= 0 && !stompActive;
    }

    public void uppercutted(int gemID) {
        uppercutEffectGem = gemID;
        uppercutEffectDuration = GauntletFunctionality.UPPERCUT_EFFECT_MAX_DURATION;
        uppercutDuration = 0;
        punchDuration = punchEffectDuration = 0;
    }

    public void punch(float cooldown, float strength, double velocityX, double velocityZ, int gemID) {
        punchCooldown = (int)Math.ceil(cooldown);
        punchStrength = strength;
        punchDuration = (int)Math.ceil(GauntletFunctionality.PUNCH_MAX_DURATION * strength);
        punchVelocityX = velocityX;
        punchVelocityZ = velocityZ;
        punchGem = gemID;
        punchStepAssist = 0;
        uppercutDuration = uppercutEffectDuration = 0;
    }

    public boolean isPunching() {
        return punchDuration > 0;
    }

    public void stopPunch() {
        punchDuration = 0;
    }

    public void punched(float strength, int gemID) {
        punchEffectDuration = (int)Math.ceil(GauntletFunctionality.PUNCH_EFFECT_MAX_DURATION * strength);
        punchEffectGem = gemID;
        punchEffectStepAssist = 0;
        uppercutDuration = uppercutEffectDuration = 0;
    }

    public boolean isPunched() {
        return punchEffectDuration > 0;
    }

    public void stopPunched() {
        punchEffectDuration = 0;
    }

    public boolean canStomp() {
        return stompCooldown <= 0 && punchDuration <= 0 && !stompActive;
    }

    public void stomp() {
        stompCooldown = GauntletFunctionality.STOMP_COOLDOWN;
        stompActive = true;
        punchDuration = 0;
        uppercutDuration = uppercutEffectDuration = 0;
    }

    public boolean isUppercut() {
        return uppercutDuration > 0;
    }

    public boolean isUppercutted() {
        return uppercutEffectDuration > 0;
    }

    public void tick() {
        if (uppercutCooldown > -4) uppercutCooldown--;
        if (uppercutDuration > 0) uppercutDuration--;
        if (uppercutEffectDuration > 0) uppercutEffectDuration--;
        if (punchCooldown > -4) punchCooldown--;
        if (punchDuration > 0) punchDuration--;
        if (punchEffectDuration > 0) punchEffectDuration--;
        if (stompCooldown > -4) stompCooldown--;
        if (stompEffectDuration > -4) stompEffectDuration--;
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

