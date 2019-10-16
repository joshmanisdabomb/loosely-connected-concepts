package com.joshmanisdabomb.lcc.computing;

import com.joshmanisdabomb.lcc.computing.system.BIOSOperatingSystem;
import com.joshmanisdabomb.lcc.computing.system.OperatingSystem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

import java.util.List;

public class ComputingSession {

    public final ComputingModule computer;

    public ComputingSession(ComputingModule module) {
        this.computer = module;
    }

}
