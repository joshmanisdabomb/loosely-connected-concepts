package com.joshmanisdabomb.lcc.data.capability;

import com.joshmanisdabomb.lcc.functionality.GauntletFunctionality;
import net.minecraft.nbt.INBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class CapabilityGauntlet implements Capability.IStorage<CapabilityGauntlet.CIGauntlet> {

    @Override
    public INBTBase writeNBT(Capability<CIGauntlet> capability, CIGauntlet instance, EnumFacing side) {
        return null;
    }

    @Override
    public void readNBT(Capability<CIGauntlet> capability, CIGauntlet instance, EnumFacing side, INBTBase nbt) {}

    public interface CIGauntlet {

        boolean canUppercut();

        void uppercut(float cooldown);

        boolean canPunch();

        void punch(float cooldown, float strength, double velocityX, double velocityZ, int gemID);

        boolean isPunching();

        void stopPunch();

        void punched(float strength);

        float getPunchStrength();

        double getPunchVelocityX();

        double getPunchVelocityZ();

        int getPunchGemID();

        boolean isPunched();

        void stopPunched();

        boolean canStomp();

        void stomp();

        boolean isStomping();

        void stopStomp();

        void tick();

        int getUppercutCooldownRaw();

        int getPunchCooldownRaw();

        int getPunchDurationRaw();

        int getStompCooldownRaw();

        int getStompDurationRaw();

    }

    public static class CGauntlet implements CIGauntlet {

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

        @Override
        public boolean canUppercut() {
            return uppercutCooldown <= 0 && punchDuration <= 0 && stompDuration <= 0;
        }

        @Override
        public void uppercut(float cooldown) {
            uppercutCooldown = (int)Math.ceil(cooldown);
        }

        @Override
        public boolean canPunch() {
            return punchCooldown <= 0 && punchDuration <= 0 && stompDuration <= 0;
        }

        @Override
        public void punch(float cooldown, float strength, double velocityX, double velocityZ, int gemID) {
            punchCooldown = (int)Math.ceil(cooldown);
            punchStrength = strength;
            punchDuration = (int)Math.ceil(GauntletFunctionality.PUNCH_MAX_DURATION * strength);
            punchVelocityX = velocityX;
            punchVelocityZ = velocityZ;
            punchGemID = gemID;
        }

        @Override
        public boolean isPunching() {
            return punchDuration > 0;
        }

        @Override
        public void stopPunch() {
            punchDuration = 0;
        }

        @Override
        public void punched(float strength) {
            punchEffectDuration = (int)Math.ceil(GauntletFunctionality.PUNCH_EFFECT_MAX_DURATION * strength);
        }

        @Override
        public float getPunchStrength() {
            return punchStrength;
        }

        @Override
        public double getPunchVelocityX() {
            return punchVelocityX;
        }

        @Override
        public double getPunchVelocityZ() {
            return punchVelocityZ;
        }

        @Override
        public int getPunchGemID() { return punchGemID; }

        @Override
        public boolean isPunched() {
            return punchEffectDuration > 0;
        }

        @Override
        public void stopPunched() {
            punchEffectDuration = 0;
        }

        @Override
        public boolean canStomp() {
            return stompCooldown <= 0 && punchDuration <= 0 && stompDuration <= 0;
        }

        @Override
        public void stomp() {
            stompCooldown = GauntletFunctionality.STOMP_COOLDOWN;
            stompDuration = GauntletFunctionality.STOMP_MAX_DURATION;
        }

        @Override
        public boolean isStomping() {
            return stompDuration > 0;
        }

        @Override
        public void stopStomp() {
            stompDuration = 0;
        }

        @Override
        public void tick() {
            if (uppercutCooldown > -4) uppercutCooldown--;
            if (punchCooldown > -4) punchCooldown--;
            if (punchDuration > 0) punchDuration--;
            if (punchEffectDuration > 0) punchEffectDuration--;
            if (stompCooldown > -4) stompCooldown--;
            if (stompDuration > 0) stompDuration--;
        }

        @Override
        public int getUppercutCooldownRaw() {
            return uppercutCooldown;
        }

        @Override
        public int getPunchCooldownRaw() {
            return punchCooldown;
        }

        @Override
        public int getPunchDurationRaw() {
            return punchDuration;
        }

        @Override
        public int getStompCooldownRaw() {
            return stompCooldown;
        }

        @Override
        public int getStompDurationRaw() {
            return stompDuration;
        }

    }

    public static class CGauntletProvider implements ICapabilityProvider {

        @CapabilityInject(CIGauntlet.class)
        public static final Capability<CIGauntlet> DEFAULT_CAPABILITY = null;

        private CIGauntlet instance = DEFAULT_CAPABILITY.getDefaultInstance();

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> capability, EnumFacing facing) {
            return capability == DEFAULT_CAPABILITY ? LazyOptional.of(() -> (T) instance) : LazyOptional.empty();
        }

    }

}

