package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.fluid.OilFluid;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.ArrayList;

public abstract class LCCFluids {
	
	public static final ArrayList<Fluid> all = new ArrayList<>();

	//Wasteland
	public static FlowingFluid oil;
	public static FlowingFluid flowing_oil;
	public static final ForgeFlowingFluid.Properties OIL_PROPERTIES = new ForgeFlowingFluid.Properties(() -> oil, () -> flowing_oil, FluidAttributes.builder(new ResourceLocation(LCC.MODID, "block/wasteland/oil_still"), new ResourceLocation(LCC.MODID, "block/wasteland/oil_flow")).color(0xFFFFFFFF)).slopeFindDistance(1).levelDecreasePerBlock(7).block(() -> LCCBlocks.oil).bucket(() -> LCCItems.oil_bucket);

	public static void init(Register<Fluid> e) {
		all.add((oil = new OilFluid(true, OIL_PROPERTIES)).setRegistryName(LCC.MODID, "oil"));
		all.add((flowing_oil = new OilFluid(false, OIL_PROPERTIES)).setRegistryName(LCC.MODID, "flowing_oil"));
    }
	
}