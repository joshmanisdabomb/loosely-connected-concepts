package com.joshmanisdabomb.lcc.data.capability;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.registry.LCCItems;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Random;

public class SpreaderCapability {

    public static final ResourceLocation LOCATION = new ResourceLocation(LCC.MODID, "spreader");

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

    public HashMap<Object, Integer> calculateCosts(SpreaderCapability old, HashMap<Object, Integer> costs) {
        costs.clear();
        for (DyeColor color : DyeColor.values()) {
            boolean changed = false;
            if (old.isEnabled(color) != this.isEnabled(color)) {
                changed = true;
                if (this.isEnabled(color)) costs.put(LCCItems.spreader_essence, costs.getOrDefault(LCCItems.spreader_essence, 0) + 1);
            }
            if (old.getSpeedLevel(color) != this.getSpeedLevel(color)) {
                changed = true;
                costs.put(Items.SUGAR, costs.getOrDefault(Items.SUGAR, 0) + Math.max(this.getSpeedLevel(color) - old.getSpeedLevel(color), 0));
            }
            if (old.getDamageLevel(color) != this.getDamageLevel(color)) {
                changed = true;
                costs.put(Items.MAGMA_BLOCK, costs.getOrDefault(Items.MAGMA_BLOCK, 0) + Math.max(this.getDamageLevel(color) - old.getDamageLevel(color), 0));
            }
            if (old.getDecayLevel(color) != this.getDecayLevel(color)) {
                changed = true;
                costs.put(Items.REDSTONE, costs.getOrDefault(Items.REDSTONE, 0) + Math.max(this.getDecayLevel(color) - old.getDecayLevel(color), 0));
            }
            if (old.isEater(color) != this.isEater(color)) {
                changed = true;
                if (this.isEater(color)) costs.put(ItemTags.LEAVES, costs.getOrDefault(ItemTags.LEAVES, 0) + 1);
            }
            if (old.throughGround(color) != this.throughGround(color)) {
                changed = true;
                if (this.throughGround(color)) costs.put(Items.DIRT, costs.getOrDefault(Items.DIRT, 0) + 1);
            }
            if (old.throughLiquid(color) != this.throughLiquid(color)) {
                changed = true;
                if (this.throughLiquid(color)) costs.put(Items.SOUL_SAND, costs.getOrDefault(Items.SOUL_SAND, 0) + 1);
            }
            if (old.throughAir(color) != this.throughAir(color)) {
                changed = true;
                if (this.throughAir(color)) costs.put(Items.PHANTOM_MEMBRANE, costs.getOrDefault(Items.PHANTOM_MEMBRANE, 0) + 1);
            }
            if (changed) costs.put(LCCItems.spreader_essence, costs.getOrDefault(LCCItems.spreader_essence, 0) + 1);
        }
        return costs;
    }

    public static void subtractCosts(PlayerInventory playerInventory, HashMap<Object, Integer> costsRaw) {
        final HashMap<Object, Integer> costs = new HashMap<>(costsRaw);
        playerInventory.mainInventory.stream().forEach(s -> {
            Item i = s.getStack().getItem();
            Integer cost = costs.get(i);
            if (cost == null) {
                costs.keySet().stream().filter(key -> key instanceof Tag).forEach(key -> {
                    if (i.isIn((Tag<Item>)key)) {
                        if (costs.get(key) > 0) {
                            costs.put(key, costs.get(key) - s.getStack().getCount());
                            s.setCount(Math.max(-costs.get(key), 0));
                        }
                    }
                });
            } else {
                if (cost > 0) {
                    costs.put(i, cost - s.getStack().getCount());
                    s.setCount(Math.max(-costs.get(i), 0));
                }
            }
        });
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

        private static final GlobalProvider<SpreaderCapability> global = new GlobalProvider<>(() -> DEFAULT_CAPABILITY);

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
            return capability == DEFAULT_CAPABILITY ? LazyOptional.of(() -> (T) instance) : LazyOptional.empty();
        }

        public static LazyOptional<SpreaderCapability> getGlobal(MinecraftServer server) {
            return global.get(server);
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
