package com.joshmanisdabomb.aimagg;

import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHandler;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityPills;
import com.joshmanisdabomb.aimagg.event.AimaggChunkManager;
import com.joshmanisdabomb.aimagg.event.AimaggEventHandler;
import com.joshmanisdabomb.aimagg.event.AimaggRegistry;
import com.joshmanisdabomb.aimagg.gen.AimaggWorldGen;
import com.joshmanisdabomb.aimagg.gui.AimaggGUIHandler;
import com.joshmanisdabomb.aimagg.gui.AimaggOverlayHandler;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketHandler;
import com.joshmanisdabomb.aimagg.proxy.CommonProxy;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
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
	
	public static final AimaggTab tab = new AimaggTab("aimagg:tab");
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInitBeforeCommon(event);
		
		AimaggItems.init();
		AimaggBlocks.init();
		
		AimaggEntities.init();

		AimaggTileEntities.init();
		
		AimaggBiome.init();
		AimaggDimension.init();
		
		MinecraftForge.EVENT_BUS.register(new AimaggRegistry());
		
        AimaggPacketHandler.registerMessages(Constants.MOD_ID);
		
		tab.setStackIcon(new ItemStack(AimaggItems.testItem, 1));
		
		proxy.preInitAfterCommon(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.initBeforeCommon(event);
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new AimaggGUIHandler());
		
		CapabilityManager.INSTANCE.register(AimaggCapabilityHearts.IHearts.class, new AimaggCapabilityHearts(), AimaggCapabilityHearts.Hearts.class);
		CapabilityManager.INSTANCE.register(AimaggCapabilityPills.IPills.class, new AimaggCapabilityPills(), AimaggCapabilityPills.Pills.class);
		MinecraftForge.EVENT_BUS.register(new AimaggCapabilityHandler());
		
		MinecraftForge.EVENT_BUS.register(new AimaggEventHandler());
		ForgeChunkManager.setForcedChunkLoadingCallback(instance, new AimaggChunkManager());
		
		GameRegistry.registerWorldGenerator(new AimaggWorldGen(), 0);
		
		proxy.initAfterCommon(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInitBeforeCommon(event);
		
		MinecraftForge.EVENT_BUS.register(new AimaggOverlayHandler());
		
		AimaggTab.AimaggTabSorting.sortItems();
		
		proxy.postInitAfterCommon(event);
	}
	
}
