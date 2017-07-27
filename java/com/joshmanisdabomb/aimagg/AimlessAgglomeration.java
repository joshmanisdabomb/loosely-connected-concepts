package com.joshmanisdabomb.aimagg;

import com.joshmanisdabomb.aimagg.event.AimaggChunkManager;
import com.joshmanisdabomb.aimagg.event.AimaggEventHandler;
import com.joshmanisdabomb.aimagg.event.AimaggRegistry;
import com.joshmanisdabomb.aimagg.gui.AimaggGUIHandler;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketHandler;
import com.joshmanisdabomb.aimagg.proxy.CommonProxy;
import com.joshmanisdabomb.aimagg.world.AimaggWorldGen;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Constants.MOD_ID, name = Constants.NAME, version = Constants.VERSION, acceptedMinecraftVersions = Constants.MCVER)
public class AimlessAgglomeration {

	@Instance
	public static AimlessAgglomeration instance;
	
	@SidedProxy(clientSide = Constants.CLIENT_PROXY_CLASS, serverSide = Constants.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static final AimaggTab tab = new AimaggTab("aimagg_tab");
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		AimaggItems.init();
		AimaggBlocks.init();
		
		AimaggEntities.init();

		AimaggTEs.init();
		
		MinecraftForge.EVENT_BUS.register(new AimaggRegistry());
		
		tab.setStackIcon(new ItemStack(AimaggItems.testItem, 1));
		
		proxy.preInit();
        AimaggPacketHandler.registerMessages(Constants.MOD_ID);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new AimaggGUIHandler());
		
		MinecraftForge.EVENT_BUS.register(new AimaggEventHandler());
		ForgeChunkManager.setForcedChunkLoadingCallback(instance, new AimaggChunkManager());
		
		GameRegistry.registerWorldGenerator(new AimaggWorldGen(), 0);
		
		proxy.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
}
