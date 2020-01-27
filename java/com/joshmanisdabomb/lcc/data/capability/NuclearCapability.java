package com.joshmanisdabomb.lcc.data.capability;

import com.joshmanisdabomb.lcc.LCC;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class NuclearCapability implements LCCCapabilityHelper {

    public static final ResourceLocation LOCATION = new ResourceLocation(LCC.MODID, "nuclear");

    private ListNBT strikes = new ListNBT();
    private int strikeCount = 0;

    @Override
    public ResourceLocation getLocation() {
        return LOCATION;
    }

    public void nuke(World world, BlockPos pos, int lifetime) {
        CompoundNBT strike = new CompoundNBT();
        strike.put("position", NBTUtil.writeBlockPos(pos));
        strike.putLong("time", world.getGameTime());
        strike.putInt("lifetime", lifetime);
        strike.putInt("radius", NuclearCapability.getExplosionRadius(lifetime));
        strikes.add(strike);
        strikeCount++;
    }

    @Override
    public void packetWrite(PacketBuffer buf) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("strikes", this.strikes);
        nbt.putInt("strikeCount", this.strikeCount);
        buf.writeCompoundTag(nbt);
    }

    @Override
    public void packetRead(PacketBuffer buf) {
        CompoundNBT nbt = buf.readCompoundTag();
        this.strikes = nbt.getList("strikes", Constants.NBT.TAG_COMPOUND);
        this.strikeCount = nbt.getInt("strikeCount");
    }

    @Override
    public void packetHandle() {
        World w = Minecraft.getInstance().world;
        if (w != null) {
            w.getCapability(Provider.DEFAULT_CAPABILITY).ifPresent(n -> {
                n.strikes = this.strikes.copy();
            });
        }
    }

    public static int getExplosionLifetime(int uranium, boolean missile) {
        return (missile ? 15 : 10) + (int)Math.ceil((uranium / 9F) * 15);
    }

    public static int getExplosionRadius(int tick) {
        return tick * 4;
    }

    public static int getFuse(int uranium) {
        return 450 + (int)Math.ceil(150 * (uranium / 9F));
    }

    public static class Storage implements Capability.IStorage<NuclearCapability> {

        @Override
        public INBT writeNBT(Capability<NuclearCapability> capability, NuclearCapability instance, Direction side) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.put("strikes", instance.strikes);
            nbt.putInt("strikeCount", instance.strikeCount);
            return nbt;
        }

        @Override
        public void readNBT(Capability<NuclearCapability> capability, NuclearCapability instance, Direction side, INBT nbt) {
            CompoundNBT nbtc = (CompoundNBT)nbt;
            instance.strikes = nbtc.getList("strikes", Constants.NBT.TAG_COMPOUND);
            instance.strikeCount = nbtc.getInt("strikeCount");
        }

    }

    public static class Provider implements ICapabilitySerializable<CompoundNBT> {

        @CapabilityInject(NuclearCapability.class)
        public static final Capability<NuclearCapability> DEFAULT_CAPABILITY = null;

        private NuclearCapability instance = DEFAULT_CAPABILITY.getDefaultInstance();

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
