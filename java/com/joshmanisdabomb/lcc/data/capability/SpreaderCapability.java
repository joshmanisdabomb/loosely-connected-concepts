package com.joshmanisdabomb.lcc.data.capability;

import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Random;

public class SpreaderCapability {

    public final HashMap<DyeColor, Boolean> enabled = new HashMap<>();

    public final HashMap<DyeColor, Integer> speedLevel = new HashMap<>();
    public final HashMap<DyeColor, Integer> damageLevel = new HashMap<>();
    public final HashMap<DyeColor, Integer> decayLevel = new HashMap<>();
    public final HashMap<DyeColor, Boolean> eating = new HashMap<>();

    public final HashMap<DyeColor, Boolean> throughGround = new HashMap<>();
    public final HashMap<DyeColor, Boolean> throughLiquid = new HashMap<>();
    public final HashMap<DyeColor, Boolean> throughAir = new HashMap<>();

    public boolean isEnabled(DyeColor color) {
        return enabled.getOrDefault(color, false);
    }

    public int getSpeedLevel(DyeColor color) {
        return speedLevel.getOrDefault(color, 0);
    }

    public int getTickSpeed(DyeColor color, Random random) {
        int s = this.getSpeedLevel(color) * 10;
        return random.nextInt(121 - s) + (120 - s);
    }

    public int getDamageLevel(DyeColor color) {
        return damageLevel.getOrDefault(color, 0);
    }

    public int getDecayLevel(DyeColor color) {
        return decayLevel.getOrDefault(color, 0);
    }

    public int getDecayPercentage(DyeColor color) {
        int d = this.getDecayLevel(color);
        return (int)Math.ceil((250 * Math.pow(0.9, d)) / 5) * 5;
    }

    public int getDecayAge(DyeColor color, Random random) {
        int d = this.getDecayPercentage(color);
        if (d == 100) return 1;
        else if (d > 100) {
            int i = d / 100;
            return i + (random.nextFloat() <= (d % 100 / 100F) ? 1 : 0);
        } else return random.nextFloat() <= (d % 100 / 100F) ? 1 : 0;
    }

    public boolean isEater(DyeColor color) {
        return eating.getOrDefault(color, false);
    }

    public boolean throughGround(DyeColor color) {
        return throughGround.getOrDefault(color, true);
    }

    public boolean throughLiquid(DyeColor color) {
        return throughLiquid.getOrDefault(color, false);
    }

    public boolean throughAir(DyeColor color) {
        return throughAir.getOrDefault(color, false);
    }

    public static class Storage implements Capability.IStorage<SpreaderCapability> {

        @Override
        public INBT writeNBT(Capability<SpreaderCapability> capability, SpreaderCapability instance, Direction side) {
            CompoundNBT nbt = new CompoundNBT();
            for (DyeColor c : DyeColor.values()) {
                CompoundNBT nbtc = nbt.getCompound(c.getName());
                nbtc.putBoolean("enabled", instance.enabled.getOrDefault(c, false));
                nbtc.putInt("speedLevel", instance.speedLevel.getOrDefault(c, 0));
                nbtc.putInt("damageLevel", instance.damageLevel.getOrDefault(c, 0));
                nbtc.putInt("decayLevel", instance.decayLevel.getOrDefault(c, 0));
                nbtc.putBoolean("eating", instance.eating.getOrDefault(c, false));
                nbtc.putBoolean("throughGround", instance.throughGround.getOrDefault(c, true));
                nbtc.putBoolean("throughLiquid", instance.throughLiquid.getOrDefault(c, false));
                nbtc.putBoolean("throughAir", instance.throughAir.getOrDefault(c, false));
            }
            return nbt;
        }

        @Override
        public void readNBT(Capability<SpreaderCapability> capability, SpreaderCapability instance, Direction side, INBT nbt) {
            CompoundNBT nbtc = (CompoundNBT)nbt;
            for (DyeColor c : DyeColor.values()) {
                CompoundNBT nbtc2 = nbtc.getCompound(c.getName());
                instance.enabled.put(c, nbtc2.getBoolean("enabled"));
                instance.speedLevel.put(c, nbtc2.getInt("speedLevel"));
                instance.damageLevel.put(c, nbtc2.getInt("damageLevel"));
                instance.decayLevel.put(c, nbtc2.getInt("decayLevel"));
                instance.eating.put(c, nbtc2.getBoolean("eating"));
                instance.throughGround.put(c, nbtc2.getBoolean("throughGround"));
                instance.throughLiquid.put(c, nbtc2.getBoolean("throughLiquid"));
                instance.throughAir.put(c, nbtc2.getBoolean("throughAir"));
            }
        }

    }

    public static class Provider implements ICapabilitySerializable<CompoundNBT> {

        @CapabilityInject(SpreaderCapability.class)
        public static final Capability<SpreaderCapability> DEFAULT_CAPABILITY = null;

        private SpreaderCapability instance = DEFAULT_CAPABILITY.getDefaultInstance();

        public static LazyOptional<SpreaderCapability> getGlobalCapability(MinecraftServer server) {
            return DimensionManager.getWorld(server, DimensionType.OVERWORLD, false, false).getCapability(SpreaderCapability.Provider.DEFAULT_CAPABILITY);
        }

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
