package com.joshmanisdabomb.aimagg;

import com.joshmanisdabomb.aimagg.proxy.CommonProxy;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Constants.MOD_ID, name = Constants.NAME, version = Constants.VERSION, acceptedMinecraftVersions = Constants.MCVER)
public class AimlessAgglomeration {

	@Instance
	public static AimlessAgglomeration instance;
	
	@SidedProxy(clientSide = Constants.CLIENT_PROXY_CLASS, serverSide = Constants.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static final AimaggTab tab = new AimaggTab("aimaggTab");
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		AimaggItems.init();
		AimaggItems.register();
		AimaggBlocks.init();
		AimaggBlocks.register();
		
		tab.setItemIcon(AimaggBlocks.testBlock);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
}
