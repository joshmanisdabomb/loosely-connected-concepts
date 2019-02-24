package com.joshmanisdabomb.lcc.data.capability;

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
    public void readNBT(Capability<CIGauntlet> capability, CIGauntlet instance, EnumFacing side, INBTBase nbt) {

    }

    public interface CIGauntlet {

        boolean canUppercut();

        void uppercut();

        boolean canPunch();

        void punch(float strength, double velocityX, double velocityZ);

        boolean isPunching();

        void stopPunch();

        void punched(float strength);

        float getPunchStrength();

        double getPunchVelocityX();

        double getPunchVelocityZ();

        boolean isPunched();

        void stopPunched();

        void tick();
    }

    public static class CGauntlet implements CIGauntlet {

        public static final int UPPERCUT_COOLDOWN = 100;
        public static final int PUNCH_COOLDOWN = 80;
        public static final int PUNCH_MAX_DURATION = 10;
        public static final int PUNCH_EFFECT_MAX_DURATION = 10;

        private int uppercutCooldown = 0;
        private int punchCooldown = 0;
        private float punchStrength = 0.0F;
        private int punchDuration = 0;
        private int punchEffectDuration = 0;
        private double punchVelocityX = 0.0D;
        private double punchVelocityZ = 0.0D;

        @Override
        public boolean canUppercut() {
            return uppercutCooldown <= 0 && punchDuration <= 0;
        }

        @Override
        public void uppercut() {
            uppercutCooldown = UPPERCUT_COOLDOWN;
        }

        @Override
        public boolean canPunch() {
            return punchCooldown <= 0 && punchDuration <= 0;
        }

        @Override
        public void punch(float strength, double velocityX, double velocityZ) {
            punchCooldown = PUNCH_COOLDOWN;
            punchStrength = strength;
            punchDuration = (int)Math.ceil(PUNCH_MAX_DURATION * strength);
            punchVelocityX = velocityX;
            punchVelocityZ = velocityZ;
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
            punchEffectDuration = (int)Math.ceil(PUNCH_EFFECT_MAX_DURATION * strength);
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
        public boolean isPunched() {
            return punchEffectDuration > 0;
        }

        @Override
        public void stopPunched() {
            punchDuration = 0;
        }

        @Override
        public void tick() {
            if (uppercutCooldown > 0) uppercutCooldown--;
            if (punchCooldown > 0) punchCooldown--;
            if (punchDuration > 0) punchDuration--;
            if (punchEffectDuration > 0) punchEffectDuration--;
        }

    }

    public static class CGauntletProvider implements ICapabilityProvider {

        @CapabilityInject(CIGauntlet.class)
        public static final Capability<CIGauntlet> DEFAULT_CAPABILITY = null;

        private CIGauntlet instance = DEFAULT_CAPABILITY.getDefaultInstance();

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> capability, EnumFacing facing) {
            return capability == DEFAULT_CAPABILITY ? LazyOptional.of(() -> (T) instance) : null;
        }

    }

}

