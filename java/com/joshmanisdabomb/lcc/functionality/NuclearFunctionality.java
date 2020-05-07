package com.joshmanisdabomb.lcc.functionality;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

import java.util.Collection;
import java.util.stream.Collectors;

public abstract class NuclearFunctionality {

    public static Collection<CompoundNBT> getActiveStrikes(ListNBT strikes, long gameTime) {
        return strikes.stream().map(n -> (CompoundNBT)n).filter(s -> s.getInt("time") + s.getInt("lifetime") >= gameTime).collect(Collectors.toList());
    }

    public static int getExplosionLifetime(int uranium, boolean missile) {
        return (missile ? 15 : 10) + (int)Math.ceil((uranium / 9F) * 15);
    }

    public static float getExplosionRadius(float tick) {
        return tick * 2;
    }

    public static int getFuse(int uranium) {
        return 450 + (int)Math.ceil(150 * (uranium / 9F));
    }

    public static int getWinterLevel(float score) {
        return (int)Math.floor(score / 100);
    }

    public static int getLightLevel(float score) {
        return (int)Math.floor((1 - Math.min(score / 500, 1)) * 15);
    }

}
