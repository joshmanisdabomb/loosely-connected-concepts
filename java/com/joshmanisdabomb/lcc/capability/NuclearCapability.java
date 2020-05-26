package com.joshmanisdabomb.lcc.capability;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.functionality.NuclearFunctionality;
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
    public float score = 0;

    @Override
    public ResourceLocation getLocation() {
        return LOCATION;
    }

    public void nuke(World world, BlockPos pos, int lifetime) {
        int radius = (int) NuclearFunctionality.getExplosionRadius(lifetime);

        CompoundNBT strike = new CompoundNBT();
        strike.put("position", NBTUtil.writeBlockPos(pos));
        strike.putLong("time", world.getGameTime());
        strike.putInt("lifetime", lifetime);
        strike.putInt("radius", radius);
        strikes.add(strike);

        score += (score * 0.1F) + 30 + lifetime;
    }

    public ListNBT getStrikes() {
        return strikes;
    }

    @Override
    public void packetWrite(PacketBuffer buf) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("strikes", this.strikes);
        nbt.putFloat("score", this.score);
        buf.writeCompoundTag(nbt);
    }

    @Override
    public void packetRead(PacketBuffer buf) {
        CompoundNBT nbt = buf.readCompoundTag();
        this.strikes = nbt.getList("strikes", Constants.NBT.TAG_COMPOUND);
        this.score = nbt.getFloat("score");
    }

    @Override
    public void packetHandle() {
        World w = LCC.proxy.getClientWorld();
        if (w != null) {
            w.getCapability(Provider.DEFAULT_CAPABILITY).ifPresent(n -> {
                int prevLevel = NuclearFunctionality.getWinterLevel(n.score);
                n.strikes = this.strikes.copy();
                n.score = this.score;
                if (NuclearFunctionality.getWinterLevel(this.score) != prevLevel) {
                    LCC.proxy.refreshWorld();
                }
            });
        }
    }

    public static class Storage implements Capability.IStorage<NuclearCapability> {

        @Override
        public INBT writeNBT(Capability<NuclearCapability> capability, NuclearCapability instance, Direction side) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.put("strikes", instance.strikes);
            nbt.putFloat("score", instance.score);
            return nbt;
        }

        @Override
        public void readNBT(Capability<NuclearCapability> capability, NuclearCapability instance, Direction side, INBT nbt) {
            CompoundNBT nbtc = (CompoundNBT)nbt;
            instance.strikes = nbtc.getList("strikes", Constants.NBT.TAG_COMPOUND);
            instance.score = nbtc.getFloat("score");
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
