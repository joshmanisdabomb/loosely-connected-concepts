package com.joshmanisdabomb.lcc.data.capability;

import com.joshmanisdabomb.lcc.functionality.HeartsFunctionality;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class HeartsCapability implements Capability.IStorage<HeartsCapability.CIHearts> {

    @Override
    public INBT writeNBT(Capability<CIHearts> capability, CIHearts instance, Direction side) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putFloat("red_max", instance.getRedMaxHealth());
        nbt.putFloat("iron_max", instance.getIronMaxHealth());
        nbt.putFloat("iron", instance.getIronHealth());
        nbt.putFloat("crystal_max", instance.getCrystalMaxHealth());
        nbt.putFloat("crystal", instance.getCrystalHealth());
        nbt.putFloat("temporary", instance.getTemporaryHealth());
        return nbt;
    }

    @Override
    public void readNBT(Capability<CIHearts> capability, CIHearts instance, Direction side, INBT nbt) {
        CompoundNBT nbtc = (CompoundNBT)nbt;
        instance.setRedMaxHealth(nbtc.getFloat("red_max"));
        instance.setIronMaxHealth(nbtc.getFloat("iron_max"));
        instance.setIronHealth(nbtc.getFloat("iron"));
        instance.setCrystalMaxHealth(nbtc.getFloat("crystal_max"));
        instance.setCrystalHealth(nbtc.getFloat("crystal"));
        instance.setTemporaryHealth(nbtc.getFloat("temporary"), Float.MAX_VALUE);
    }

    public interface CIHearts {

        float getRedMaxHealth();

        float getIronHealth();

        float getIronMaxHealth();

        float getCrystalHealth();

        float getCrystalMaxHealth();

        float getTemporaryHealth();

        void setRedMaxHealth(float value);

        void setIronHealth(float value);

        void setIronMaxHealth(float value);

        void setCrystalHealth(float value);

        void setCrystalMaxHealth(float value);

        void setTemporaryHealth(float value, float limit);

        void addRedMaxHealth(float value);

        void addIronHealth(float value);

        void addIronMaxHealth(float value);

        void addCrystalHealth(float value);

        void addCrystalMaxHealth(float value);

        void addTemporaryHealth(float value, float limit);

    }

    public static class CHearts implements CIHearts {

        private float redMax = 0.0F;
        private float ironMax = 0.0F;
        private float iron = 0.0F;
        private float crystalMax = 0.0F;
        private float crystal = 0.0F;
        private float temporary = 0.0F;

        @Override
        public float getRedMaxHealth() {
            return ironMax;
        }

        @Override
        public float getIronHealth() {
            return iron;
        }

        @Override
        public float getIronMaxHealth() {
            return ironMax;
        }

        @Override
        public float getCrystalHealth() {
            return crystal;
        }

        @Override
        public float getCrystalMaxHealth() {
            return crystalMax;
        }

        @Override
        public float getTemporaryHealth() {
            return temporary;
        }

        @Override
        public void setRedMaxHealth(float value) {
            redMax = Math.min(Math.max(value, 0), HeartsFunctionality.RED_LIMIT);
        }

        @Override
        public void setIronHealth(float value) {
            iron = Math.min(Math.max(value, 0), this.getIronMaxHealth());
        }

        @Override
        public void setIronMaxHealth(float value) {
            ironMax = Math.min(Math.max(value, 0), HeartsFunctionality.IRON_LIMIT);
        }

        @Override
        public void setCrystalHealth(float value) {
            crystal = Math.min(Math.max(value, 0), this.getCrystalMaxHealth());
        }

        @Override
        public void setCrystalMaxHealth(float value) {
            crystalMax = Math.min(Math.max(value, 0), HeartsFunctionality.CRYSTAL_LIMIT);
        }

        @Override
        public void setTemporaryHealth(float value, float limit) {
            temporary = Math.min(Math.max(value, 0), limit);
        }

        @Override
        public void addRedMaxHealth(float value) {
            redMax = Math.min(Math.max(redMax + value, 0), HeartsFunctionality.RED_LIMIT);
        }
        @Override
        public void addIronHealth(float value) {
            iron = Math.min(Math.max(iron + value, 0), this.getIronMaxHealth());
        }

        @Override
        public void addIronMaxHealth(float value) {
            ironMax = Math.min(Math.max(ironMax + value, 0), HeartsFunctionality.IRON_LIMIT);
        }

        @Override
        public void addCrystalHealth(float value) {
            crystal = Math.min(Math.max(crystal + value, 0), this.getCrystalMaxHealth());
        }

        @Override
        public void addCrystalMaxHealth(float value) {
            crystalMax = Math.min(Math.max(crystalMax + value, 0), HeartsFunctionality.CRYSTAL_LIMIT);
        }

        @Override
        public void addTemporaryHealth(float value, float limit) {
            temporary = Math.min(Math.max(temporary + value, 0), limit);
        }

    }

    public static class CHeartsProvider implements ICapabilitySerializable<CompoundNBT> {

        @CapabilityInject(CIHearts.class)
        public static final Capability<CIHearts> DEFAULT_CAPABILITY = null;

        private CIHearts instance = DEFAULT_CAPABILITY.getDefaultInstance();

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
            return capability == DEFAULT_CAPABILITY ? LazyOptional.of(() -> (T) instance) : LazyOptional.empty();
        }

        @Override
        public CompoundNBT serializeNBT() {
            return (CompoundNBT)DEFAULT_CAPABILITY.getStorage().writeNBT(DEFAULT_CAPABILITY, this.instance, null);
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            DEFAULT_CAPABILITY.getStorage().readNBT(DEFAULT_CAPABILITY, this.instance, null, nbt);
        }

    }

}

