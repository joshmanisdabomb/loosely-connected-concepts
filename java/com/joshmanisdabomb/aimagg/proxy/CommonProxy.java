package com.joshmanisdabomb.aimagg.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface CommonProxy {

	public void preInitBeforeCommon(FMLPreInitializationEvent event);

	public void preInitAfterCommon(FMLPreInitializationEvent event);

	public void initBeforeCommon(FMLInitializationEvent event);

	public void initAfterCommon(FMLInitializationEvent event);

	public void postInitBeforeCommon(FMLPostInitializationEvent event);

	public void postInitAfterCommon(FMLPostInitializationEvent event);
	
}
