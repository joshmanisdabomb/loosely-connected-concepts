package com.joshmanisdabomb.lcc.data.capability;

import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Random;

public class SpreaderCapability {

    public static final int[] DECAY_PERCENTAGE = new int[]{250, 225, 200, 180, 160, 140, 120, 100, 90, 80, 75, 70, 65, 60, 55, 50};

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

    public int getMinTickSpeed(DyeColor color) {
        return 120 - (this.getSpeedLevel(color) * 10);
    }

    public int getMaxTickSpeed(DyeColor color) {
        return 240 - (this.getSpeedLevel(color) * 20);
    }

    public int getTickSpeed(DyeColor color, Random random) {
        int a = this.getMaxTickSpeed(color);
        int b = this.getMinTickSpeed(color);
        return random.nextInt((a-b)+1) + b;
    }

    public int getDamageLevel(DyeColor color) {
        return damageLevel.getOrDefault(color, 0);
    }

    public int getDecayLevel(DyeColor color) {
        return decayLevel.getOrDefault(color, 0);
    }

    public int getDecayPercentage(DyeColor color) {
        return DECAY_PERCENTAGE[this.getDecayLevel(color)];
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

    public SpreaderCapability readFromPacket(PacketBuffer buf) {
        byte[] enabled = buf.readByteArray();
        byte[] speedLevel = buf.readByteArray();
        byte[] damageLevel = buf.readByteArray();
        byte[] decayLevel = buf.readByteArray();
        byte[] eating = buf.readByteArray();
        byte[] throughGround = buf.readByteArray();
        byte[] throughLiquid = buf.readByteArray();
        byte[] throughAir = buf.readByteArray();
        for (int i = 0; i < 16; i++) {
            DyeColor color = DyeColor.byId(i);
            this.enabled.put(color, enabled[i] > 0);
            this.speedLevel.put(color, (int)speedLevel[i]);
            this.damageLevel.put(color, (int)damageLevel[i]);
            this.decayLevel.put(color, (int)decayLevel[i]);
            this.eating.put(color, eating[i] > 0);
            this.throughGround.put(color, throughGround[i] > 0);
            this.throughLiquid.put(color, throughLiquid[i] > 0);
            this.throughAir.put(color, throughAir[i] > 0);
        }
        return this;
    }

    public SpreaderCapability writeToPacket(PacketBuffer buf) {
        byte[] enabled = new byte[16];
        byte[] speedLevel = new byte[16];
        byte[] damageLevel = new byte[16];
        byte[] decayLevel = new byte[16];
        byte[] eating = new byte[16];
        byte[] throughGround = new byte[16];
        byte[] throughLiquid = new byte[16];
        byte[] throughAir = new byte[16];
        for (DyeColor color : DyeColor.values()) {
            int i = color.getId();
            enabled[i] = (byte)(this.isEnabled(color) ? 1 : 0);
            speedLevel[i] = (byte)this.getSpeedLevel(color);
            damageLevel[i] = (byte)this.getDamageLevel(color);
            decayLevel[i] = (byte)this.getDecayLevel(color);
            eating[i] = (byte)(this.isEater(color) ? 1 : 0);
            throughGround[i] = (byte)(this.throughGround(color) ? 1 : 0);
            throughLiquid[i] = (byte)(this.throughLiquid(color) ? 1 : 0);
            throughAir[i] = (byte)(this.throughAir(color) ? 1 : 0);
        }
        buf.writeByteArray(enabled).writeByteArray(speedLevel).writeByteArray(damageLevel).writeByteArray(decayLevel).writeByteArray(eating).writeByteArray(throughGround).writeByteArray(throughLiquid).writeByteArray(throughAir);
        return this;
    }

    public SpreaderCapability clone(SpreaderCapability other) {
        this.enabled.putAll(other.enabled);
        this.speedLevel.putAll(other.speedLevel);
        this.damageLevel.putAll(other.damageLevel);
        this.decayLevel.putAll(other.decayLevel);
        this.eating.putAll(other.eating);
        this.throughGround.putAll(other.throughGround);
        this.throughLiquid.putAll(other.throughLiquid);
        this.throughAir.putAll(other.throughAir);
        return this;
    }

    public static class Storage implements Capability.IStorage<SpreaderCapability> {

        @Override
        public INBT writeNBT(Capability<SpreaderCapability> capability, SpreaderCapability instance, Direction side) {
            CompoundNBT nbt = new CompoundNBT();
            for (DyeColor c : DyeColor.values()) {
                CompoundNBT nbtc = new CompoundNBT();
                nbtc.putBoolean("enabled", instance.isEnabled(c));
                nbtc.putInt("speedLevel", instance.getSpeedLevel(c));
                nbtc.putInt("damageLevel", instance.getDamageLevel(c));
                nbtc.putInt("decayLevel", instance.getDecayLevel(c));
                nbtc.putBoolean("eating", instance.isEater(c));
                nbtc.putBoolean("throughGround", instance.throughGround(c));
                nbtc.putBoolean("throughLiquid", instance.throughLiquid(c));
                nbtc.putBoolean("throughAir", instance.throughAir(c));
                nbt.put(c.getName(), nbtc);
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
