package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.ArrayList;

public abstract class LCCFluids {
	
	public static final ArrayList<Fluid> all = new ArrayList<>();

	//Wasteland
	public static FlowingFluid oil;
	public static FlowingFluid oil_flowing;
	public static final ForgeFlowingFluid.Properties OIL = new ForgeFlowingFluid.Properties(() -> oil, () -> oil_flowing, FluidAttributes.builder(new ResourceLocation(LCC.MODID, "block/wasteland/oil_still"), new ResourceLocation(LCC.MODID, "block/wasteland/oil_flow")).color(0xFFFFFFFF)).slopeFindDistance(1).levelDecreasePerBlock(7).block(() -> LCCBlocks.oil).bucket(() -> LCCItems.oil_bucket);

	public static void init(Register<Fluid> e) {
		all.add((oil = new ForgeFlowingFluid.Source(OIL) {
			@Override
			public int getTickRate(IWorldReader world) {return 20;}
		}).setRegistryName(LCC.MODID, "oil"));
		all.add((oil_flowing = new ForgeFlowingFluid.Flowing(OIL) {
			@Override
			public int getTickRate(IWorldReader world) {return 20;}
		}).setRegistryName(LCC.MODID, "oil_flowing"));
    }
	
}